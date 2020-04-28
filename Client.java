import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		String line = null;
		
		if (args.length != 2) { // Test for correct num. of arguments
			 System.err.println(
			 "ERROR server host name AND port number not given");
			 System.exit(1);
			 }
		
		String server = args[0]; //host
		int port = Integer.parseInt(args[1]); 
		
		try {

			Socket c_sock = new Socket(server , port);
			
			/* The methods getInputStream() and getOutputStream() return the basic streams for the c_sock 
			 * Create a DataOutputStream for the c_sock so we can write a string as bytes
			 * Create a BufferedReader so we can read a line of results from the server 
			 */
			DataOutputStream out = new	DataOutputStream(c_sock.getOutputStream() );
			BufferedReader in = new	BufferedReader(new	InputStreamReader (	c_sock.getInputStream()));
			BufferedReader userdata = new BufferedReader(new InputStreamReader(System.in));
		
			while(true){
				//While starts
				System.out.print("User, enter your command: ");
				line = userdata.readLine();
				out.writeBytes(line + '\n'); //// send the line we read from the user 
				String result = in.readLine(); //// read the response from the server
			
				System.out.println("=> " + result);
				
				if (result.charAt(0) == '~')
					break;
				
			
			}// end of while loop
			
			System.out.println("conncetion is terminated");
			c_sock.close();
			
		} catch (IOException ex) { ex.printStackTrace(); }
		
		System.exit(0); 

	}

}
