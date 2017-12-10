import os
import binascii
import pem


def load_authentication_key():
    path = os.path.join(os.path.dirname(__file__), '..\key.pem')
    keys = pem.parse_file(path)
    key_string = str(keys[0]).replace('-----BEGIN PRIVATE KEY-----', '')\
        .replace('-----END PRIVATE KEY-----', '')\
        .replace('\r', '')\
        .replace('\n', '')
    return binascii.a2b_base64(key_string)
