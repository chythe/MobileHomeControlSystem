import select
import socket
from threading import Thread
from enums.tcpcommandtype import TCPCommandType
from job.jobcommand import JobCommand
from tcp.tcpconst import *


class TCPReceiver(Thread):

    def __init__(self, connected_socket, ip_address):
        super(TCPReceiver, self).__init__()
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
                data = self.receive()
                if data:
                    print("TCPReceiver: " + data)
                    self.parse_response(data)
            except ConnectionResetError:
                self.inform_service_error()
                self.__stopped = True
        self.__socket.shutdown(socket.SHUT_RDWR)
        self.__socket.close()

    def inform_service_error(self):
        from tcp.tcpserver import tcp_server
        connection = tcp_server\
            .connected_modules_dict.get(self.__ip_address)
        command = JobCommand(command_type=TCPCommandType.ERROR)
        connection.service.add_command(command)

    def receive(self):
        ready = select.select([self.__socket], [], [], TIMEOUT_SEC)
        if ready[0]:
            data = self.__socket.recv(RECEIVE_BUFFER_SIZE)
            return data.decode('utf-8')
        return ""

    def redirect_data_to_service(self, data):
        from tcp.tcpserver import tcp_server
        command = JobCommand(
            command_type=TCPCommandType.ACK, arguments=[data])
        tcp_server.connected_modules_dict.get(
            self.__ip_address).service.add_command(command)

    def parse_response(self, data):
        params = data.split(" ")
        if params[TCP_CMD_ARG_TYPE] == SWITCH_TCP_COMMAND:
            self.set_all_states(params)
        elif params[TCP_CMD_ARG_TYPE] == ON_TCP_COMMAND:
            self.set_one_state(params, True)
        elif params[TCP_CMD_ARG_TYPE] == OFF_TCP_COMMAND:
            self.set_one_state(params, False)

    def set_all_states(self, params):
        from tcp.tcpserver import tcp_server
        if len(params) == STATES_TCP_CMD_ARGS_COUNT:
            try:
                for i in range(0, SWITCH_COUNT):
                    state = bool(int(params[i + 1]))
                    tcp_server.connected_modules_dict\
                        .get(self.__ip_address).states[i] = state
            except ValueError:
                print('bad switch all states response')

    def set_one_state(self, params, state):
        from tcp.tcpserver import tcp_server
        if len(params) == ON_OFF_TCP_CMD_ARGS_COUNT:
            try:
                switch_no = int(params[ON_OFF_TCP_CMD_ARG_STATE])
                tcp_server.connected_modules_dict.get(
                    self.__ip_address).states[switch_no] = state
            except ValueError:
                print('bad switch one state response')
