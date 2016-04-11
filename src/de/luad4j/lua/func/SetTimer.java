package de.luad4j.lua.func;

import java.util.Timer;
import java.util.TimerTask;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class SetTimer extends VarArgFunction
{
	@Override
	public LuaValue invoke(Varargs varargs) 
	{
		Timer timer = new Timer();
		
		try
		{
			timer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					varargs.arg(2).checkfunction().invoke(varargs.subargs(3));
				}
			}, varargs.arg(1).checklong());
		}
		catch(LuaError e)
		{
			return LuaValue.valueOf("LuaError");
		}
		
		return LuaValue.NIL;
	}
}
