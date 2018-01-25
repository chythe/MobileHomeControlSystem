
# useful at parse ip address
IP_ADDRESS_REGEX = '(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)' \
                   '(\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}'

RECEIVE_BUFFER_SIZE = 20
TIMEOUT_SEC = 1

SWITCH_COUNT = 6  # number of switches in one module

# tcp commands
SWITCH_TCP_COMMAND = 'switch'
ON_TCP_COMMAND = 'on'
OFF_TCP_COMMAND = 'off'
GET_TCP_COMMAND = 'get'

STATES_TCP_CMD_ARGS_COUNT = 7  # number of arguments in states tcp response command (type is also an argument)
ON_OFF_TCP_CMD_ARGS_COUNT = 2  # number of arguments in switch on / off tcp command (type is also an argument)
ON_OFF_TCP_CMD_ARG_STATE = 1  # position state in switch on / off tcp command
TCP_CMD_ARG_TYPE = 0  # position type of command in tcp command

SWITCH_JOB_CMD_ARGS_COUNT = 2  # number of arguments in switch job command (type isn't an argument)
SWITCH_JOB_CMD_ARG_SWITCH_NO = 0  # position switch no in switch job command
SWITCH_JOB_CMD_ARG_STATE = 1  # position state in switch job command

# states flags of job tcp service
TCP_SERVICE_STATE_FLAGS = {
    'FREE': 0x0000,
    'SWITCHING': 0x0001,
    'GETTING_STATES': 0x0002
}