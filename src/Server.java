import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {
	private ServerSocket s;
	private Socket p1, p2;
	private PrintWriter bis1, bis2;
	private BufferedReader bos1, bos2;
	 private static final Logger LOG =
		      Logger.getLogger(Server.class.getName());

	public Server(int port) {
	    try {
	        s = new ServerSocket(port);
	    } catch (IOException e) {
	    	LOG.warning("Exception when opening a socket "+ e.getMessage());
	    }
	}

	public void execute() {
	    try {
	        p1 = s.accept();
	        p2 = s.accept();
	    } catch (IOException e) {
	    	LOG.warning("Exception when waiting for connection "+ e.getMessage());
	    }

	    try {
	        bis1 = new PrintWriter(p1.getOutputStream());
	        bis2 = new PrintWriter(p2.getOutputStream());
	        bos1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
	        bos2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
	    } catch (IOException e) {
	    	LOG.warning("Exception when fetching input stream "+ e.getMessage());
	    }

	    while (p1.isClosed() || p2.isClosed()) { // if one of the players disconnect, the match will end.
	        try {
	            String p1 = bos1.readLine(); // what p1 says
	            String p2 = bos2.readLine(); // what p2 says

	            if (!p1.equalsIgnoreCase("")) { // if what p1 says is something
	                if(p1.startsWith("Player1")) {
	                    String added = p1.split(",")[1];
	                    String output = p1.split(",")[2];
						bis2.write("Player1," + added + "," + output);
	                } else if(p1.startsWith("Player2")) {
	                	String added = p1.split(",")[1];
	                    String output = p1.split(",")[2];
						bis2.write("Player2," + added + "," + output);
	                }
	            }

	            if (!p2.equalsIgnoreCase("")) { // if what p1 says is something
	                if(p2.startsWith("Player1")) {
	                	String added = p2.split(",")[1];
	                    String output = p2.split(",")[2];
	                    bis1.write("Player1," + added + "," + output);
	                } else if(p2.startsWith("Player2")) {
	                	String added = p2.split(",")[1];
	                    String output = p2.split(",")[2];
	                    bis1.write("Player2," + added + "," + output);
	                }
	            }
	        } catch (IOException e) {
	        	LOG.warning("Exception reading input stream "+ e.getMessage());
	        }
	    }
        
    }
 
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: java Server <port-number>");
            System.exit(0);
        }
 
        int port = Integer.parseInt(args[0]);
 
        Server server = new Server(port);
        server.execute();
    }

}
