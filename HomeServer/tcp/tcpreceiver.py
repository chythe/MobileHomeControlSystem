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
        from tcp.tcpserver import tcp_server
        while not self.__stopped:
            data = self.receive()
            if data:
                print("TCPReceiver: " + data + "\n")
                command = JobCommand(command_type=SwitchCommandType.ACK, arguments=[data])
                tcp_server.connected_modules_dict.get(self.__ip_address).service.add_command(command)
        self.__socket.close()

    def receive(self):
        ready = select.select([self.__socket], [], [], self.TIMEOUT_SEC)
        if ready[0]:
            data = self.__socket.recv(self.RECEIVE_BUFFER_SIZE)
            return data.decode('utf-8')
        return ""
