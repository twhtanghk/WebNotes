package groovy

import groovyx.net.http.RESTClient

class Notes {
	static public boolean send(String to, String cc, String subject, String body, String url = 'http://localhost:3000/mail/api/mail/') {
		def http = new RESTClient(url)
		def res = http.post(
			body: [
				to: to,
				cc:	cc,
				subject: subject,
				body: body],
			requestContentType:
				'application/json')
		return res.status == 201
	}		
	
	static public void main(String[] args) {
		if (args.length != 4) {
			println "Usage: groovy Notes.groovy 'a@abc.com' '' 'subject a' 'body a'"
			System.exit(1)
		}
		Notes.send(args[0], args[1], args[2], args[3])
	}
}