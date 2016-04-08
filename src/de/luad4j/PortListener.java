package de.luad4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import de.luad4j.events.PortDataEvent;

public class PortListener extends Thread
{
	private final int mPort;
	
	public PortListener(int port)
	{
		mPort = port;
	}
	
	@Override
	public void run()
	{
		ServerSocket serverSocket;
		try 
		{
			serverSocket = new ServerSocket(mPort);

			while (true)
			{
				Socket socket = serverSocket.accept();
				
				BufferedReader in = new BufferedReader (new InputStreamReader (socket.getInputStream ()));
				
				String line = "";
				String message = "";
				while((line=in.readLine()) != null)
				{
					message = message + line + "\n";
					line = "";
				}
				
				Main.mDiscordClient.getDispatcher().dispatch(new PortDataEvent(message));
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
