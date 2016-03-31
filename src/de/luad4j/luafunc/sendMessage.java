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

package de.luad4j.luafunc;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import de.luad4j.Main;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;

public class sendMessage extends VarArgFunction
{
	@Override
	public Varargs invoke(Varargs v) 
	{	
		Varargs returnval;
		
		try 
		{
			new MessageBuilder(Main.mDiscordClient).withChannel(v.tojstring(1)).withContent(v.tojstring(2)).build();
			returnval = LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(true), NIL});
		} 
		catch (HTTP429Exception e)
		{
			System.err.println("Sending messages too quickly");
			e.printStackTrace();
			returnval = LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(false), LuaValue.valueOf("Sending messages too quickly!")});
		} 
		catch (DiscordException e) 
		{
			System.err.println(e.getErrorMessage());
			e.printStackTrace();
			returnval = LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(false), LuaValue.valueOf(e.getErrorMessage())});
		} 
		catch (MissingPermissionsException e) 
		{
			System.err.println("Missing permissions for channel!");
			e.printStackTrace();
			returnval = LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(false), LuaValue.valueOf("Missing permissions for channel!")});
		}
		
		return returnval;
	}

}
