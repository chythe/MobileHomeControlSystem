import time

from rest.commands.switchcmd import SwitchCommand
from rest.controllers.modulecontroller import module_service
from tcp.tcpserver import tcp_server
from enums.switchcommdtype import SwitchCommandType
from job.jobcommand import JobCommand
from tcp.tcpservice import TCPService


class SwitchService(object):

    def __init__(self):
        # For future use
        pass

    def get_states(self):
        states = []
        tcp_connections = tcp_server.connected_modules_dict
        for addr, conn in tcp_connections.items():
            for k, s in conn.states.items():
                module = module_service.read_module_by_ip(addr)
                if module:
                    states.append(SwitchCommand(module.module_id, k, s))
        return states

    def switch(self, module_id, switch_no, state):
        module = module_service.read_module(module_id)
        connected_modules_dict = tcp_server.connected_modules_dict
        tcp_connection = connected_modules_dict.get(module.ip_address.strip())
        tcp_connection.service.set_job_state_flag(TCPService.STATE_FLAGS['SWITCHING'], True)
        tcp_connection.service.add_command(JobCommand(SwitchCommandType.SWITCH, [str(switch_no), str(state)]))
        # while tcp_connection.service.get_job_state_flag(TCPService.STATE_FLAGS['SWITCHING']):
        #     time.sleep(.100)
        for i in range(0, 5):
            if tcp_connection.states[switch_no] == state:
                return True
            time.sleep(.100)
        return False

