from dao.moduleconfdao import ModuleConfigurationDao


class ModuleConfigurationService(object):

    def __init__(self):
        self.__module_configuration_dao = ModuleConfigurationDao()

    def create_module_configuration(self, module_id, switch_no, room_id, switch_type_id, name):
        self.__module_configuration_dao.create_module_configuration(module_id, switch_no, room_id, switch_type_id, name)

    def read_module_configurations(self):
        return self.__module_configuration_dao.read_module_configurations()

    def read_module_configurations_by_module_id(self, module_id):
        return self.__module_configuration_dao.read_module_configurations_by_module_id(module_id)

    def read_module_configuration(self, module_id, switch_no):
        return self.__module_configuration_dao.read_module_configuration(module_id, switch_no)

    def update_module_configuration(self, module_id, switch_no, room_id, switch_type_id, name):
        self.__module_configuration_dao.update_module_configuration(module_id, switch_no, room_id, switch_type_id, name)

    def delete_module_configuration(self, module_id, switch_no):
        self.__module_configuration_dao.delete_module_configuration(module_id, switch_no)
