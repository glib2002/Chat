package start;
import java.io.IOException;

import server.Server;

public class Main2 {

	public static void main(String[] args) {
		Server s = new Server();
		try {
			s.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("CANNOT START");
		}

	}

}
