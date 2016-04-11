package de.luad4j.lua.func.audio;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.luad4j.Main;

public class IsConnectedToVoiceChannel extends OneArgFunction
{
	@Override
	public LuaValue call(LuaValue voicechannelid) 
	{
		if(Main.mDiscordClient.getVoiceChannelByID(voicechannelid.tojstring()).isConnected())
		{
			return LuaValue.TRUE;
		}
		else
		{
			return LuaValue.FALSE;
		}
	}
}