package de.luad4j.events;

import sx.blah.discord.api.Event;

public class LuaErrorEvent extends Event
{
	private final String message;
	
	public LuaErrorEvent(String msg)
	{
		message = msg;
	}
	
	public String getMessage()
	{
		return message;
	}
}
