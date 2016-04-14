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

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sx.blah.discord.handle.obj.IMessage;
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
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
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
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
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
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
		}
	}
	
	// TODO: needs implementation of List and IMessage.Attachment
	private class GetAttachments extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			// TODO Auto-generated method stub
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
	
	// TODO: implement LuaChannel
	private class GetChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			//return (new LuaChannel(mMessage.getChannel())).getTable();
			return LuaValue.NIL;
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
	
	// TODO: implement LuaGuild
	private class GetGuild extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			//return (new LuaGuild(mMessage.getGuild())).getTable();
			return LuaValue.NIL;
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
	
	// TODO: implement List
	private class GetMentions extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			//return LuaValue.valueOf(mMessage.getMentions());
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
				return LuaValue.NIL;
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaMessage;
	}
}
