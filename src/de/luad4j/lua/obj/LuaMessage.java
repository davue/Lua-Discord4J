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
	}
	
	class acknowledge extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			try 
			{
				mMessage.acknowledge();
			} 
			catch (HTTP429Exception e) 
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf("HTTP429Exception");
			} 
			catch (DiscordException e) 
			{
				logger.error(e.getErrorMessage());
				return LuaValue.valueOf("DiscordException");
			}
			
			return LuaValue.NIL;
		}
	}
	
	class delete extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			try 
			{
				mMessage.delete();
			} 
			catch (MissingPermissionsException e) 
			{
				logger.error(e.getErrorMessage());
				return LuaValue.valueOf("MissingPermissionsException");
			} 
			catch (HTTP429Exception e) 
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf("HTTP429Exception");
			} 
			catch (DiscordException e) 
			{
				logger.error(e.getErrorMessage());
				return LuaValue.valueOf("DiscordException");
			}
			
			return LuaValue.NIL;
		}
	}
	
	class edit extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue content) 
		{
			try 
			{
				return (new LuaMessage(mMessage.edit(content.tojstring()))).getTable(); // Return a new message object containing the edited message
			} 
			catch (MissingPermissionsException e) 
			{
				logger.error(e.getErrorMessage());
				return LuaValue.valueOf("MissingPermissionsException");
			} 
			catch (HTTP429Exception e) 
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf("HTTP429Exception");
			} 
			catch (DiscordException e) 
			{
				logger.error(e.getErrorMessage());
				return LuaValue.valueOf("DiscordException");
			}
		}
	}
	
	// TODO: needs implementation of List and IMessage.Attachment
	class getAttachments extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			// TODO Auto-generated method stub
			return LuaValue.NIL;
		}
	}
	
	class getAuthor extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return (new LuaUser(mMessage.getAuthor())).getTable();
		}
	}
	
	// TODO: implement LuaChannel
	class getChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			//return (new LuaChannel(mMessage.getChannel())).getTable();
			return LuaValue.NIL;
		}
	}
	
	// getClient() not needed
	
	class getContent extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.getContent());
		}
	}
	
	class getCreationDate extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.getCreationDate().toString());
		}
	}
	
	class getEditedTimestamp extends ZeroArgFunction
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
	class getGuild extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			//return (new LuaGuild(mMessage.getGuild())).getTable();
			return LuaValue.NIL;
		}
	}
	
	class getID extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.getID());
		}
	}
	
	// TODO: implement List
	class getMentions extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			//return LuaValue.valueOf(mMessage.getMentions());
			return LuaValue.NIL;
		}
	}	
	
	class getTimestamp extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.getTimestamp().toString());
		}
	}

	class isAcknowledged extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.isAcknowledged());
		}
	}
	
	class mentionsEveryone extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mMessage.mentionsEveryone());
		}
	}
	
	class reply extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue content) 
		{
			try 
			{
				mMessage.reply(content.tojstring());
				return LuaValue.NIL;
			} 
			catch (MissingPermissionsException e) 
			{
				logger.error(e.getErrorMessage());
				return LuaValue.valueOf("MissingPermissionsException");
			} 
			catch (HTTP429Exception e) 
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf("HTTP429Exception");
			} 
			catch (DiscordException e) 
			{
				logger.error(e.getErrorMessage());
				return LuaValue.valueOf("DiscordException");
			}
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaMessage;
	}
}
