import re
from threading import Thread
import socket
from tcp.tcpreceiver import TCPReceiver
from .tcpservice import TCPService
from tcp.tcpconst import *


class TCPServer(Thread):

    TCP_IP = ''  # localhost
    TCP_PORT = 8888

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
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.bind((self.TCP_IP, self.TCP_PORT))
        print('Server address: ', s.getsockname())
        while not self.stopped:
            s.listen(1)
            connected_socket, address = s.accept()
            ip_address = self.parse_ip_address(address)
            if ip_address:
                self.check_presence_connection(ip_address)
                self.start_module_connection(connected_socket, ip_address)

    def parse_ip_address(self, address):
        found = re.search(IP_ADDRESS_REGEX, str(address))
        if found:
            return found.group()
        else:
            return None

    def start_module_connection(self, connected_socket, ip_address):
        connected_socket.setblocking(0)
        print('Connection address: ', ip_address)
        tcp_service = TCPService(connected_socket, ip_address)
        tcp_receiver = TCPReceiver(connected_socket, ip_address)
        self.connected_modules_dict[ip_address] = \
            TCPServer.ModuleConnection(tcp_service, tcp_receiver, ip_address)
        tcp_service.start()
        tcp_receiver.start()

    def check_presence_connection(self, ip_address):
        try:
            connection = self.connected_modules_dict[ip_address]
            if connection:
                connection.receiver.stopped = True
                connection.service.stopped = True
                del connection
        except KeyError:
            pass


tcp_server = TCPServer()
