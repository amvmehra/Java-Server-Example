import java.net.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.ArrayList;
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
			count = in.read(inputdata);

			while(count > 1)
			{

				for(Socket cl : Clients.list)
				{
					if (cl == client) continue;
					try
					{
						out = new DataOutputStream(cl.getOutputStream());
						out.write(inputdata, 0, count);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}

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
				Clients.list.remove(client);
				client.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
class Clients
{
	public static ArrayList<Socket> list = new ArrayList<Socket>();
}
public class Server {
	public static void main(String[] args)
	{
		ServerSocket server = null;
		Socket client = null;
		int id = 0;
		ExecutorService pool = null;
		try
		{
			server = new ServerSocket(8090, 10);
			pool = Executors.newFixedThreadPool(100);
			while(true)
			{
				try
				{
					System.out.println("Listening for Clients");
					client = server.accept();
					System.out.println("Connection Establised :" + client);
					Clients.list.add(client);
					Thread cm = new Communicate(client, id);
					pool.execute(cm);
					id++;
				}
				catch(Exception e)
				{
					e.printStackTrace();
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
			pool.shutdown();
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


