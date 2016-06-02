package de.luad4j.events;

import sx.blah.discord.api.Event;
import sx.blah.discord.handle.obj.IGuild;

public class AudioUpdateEvent extends Event
{
	private final IGuild mGuild;
	
	public AudioUpdateEvent(IGuild guild)
	{
		mGuild = guild;
	}
	
	public IGuild getGuild()
	{
		return mGuild;
	}
}
