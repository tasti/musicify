from flask import Blueprint, jsonify, request
import echonest, spotify

artists_api = Blueprint('artists_api', __name__)

@artists_api.route('', methods=['GET'])
def get_artists():
  if 'name' not in request.args:
    return jsonify({ 'success': False, 'message': 'name not specified.' })    

  artists = spotify.get_artists_by_name(request.args['name'])
  if artists == None:
    return jsonify({ 'success': False, 'message': 'unexpected error occurred.' })

  return jsonify({ 'success': True, 'items': artists })

@artists_api.route('/genre', methods=['GET'])
def get_artists_genre():
  if 'name' not in request.args:
    return jsonify({ 'success': False, 'message': 'name not specified.' })    

  artists = echonest.get_artists_by_genre(request.args['name'])
  if artists == None:
    return jsonify({ 'success': False, 'message': 'unexpected error occurred.' })

  # We want the spotify equivalent data of the artists
  artists = spotify.get_artists_by_id([artist['spotify_id'] for artist in artists])

  return jsonify({ 'success': True, 'items': artists })

@artists_api.route('/related', methods=['GET'])
def get_artists_similar():
  if 'spotify_id' not in request.args:
    return jsonify({ 'success': False, 'message': 'spotify_id not specified.' })    

  artists = spotify.get_artists_by_relation(request.args['spotify_id'])
  if artists == None:
    return jsonify({ 'success': False, 'message': 'unexpected error occurred.' })

  return jsonify({ 'success': True, 'items': artists })
