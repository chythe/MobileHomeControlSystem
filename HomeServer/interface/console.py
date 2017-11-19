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
            tcp_server.services_map.get(0).add_command(command)


console = Console()
