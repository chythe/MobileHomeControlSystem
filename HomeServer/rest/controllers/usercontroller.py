from flask import Blueprint

user_controller = Blueprint('user_controller', __name__)


@user_controller.route('/api/user')
def user():
    return 'User'
