

class SwitchesDict(object):

    def __init__(self, ip_address, switch_no):
        self.switch_dict = {}

    class SwitchKey(object):

        def __init__(self, ip_address, switch_no, defined, state):
            self.ip_address = ip_address
            self.switch_no = switch_no
            self.defined = defined
            self.state = state


switches_dict = SwitchesDict()
