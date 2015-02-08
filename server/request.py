import httplib, json

def get(url):
  conn = httplib.HTTPSConnection('musicify-app.appspot.com')
  conn.request('GET', url)
  response = conn.getresponse()

  if response.status != 200:
    return None

  return json.loads(response.read())
