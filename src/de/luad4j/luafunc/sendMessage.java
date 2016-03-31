package de.luad4j.luafunc;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;

public class sendMessage extends VarArgFunction
{
	private IDiscordClient mClient;
	
	public sendMessage(IDiscordClient client)
	{
		mClient = client;
	}
	
	@Override
	public Varargs invoke(Varargs v) 
	{	
		Varargs returnval;
		
		try 
		{
			new MessageBuilder(mClient).withChannel(v.tojstring(1)).withContent(v.tojstring(2)).build();
			returnval = LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(true), NIL});
		} 
		catch (HTTP429Exception e)
		{
			System.err.println("Sending messages too quickly");
			e.printStackTrace();
			returnval = LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(false), LuaValue.valueOf("Sending messages too quickly!")});
		} 
		catch (DiscordException e) 
		{
			System.err.println(e.getErrorMessage());
			e.printStackTrace();
			returnval = LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(false), LuaValue.valueOf(e.getErrorMessage())});
		} 
		catch (MissingPermissionsException e) 
		{
			System.err.println("Missing permissions for channel!");
			e.printStackTrace();
			returnval = LuaValue.varargsOf(new LuaValue[] {LuaValue.valueOf(false), LuaValue.valueOf("Missing permissions for channel!")});
		}
		
		return returnval;
	}

}
