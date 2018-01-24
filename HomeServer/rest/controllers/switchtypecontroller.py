from flask import Blueprint, jsonify, request, abort, g
from rest.controllers.authcontroller import auth
from rest.services.authservice import AuthenticationService
from rest.services.switchtypeservice import SwitchTypeService
from pony.orm import OrmError, IntegrityError

switch_type_controller = Blueprint('switch_type_controller', __name__)

authentication_service = AuthenticationService()

switch_type_service = SwitchTypeService()


@switch_type_controller.route('/api/switch-type', methods=['GET'])
@auth.login_required
def get_switch_types():
    try:
        switch_types = switch_type_service.read_switch_types()
        switch_types_dict = [st.to_dict() for st in switch_types]
        response = jsonify(switch_types_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@switch_type_controller.route('/api/switch-type/<int:switch_type_id>', methods=['GET'])
@auth.login_required
def get_switch_type(switch_type_id):
    try:
        switch_type = switch_type_service.read_switch_type(switch_type_id)
        switch_type_dict = switch_type.to_dict()
        response = jsonify(switch_type_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@switch_type_controller.route('/api/switch-type', methods=['POST'])
@auth.login_required
def create_switch_type():
    try:
        name = request.json['name']
        switch_type = switch_type_service.create_switch_type(name)
        switch_type_dict = switch_type.to_dict()
        response = jsonify(switch_type_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, KeyError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)
    except ValueError as e:
        print(str(e))
        abort(409)


@switch_type_controller.route('/api/switch-type', methods=['PUT'])
@auth.login_required
def update_switch_type():
    try:
        switch_type_id = request.json['switchTypeId']
        name = request.json['name']
        switch_type = switch_type_service.update_switch_type(switch_type_id, name)
        switch_type_dict = switch_type.to_dict()
        response = jsonify(switch_type_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, TypeError, AttributeError, IntegrityError) as e:
        print(str(e))
        abort(400)


@switch_type_controller.route('/api/switch-type/<int:switch_type_id>', methods=['DELETE'])
@auth.login_required
def delete_switch_type(switch_type_id):
    try:
        switch_type_service.delete_switch_type(switch_type_id)
        response = jsonify({'result': True})
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)


@switch_type_controller.route('/api/switch-type/module-configuration/<int:switch_type_id>', methods=['GET'])
@auth.login_required
def get_switch_type_module_configurations(switch_type_id):
    try:
        module_configurations = switch_type_service.get_switch_type_module_configurations(switch_type_id)
        module_configurations_dict = [mc.to_dict() for mc in module_configurations]
        response = jsonify(module_configurations_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except (OrmError, RuntimeError) as e:
        print(str(e))
        abort(404)
