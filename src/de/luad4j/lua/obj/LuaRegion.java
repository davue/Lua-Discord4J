package de.luad4j.lua.obj;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import sx.blah.discord.handle.obj.IRegion;

public class LuaRegion
{
	private IRegion 	mRegion;
	private LuaValue 	mLuaRegion;
	
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
