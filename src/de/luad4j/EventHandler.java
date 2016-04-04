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
import sx.blah.discord.handle.obj.IMessage;

import org.luaj.vm2.LuaValue;

public class EventHandler 
{
	// Helper functions
	public LuaValue buildDefaultMsgTable(IMessage msg)
	{
		LuaValue author = LuaValue.tableOf();
		author.set("id", msg.getAuthor().getID());
		author.set("name", msg.getAuthor().getName());
		
		LuaValue channel = LuaValue.tableOf();
		channel.set("id", msg.getChannel().getID());
		channel.set("name", msg.getChannel().getName());
		
		LuaValue message = LuaValue.tableOf();
		message.set("author", author);
		message.set("channel", channel);
		message.set("text", msg.getContent());
		if(!msg.getChannel().isPrivate())
		{
			message.set("guild", msg.getGuild().getName());
		}
		
		return message;
	}
	
	
	// Core Events
	@EventSubscriber
	public void onReady(ReadyEvent event) // If Discord API is ready
	{
		Main.initializeLua();
	}
	
	@EventSubscriber
	public void onDiscordDisconnected(DiscordDisconnectedEvent event) // If connection is lost
	{
		String MethodName = "onDiscordDisconnected";
		
		if(Main.mLuaEnv.get(MethodName).isfunction())
		{
			Main.mLuaEnv.get(MethodName).call(event.getReason().toString());
		}
	}
	
	// Message Events
	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event)
	{
		String MethodName = "onMessageReceived";
		
		try 
		{
			LuaValue message = buildDefaultMsgTable(event.getMessage());
			
			if(Main.mLuaEnv.get(MethodName).isfunction())
			{
				Main.mLuaEnv.get(MethodName).call(message);
			}
		} 
		catch(LuaError e) 
		{
			System.err.println("[JAVA][EventHandler] A Lua error occured while calling event: " + MethodName);
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
		String MethodName = "onMessageDeleted";
		
		try
		{
			LuaValue message = buildDefaultMsgTable(event.getMessage());
			
			if(Main.mLuaEnv.get(MethodName).isfunction())
			{
				Main.mLuaEnv.get(MethodName).call(message);
			}
		} 
		catch(LuaError e) 
		{
			System.err.println("[JAVA][EventHandler] A Lua error occured while calling event: " + MethodName);
			e.printStackTrace();
		}
	}
	
	@EventSubscriber
	public void onMessageUpdated(MessageUpdateEvent event)
	{
		String MethodName = "onMessageUpdated";
		
		try
		{
			LuaValue message = buildDefaultMsgTable(event.getNewMessage());
			message.set("oldtext", event.getOldMessage().getContent());
			
			if(Main.mLuaEnv.get(MethodName).isfunction())
			{
				Main.mLuaEnv.get(MethodName).call(message);
			}
		} 
		catch(LuaError e) 
		{
			System.err.println("[JAVA][EventHandler] A Lua error occured while calling event: " + MethodName);
			e.printStackTrace();
		}
	}
	
	@EventSubscriber
	public void onMessageAcknowledged(MessageAcknowledgedEvent event)
	{
		String MethodName = "onMessageAcknowledged";
		
		try
		{
			LuaValue message = buildDefaultMsgTable(event.getAcknowledgedMessage());
			
			if(Main.mLuaEnv.get(MethodName).isfunction())
			{
				Main.mLuaEnv.get(MethodName).call(message);
			}
		} 
		catch(LuaError e) 
		{
			System.err.println("[JAVA][EventHandler] A Lua error occured while calling event: " + MethodName);
			e.printStackTrace();
		}
	}
	
	@EventSubscriber
	public void onMention(MentionEvent event)
	{
		String MethodName = "onMention";
		
		try
		{
			LuaValue message = buildDefaultMsgTable(event.getMessage());
			
			if(Main.mLuaEnv.get(MethodName).isfunction())
			{
				Main.mLuaEnv.get(MethodName).call(message);
			}
		} 
		catch(LuaError e) 
		{
			System.err.println("[JAVA][EventHandler] A Lua error occured while calling event: " + MethodName);
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
