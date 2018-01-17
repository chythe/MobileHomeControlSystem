import select
from threading import Thread

from enums.switchcommdtype import SwitchCommandType
from job.jobcommand import JobCommand


class TCPReceiver(Thread):

    RECEIVE_BUFFER_SIZE = 20
    TIMEOUT_SEC = 1

    def __init__(self, socket, ip_address):
        super(TCPReceiver, self).__init__()
        self.__stopped = False
        self.__socket = socket
        self.__ip_address = ip_address

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
        self.__socket.close()

    def inform_service_error(self):
        from tcp.tcpserver import tcp_server
        connection = tcp_server.connected_modules_dict.get(self.__ip_address)
        command = JobCommand(command_type=SwitchCommandType.ERROR)
        connection.service.add_command(command)

    def receive(self):
        ready = select.select([self.__socket], [], [], self.TIMEOUT_SEC)
        if ready[0]:
            data = self.__socket.recv(self.RECEIVE_BUFFER_SIZE)
            return data.decode('utf-8')
        return ""

    def redirect_data_to_service(self, data):
        from tcp.tcpserver import tcp_server
        command = JobCommand(command_type=SwitchCommandType.ACK, arguments=[data])
        tcp_server.connected_modules_dict.get(self.__ip_address).service.add_command(command)

    def parse_response(self, data):
        params = data.split(" ")
        if params[0] == 'states':
            self.set_all_states(params)
        elif params[0] == 'on':
            self.set_one_state(params, True)
        elif params[0] == 'off':
            self.set_one_state(params, False)

    def set_all_states(self, params):
        from tcp.tcpserver import tcp_server
        if len(params) == 7:
            try:
                for i in range(0, 6):
                    state = bool(int(params[i + 1]))
                    tcp_server.connected_modules_dict.get(self.__ip_address).states[i] = state
            except ValueError:
                print('bad switch all states response')

    def set_one_state(self, params, state):
        from tcp.tcpserver import tcp_server
        if len(params) == 2:
            try:
                switch_no = int(params[1])
                tcp_server.connected_modules_dict.get(self.__ip_address).states[switch_no] = state
            except ValueError:
                print('bad switch one state response')
