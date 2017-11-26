from flask import Flask
from threading import Thread
from enums.jsonenumencoder import JSONWithEnumEncoder
from .controllers.moduleconfcontroller import module_configuration_controller
from .controllers.switchtypecontroller import switch_type_controller
from .controllers.switchcontroller import switch_controller
from .controllers.modulecontroller import module_controller
from .controllers.errorcontroller import error_controller
from .controllers.roomcontroller import room_controller
from .controllers.usercontroller import user_controller

flask_server = Flask(__name__)
flask_server.register_blueprint(module_configuration_controller)
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

    def run(self):
        flask_server.run(host='0.0.0.0')


rest_server = RESTServer()
