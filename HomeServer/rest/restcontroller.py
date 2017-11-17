from flask import Blueprint

rest_controller = Blueprint('rest_controller', __name__)


@rest_controller.route('/api/elo')
def elo():
    return 'This is Mobile Home Control System Server home page.'
