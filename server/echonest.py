import my_request

ECHONEST_API = 'http://developer.echonest.com/api/v4'
ECHONEST_API_KEY = open('echonest_api_key.txt', 'r').readline().replace('\n', '')

def _get(url, cleanup):
  data = my_request.get(url)
  if data == None:
    return None

  return cleanup(data)

def _cleanup_genres(genres):
  genres = genres['response']['genres']

  return genres

def _cleanup_artist(artist):
  artist['echonest_id'] = artist.pop('id', None)
  artist['spotify_id'] = artist.pop('foreign_ids', None)[0]['foreign_id'].replace('spotify:artist:', '')

  return artist

def _cleanup_artists(artists):
  artists = artists['response']['artists']
  artists = [_cleanup_artist(artist) for artist in artists]

  return artists

def get_genres():
  # Leaving results blank isn't giving all the results, hence set to 2000
  return _get('%s/genre/list?api_key=%s&format=json&results=2000' % (ECHONEST_API, ECHONEST_API_KEY), _cleanup_genres)

def get_artists_by_genre(genre):
  return _get('%s/genre/artists?api_key=%s&format=json&name=%s&bucket=id:spotify' % (ECHONEST_API, ECHONEST_API_KEY, genre), _cleanup_artists)
