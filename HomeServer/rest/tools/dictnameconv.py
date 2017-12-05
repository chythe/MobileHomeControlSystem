import re


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
