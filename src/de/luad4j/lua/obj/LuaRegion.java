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
import org.luaj.vm2.lib.ZeroArgFunction;

import sx.blah.discord.handle.obj.IRegion;

public class LuaRegion
{
	private final IRegion 	mRegion;	// Region object inside Java
	private final LuaValue 	mLuaRegion;	// Lua implementation of Region
	
	public LuaRegion(IRegion region)
	{
		mRegion = region;
		
		// Init Lua
		mLuaRegion = LuaValue.tableOf();
		mLuaRegion.set("getID", new GetID());
		mLuaRegion.set("getName", new GetName());
		mLuaRegion.set("isVIPOnly", new IsVIPOnly());
	}
	
	private class GetID extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mRegion.getID());
		}
	}
	
	private class GetName extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mRegion.getName());
		}
	}
	
	private class IsVIPOnly extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mRegion.isVIPOnly());
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaRegion;
	}
}
