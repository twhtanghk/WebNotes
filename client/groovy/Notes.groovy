package groovy

import groovyx.net.http.RESTClient

class Notes {
	static public boolean send(String to, String subject, String body, String cc = '', String bcc = '', String url = 'http://localhost:3000/mail/api/mail/') {
		def http = new RESTClient(url)
		def res = http.post(
			body: [
				to: to,
				cc:	cc,
				bcc: bcc,
				subject: subject,
				body: body],
			requestContentType:
				'application/json')
		return res.status == 201
	}		
	
	static public void main(String[] args) {
		if (args.length != 5) {
			println "Usage: groovy Notes.groovy 'to list' 'subject' 'body' 'cc list' 'bcc list'"
			System.exit(1)
		}
		Notes.send(args[0], args[1], args[2], args[3], args[4])
	}
}