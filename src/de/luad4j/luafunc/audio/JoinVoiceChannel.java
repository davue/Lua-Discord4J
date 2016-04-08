package de.luad4j.luafunc.audio;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.luad4j.Main;

public class JoinVoiceChannel extends OneArgFunction 
{
	@Override
	public LuaValue call(LuaValue voicechannelid) 
	{
		Main.mDiscordClient.getVoiceChannelByID(voicechannelid.tojstring()).join();
		return null;
	}
}