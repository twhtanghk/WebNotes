package client;

class Notes {
	static public void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Usage: java client.Notes 'a@abc.com' '' 'subject a' 'body a'");
			System.exit(1);
		}
		groovy.Notes.send(args[0], args[1], args[2], args[3]);
	}
}