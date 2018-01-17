import re
from threading import Thread
import socket

from tcp.tcpreceiver import TCPReceiver
from .tcpservice import TCPService


class TCPServer(Thread):

    TCP_IP = ''
    TCP_PORT = 8888

    IP_ADDRESS_REGEX = '(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}'

    class ModuleConnection(object):

        def __init__(self, service, receiver, ip_address):
            self.service = service
            self.receiver = receiver
            self.ip_address = ip_address
            self.states = {
                0: False,
                1: False,
                2: False,
                3: False,
                4: False,
                5: False
            }

    def __init__(self):
        super(TCPServer, self).__init__()
        self.stopped = False
        self.connected_modules_dict = {}

    def run(self):
        while not self.stopped:
            s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            s.bind((self.TCP_IP, self.TCP_PORT))
            print('Server address: ', s.getsockname())
            s.listen(1)
            connected_socket, address = s.accept()
            self.start_module_connection(connected_socket, address)

    def start_module_connection(self, connected_socket, address):
        found = re.search(self.IP_ADDRESS_REGEX, str(address))
        if found:
            ip_address = found.group()
            connected_socket.setblocking(0)
            print('Connection address: ', ip_address)
            tcp_service = TCPService(connected_socket, ip_address)
            tcp_receiver = TCPReceiver(connected_socket, ip_address)
            # if self.connected_modules_dict[ip_address]:
            #     del self.connected_modules_dict[ip_address]
            self.connected_modules_dict[ip_address] = TCPServer.ModuleConnection(tcp_service, tcp_receiver, address)
            self.connected_modules_dict.get(ip_address).service.start()
            self.connected_modules_dict.get(ip_address).receiver.start()


tcp_server = TCPServer()
