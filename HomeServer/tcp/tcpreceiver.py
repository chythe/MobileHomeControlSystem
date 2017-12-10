from enums.switchcommdtype import SwitchCommandType
from job.job import Job


class TCPReceiver(Job):

    RECEIVE_BUFFER_SIZE = 20

    def __init__(self, connection):
        super(TCPReceiver, self).__init__()
        self.__connection = connection
        self.stopped = False

    def run(self):
        while not self.stopped:
            command = self._receive_command()
            if command:
                self.on_action(command)
        self.__connection.close()

    def receive(self):
        data = self.__connection.recv(self.RECEIVE_BUFFER_SIZE)
        # print("TCPReceiver: " + str(self.__connection.getsockname()) + " : " + str(data) + "\n")
        return data

    def on_action(self, command):
        command_type = command.command_type
        if SwitchCommandType.SWITCH.__eq__(command_type):
            self.receive_switch_ack()
        if SwitchCommandType.GET_STATES.__eq__(command_type):
            self.receive_states()

    def receive_switch_ack(self):
        data = ''
        while data != 'ack':
            data = self.receive()
        print('switch ack')

    def receive_states(self):
        data = ''
        while data == '':
            data = self.receive()
        print('receive_states' + data)
