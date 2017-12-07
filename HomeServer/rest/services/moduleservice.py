from dao.moduledao import ModuleDao


class ModuleService(object):

    def __init__(self):
        self.__module_dao = ModuleDao()

    def create_module(self, name, ip_address):
        return self.__module_dao.create_module(name, ip_address)

    def read_modules(self):
        return self.__module_dao.read_modules()

    def read_module(self, module_id):
        return self.__module_dao.read_module(module_id)

    def update_module(self, module_id, name, ip_address):
        return self.__module_dao.update_module(module_id, name, ip_address)

    def delete_module(self, module_id):
        self.__module_dao.delete_module(module_id)
