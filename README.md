WebNotes
========

Restful Web Service for sending mail via Lotus Notes Client 

Restful API to interface with Lotus Notes Client
----------------------------------------------------------------
*   mail

```
    post api/mail - send lotus notes mail with the following parameters
    	to:			comma delimited list of recipients
    	cc:			comma delimited list of copy-to recipients
    	subject:	subject to be shown in the lotus notes mail
    	body:	 	mail body text in html or plain text
```

Configuration
=============
*   git clone https://github.com/twhtanghk/WebNotes.git
*   cd WebNotes
*   pip install -r requirements.txt
*   update environment variables in start.bat
```
    set PORT=3000
```

*   update environment variables in mail/env.py
	*	set server DEBUG to True or False (default: False)
	*	set KEEPRECORD of sent mail to True or False (default: False)
	*	set allowed client in ALLOWED_HOSTS (default: ['localhost', '127.0.0.1'])
	*	set log level of system access log (default: INFO) 
```
    DEBUG = False
	KEEPRECORD = True
    ALLOWED_HOSTS = ['localhost', '127.0.0.1']
    LOGGING = {
	    'loggers': {
	        'app': {
	            'handlers': ['file'],
	            'level': 'INFO',
	            'propagate': True,
	        },
	    }
	}
```

*	start the web service and input 'no' for not creating admin account
```
	.\start.bat
```

Testing
=======
*	command to send mail via Web Service
```
	curl -v --noproxy -X POST -d 'to=a@abc.com,b@def.com&cc=c@ghi.com&subject=subject a&body=body a' http://localhost:3000/mail/api/mail/
```

*	copy the following html to temporary file "mail.html" and open it with browser
	, complete the form, and click send button to send mail via Web Service
```
<html>
	<head>
		<style>
			input, textarea {
				display: block;
			}
		</style>
	</head>
	<body>
		<form action='http://localhost:3000/mail/api/mail/' method='post'>
			<input type='text' name='to' placeholder='To'>
			<input type='text' name='cc' placeholder='Cc'>
			<input type='text' name='subject' placeholder='Subject'>
			<textarea rows='10' cols='50' name='body'>
			</textarea>
			<button type='submit'>Send</button>
		</form>
	</body>
</html>
```