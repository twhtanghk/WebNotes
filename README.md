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
*	Command: send mail via Web Service
```
	curl -v --noproxy -X POST -d 'to=a@abc.com,b@def.com&cc=c@ghi.com&subject=subject a&body=body a' http://localhost:3000/mail/api/mail/
```

*	HTML Form: open the file 'client/html/mail.html' with browser, 
	complete the form, and click send button to send mail via Web Service
	
*	Groovy
```
	groovy -jar client/groovy/Notes.jar
```

*	Java: See client/java/client/Notes.java
```
	cd client/java
	javac -cp ../groovy/Notes.jar client/Notes.java
	set CLASSPATH=.;../groovy/Notes.jar
	java client.Notes 'a@abc.com' '' 'subject a' 'body a' 
```
