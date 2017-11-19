from tcp.tcpserver import tcp_server
from enums.switchcommdtype import SwitchCommandType
from job.jobcommand import JobCommand


class SwitchService(object):

    def __init__(self):
        # For future use
        pass

    def switch(self, module_id, switch_no, state):
        tcp_server.services_map.get(module_id).add_command(
            JobCommand(SwitchCommandType.SWITCH, [str(switch_no), str(state)]))

    def get_state(self, module_id, switch_no):
        # TODO
        pass
