from exception.moduleconnexcept import ModuleConnectionException
from job.job import Job
from enums.tcpcommandtype import TCPCommandType
from job.jobcommand import JobCommand
from tcp.tcpconst import *


class TCPService(Job):

    def __init__(self, connected_socket, ip_address):
        super(TCPService, self).__init__()
        self.__stopped = False
        self.__socket = connected_socket
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
                if command.arguments and \
                        isinstance(command.arguments, list) and \
                        len(command.arguments) == SWITCH_JOB_CMD_ARGS_COUNT:
                    self.switch(command.arguments[SWITCH_JOB_CMD_ARG_SWITCH_NO],
                                command.arguments[SWITCH_JOB_CMD_ARG_STATE])
            elif TCPCommandType.GET_STATES == command_type:
                self.get_states()
            elif TCPCommandType.ERROR == command_type:
                raise ModuleConnectionException

    def error_exit(self):
        from tcp.tcpserver import tcp_server
        connection = tcp_server \
            .connected_modules_dict.get(self.__ip_address)
        del connection

    def switch(self, switch_no, state):
        print('switch ' + str(switch_no) + ' ' + str(state))
        if state:
            self.__socket.send(
                str.encode(ON_TCP_COMMAND + ' ' + str(switch_no)))
        else:
            self.__socket.send(
                str.encode(OFF_TCP_COMMAND + ' ' + str(switch_no)))
        self.set_job_state_flag(
            TCP_SERVICE_STATE_FLAGS['SWITCHING'], False)

    def get_states(self):
        print('get state')
        self.__socket.send(str.encode(GET_TCP_COMMAND))
        self.set_job_state_flag(
            TCP_SERVICE_STATE_FLAGS['GETTING_STATES'], False)
