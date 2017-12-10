from config.databaseconfig import *
from model.module import Module
from model.room import Room
from model.switchtype import SwitchType
from rest.tools.dictnameconv import change_dict_naming_convention, underscore_to_camel


class ModuleConfiguration(db.Entity):

    _table_ = "module_configurations"
    mod = Required(Module, column="module_id")
    switch_no = Required(int, size=16)
    room = Required(Room, column="room_id")
    switch_type = Required(SwitchType, column="switch_type_id")
    name = Required(str, unique=True)
    PrimaryKey(mod, switch_no)

    def to_dict(self):
        module_configuration_dict = change_dict_naming_convention(super(ModuleConfiguration, self).to_dict(), underscore_to_camel)
        module_configuration_dict['moduleId'] = module_configuration_dict.pop('mod')
        module_configuration_dict['roomId'] = module_configuration_dict.pop('room')
        module_configuration_dict['switchTypeId'] = module_configuration_dict.pop('switchType')
        return module_configuration_dict
