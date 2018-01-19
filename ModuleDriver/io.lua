
function set_state(switch_no, state)
    if state == true then
        gpio.write(switch_no, gpio.HIGH)
    else
        gpio.write(switch_no, gpio.LOW)
    end
end

local function switch(switch_no)
    require("connection")
    if gpio.read(switch_no) == 0 then
        gpio.write(switch_no, gpio.HIGH)
        send_switch_info(switch_no, true)
    else
        gpio.write(switch_no, gpio.LOW)
        send_switch_info(switch_no, false)
    end
end

local function int0_handle(level, stamp)
    switch(0)
end

local function int1_handle(level, stamp)
    switch(1)
end

local function int2_handle(level, stamp)
    switch(2)
end

local function int3_handle(level, stamp)
    switch(3)
end

local function int4_handle(level, stamp)
    switch(4)
end

local function int5_handle(level, stamp)
    switch(5)
end

function init_io()
    gpio.mode(0, gpio.OUTPUT)
    gpio.mode(1, gpio.OUTPUT)
    --gpio.mode(2, gpio.OUTPUT)
    --gpio.mode(3, gpio.OUTPUT)
    --gpio.mode(4, gpio.OUTPUT)
    --gpio.mode(5, gpio.OUTPUT)
    gpio.mode(6, gpio.INT, gpio.PULLUP)
    gpio.mode(7, gpio.INT, gpio.PULLUP)
    --gpio.mode(8, gpio.INT, gpio.PULLUP)
    --gpio.mode(9, gpio.INT, gpio.PULLUP)
    --gpio.mode(10, gpio.INT, gpio.PULLUP)
    --gpio.mode(11, gpio.INT, gpio.PULLUP)
    gpio.write(0, gpio.LOW)
    gpio.write(1, gpio.LOW)
    --gpio.write(2, gpio.LOW)
    --gpio.write(3, gpio.LOW)
    --gpio.write(4, gpio.LOW)
    --gpio.write(5, gpio.LOW)
    gpio.trig(6, "both", int0_handle)
    gpio.trig(7, "both", int1_handle)
    --gpio.trig(8, "both", int2_handle)
    --gpio.trig(9, "both", int3_handle)
    --gpio.trig(10, "both", int4_handle)
    --gpio.trig(11, "both", int5_handle)
end
