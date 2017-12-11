from enum import Enum

from job.job import Job
from enums.switchcommdtype import SwitchCommandType
from job.jobcommand import JobCommand


class TCPService(Job):

    STATE_FLAGS = {
        'FREE': 0x0000,
        'SWITCHING': 0x0001,
        'GETTING_STATES': 0x0002
    }

    def __init__(self, socket, ip_address):
        super(TCPService, self).__init__()
        self.__stopped = False
        self.__socket = socket
        self.__ip_address = ip_address

    def run(self):
        while not self.__stopped:
            command = self._receive_command()
            if command:
                self.on_action(command)
        self.__socket.close()

    def on_action(self, command):
        if command and isinstance(command, JobCommand):
            command_type = command.command_type
            if SwitchCommandType.SWITCH == command_type:
                if command.arguments and isinstance(command.arguments, list) and len(command.arguments) == 2:
                    self.switch(command.arguments[0], command.arguments[1])
            elif SwitchCommandType.GET_STATES == command_type:
                self.get_states()

    def receive_ack_command(self):
        while True:
            command = self._receive_command()
            if command:
                return command

    def switch(self, switch_no, state):
        self.execute_result = False
        self.set_job_state_flag(TCPService.STATE_FLAGS['SWITCHING'], True)
        print('switch ' + str(switch_no) + ' ' + str(state))
        if 'True' == state or 'true' == state:
            self.__socket.send(str.encode('on' + str(switch_no)))
        elif 'False' == state or 'false' == state:
            self.__socket.send(str.encode('off' + str(switch_no)))
        command = self.receive_ack_command()
        if SwitchCommandType.ACK == command.command_type:
            self.execute_result = True
        self.set_job_state_flag(TCPService.STATE_FLAGS['SWITCHING'], False)

    def get_states(self):
        from tcp.tcpserver import tcp_server
        self.execute_result = False
        self.set_job_state_flag(TCPService.STATE_FLAGS['GETTING_STATES'], True)
        print('get state')
        self.__socket.send(str.encode('get'))
        command = self.receive_ack_command()
        if SwitchCommandType.ACK == command.command_type:
            if command.arguments[0]:
                states = command.arguments[0].split(" ")
                if len(states) == 6:
                    for i in range(0, 6):
                        tcp_server.connected_modules_dict.get(self.__ip_address).states[i] = bool(int(states[i]))
                    self.execute_result = True
        self.set_job_state_flag(TCPService.STATE_FLAGS['GETTING_STATES'], False)

