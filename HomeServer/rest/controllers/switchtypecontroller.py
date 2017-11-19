from flask import Blueprint, jsonify
from dao.switchtypedao import SwitchTypeDao

switch_type_controller = Blueprint('switch_type_controller', __name__)

switch_type_dao = SwitchTypeDao()


@switch_type_controller.route('/api/switch-type')
def switch_type():
    return 'Switch Type'
