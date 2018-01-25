from exception.moduleconnexcept import ModuleConnectionException
from job.job import Job
from enums.tcpcommandtype import TCPCommandType
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

    @property
    def stopped(self):
        return self.__stopped

    @stopped.setter
    def stopped(self, stopped):
        self.__stopped = stopped

    def run(self):
        while not self.__stopped:
            try:
                command = self._receive_command()
                if command:
                    self.on_action(command)
            except ModuleConnectionException:
                self.error_exit()
                self.__stopped = True

    def on_action(self, command):
        if command and isinstance(command, JobCommand):
            command_type = command.command_type
            if TCPCommandType.SWITCH == command_type:
                if command.arguments and isinstance(command.arguments, list) and len(command.arguments) == 2:
                    self.switch(command.arguments[0], command.arguments[1])
            elif TCPCommandType.GET_STATES == command_type:
                self.get_states()
            elif TCPCommandType.ERROR == command_type:
                raise ModuleConnectionException

    def error_exit(self):
        from tcp.tcpserver import tcp_server
        connection = tcp_server.connected_modules_dict.get(self.__ip_address)
        del connection

    def receive_ack_command(self):
        while True:
            command = self._receive_command()
            if command:
                return command

    def switch(self, switch_no, state):
        print('switch ' + str(switch_no) + ' ' + str(state))
        if 'True' == state or 'true' == state:
            self.__socket.send(str.encode('on ' + str(switch_no)))
        elif 'False' == state or 'false' == state:
            self.__socket.send(str.encode('off ' + str(switch_no)))
        self.set_job_state_flag(TCPService.STATE_FLAGS['SWITCHING'], False)

    def get_states(self):
        print('get state')
        self.__socket.send(str.encode('get'))
        self.set_job_state_flag(TCPService.STATE_FLAGS['GETTING_STATES'], False)

