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
            data = self.receive()
            if data:
                print("TCPReceiver: " + data)
                done = self.set_state(data)
                if not done:
                    self.redirect_data_to_service(data)
        self.__socket.close()

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

    def set_state(self, data):
        from tcp.tcpserver import tcp_server
        params = data.split(" ")
        if len(params) == 2:
            try:
                state = False
                switch_no = int(params[1])
                if params[0] == 'on': state = True
                elif params[0] == 'off': state = False
                tcp_server.connected_modules_dict.get(self.__ip_address).states[switch_no] = state
                return True
            except ValueError:
                print('bad state switch command')
                return False
        return False

