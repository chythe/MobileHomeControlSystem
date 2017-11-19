from job.job import Job
from enums.switchcommdtype import SwitchCommandType
import asyncio


class TCPService(Job):

    BUFFER_SIZE = 20

    def __init__(self, connection, module_id):
        super(TCPService, self).__init__()
        self.__stopped = False
        self.__module_id = module_id
        # self.command_queue = Queue()
        self.__connection = connection

    @property
    def module_id(self):
        return self.__module_id

    @module_id.setter
    def module_id(self, module_id):
        self.__module_id = module_id

    def run(self):
        while not self.__stopped:
            command = self._receive_command()
            if command:
                self.on_action(command)
            # else:
            #     self.receive()
        self.__connection.close()

    async def receive(self):
        data = self.__connection.recv(self.BUFFER_SIZE)
        print('received data:', data)
        return data

    def on_action(self, command):
        command_type = command.command_type
        if SwitchCommandType.SWITCH == command_type:
            self.switch(command.arguments[0], command.arguments[1])
        if SwitchCommandType.GET_STATE == command_type:
            self.get_state()

    def switch(self, switch_no, state):
        print('switch ' + str(switch_no) + ' ' + str(state))
        if 'True' == state or 'true' == state:
            self.__connection.send(str.encode('on' + str(switch_no)))
        elif 'False' == state or 'false' == state:
            self.__connection.send(str.encode('off' + str(switch_no)))

    def get_state(self):
        print('get_state')
        # self.connection.send(data)
        self.__connection.recv(self.BUFFER_SIZE)
