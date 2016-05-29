package de.luad4j.lua;

import java.util.concurrent.Callable;

import org.luaj.vm2.LuaValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.RequestBuffer;

public class LuaHelper
{	
	public static LuaValue handleRequestExceptions(Class<?> callingClass, Callable<LuaValue> request)
	{
		Logger logger = LoggerFactory.getLogger(callingClass);	// Logger of calling class
		
		return RequestBuffer.request(() -> {
			try
			{
				return request.call();
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
				if(e instanceof HTTP429Exception)
				{
					throw (HTTP429Exception) e;
				}
			}
			
			return LuaValue.NIL;
		}).get();
	}
	
	public static LuaValue handleExceptions(Class<?> callingClass, Callable<LuaValue> request)
	{
		Logger logger = LoggerFactory.getLogger(callingClass);	// Logger of calling class
		
		try
		{
			return request.call();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
		
		return LuaValue.NIL;
	}
}
