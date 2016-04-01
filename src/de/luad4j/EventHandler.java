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
		// not needed
	}
	
	@EventSubscriber
	public void onMessageDeleted(MessageDeleteEvent event)
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
			
			Main.mLuaEnv.get("onMessageDeleted").call(message);
		} 
		catch(LuaError e) 
		{
			e.printStackTrace();
		}
	}
	
	@EventSubscriber
	public void onMessageUpdated(MessageUpdateEvent event)
	{
		try
		{
			LuaValue author = LuaValue.tableOf();
			author.set("id", event.getNewMessage().getAuthor().getID());
			author.set("name", event.getNewMessage().getAuthor().getName());
			
			LuaValue channel = LuaValue.tableOf();
			channel.set("id", event.getNewMessage().getChannel().getID());
			channel.set("name", event.getNewMessage().getChannel().getName());
			
			LuaValue message = LuaValue.tableOf();
			message.set("author", author);
			message.set("channel", channel);
			message.set("oldtext", event.getOldMessage().getContent());
			message.set("newtext", event.getNewMessage().getContent());
			
			Main.mLuaEnv.get("onMessageUpdated").call(message);
		} 
		catch(LuaError e) 
		{
			if(e.getCause().getMessage() == "attempt to call nil")
			{
				System.out.println("Event handler not defined in lua file!");
			}
			else
			{
				System.out.println(e.getCause().getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@EventSubscriber
	public void onMessageAcknowledged(MessageAcknowledgedEvent event)
	{
		try
		{
			LuaValue author = LuaValue.tableOf();
			author.set("id", event.getAcknowledgedMessage().getAuthor().getID());
			author.set("name", event.getAcknowledgedMessage().getAuthor().getName());
			
			LuaValue channel = LuaValue.tableOf();
			channel.set("id", event.getAcknowledgedMessage().getChannel().getID());
			channel.set("name", event.getAcknowledgedMessage().getChannel().getName());
			
			LuaValue message = LuaValue.tableOf();
			message.set("author", author);
			message.set("channel", channel);
			message.set("text", event.getAcknowledgedMessage().getContent());
			
			Main.mLuaEnv.get("onMessageAcknowledged").call(message);
		} 
		catch(LuaError e) 
		{
			e.printStackTrace();
		}
	}
	
	@EventSubscriber
	public void onMention(MentionEvent event)
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
			
			Main.mLuaEnv.get("onMention").call(message);
		} 
		catch(LuaError e) 
		{
			e.printStackTrace();
		}
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
