from enums.switchcommdtype import SwitchCommandType


class JobCommand(object):

    def __init__(self, command_type, arguments=[]):
        self._command_type = command_type
        self._arguments = arguments

    @property
    def command_type(self):
        return self._command_type

    @property
    def arguments(self):
        return self._arguments

    @command_type.setter
    def command_type(self, command_type):
        self._command_type = command_type

    @arguments.setter
    def arguments(self, arguments):
        self._arguments = arguments

    @staticmethod
    def parse_command(string):
        params = string.split()
        return JobCommand(SwitchCommandType(params[0]), params[1:])
