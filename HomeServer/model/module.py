from config.databaseconfig import *


class Module(db.Entity):

    _table_ = "modules"
    module_id = PrimaryKey(int, size=64, auto=True)
    name = Required(str, unique=True)
    ip_address = Required(str, unique=True)
    module_configurations = Set("ModuleConfiguration")
