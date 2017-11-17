from flask import Blueprint

module_configuration_controller = Blueprint('module_configuration_controller', __name__)


@module_configuration_controller.route('/api/module-configuration')
def module_configuration():
    return 'Module Configuration'
