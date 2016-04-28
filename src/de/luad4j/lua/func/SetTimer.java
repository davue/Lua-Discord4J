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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;

public class SetTimer extends VarArgFunction
{
	private static final Logger mLogger = LoggerFactory.getLogger(SetTimer.class);	// Logger of this class
	
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
					varargs.arg(2).checkfunction().invoke(varargs.subargs(3)); // Call lua function with varargs
				}
			}, varargs.arg(1).checklong());
		}
		catch(LuaError e)
		{
			mLogger.error(e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
		
		return LuaValue.NIL;
	}
}