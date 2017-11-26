from config.databaseconfig import *


class SwitchType(db.Entity):

    _table_ = "switch_types"
    switch_type_id = PrimaryKey(int, size=64, auto=True)
    name = Required(str, unique=True)
    module_configurations = Set("ModuleConfiguration")