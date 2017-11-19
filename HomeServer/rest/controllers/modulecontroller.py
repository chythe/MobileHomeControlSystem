from flask import Blueprint

module_controller = Blueprint('module_controller', __name__)


@module_controller.route('/api/module')
def mod():
    return 'Module'
