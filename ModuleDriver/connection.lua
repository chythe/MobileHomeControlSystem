local connected = false
local connection = nil

local SERVER_IP = "192.168.0.51"
local SERVER_PORT = 8888

local SSID = 'WiFi'
local PASSWORD = '12345678'

local function on_receive(socket, buffer)
    local switch = false
    print('on_receive ' .. buffer)
    for i = 0, 5, 1 do
        if buffer == ('on ' .. tostring(i)) then
            gpio.write(i, gpio.HIGH)
            switch = true
        elseif buffer == ('off ' .. tostring(i)) then
            gpio.write(i, gpio.LOW)
            switch = true
        end
    end
    if switch == false then
        if buffer == 'get' then
            local states = "states"
            for i = 0, 5, 1 do
                states = states .. "  ".. tostring(gpio.read(i))
            end
            socket:send(states)
        end
    else
        socket:send(buffer)
    end
end

local function on_connection(socket, buffer)
    tmr.stop(2)
    connected = true
    local states = "states"
    for i = 0, 5, 1 do
        states = states .. " " .. tostring(gpio.read(i))
    end
    socket:send(states)
    global_socket = socket
end

function switch(switch_no)
    tmr.delay(100000)
    print('switch ' .. tostring(switch_no))
    if gpio.read(switch_no) == 0 then
        gpio.write(switch_no, gpio.HIGH)
        if connected == true then
            connection:send("on " .. tostring(switch_no))
        end
    else
        gpio.write(switch_no, gpio.LOW)
        if connected == true then
            connection:send("off " .. tostring(switch_no))
        end
    end
end

function init_wifi()
    wifi.sta.autoconnect(1)
    wifi.setmode(wifi.STATION)
    wifi.sta.config(SSID, PASSWORD)
    print(wifi.sta.getip())
end

function init_connection()
    tmr.alarm(2, 1500, 1, function()
        if wifi.sta.getip() then
            connection = net.createConnection(net.TCP, 0)
            connection:on("receive", on_receive)
            connection:on("connection", on_connection)
            connection:connect(SERVER_PORT, SERVER_IP)
            print("server connecting...")
        end
    end)
end