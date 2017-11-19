from threading import Thread
from tcp.tcpserver import tcp_server
from job.jobcommand import JobCommand
from enums.role import Role


class Console(Thread):

    def __init__(self):
        super(Console, self).__init__()
        self.__stopped = False

    def run(self):
        # self.__module_configuration_dao.create_module_configuration(
        #     module_id=3, switch_no=1, room_id=1, switch_type_id=1, name='Light on the left')
        # self.__switch_type_dao.create_switch_type('Light')
        # self.__module_dao.create_module('Module Kitchen 1')
        # self.__room_dao.update_room(1, 'Kitchen')
        # self.__user_dao.create_user('user1', 'psadfsad', Role.USER)
        # users = self.__user_dao.read_users()
        # result = [u.to_dict() for u in users]
        # print(result)
        # print(self.__user_dao.read_user(2))
        while not self.__stopped:
            console_input = input()
            command = JobCommand.parse_command(console_input)
            # tcp_server.services_map.get(0).add_command(command)


console = Console()
