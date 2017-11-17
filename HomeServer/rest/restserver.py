from flask import Flask
from threading import Thread
# from .restcontroller import rest_controller

flask_server = Flask(__name__)
# flask_server.register_blueprint(rest_controller)


@flask_server.route('/')
def index():
    return 'This is Mobile Home Control System Server home page.'


@flask_server.route('/api/elo')
def elo():
    return 'Elo elo elo elo elo elo elo.'


class RESTServer(Thread):

    def __init__(self):
        super(RESTServer, self).__init__()

    def run(self):
        flask_server.run()


rest_server = RESTServer()
