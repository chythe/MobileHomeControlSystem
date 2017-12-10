-----------------------------variables-----------------------

local connected = false
local connection = nil

local SSID = 'WiFi'
local PASSWORD = '12345678'
local SERVER_IP = "192.168.0.51"
local SERVER_PORT = 8888

----------------------------interrupts-----------------------

local function int0_handle(level, stamp)
    print('int0_handle')
    --print(level)
    --print(stamp)
    tmr.delay(100000)
    if gpio.read(0) == 0 then
        gpio.write(0, gpio.HIGH)
        if connected == true then
            connection:send("on0")
        end
    else
        gpio.write(0, gpio.LOW)
        if connected == true then
            connection:send("off0")
        end
    end
end

local function int1_handle(level, stamp)
    print('int1_handle')
    --print(level)
    --print(stamp)
    tmr.delay(100000)
    if gpio.read(1) == 0 then
        gpio.write(1, gpio.HIGH)
        if connected == true then
            connection:send("on1")
        end
    else
        gpio.write(1, gpio.LOW)
        if connected == true then
            connection:send("off1")
        end
    end
end

-----------------------------tools---------------------------

local function count_lrc(buffer)
    local lrc = 0x00
    for i = 1, #buffer do
        local byte = str:sub(i,i)
        lrc = bit.bxor(lrc, byte)
    end
    return lrc
end

------------------------connection handlers------------------

local function on_receive(socket, buffer)
    print(buffer)
    if buffer == 'on0' then gpio.write(0, gpio.HIGH) end
    if buffer == 'off0' then gpio.write(0, gpio.LOW) end
    if buffer == 'on1' then gpio.write(1, gpio.HIGH) end
    if buffer == 'off1' then gpio.write(1, gpio.LOW) end
end

local function on_connection(socket, buffer)
    tmr.stop(2)
    connected = true
    socket:send("NodeMCU connected")
    print('NodeMCU connected')
    global_socket = socket
    --count_lrc(buffer)
    --print(lrc)
end

---------------------------initialisation--------------------

local function init_io()
    gpio.mode(0, gpio.OUTPUT)
    gpio.mode(1, gpio.OUTPUT)
    gpio.mode(6, gpio.INT, gpio.PULLUP)
    gpio.mode(7, gpio.INT, gpio.PULLUP)
    gpio.write(0, gpio.LOW)
    gpio.write(1, gpio.LOW)
    gpio.trig(6, "both", int0_handle)
    gpio.trig(7, "both", int1_handle)
end

local function init_wifi()
    --wifi.sta.disconnect()
    wifi.setmode(wifi.STATION)
    wifi.sta.autoconnect(0)
    tmr.alarm(1, 1000, 1, function()
        if not wifi.sta.getip() then
            wifi.sta.config(SSID, PASSWORD)
            wifi.sta.connect()
            print('wifi connecting...')
        else
            tmr.stop(1)
            print(wifi.sta.getip())
        end
    end)
end

local function init_connection()
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

----------------------------------------------------------

init_io()
init_wifi()
init_connection()

----------------------------------------------------------
