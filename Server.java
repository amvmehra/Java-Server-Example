import java.net.*;
import java.io.*;
public class Server
{
	public static void main(String[] args)
	{
		Socket client = null;
		ServerSocket ss = null;
		String msg = "";
		try
		{
			ss = new ServerSocket(8090);
			while(true)
			{
				System.out.println("Listening for new Client...");
				client = ss.accept();
				System.out.println("Client Connected: " + client);
				DataInputStream in = new DataInputStream(client.getInputStream());
			try
			{
				msg = in.readUTF();
			}
			catch(Exception e) {}
				while(msg.length() > 0)
				{
				System.out.println("Client Send : " + msg );
				System.out.println("Echoing...");
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
			out.writeUTF(msg);	
			System.out.println("Done");
			try
			{
				msg = in.readUTF();
			}
			catch(Exception e) {}
				}
			client.close();


			}
		}
		catch(Exception e) {System.out.println(e);}
		finally
		{
			try 
			{
				ss.close();
			}
			catch(Exception e){}
			try 
			{
				client.close();
			}
			catch(Exception e){}
		}
	}
}
