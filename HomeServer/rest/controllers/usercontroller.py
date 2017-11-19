from flask import Blueprint, jsonify
from dao.userdao import UserDao
from enums.role import Role

user_controller = Blueprint('user_controller', __name__)

user_dao = UserDao()


@user_controller.route('/api/user')
def user():
    return 'User'
