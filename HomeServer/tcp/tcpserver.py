from threading import Thread
import socket
from .tcpservice import TCPService


class TCPServer(Thread):

    TCP_IP = ''
    TCP_PORT = 8888

    def __init__(self):
        super(TCPServer, self).__init__()
        self.stopped = False
        self.services_map = {}

    def run(self):
        while not self.stopped:
            s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            s.bind((self.TCP_IP, self.TCP_PORT))
            print('Server address: ', s.getsockname())
            s.listen(1)
            connection, address = s.accept()
            print('Connection address: ', address)
            id_module = len(self.services_map)
            self.services_map[id_module] = TCPService(connection, id_module)
            self.services_map.get(id_module).start()


tcp_server = TCPServer()
