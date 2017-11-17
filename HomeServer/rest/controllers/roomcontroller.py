from flask import Blueprint

room_controller = Blueprint('room_controller', __name__)


@room_controller.route('/api/room')
def room():
    return 'Room'
