from flask import Flask
from threading import Thread
from .controllers.moduleconfcontroller import module_configuration_controller
from .controllers.switchtypecontroller import switch_type_controller
from .controllers.switchcontroller import switch_controller
from .controllers.modulecontroller import module_controller
from .controllers.roomcontroller import room_controller
from .controllers.usercontroller import user_controller

flask_server = Flask(__name__)
flask_server.register_blueprint(module_configuration_controller)
flask_server.register_blueprint(switch_type_controller)
flask_server.register_blueprint(switch_controller)
flask_server.register_blueprint(module_controller)
flask_server.register_blueprint(room_controller)
flask_server.register_blueprint(user_controller)


class RESTServer(Thread):

    def __init__(self):
        super(RESTServer, self).__init__()

    def run(self):
        flask_server.run()


rest_server = RESTServer()
