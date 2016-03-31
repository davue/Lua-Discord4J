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
