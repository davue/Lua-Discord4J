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

package de.luad4j.lua.func;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import de.luad4j.Main;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

public class DeleteMessage extends TwoArgFunction 
{
	@Override
	public LuaValue call(LuaValue channelid, LuaValue messageid) 
	{
		try 
		{
			Main.mDiscordClient.getChannelByID(channelid.tojstring()).getMessageByID(messageid.tojstring()).delete();
			return LuaValue.NIL;
		} 
		catch (HTTP429Exception e)
		{
			return LuaValue.valueOf("TooManyRequests");
		} 
		catch (DiscordException e) 
		{
			return LuaValue.valueOf("DiscordException");
		} 
		catch (MissingPermissionsException e) 
		{
			return LuaValue.valueOf("MissingPermissions");
		}
	}
}
