import requests

URL = 'https://pastebin.com/api/api_post.php'

DEV_KEY = 'a6228d0f75744dc8538cce8bac923ee'

parameters = {'api_option': 'paste',
              'api_dev_key': DEV_KEY,
              'api_paste_code': 'Test',
              'api_paste_format': 'python',
              }

parameters = {key.encode('utf-8'): value.encode('utf-8') for key, value in parameters.items() if value != None}

r = requests.post(URL, data=parameters)
print(r.text)