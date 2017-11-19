from config.databaseconfig import *


class ModuleDao(object):

    @db_session
    def create_module(self, name):
        try:
            return Module(name=name)
        except ValueError:
            rollback()

    @db_session
    def read_modules(self):
        return Module.select()[:]

    @db_session
    def read_module(self, module_id):
        return Module[module_id]

    @db_session
    def update_module(self, module_id, name):
        m = Module[module_id]
        m.name = name
        return m

    @db_session
    def delete_module(self, module_id):
        Module[module_id].delete()
