from job.job import Job
from enums.switchcommdtype import SwitchCommandType
import asyncio


class TCPService(Job):

    def __init__(self, connection):
        super(TCPService, self).__init__()
        self.__stopped = False
        self.__connection = connection

    def run(self):
        while not self.__stopped:
            command = self._receive_command()
            if command:
                self.on_action(command)
        self.__connection.close()

    def on_action(self, command):
        command_type = command.command_type
        if SwitchCommandType.SWITCH.__eq__(command_type):
            self.switch(command.arguments[0], command.arguments[1])

    def switch(self, switch_no, state):
        print('switch ' + str(switch_no) + ' ' + str(state))
        if 'True' == state or 'true' == state:
            self.__connection.send(str.encode('on' + str(switch_no)))
        elif 'False' == state or 'false' == state:
            self.__connection.send(str.encode('off' + str(switch_no)))

