package de.luad4j.events;

import sx.blah.discord.api.Event;

public class PortDataEvent extends Event
{
	private final String message;
	
	public PortDataEvent(String msg)
	{
		message = msg;
	}
	
	public String getMessage()
	{
		return message;
	}
}
