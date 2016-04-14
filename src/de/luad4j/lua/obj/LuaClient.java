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

import java.io.File;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.luad4j.Main;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.Image;

// Client is a global lua table -> no getClient() lua implementation needed for other objects

public class LuaClient
{
	private static IDiscordClient mClient; // Client object inside Java
	private static LuaValue mLuaClient; // Table: Client object inside Lua

	public LuaClient()
	{
		// Init only once
		if (mClient == null)
		{
			mClient = Main.mDiscordClient;
		}

		if (mLuaClient == null)
		{
			// Init Lua
			mLuaClient = LuaValue.tableOf();
		}
	}

	static class changeAvatar extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue filepath)
		{
			if (filepath != LuaValue.NIL)
			{
				File file = new File(filepath.tojstring());
				try
				{
					mClient.changeAvatar(Image.forFile(file));
					return LuaValue.NIL;
				}
				catch (DiscordException | HTTP429Exception e)
				{
					return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
				}
			}
			else
			{
				try
				{
					mClient.changeAvatar(Image.defaultAvatar());
					return LuaValue.NIL;
				}
				catch (DiscordException | HTTP429Exception e)
				{
					return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
				}
			}
		}
	}

	static class changeEmail extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue email)
		{
			try
			{
				mClient.changeEmail(email.tojstring());
				return LuaValue.NIL;
			}
			catch (DiscordException | HTTP429Exception e)
			{
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
		}
	}

	static class changePassword extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue password)
		{
			try
			{
				mClient.changePassword(password.tojstring());
				return LuaValue.NIL;
			}
			catch (DiscordException | HTTP429Exception e)
			{
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
		}
	}

	static class changeUsername extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue username)
		{
			try
			{
				mClient.changeUsername(username.tojstring());
				return LuaValue.NIL;
			}
			catch (DiscordException | HTTP429Exception e)
			{
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
		}
	}

	// TODO: get a region from somewhere
	static class createGuild extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue guildname)
		{
			//mClient.createGuild(name, (new Region()))
			return LuaValue.NIL;
		}
	}

	static public LuaValue getTable()
	{
		return mLuaClient;
	}
}
