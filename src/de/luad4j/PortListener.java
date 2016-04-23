/*
 * Lua-Discord4J - Lua wrapper for Discord4J Discord API
 * Copyright (C) 2016
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.luad4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.luad4j.events.JavaErrorEvent;
import de.luad4j.events.PortDataEvent;
import de.luad4j.lua.obj.LuaMessage;

public class PortListener extends Thread
{
	private final int mPort;	// The port, the port listener is listening to
	
	private static final Logger mLogger = LoggerFactory.getLogger(LuaMessage.class);	// Logger of this class
	
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
			// Create new socket
			serverSocket = new ServerSocket(mPort);

			while (true)
			{
				// Waits for connection and accepts it
				Socket socket = serverSocket.accept();
				
				// Save input stream
				BufferedReader in = new BufferedReader (new InputStreamReader (socket.getInputStream ()));
				
				// Read input stream, line by line
				String line = "";
				String message = "";
				while((line=in.readLine()) != null)
				{
					message = message + line + "\n";
					line = "";
				}
				
				// Dispatch event with data
				Main.mDiscordClient.getDispatcher().dispatch(new PortDataEvent(message));
			}
		} 
		catch (IOException e) 
		{
			mLogger.error(e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
}
