from mongoengine import *

DATABASE_NAME = 'mobile_home_db'


def connect_mongodb():
    connect(DATABASE_NAME)
