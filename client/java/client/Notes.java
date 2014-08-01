package client;

class Notes {
	static public void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Usage: java client.Notes 'to list' 'subject' 'body' 'cc list' 'bcc list'");
			System.exit(1);
		}
		groovy.Notes.send(args[0], args[1], args[2], args[3], args[4]);
	}
}