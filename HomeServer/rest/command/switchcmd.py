from rest.tools.dictnameconv import change_dict_naming_convention, underscore_to_camel


class SwitchCommand(object):

    ip_address = 0
    switch_no = 0
    state = False

    def __init__(self, ip_address, switch_no, state):
        self.ip_address = ip_address
        self.switch_no = switch_no
        self.state = state

    def to_dict(self):
        switch_command_dict = change_dict_naming_convention(self.__dict__, underscore_to_camel)
        return switch_command_dict
