package util.remote.runner;

public class Main {

	public static void main(String[] args) {
		RemoteRunner rr = new RemoteRunner();
		try {
			rr.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
