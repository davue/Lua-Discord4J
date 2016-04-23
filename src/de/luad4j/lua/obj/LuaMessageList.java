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

import sx.blah.discord.util.MessageList;

public class LuaMessageList
{
	private final MessageList 	mMessageList;		// MessageList object inside Java
	private final LuaValue		mLuaMessageList;	// Lua implementation of MessageList
	
	public LuaMessageList(MessageList messageList)
	{
		mMessageList = messageList;
		
		// Init Lua
		mLuaMessageList = LuaValue.tableOf();
		mLuaMessageList.set("get", new Get());
	}
	
	private class Get extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue messageID)
		{
			return (new LuaMessage(mMessageList.get(messageID.tojstring()))).getTable();
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaMessageList;
	}
}
