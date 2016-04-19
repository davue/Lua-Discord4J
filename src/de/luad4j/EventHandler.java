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
import de.luad4j.events.*;
import de.luad4j.lua.obj.LuaMessage;

import org.luaj.vm2.LuaValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventHandler 
{
	private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);
	
	// Custom Events
	@EventSubscriber
	public void onLuaError(LuaErrorEvent event)
	{
		if(Main.mLuaEnv.get("onLuaError").isfunction())
		{
			Main.mLuaEnv.get("onLuaError").call(event.getMessage());
		}
		else
		{
			logger.warn("[JAVA] onLuaError(string: reason) undefined. It is recommended to define a LuaError event handler.");
		}
	}
	
	@EventSubscriber
	public void onPortData(PortDataEvent event)
	{
		if(Main.mLuaEnv.get("onPortData").isfunction())
		{
			Main.mLuaEnv.get("onPortData").call(event.getMessage());
		}
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
		
		try
		{
			if(Main.mLuaEnv.get(MethodName).isfunction())
			{
				Main.mLuaEnv.get(MethodName).call(LuaValue.valueOf(event.getReason().toString()));
			}
		} 
		catch(LuaError e) 
		{
			logger.error("[JAVA] A Lua error occured while calling event: " + MethodName + "\n" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new LuaErrorEvent(e.getMessage()));
		}	
	}
	
	// Message Events
	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event)
	{
		String MethodName = "onMessageReceived";
		
		try 
		{
			if(Main.mLuaEnv.get(MethodName).isfunction())
			{
				Main.mLuaEnv.get(MethodName).call(new LuaMessage(event.getMessage()).getTable());
			}
		} 
		catch(LuaError e) 
		{
			logger.error("[JAVA] A Lua error occured while calling event: " + MethodName + "\n" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new LuaErrorEvent(e.getMessage()));
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
			if(Main.mLuaEnv.get(MethodName).isfunction())
			{
				Main.mLuaEnv.get(MethodName).call(new LuaMessage(event.getMessage()).getTable());
			}
		} 
		catch(LuaError e) 
		{
			logger.error("[JAVA] A Lua error occured while calling event: " + MethodName + "\n" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new LuaErrorEvent(e.getMessage()));
		}
	}
	
	@EventSubscriber
	public void onMessageUpdated(MessageUpdateEvent event)
	{
		String MethodName = "onMessageUpdated";
		
		try
		{
			if(Main.mLuaEnv.get(MethodName).isfunction())
			{
				LuaValue messages = LuaValue.tableOf();
				messages.set("old", new LuaMessage(event.getOldMessage()).getTable());
				messages.set("new", new LuaMessage(event.getNewMessage()).getTable());
				
				Main.mLuaEnv.get(MethodName).call(messages);
			}
		} 
		catch(LuaError e) 
		{
			logger.error("[JAVA] A Lua error occured while calling event: " + MethodName + "\n" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new LuaErrorEvent(e.getMessage()));
		}
	}
	
	@EventSubscriber
	public void onMessageAcknowledged(MessageAcknowledgedEvent event)
	{
		String MethodName = "onMessageAcknowledged";
		
		try
		{
			if(Main.mLuaEnv.get(MethodName).isfunction())
			{
				Main.mLuaEnv.get(MethodName).call(new LuaMessage(event.getAcknowledgedMessage()).getTable());
			}
		} 
		catch(LuaError e) 
		{
			logger.error("[JAVA] A Lua error occured while calling event: " + MethodName + "\n" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new LuaErrorEvent(e.getMessage()));
		}
	}
	
	@EventSubscriber
	public void onMention(MentionEvent event)
	{
		String MethodName = "onMention";
		
		try
		{
			if(Main.mLuaEnv.get(MethodName).isfunction())
			{
				Main.mLuaEnv.get(MethodName).call(new LuaMessage(event.getMessage()).getTable());
			}
		} 
		catch(LuaError e) 
		{
			logger.error("[JAVA] A Lua error occured while calling event: " + MethodName + "\n" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new LuaErrorEvent(e.getMessage()));
		}
	}
	
	// Audio Events
	@EventSubscriber
	public void onAudioPlay(AudioPlayEvent event)
	{
		LuaValue audio = LuaValue.tableOf();
		
		if(event.getFileSource().isPresent())
		{
			audio.set("file", event.getFileSource().get().getAbsolutePath());
		}
		
		if(event.getUrlSource().isPresent())
		{
			audio.set("url", event.getUrlSource().get().getPath());
		}
		
		audio.set("format", event.getFormat().toString());
	}
	
	@EventSubscriber
	public void onAudioQueued(AudioQueuedEvent event)
	{
		LuaValue audio = LuaValue.tableOf();
		
		if(event.getFileSource().isPresent())
		{
			audio.set("file", event.getFileSource().get().getAbsolutePath());
		}
		
		if(event.getUrlSource().isPresent())
		{
			audio.set("url", event.getUrlSource().get().getPath());
		}
		
		audio.set("format", event.getFormat().toString());
	}
	
	@EventSubscriber
	public void onAudioReceive(AudioReceiveEvent event)
	{
		
	}
	
	@EventSubscriber
	public void onAudioStop(AudioStopEvent event)
	{
		LuaValue audio = LuaValue.tableOf();
		
		if(event.getFileSource().isPresent())
		{
			audio.set("file", event.getFileSource().get().getAbsolutePath());
		}
		
		if(event.getUrlSource().isPresent())
		{
			audio.set("url", event.getUrlSource().get().getPath());
		}
		
		audio.set("format", event.getFormat().toString());
	}
	
	@EventSubscriber
	public void onAudioUnqueued(AudioUnqueuedEvent event)
	{
		LuaValue audio = LuaValue.tableOf();
		
		if(event.getFileSource().isPresent())
		{
			audio.set("file", event.getFileSource().get().getAbsolutePath());
		}
		
		if(event.getUrlSource().isPresent())
		{
			audio.set("url", event.getUrlSource().get().getPath());
		}
		
		audio.set("format", event.getFormat().toString());
	}
}
