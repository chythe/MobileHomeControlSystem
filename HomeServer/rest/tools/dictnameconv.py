import re

from pony.orm.core import Entity


def camel_to_underscore(name):
    camel_pat = re.compile(r'([A-Z])')
    return camel_pat.sub(lambda x: '_' + x.group(1).lower(), name)


def underscore_to_camel(name):
    under_pat = re.compile(r'_([a-z])')
    return under_pat.sub(lambda x: x.group(1).upper(), name)


def change_dict_naming_convention(d, convert_function):
    new = {}
    for k, v in d.items():
        new_v = v
        if isinstance(v, dict):
            new_v = change_dict_naming_convention(v, convert_function)
        elif isinstance(v, list):
            new_v = list()
            for x in v:
                new_v.append(change_dict_naming_convention(x, convert_function))
        new[convert_function(k)] = new_v
    return new


def recursive_to_dict(entity, has_iterated=False, **kwargs):
    if isinstance(entity, Entity):
        entity = entity.to_dict(**kwargs)

    delete_these = []
    for key, value in entity.items():
        if has_iterated:
            if isinstance(value, (list, tuple)):
                for iterable in value:
                    if isinstance(iterable, Entity):
                        delete_these.append(key)
                        break
                continue
        else:
            if isinstance(value, (list, tuple)):
                value_list = []
                for iterable in value:
                    if isinstance(iterable, Entity):
                        value_list.append(recursive_to_dict(iterable, True, **kwargs))
                entity[key] = value_list

        if isinstance(value, Entity) and not has_iterated:
            entity[key] = recursive_to_dict(value, True, **kwargs)

        elif isinstance(value, Entity) and has_iterated:
            delete_these.append(key)

    for deletable_key in delete_these:
        del entity[deletable_key]

    return entity
