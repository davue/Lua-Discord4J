package de.luad4j.lua.obj;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;
import sx.blah.discord.handle.obj.IVoiceChannel;

public class LuaVoiceChannel extends LuaChannel
{	
	private IVoiceChannel mVoiceChannel;
	
	public LuaVoiceChannel(IVoiceChannel channel)
	{
		// Call parent constructor
		super(channel);
		mVoiceChannel = channel;
		
		super.mLuaChannel.set("getAudioChannel", new GetAudioChannel());
		super.mLuaChannel.set("isConnected", new IsConnected());
		super.mLuaChannel.set("join", new Join());
		super.mLuaChannel.set("leave", new Leave());
	}
	
	private class GetAudioChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				return (new LuaAudioChannel(mVoiceChannel.getAudioChannel())).getTable();
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class IsConnected extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mVoiceChannel.isConnected());
		}
	}
	
	private class Join extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			mVoiceChannel.join();
			return LuaValue.NIL;
		}
	}
	
	private class Leave extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			mVoiceChannel.leave();
			return LuaValue.NIL;
		}
	}
}
