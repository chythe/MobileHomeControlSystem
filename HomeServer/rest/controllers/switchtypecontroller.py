from flask import Blueprint, jsonify, request, abort

from model.moduleconf import ModuleConfiguration
from rest.services.switchtypeservice import SwitchTypeService
from pony.orm import OrmError, IntegrityError
from rest.tools.dictnameconv import *

switch_type_controller = Blueprint('switch_type_controller', __name__)

switch_type_service = SwitchTypeService()


@switch_type_controller.route('/api/switch-type', methods=['GET'])
def get_switch_types():
    try:
        switch_types = switch_type_service.read_switch_types()
        switch_types_dict = [change_dict_naming_convention(st.to_dict(), underscore_to_camel) for st in switch_types]
        return jsonify(switch_types_dict)
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@switch_type_controller.route('/api/switch-type/<int:switch_type_id>', methods=['GET'])
def get_switch_type(switch_type_id):
    try:
        switch_type = switch_type_service.read_switch_type(switch_type_id)
        switch_type_dict = change_dict_naming_convention(switch_type.to_dict(), underscore_to_camel)
        return jsonify(switch_type_dict)
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@switch_type_controller.route('/api/switch-type', methods=['POST'])
def create_switch_type():
    try:
        name = request.json['name']
        switch_type = switch_type_service.create_switch_type(name)
        switch_type_dict = change_dict_naming_convention(switch_type.to_dict(), underscore_to_camel)
        return jsonify(switch_type_dict)
    except (OrmError, KeyError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@switch_type_controller.route('/api/switch-type', methods=['PUT'])
def update_switch_type():
    try:
        switch_type_id = request.json['switchTypeId']
        name = request.json['name']
        switch_type = switch_type_service.update_switch_type(switch_type_id, name)
        switch_type_dict = change_dict_naming_convention(switch_type.to_dict(), underscore_to_camel)
        return jsonify(switch_type_dict)
    except (OrmError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)


@switch_type_controller.route('/api/switch-type/<int:switch_type_id>', methods=['DELETE'])
def delete_switch_type(switch_type_id):
    try:
        switch_type_service.delete_switch_type(switch_type_id)
        return jsonify({'result': True})
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@switch_type_controller.route('/api/switch-type/module-configuration/<int:switch_type_id>', methods=['GET'])
def get_switch_type_module_configurations(switch_type_id):
    try:
        module_configurations = switch_type_service.get_switch_type_module_configurations(switch_type_id)
        module_configurations_dict = [change_dict_naming_convention(
            mc.to_dict(), underscore_to_camel) for mc in module_configurations]
        for mcd in module_configurations_dict:
            mcd = ModuleConfiguration.change_dict_keys_names(mcd)
        return jsonify(module_configurations_dict)
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)
