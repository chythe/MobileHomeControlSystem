from flask import Blueprint, request, jsonify, abort, g

from rest.services.authservice import AuthenticationService
from rest.services.switchservice import SwitchService
from rest.controllers.authcontroller import auth

switch_controller = Blueprint('switch_controller', __name__)

authentication_service = AuthenticationService()

switch_service = SwitchService()


@switch_controller.route('/api/switch', methods=['GET'])
@auth.login_required
def get_state():
    try:
        states = switch_service.get_states()
        states_dict = [s.to_dict() for s in states]
        response = jsonify(states_dict)
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except KeyError:
        abort(400)
    except AttributeError:
        abort(404)


@switch_controller.route('/api/switch', methods=['POST'])
@auth.login_required
def switch():
    try:
        ip_address = request.json['ipAddress']
        switch_no = request.json['switchNo']
        state = request.json['state']
        switch_service.switch(ip_address, switch_no, state)
        response = jsonify({'result': True})
        token = authentication_service.generate_auth_token(g.current_user)
        response.headers['Authorization'] = 'Bearer ' + token.decode('ascii')
        return response
    except KeyError:
        abort(400)
    except AttributeError:
        abort(404)
