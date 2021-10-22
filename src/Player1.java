import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Logger;

public class Player1 {
	private static final Logger LOG = Logger.getLogger(Player1.class.getName());
	String host;
	int port = 0;

	public static void main(String[] args) {
		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);
		Player1 player1 = new Player1(hostName, portNumber);
		player1.execute();
	}

	public Player1(String hostname, int port) {
		this.host = hostname;
		this.port = port;
	}

	public void execute() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream out = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(out, true);

			String in = reader.readLine();
			int num = 0;
			if (in == null) {
				Random rand = new Random();
				num = rand.nextInt(1000);
			} else {
				num = Integer.valueOf(in.split(",")[1]);
			}
			int output = 0;
			int added = 0;
			if ((num % 3) == 0) {
				output = num / 3;
				added = 0;
			} else if (((num + 1) % 3) == 0) {
				output = (num + 1) / 3;
				added = 1;
			} else if (((num - 1) % 3) == 0) {
				output = (num - 1) / 3;
				added = -1;
			}
			writer.write("Player1," + added + "," + output);

		} catch (IOException ex) {
			LOG.warning("Exception has occured due to " + ex.getMessage());
		}
	}

}
