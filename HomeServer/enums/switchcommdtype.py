from enum import Enum


class SwitchCommandType(Enum):

    SWITCH = 'SWITCH'
    GET_STATES = 'GET_STATES'
    ACK = 'ACK'
    ERROR = 'ERROR'


