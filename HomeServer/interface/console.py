from threading import Thread
from tcp.tcpserver import tcp_server
from job.jobcommand import JobCommand


class Console(Thread):

    def __init__(self):
        super(Console, self).__init__()
        self.__stopped = False

    def run(self):
        while not self.__stopped:
            console_input = input()
            command = JobCommand.parse_command(console_input)
            tcp_server.connected_modules_dict.get('192.168.0.55').service.add_command(command)


console = Console()
