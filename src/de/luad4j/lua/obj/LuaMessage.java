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

package de.luad4j.lua.obj;

import java.util.List;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

public class LuaMessage 
{
	private IMessage 	mMessage;		// Message object inside Java
	private LuaValue 	mLuaMessage;	// Table: Message object inside Lua
	
	private static final Logger logger = LoggerFactory.getLogger(LuaMessage.class);	// Logger of this class
	
	public LuaMessage(IMessage message)
	{
		mMessage = message;
		
		// Init Lua
		mLuaMessage = LuaValue.tableOf();
		mLuaMessage.set("acknowledge", new Acknowledge());
		mLuaMessage.set("delete", new Delete());
		mLuaMessage.set("edit", new Edit());
		mLuaMessage.set("getAttachments", new GetAttachments());
		mLuaMessage.set("getAuthor", new GetAuthor());
		mLuaMessage.set("getChannel", new GetChannel());
		mLuaMessage.set("getContent", new GetContent());
		mLuaMessage.set("getCreationDate", new GetCreationDate());
		mLuaMessage.set("getEditedTimestamp", new GetEditedTimestamp());
		mLuaMessage.set("getGuild", new GetGuild());
		mLuaMessage.set("getID", new GetID());
		mLuaMessage.set("getMentions", new GetMentions());
		mLuaMessage.set("getTimestamp", new GetTimestamp());
		mLuaMessage.set("isAcknowledged", new IsAcknowledged());
		mLuaMessage.set("mentionsEveryone", new MentionsEveryone());
		mLuaMessage.set("reply", new Reply());
	}
	
	private class Acknowledge extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			try
			{
				mMessage.acknowledge();
			}
			catch (HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class Delete extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			try
			{
				mMessage.delete();
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class Edit extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue content) 
		{
			try
			{
				return (new LuaMessage(mMessage.edit(content.tojstring()))).getTable();
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetAttachments extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			try
			{
				List<IMessage.Attachment> messageAttachments = mMessage.getAttachments();
				LuaValue luaMessageAttachments = LuaValue.tableOf();
				
				for (IMessage.Attachment attachment : messageAttachments)
				{
					luaMessageAttachments.set(luaMessageAttachments.length()+1, (new Attachment(attachment)).getTable());
				}
				
				return luaMessageAttachments;
			}
			catch (LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetAuthor extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return (new LuaUser(mMessage.getAuthor())).getTable();
		}
	}

	private class GetChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return (new LuaChannel(mMessage.getChannel())).getTable();
		}
	}
	
	// getClient() not needed
	
	private class GetContent extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.getContent());
		}
	}
	
	private class GetCreationDate extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.getCreationDate().toString());
		}
	}
	
	private class GetEditedTimestamp extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			if(mMessage.getEditedTimestamp().isPresent())
			{
				return LuaValue.valueOf(mMessage.getEditedTimestamp().get().toString());
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetGuild extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return (new LuaGuild(mMessage.getGuild())).getTable();
		}
	}
	
	private class GetID extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.getID());
		}
	}
	
	private class GetMentions extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			try
			{
				List<IUser> users = mMessage.getMentions();
				LuaValue luaUsers = LuaValue.tableOf();
				for(IUser user : users)
				{
					luaUsers.set(luaUsers.length()+1, (new LuaUser(user)).getTable());
				}
			}
			catch(LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}	
	
	private class GetTimestamp extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.getTimestamp().toString());
		}
	}

	private class IsAcknowledged extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.isAcknowledged());
		}
	}
	
	private class MentionsEveryone extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.mentionsEveryone());
		}
	}
	
	private class Reply extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue content) 
		{
			try
			{
				mMessage.reply(content.tojstring());
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class Attachment
	{
		private IMessage.Attachment mMessageAttachment;
		private LuaValue			mLuaMessageAttachment;
		
		public Attachment(IMessage.Attachment messageAttachment)
		{
			mMessageAttachment = messageAttachment;
			
			// Init Lua
			mLuaMessageAttachment = LuaValue.tableOf();
			mLuaMessageAttachment.set("getFilename", new GetFilename());
			mLuaMessageAttachment.set("getFilesize", new GetFilesize());
			mLuaMessageAttachment.set("getID", new GetID());
			mLuaMessageAttachment.set("getURL", new GetURL());
		}
		
		private class GetFilename extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mMessageAttachment.getFilename());
			}
		}
		
		private class GetFilesize extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mMessageAttachment.getFilesize());
			}
		}
		
		private class GetID extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mMessageAttachment.getId());
			}
		}
		
		private class GetURL extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mMessageAttachment.getUrl());
			}
		}
		
		public LuaValue getTable()
		{
			return mLuaMessageAttachment;
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaMessage;
	}
}
