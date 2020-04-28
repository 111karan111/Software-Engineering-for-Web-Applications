import java.io.*;

import java.net.*;
import java.util.*;


public class Server {
	
	public static void main(String[] args) throws IOException {
		
	 if (args.length != 1) { // Test for correct num. of arguments
	 System.err.println( "ERROR server port number not given");
	 System.exit(1);
	 }
	 int port_num = Integer.parseInt(args[0]);
	 
		System.out.println("Connecting to port " + port_num);
		
		ServerSocket rv_sock = new ServerSocket(port_num, 5);//listen on port 12345
		System.out.println(rv_sock);
		System.out.println("\nWaiting for client to connect...");
		Socket conn = rv_sock.accept(); //it waits till it gets a connection
		
		System.out.println("\nConnection Accepted...");
		
		String str1 = "Bad input. Try again" + '\n';
		
		//While start
		while(true){
			 // run forever, waiting for clients to connect 
			String result = "";
			
			// get input/output streams for the socket
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			
			// reading the data from the client
			String line =  in.readLine();
			System.out.println("got command \" " + line + "\"");
			
			String[] str = line.split(" ");
			
			//now we check each input received from the user
			
			if (str.length > 2)
				out.writeBytes(str1);
			
			// if the input has bounce in it
			else if ( str[0].equalsIgnoreCase("bounce") ) {
				
				//I know that input should be only two parts(for some inputs). 
				//So I split it to check it later
				
				
				//making sure that input is in the right structure
				if( str.length != 2 ){
					
					out.writeBytes(str1);
					

				}else{ 
					//we made sure that structure is right.
					//now we bounce back a message to the user.
					String snr = str[1];
					
					//Create the result
					result = snr.toUpperCase() + '\n';
					
					//Write back the result to the user:
					out.writeBytes(result);
					System.out.println(result + "-> bounced");
				}
				//end of bounce
				
				
			} else if (str[0].equalsIgnoreCase("GET")){
				
				//making sure that input is in the right structure
				if( str.length != 2 )
					out.writeBytes(str1 + '\n');
				else{
					//System.out.println("else entered"); //test
					String path = str[1];
//					path = "src/" + path; //making the file path
					//System.out.println("path is : " + path);
					System.out.println("Checking if the file exists");
					
					File file = new File(path);
					String curLine = " ";
					
					//checking whether or not the file exists in our directory
					if (file.exists() == false ) {
						
						out.writeBytes("ERROR:no such file" + '\n');
						System.out.println("ERROR: no such file"); // test
						
					}
					// Now that we know the file exists, we get whats inside it
					// and send it to the user. 
					else{
						System.out.println("File exists. Ready to do send content to Client");//test
						
						BufferedReader input =  new BufferedReader(new FileReader(path));
						 String Line = null; //not declared within while loop
						 while (  (Line = input.readLine()) != null){
							 curLine = curLine.concat(";");
							 curLine = curLine.concat(Line);
						      //Process line
						  }//end of while
						 out.writeBytes(curLine + '\n');
						 System.out.println("User has recieved the data");//test
						
						}// end of else

					
				}
				
			}else if (str[0].equalsIgnoreCase("EXIT")){
				
				//making sure that input is in the right structure
				if( str.length == 1 ){
					out.writeBytes("SESSION WILL CLOSE" + + '\n');
					break;
				}
				else if (str.length == 2){
					
					out.writeBytes("~" + str[1] + + '\n');
//					out.writeBytes("SERVEROFF");
					break;
					
				}else
					out.writeBytes("bad input . Try again \n");
			}
			
			// This case happens when none of the inputs are correct
			else{
				out.writeBytes("bad input . Try again \n");
			}
			// return bad input
			
			
		
		}
		//While ends
		
		System.out.println("server exiting\n");
		
		conn.close(); //closing the connection
		rv_sock.close(); //stop listening
		
		
		
		
		
		
		
	}
	
	

}
