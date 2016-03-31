package de.luad4j;

import org.luaj.vm2.LuaError;

import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import org.luaj.vm2.LuaValue;

public class EventHandler 
{
	// Core Events
	@EventSubscriber
	public void onReady(ReadyEvent event) // If Discord API is ready
	{
		Main.mLuaEnv.get("onReady").call();
	}
	
	@EventSubscriber
	public void onDiscordDisconnected(DiscordDisconnectedEvent event) // If connection is lost
	{
		Main.mLuaEnv.get("onDisconnected").call(event.getReason().toString());
	}
	
	// Message Events
	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event)
	{
		try 
		{
			LuaValue author = LuaValue.tableOf();
			author.set("id", event.getMessage().getAuthor().getID());
			author.set("name", event.getMessage().getAuthor().getName());
			
			LuaValue channel = LuaValue.tableOf();
			channel.set("id", event.getMessage().getChannel().getID());
			channel.set("name", event.getMessage().getChannel().getName());
			
			LuaValue message = LuaValue.tableOf();
			message.set("author", author);
			message.set("channel", channel);
			message.set("text", event.getMessage().getContent());
			//message.set("guild", event.getMessage().getGuild().getName()); // <- this breaks execution of this function
			
			Main.mLuaEnv.get("onMessageReceived").call(message);
		} 
		catch(LuaError e) 
		{
			e.printStackTrace();
		}	
	}
	
	@EventSubscriber
	public void onMessageSend(MessageSendEvent event)
	{
		
	}
	
	@EventSubscriber
	public void onMessageDelete(MessageDeleteEvent event)
	{
		
	}
	
	@EventSubscriber
	public void onMessageUpdate(MessageUpdateEvent event)
	{
		
	}
	
	@EventSubscriber
	public void onMessageAcknowledged(MessageAcknowledgedEvent event)
	{
		
	}
	
	@EventSubscriber
	public void onMention(MentionEvent event)
	{

	}
	
	// Audio Events
	@EventSubscriber
	public void onAudioPlay(AudioPlayEvent event)
	{
		
	}
	
	@EventSubscriber
	public void onAudioQueued(AudioQueuedEvent event)
	{
		
	}
	
	@EventSubscriber
	public void onAudioReceive(AudioReceiveEvent event)
	{
		
	}
	
	@EventSubscriber
	public void onAudioStop(AudioStopEvent event)
	{
		
	}
	
	@EventSubscriber
	public void onAudioUnqueued(AudioUnqueuedEvent event)
	{
		
	}
}
