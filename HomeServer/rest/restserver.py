from flask import Flask
from threading import Thread

from enums.jsonenumencoder import JSONWithEnumEncoder
from .controllers.moduleconfcontroller import module_configuration_controller
from rest.controllers.authcontroller import authentication_controller
from .controllers.switchtypecontroller import switch_type_controller
from .controllers.switchcontroller import switch_controller
from .controllers.modulecontroller import module_controller
from .controllers.errorcontroller import error_controller
from .controllers.roomcontroller import room_controller
from .controllers.usercontroller import user_controller
import os
import binascii
import pem

flask_server = Flask(__name__)
flask_server.register_blueprint(module_configuration_controller)
flask_server.register_blueprint(authentication_controller)
flask_server.register_blueprint(switch_type_controller)
flask_server.register_blueprint(switch_controller)
flask_server.register_blueprint(module_controller)
flask_server.register_blueprint(error_controller)
flask_server.register_blueprint(room_controller)
flask_server.register_blueprint(user_controller)
flask_server.json_encoder = JSONWithEnumEncoder


class RESTServer(Thread):

    def __init__(self):
        super(RESTServer, self).__init__()
        flask_server.config['SECRET_KEY'] = self.load_authentication_key()

    def run(self):
        flask_server.run(host='0.0.0.0', port=5000)

    def load_authentication_key(self):
        path = os.path.join(os.path.dirname(__file__), '..\key.pem')
        keys = pem.parse_file(path)
        key_string = str(keys[0]).replace('-----BEGIN PRIVATE KEY-----', '') \
            .replace('-----END PRIVATE KEY-----', '') \
            .replace('\r', '') \
            .replace('\n', '')
        return binascii.a2b_base64(key_string)


rest_server = RESTServer()
