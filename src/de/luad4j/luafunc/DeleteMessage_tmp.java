package de.luad4j.luafunc;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import de.luad4j.Main;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

public class DeleteMessage extends TwoArgFunction 
{
	@Override
	public LuaValue call(LuaValue channelid, LuaValue messageid) 
	{
		try 
		{
			Main.mDiscordClient.getChannelByID(channelid.tojstring()).getMessageByID(messageid.tojstring()).delete();
			return LuaValue.NIL;
		} 
		catch (HTTP429Exception e)
		{
			return LuaValue.valueOf("TooManyRequests");
		} 
		catch (DiscordException e) 
		{
			return LuaValue.valueOf("DiscordException");
		} 
		catch (MissingPermissionsException e) 
		{
			return LuaValue.valueOf("MissingPermissions");
		}
	}
}
