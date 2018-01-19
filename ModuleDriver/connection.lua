
local connected = false
local connection = nil
local timer = nil

local SERVER_IP = "192.168.0.51"
local SERVER_PORT = 8888

local SSID = 'WiFi'
local PASSWORD = '12345678'

local function on_receive(socket, buffer)
    require("io")
    local switch = false
    print('on_receive ' .. buffer)
    for i = 0, 5, 1 do
        if buffer == ('on ' .. tostring(i)) then
            set_state(i, true)
            switch = true
        elseif buffer == ('off ' .. tostring(i)) then
            set_state(i, false)
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
    tmr.stop(1)
    connected = true
    local states = "states"
    for i = 0, 5, 1 do
        states = states .. " " .. tostring(gpio.read(i))
    end
    socket:send(states)
    print(states)
    global_socket = socket
end

function send_switch_info(switch_no, state)
    print('switch ' .. tostring(switch_no))
    if connected == true then
        if state == true then
            connection:send("on " .. tostring(switch_no))
        else
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
    tmr.alarm(1, 1000 * 60, 1, function()
        if wifi.sta.getip() then
            if connected == true then
                connection:close()
                connected = false
            end
            connection = net.createConnection(net.TCP, 0)
            connection:on("receive", on_receive)
            connection:on("connection", on_connection)
            connection:connect(SERVER_PORT, SERVER_IP)
            print("server connecting...")
        end
    end)
end
