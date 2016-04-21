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

import java.util.Timer;
import java.util.TimerTask;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class SetTimer extends VarArgFunction
{
	@Override
	public LuaValue invoke(Varargs varargs) 
	{
		Timer timer = new Timer();
		
		try
		{
			timer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					varargs.arg(2).checkfunction().invoke(varargs.subargs(3));
				}
			}, varargs.arg(1).checklong());
		}
		catch(LuaError e)
		{
			return LuaValue.valueOf("LuaError");
		}
		
		return LuaValue.NIL;
	}
}