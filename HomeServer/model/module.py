from database.databaseconfig import *
from tools.dictnameconv import change_dict_naming_convention, underscore_to_camel


class Module(db.Entity):

    _table_ = "modules"
    module_id = PrimaryKey(int, size=64, auto=True)
    name = Required(str, unique=True)
    ip_address = Required(str, unique=True)
    module_configurations = Set("ModuleConfiguration")

    def to_dict(self):
        return change_dict_naming_convention(super(Module, self).to_dict(), underscore_to_camel)
