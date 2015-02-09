from flask import Blueprint, jsonify, request
from google.appengine.ext import ndb

users_api = Blueprint('users_api', __name__)

class User(ndb.Model):
  username = ndb.StringProperty(required=True)

@users_api.route('/<username>', methods=['GET'])
def get_user(username):
  user = User.query(User.username == username).get()
  if user != None:
    return jsonify({ 'success': True, 'item': user.to_dict() })

  return jsonify({ 'success': False, 'message': '%s is not a user.' % username })

@users_api.route('', methods=['GET'])
def get_users():
  users = User.query().fetch()

  return jsonify({ 'success': True, 'users': [user.to_dict() for user in users] })

@users_api.route('', methods=['POST'])
def post_user():
  if 'username' not in request.json:
    return jsonify({ 'success': False, 'message': 'username not specified.' })

  user = User.query(User.username == request.json['username']).get()
  if user != None:
    return jsonify({ 'success': False, 'message': '%s is already a user.' % user.username })

  user = User(username=request.json['username'])
  user.put()

  return jsonify({ 'success': True, 'message': '%s has been added as a user.' % user.username })
