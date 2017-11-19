from config.databaseconfig import *


class ModuleConfigurationDao(object):

    @db_session
    def create_module_configuration(self, module_id, switch_no, room_id, switch_type_id, name):
        ModuleConfiguration(
            mod=Module[module_id], switch_no=switch_no, room=Room[room_id],
            switch_type=SwitchType[switch_type_id], name=name)

    @db_session
    def read_module_configurations(self):
        return ModuleConfiguration.select()[:]

    @db_session
    def read_module_configurations_by_module_id(self, module_id):
        return ModuleConfiguration.get(module_id=module_id)[:]

    @db_session
    def read_module_configuration(self, module_id, switch_no):
        return ModuleConfiguration[module_id, switch_no]

    @db_session
    def update_module_configuration(self, module_id, switch_no, room_id, switch_type_id, name):
        mc = ModuleConfiguration[Module[module_id], switch_no]
        mc.room = Room[room_id]
        mc.switch_type = SwitchType[switch_type_id]
        mc.name = name

    @db_session
    def delete_module_configuration(self, module_id, switch_no):
        ModuleConfiguration[module_id, switch_no].delete()
