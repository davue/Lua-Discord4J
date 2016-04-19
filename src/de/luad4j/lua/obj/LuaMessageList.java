package de.luad4j.lua.obj;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import sx.blah.discord.util.MessageList;

public class LuaMessageList
{
	private MessageList 	mMessageList;
	private LuaValue		mLuaMessageList;
	
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
