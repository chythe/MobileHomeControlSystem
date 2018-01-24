from tools.dictnameconv import change_dict_naming_convention, underscore_to_camel


class SwitchCommand(object):

    module_id = 0
    switch_no = 0
    state = False

    def __init__(self, module_id, switch_no, state):
        self.module_id = module_id
        self.switch_no = switch_no
        self.state = state

    def to_dict(self):
        switch_command_dict = change_dict_naming_convention(self.__dict__, underscore_to_camel)
        return switch_command_dict
