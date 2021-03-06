from enum import Enum


class Role(Enum):

    ADMIN = 0
    USER = 1
    GUEST = 2

    @property
    def serialize(self):
        return self.name
