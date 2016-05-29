package de.luad4j.lua.obj;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.luad4j.lua.LuaHelper;
import sx.blah.discord.handle.obj.Status;

public class LuaStatus
{
	private final Status mStatus;
	private final LuaValue mLuaStatus;
	
	public LuaStatus(Status status)
	{
		mStatus = status;
		
		// Init Lua
		mLuaStatus = LuaValue.tableOf();
		mLuaStatus.set("getStatusMessage", new GetStatusMessage());
		mLuaStatus.set("getType", new GetType());
		mLuaStatus.set("getURL", new GetURL());
		mLuaStatus.set("isEmtpy", new IsEmtpy());
	}
	
	// Do not implement builder methods as they are used directly in the client object
	
	private class GetStatusMessage extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mStatus.getStatusMessage());
			});
		}
	}
	
	private class GetType extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mStatus.getType().name());
			});
		}
	}
	
	private class GetURL extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mStatus.getUrl().get());
			});
		}
	}
	
	private class IsEmtpy extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mStatus.isEmpty());
			});
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaStatus;
	}
}
