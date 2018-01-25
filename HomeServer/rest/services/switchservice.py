import time
from rest.commands.switchcmd import SwitchCommand
from rest.controllers.modulecontroller import module_service
from tcp.tcpserver import tcp_server
from enums.tcpcommandtype import TCPCommandType
from job.jobcommand import JobCommand
from tcp.tcpservice import TCPService
from tcp.tcpconst import *


class SwitchService(object):

    WAIT_CHANGE_ATTEMPT_COUNT = 5
    WAIT_CHANGE_TIME = .100

    def __init__(self):
        # For future use
        pass

    def get_states(self):
        states = []  # switch command list represent states
        tcp_connections = tcp_server.connected_modules_dict
        for addr, conn in tcp_connections.items():
            self.send_get_command(conn)
            states = self.append_states(conn, states)
        return states

    def send_get_command(self, tcp_connection):
        tcp_connection.service.set_job_state_flag(
            TCP_SERVICE_STATE_FLAGS['GETTING_STATES'], True)
        tcp_connection.service.add_command(
            JobCommand(TCPCommandType.GET_STATES))
        for i in range(0, self.WAIT_CHANGE_ATTEMPT_COUNT):
            if tcp_connection.service.get_job_state_flag(
                    TCP_SERVICE_STATE_FLAGS['GETTING_STATES']):
                return True
            time.sleep(self.WAIT_CHANGE_TIME)
        return False

    def append_states(self, connection, states):
        module = module_service.read_module_by_ip(connection.ip_address)
        if module:
            for k, s in connection.states.items():
                states.append(SwitchCommand(module.module_id, k, s))
        return states

    def switch(self, module_id, switch_no, state):
        module = module_service.read_module(module_id)
        connected_modules_dict = tcp_server.connected_modules_dict
        connection = connected_modules_dict.get(module.ip_address.strip())
        return self.send_switch_command(connection, switch_no, state)

    def send_switch_command(self, tcp_connection, switch_no, state):
        tcp_connection.service.set_job_state_flag(
            TCP_SERVICE_STATE_FLAGS['SWITCHING'], True)
        tcp_connection.service.add_command(JobCommand(
            TCPCommandType.SWITCH, [switch_no, state]))
        for i in range(0, self.WAIT_CHANGE_ATTEMPT_COUNT):
            if tcp_connection.states[switch_no] == state:
                return True
            time.sleep(self.WAIT_CHANGE_TIME)
        return False
