from config.databaseconfig import *
from enum import Enum
import json


class EnumEncoder(json.JSONEncoder):

    def default(self, obj):
        if isinstance(obj, Enum):
            return obj.name
        elif isinstance(obj, str):
            return obj
        elif isinstance(obj, db.Entity):
            return obj.__str__()
        return json.JSONEncoder.default(self, obj)
