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

import de.luad4j.Main;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

public class LuaUser
{
	private IUser		mUser;			// User object inside Java
	private LuaValue	mLuaUser;		// Table: User object inside Lua
	
	// Constructor
	public LuaUser(IUser user)
	{	
		mUser = user;
		
		// Init Lua
		mLuaUser = LuaValue.tableOf();
		mLuaUser.set("getAvatar", new getAvatar());
		mLuaUser.set("getAvatarURL", new getAvatarURL());
		mLuaUser.set("getClient", new getClient());
		mLuaUser.set("getCreationDate", new getCreationDate());
		mLuaUser.set("getDiscriminator", new getDiscriminator());
		mLuaUser.set("getGame", new getGame());
		mLuaUser.set("getID", new getID());
		mLuaUser.set("getName", new getName());
		mLuaUser.set("getPresence", new getPresence());
		mLuaUser.set("getRolesForGuildID", new getRolesForGuildID());
		mLuaUser.set("getVoiceChannel", new getVoiceChannel());
		mLuaUser.set("isBot", new isBot());
		mLuaUser.set("mention", new mention());
		mLuaUser.set("moveToVoiceChannel", new moveToVoiceChannel());
	}
	
	class getAvatar extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getAvatar());
		}
	}
	
	class getAvatarURL extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getAvatarURL());
		}
	}
	
	// TODO: implement LuaClient object
	class getClient extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			//return (new LuaClient(mUser.getClient())).getTable();
			return LuaValue.NIL;
		}
	}
	
	class getCreationDate extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getCreationDate().toString());
		}
	}
	
	class getDiscriminator extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getDiscriminator());
		}
	}
	
	class getGame extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			if(mUser.getGame().isPresent())
			{
				return LuaValue.valueOf(mUser.getGame().get());
			}
			return LuaValue.NIL;
		}
	}
	
	class getID extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getID());
		}
	}
	
	class getName extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getName());
		}
	}
	
	// TODO: implement LuaPresence object
	class getPresence extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			//return (new LuaPresence(mMain.getPresence())).getTable();
			return LuaValue.NIL;
		}
	}
	
	// TODO: somehow implement a list in lua
	class getRolesForGuildID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue guildid) 
		{
			//return LuaValue.valueOf(mUser.getPresence());
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaVoiceChannel object
	class getVoiceChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			if(mUser.getVoiceChannel().isPresent())
			{
				//return (new LuaVoiceChannel(mUser.getVoiceChannel().get())).getTable();
				return LuaValue.NIL;
			}
			return LuaValue.NIL;
		}
	}
	
	class isBot extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.isBot());
		}
	}
	
	class mention extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.mention());
		}
	}
	
	class moveToVoiceChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelid) 
		{
			try 
			{
				mUser.moveToVoiceChannel(Main.mDiscordClient.getVoiceChannelByID(channelid.tojstring()));
			} 
			catch (DiscordException e) 
			{
				return LuaValue.valueOf("DiscordException");
			} 
			catch (HTTP429Exception e) 
			{
				return LuaValue.valueOf("HTTP429Exception");
			} 
			catch (MissingPermissionsException e) 
			{
				return LuaValue.valueOf("MissingPermissionsException");
			}

			return LuaValue.NIL;
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaUser;
	}
}
