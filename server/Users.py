from flask import Blueprint, jsonify, request
from google.appengine.ext import ndb
from Artists import get_artists_genre_helper
import spotify

users_api = Blueprint('users_api', __name__)

class User(ndb.Model):
  username = ndb.StringProperty(required=True)
  genre = ndb.StringProperty(required=True)

class Suggestion(ndb.Model):
  user = ndb.KeyProperty(kind=User, required=True)
  spotify_id = ndb.StringProperty(required=True)
  score = ndb.IntegerProperty(required=True)

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

  user = User(username=request.json['username'], genre='')
  user.put()

  return jsonify({ 'success': True, 'message': '%s has been added as a user.' % user.username })

@users_api.route('/<username>/genre', methods=['PUT'])
def put_user_genre(username):
  if 'genre' not in request.json:
    return jsonify({ 'success': False, 'message': 'genre not specified.' })

  user = User.query(User.username == username).get()
  if user == None:
    return jsonify({ 'success': False, 'message': '%s is not a user.' % username })

  user.genre = request.json['genre']
  user.put()

  # Populate base suggestions
  artists = get_artists_genre_helper(user.genre)
  if artists == None:
    return jsonify({ 'success': False, 'message': 'unexpected error occurred.' })

  for artist in artists:
    suggestion = Suggestion(user=user.key, spotify_id=artist['spotify_id'], score=1)
    suggestion.put()

  return jsonify({ 'success': True, 'message': '%s has been associated to the %s genre.' % (user.username, user.genre) })

@users_api.route('/<username>/suggestions', methods=['GET'])
def get_user_suggestions(username):
  user = User.query(User.username == username).get()
  if user == None:
    return jsonify({ 'success': False, 'message': '%s is not a user.' % username })

  suggestions = Suggestion.query(Suggestion.user == user.key).order(-Suggestion.score).fetch(10)
  artists = spotify.get_artists_by_id([suggestion.spotify_id for suggestion in suggestions])

  # Place artist data in suggestions
  suggestions_with_artists = []
  for suggestion in suggestions:
    suggestions_with_artists.append({ 'artist': artists[len(suggestions_with_artists)], 'score': suggestion.score })

  return jsonify({ 'success': True, 'items': suggestions_with_artists })

@users_api.route('/<username>/suggestions', methods=['PUT'])
def put_user_suggestions(username):
  user = User.query(User.username == username).get()
  if user == None:
    return jsonify({ 'success': False, 'message': '%s is not a user.' % username })

  if 'spotify_id' not in request.json:
    return jsonify({ 'success': False, 'message': 'spotify_id not specified.' })

  suggestion = Suggestion.query(Suggestion.user == user.key, Suggestion.spotify_id == request.json['spotify_id']).get()
  if suggestion == None:
    suggestion = Suggestion(user=user.key, spotify_id=request.json['spotify_id'], score=1)
  else:
    suggestion.score += 1

  suggestion.put()

  return jsonify({ 'success': True, 'message': '%s was suggested the artist with spotify_id %s.' %(user.username, suggestion.spotify_id) })
