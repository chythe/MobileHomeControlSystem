from enum import Enum


class TCPCommandType(Enum):

    SWITCH = 'SWITCH'
    GET_STATES = 'GET_STATES'
    ACK = 'ACK'
    ERROR = 'ERROR'


