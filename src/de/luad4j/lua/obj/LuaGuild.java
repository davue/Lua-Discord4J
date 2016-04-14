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
import java.util.Optional;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.luad4j.Main;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.MissingPermissionsException;

public class LuaGuild
{
	private static IGuild mGuild;
	private static LuaValue mLuaGuild;
	private static final Logger logger = LoggerFactory.getLogger(LuaMessage.class);	// Logger of this class
	
	public LuaGuild(IGuild guild)
	{
		mGuild = guild;
		
		mLuaGuild = LuaValue.tableOf();	
		mLuaGuild.set("banUser", new BanUser());
		mLuaGuild.set("changeAFKChannel", new ChangeAFKChannel());
		mLuaGuild.set("changeAFKTimeout", new ChangeAFKTimeout());
		mLuaGuild.set("changeIcon", new ChangeIcon());
		mLuaGuild.set("changeName", new ChangeName());
		mLuaGuild.set("changeRegion", new ChangeRegion());
		mLuaGuild.set("changeChannel", new CreateChannel());
		mLuaGuild.set("createRole", new CreateRole());
		mLuaGuild.set("createVoiceChannel", new CreateVoiceChannel());
	}
	
	private class BanUser extends VarArgFunction
	{
		public LuaValue invoke(Varargs args)
		{
			try
			{
				if(args.isnumber(args.toint(2))) // If there are 2 arguments
				{
					mGuild.banUser(Main.mDiscordClient.getUserByID(args.tojstring(1)), args.toint(2));
				}
				else
				{
					mGuild.banUser(Main.mDiscordClient.getUserByID(args.tojstring(1)));
				}
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangeAFKChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelID)
		{
			try
			{
				mGuild.changeAFKChannel(Optional.ofNullable(Main.mDiscordClient.getVoiceChannelByID(channelID.tojstring())));
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
			
			return LuaValue.NIL;
		}
	}

	private class ChangeAFKTimeout extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue timeout)
		{
			try
			{
				mGuild.changeAFKTimeout(timeout.toint());
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangeIcon extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue filepath)
		{
			File file = new File(filepath.tojstring(1));
			try
			{
				mGuild.changeIcon(Optional.ofNullable(Image.forFile(file)));
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangeName extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			try
			{
				mGuild.changeName(name.tojstring());
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangeRegion extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue regionID)
		{
			try
			{
				mGuild.changeRegion(Main.mDiscordClient.getRegionByID(regionID.tojstring()));
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class CreateChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			/*try
			{
				return new LuaChannel(mGuild.createChannel(name.tojstring()));
			}
			catch (DiscordException | MissingPermissionsException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}*/
			
			return LuaValue.NIL;
		}
	}
	
	private class CreateRole extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			/*try
			{
				return new LuaRole(mGuild.createRole());
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}*/

			return LuaValue.NIL;
		}
	}
	
	private class CreateVoiceChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			/*try
			{
				return new LuaVoiceChannel(mGuild.createVoiceChannel(name.tojstring()));
			}
			catch (DiscordException | MissingPermissionsException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}*/
			
			return LuaValue.NIL;
		}
	}
	
	private class DeleteGuild extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				mGuild.deleteGuild();
			}
			catch (DiscordException | HTTP429Exception | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class EditUserRoles extends TwoArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID, LuaValue roleIDTable)
		{
			IRole[] roles = new IRole[roleIDTable.length()];
			for(int i = 1; i < roleIDTable.length(); i++)
			{
				roles[i-1] = mGuild.getRoleByID(roleIDTable.get(i).tojstring());
			}
			
			try
			{
				mGuild.editUserRoles(Main.mDiscordClient.getUserByID(userID.tojstring()), roles);
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				return LuaValue.valueOf(e.getClass().getSimpleName() + ":" + e.getMessage());
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetAFKChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			//return (new LuaChannel(mGuild.getAFKChannel())).getTable();
			return LuaValue.NIL;
		}
	}
	
	private class GetAFKTimeout extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			LuaValue.valueOf(mGuild.getAFKTimeout());
		}
	}
	
	public LuaValue getTable()
	{
		mGuild.
		return mLuaGuild;
	}
}
