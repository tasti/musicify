from flask import Flask, request, jsonify
from Artists import artists_api
from Genres import genres_api
from Tracks import tracks_api
from Users import users_api

app = Flask(__name__)
app.config['DEBUG'] = True

api_v1 = '/api/v1'

app.register_blueprint(artists_api, url_prefix='%s/artists' % api_v1)
app.register_blueprint(genres_api, url_prefix='%s/genres' % api_v1)
app.register_blueprint(tracks_api, url_prefix='%s/tracks' % api_v1)
app.register_blueprint(users_api, url_prefix='%s/users' % api_v1)

@app.route('/')
def hello():
    """Return a friendly HTTP greeting."""
    return 'Hello World!'

@app.errorhandler(404)
def page_not_found(e):
    """Return a custom 404 error."""
    return 'Sorry, nothing at this URL.', 404
