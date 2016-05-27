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

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import de.luad4j.Main;
import de.luad4j.lua.LuaHelper;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

public class LuaUser
{
	private final IUser		mUser;			// User object inside Java
	private final LuaValue	mLuaUser;		// Lua implementation of User
	
	// Constructor
	public LuaUser(IUser user)
	{	
		mUser = user;
		
		// Init Lua
		mLuaUser = LuaValue.tableOf();
		mLuaUser.set("getAvatar", new GetAvatar());
		mLuaUser.set("getAvatarURL", new GetAvatarURL());
		mLuaUser.set("getCreationDate", new GetCreationDate());
		mLuaUser.set("getDiscriminator", new GetDiscriminator());
		mLuaUser.set("getGame", new GetGame());
		mLuaUser.set("getID", new GetID());
		mLuaUser.set("getName", new GetName());
		mLuaUser.set("getPresence", new GetPresence());
		mLuaUser.set("getRolesForGuildID", new GetRolesForGuildID());
		mLuaUser.set("getVoiceChannel", new GetVoiceChannel());
		mLuaUser.set("isBot", new IsBot());
		mLuaUser.set("mention", new Mention());
		mLuaUser.set("moveToVoiceChannel", new MoveToVoiceChannel());
	}
	
	private class GetAvatar extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mUser.getAvatar());
			});
		}
	}
	
	private class GetAvatarURL extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mUser.getAvatarURL());
			});
		}
	}
	
	private class GetCreationDate extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mUser.getCreationDate().toString());
			});
		}
	}
	
	private class GetDiscriminator extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mUser.getDiscriminator());
			});
		}
	}
	
	private class GetGame extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				if (mUser.getGame().isPresent())
				{
					return LuaValue.valueOf(mUser.getGame().get());
				}
				return LuaValue.NIL;
			});
		}
	}
	
	private class GetID extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mUser.getID());
			});
		}
	}
	
	private class GetName extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mUser.getName());
			});
		}
	}
	
	private class GetPresence extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mUser.getPresence().name());
			});
		}
	}
	
	private class GetRolesForGuildID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue guildID) 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				List<IRole> roles = mUser.getRolesForGuild(Main.mDiscordClient.getGuildByID(guildID.tojstring()));
				LuaValue luaRoles = LuaValue.tableOf();
				for (IRole role : roles)
				{
					luaRoles.set(luaRoles.length() + 1, (new LuaRole(role)).getTable());
				}
				return luaRoles;
			});
		}
	}
	
	private class GetVoiceChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				if (mUser.getVoiceChannel().isPresent())
				{
					return (new LuaVoiceChannel(mUser.getVoiceChannel().get())).getTable();
				}
				return LuaValue.NIL;
			});
		}
	}
	
	private class IsBot extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mUser.isBot());
			});
		}
	}
	
	private class Mention extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mUser.mention());
			});
		}
	}
	
	private class MoveToVoiceChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelid) 
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mUser.moveToVoiceChannel(Main.mDiscordClient.getVoiceChannelByID(channelid.tojstring()));
				return LuaValue.NIL;
			});
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaUser;
	}
}
