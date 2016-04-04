package de.luad4j.luafunc;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.luad4j.Main;

public class LeaveVoiceChannel extends OneArgFunction 
{
	@Override
	public LuaValue call(LuaValue voicechannelid) 
	{
		Main.mDiscordClient.getVoiceChannelByID(voicechannelid.tojstring()).leave();
		return null;
	}
}