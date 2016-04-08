package de.luad4j.luafunc;

import java.util.Optional;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import de.luad4j.Main;

public class SetGame extends OneArgFunction
{
	@Override
	public LuaValue call(LuaValue game) 
	{
		try
		{
			Main.mDiscordClient.updatePresence(true, Optional.of(game.tojstring()));
			return LuaValue.NIL;
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
			return LuaValue.valueOf("NullPointerException");
		}
	}
}
