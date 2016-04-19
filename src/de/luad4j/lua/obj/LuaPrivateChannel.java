package de.luad4j.lua.obj;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import sx.blah.discord.handle.obj.IPrivateChannel;

public class LuaPrivateChannel extends LuaChannel
{
	private IPrivateChannel mPrivateChannel;
	
	public LuaPrivateChannel(IPrivateChannel channel)
	{
		// Call parent constructor
		super(channel);
		mPrivateChannel = channel;
		
		super.mLuaChannel.set("getRecipient", new GetRecipient());
	}
	
	private class GetRecipient extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return (new LuaUser(mPrivateChannel.getRecipient())).getTable();
		}
	}
}
