from config.databaseconfig import *


class ModuleDao:

    @db_session
    def create_module(self, name):
        Module(name=name)

    @db_session
    def read_modules(self):
        return Module.select()[:]

    @db_session
    def read_module(self, module_id):
        return Module[module_id]

    @db_session
    def update_module(self, mod):
        Module[mod.module_id] = mod

    @db_session
    def delete_module(self, module_id):
        Module[module_id].delete()
