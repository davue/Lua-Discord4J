package de.luad4j.luafunc;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.luad4j.Main;
import sx.blah.discord.util.DiscordException;

public class PauseAudio extends OneArgFunction
{

	@Override
	public LuaValue call(LuaValue voicechannelid) {
		
		try 
		{
			Main.mDiscordClient.getVoiceChannelByID(voicechannelid.tojstring()).getAudioChannel().pause();
			return LuaValue.NIL;
		} 
		catch (DiscordException e) 
		{
			e.printStackTrace();
			return LuaValue.valueOf("DiscordException");
		}
	}
}
