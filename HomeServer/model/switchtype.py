from database.databaseconfig import *
from tools.dictnameconv import change_dict_naming_convention, underscore_to_camel


class SwitchType(db.Entity):

    _table_ = "switch_types"
    switch_type_id = PrimaryKey(int, size=64, auto=True)
    name = Required(str, unique=True)
    module_configurations = Set("ModuleConfiguration")

    def to_dict(self):
        return change_dict_naming_convention(super(SwitchType, self).to_dict(), underscore_to_camel)
