import httplib, json

HOST = 'musicify-app.appspot.com'

def get(url, ssl=False):
  conn = None
  if ssl:
    conn = httplib.HTTPSConnection(HOST)
  else:
    conn = httplib.HTTPConnection(HOST)
  
  conn.request('GET', url)
  response = conn.getresponse()

  if response.status != 200:
    return None

  return json.loads(response.read())
