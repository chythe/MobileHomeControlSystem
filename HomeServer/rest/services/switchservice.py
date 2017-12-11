from rest.command.switchcmd import SwitchCommand
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
            conn.service.add_command(JobCommand(SwitchCommandType.GET_STATES))
            while conn.service.get_job_state_flag(TCPService.STATE_FLAGS['GETTING_STATES']):
                pass
            if conn.service.execute_result:
                for k, s in conn.states.items():
                    states.append(SwitchCommand(addr, k, s))
        return states

    def switch(self, ip_address, switch_no, state):
        tcp_service = tcp_server.connected_modules_dict.get(ip_address.strip()).service
        tcp_service.add_command(JobCommand(SwitchCommandType.SWITCH, [str(switch_no), str(state)]))
        while tcp_service.get_job_state_flag(TCPService.STATE_FLAGS['SWITCHING']):
            pass
        return tcp_service.execute_result

