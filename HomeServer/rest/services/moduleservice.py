from dao.moduledao import ModuleDao
from model.module import Module
from rest.command.switchcmd import SwitchCommand
from rest.command.unknownmodcmd import UnknownModuleCommand
from tcp.tcpserver import tcp_server


class ModuleService(object):

    def __init__(self):
        self.__module_dao = ModuleDao()

    def create_module(self, name, ip_address):
        return self.__module_dao.create_module(name, ip_address)

    def read_modules(self):
        return self.__module_dao.read_modules()

    def read_module(self, module_id):
        return self.__module_dao.read_module(module_id)

    def read_module_by_ip(self, ip_address):
        return self.__module_dao.read_module_by_ip(ip_address)

    def update_module(self, module_id, name, ip_address):
        return self.__module_dao.update_module(module_id, name, ip_address)

    def delete_module(self, module_id):
        self.__module_dao.delete_module(module_id)

    def search_unknown_modules(self):
        database_modules = self.__module_dao.read_modules()
        connected_modules = tcp_server.connected_modules_dict
        connected_unknown_modules_ips = []
        for k, cm in connected_modules.items():
            known = False
            for dm in database_modules:
                if dm.ip_address.strip() == k:
                    known = True
            if not known:
                connected_unknown_modules_ips.append(k)
        connected_unknown_modules = [UnknownModuleCommand(ip_address=ip) for ip in connected_unknown_modules_ips]
        return connected_unknown_modules
