from pony.orm.dbapiprovider import StrConverter
from enum import Enum


class EnumConverter(StrConverter):

    def validate(self, val, obj):
        if not isinstance(val, Enum):
            raise ValueError('Must be an Enum.  Got {}'.format(type(val)))
        return val

    def py2sql(self, val):
        return val.name

    def sql2py(self, value):
        return self.py_type[value.strip()]
