package de.luad4j.luafunc.audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import de.luad4j.Main;
import sx.blah.discord.util.DiscordException;

public class QueueURL extends TwoArgFunction
{	
	@Override
	public LuaValue call(LuaValue voicechannelid, LuaValue luaurl) 
	{
		AudioInputStream stream;
		
		try 
		{
			URL url = new URL(luaurl.tojstring());
			System.out.println("Protocol: "+url.getProtocol());
			System.out.println("Host: "+url.getHost());
			System.out.println("File: "+url.getFile());
			System.out.println("Path: "+url.getPath());
			
			URLConnection connection = url.openConnection();
			connection.connect();
			
			if(connection.getContentEncoding() != null)
			{
				System.out.println("Content-Encoding: " + connection.getContentEncoding());
			}
			else
			{
				System.out.println("Content-Encoding: null");
			}
			
			if(connection.getContentType() != null)
			{
				System.out.println("Content-Type: "+connection.getContentType());
			}
			else
			{
				System.out.println("Content-Type: null");
			}
			
			BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
			
			stream = AudioSystem.getAudioInputStream(bis);
			
			try 
			{
				Main.mDiscordClient.getVoiceChannelByID(voicechannelid.tojstring()).getAudioChannel().queue(stream);
				return LuaValue.NIL;
			} 
			catch (DiscordException e) 
			{
				e.printStackTrace();
				return LuaValue.valueOf("DiscordException");
			}
			
		} 
		catch (IOException | UnsupportedAudioFileException e) 
		{
			e.printStackTrace();
			return LuaValue.NIL;
		}
	}
}
