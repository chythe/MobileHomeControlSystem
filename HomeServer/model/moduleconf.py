from config.databaseconfig import *


class ModuleConfiguration(db.Entity):

    _table_ = "module_configurations"
    mod = Required(Module, column="module_id")
    switch_no = Required(int, size=16)
    room = Required(Room, column="room_id")
    switch_type = Required(SwitchType, column="switch_type_id")
    name = Required(str, unique=True)
    PrimaryKey(mod, switch_no)

    @staticmethod
    def change_dict_keys_names(module_configuration_dict):
        module_configuration_dict['moduleId'] = module_configuration_dict.pop('mod')
        module_configuration_dict['roomId'] = module_configuration_dict.pop('room')
        module_configuration_dict['switchTypeId'] = module_configuration_dict.pop('switchType')
        return module_configuration_dict
