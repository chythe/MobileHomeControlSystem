from flask import Blueprint

switch_controller = Blueprint('switch_controller', __name__)


@switch_controller.route('/api/switch')
def switch():
    return 'Switch'
