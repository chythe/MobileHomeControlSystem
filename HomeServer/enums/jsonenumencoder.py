from enum import Enum
import json


class JSONWithEnumEncoder(json.JSONEncoder):

    def default(self, obj):
        try:
            if isinstance(obj, Enum):
                return obj.name
            iterable = iter(obj)
        except TypeError:
            pass
        else:
            return list(iterable)
        return json.JSONEncoder.default(self, obj)
