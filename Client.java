import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client
{
	public static void main(String[] args)
	{
		Socket conn = null;
		try
		{
			System.out.println("Connecting to Server...");
			conn = new Socket("localhost", 8090);
			System.out.println("Connected to Server...");
			String msg = "test";
			Scanner in = new Scanner(System.in);
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			DataInputStream sin = new DataInputStream(conn.getInputStream());
			while(true)
			{
				System.out.print("Type a message for the server : ");
				msg  = in.nextLine();
				if(msg.isEmpty()) break;
				System.out.println("Sending Message to Server...");
				out.writeUTF(msg);	
				System.out.println("Sent");
				String incoming = sin.readUTF();
				System.out.println("Server Replied with: " + incoming);
			}
			
		}
		catch(Exception ie) {}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception e) {}
		}
	}
}
