import java.net.*;
import java.io.*;
class Communicate extends Thread
{
	private Socket client;
	private int id;
	Communicate(Socket client, int id)
	{
		this.client = client;
		this.id = id;
	}
	public void run()
	{
		int count = 0;
		byte[] inputdata = new byte[1024];
		DataInputStream in = null;
		DataOutputStream out = null;

		try
		{
			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());
			count = in.read(inputdata);

			while(count > 1)
			{
				out.write(inputdata, 0, count);

				count = in.read(inputdata);

			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				client.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
public class Server {
	public static void main(String[] args)
	{
		ServerSocket server = null;
		Socket client = null;
		int id = 0;
		try
		{
			server = new ServerSocket(8090, 10);
			while(true)
			{
				try
				{
					System.out.println("Listening for Clients");
					client = server.accept();
					System.out.println("Connection Establised :" + client);
					Thread cm = new Communicate(client, id);
					cm.start();
					id++;
				}
				catch(Exception e)
				{
					continue;
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				server.close();
			}
			catch(Exception e) { e.printStackTrace(); }
			try
			{
				client.close();
			}
			catch(Exception e) { e.printStackTrace(); }

		}
	}
}


