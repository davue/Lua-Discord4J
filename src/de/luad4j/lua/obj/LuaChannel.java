package de.luad4j.lua.obj;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

public class LuaChannel
{
	private IChannel mChannel;
	private LuaValue mLuaChannel;
	
	private static final Logger logger = LoggerFactory.getLogger(LuaMessage.class);	// Logger of this class
	
	public LuaChannel(IChannel channel)
	{
		mChannel = channel;
		
		// Init Lua
		mLuaChannel = LuaValue.tableOf();
		mLuaChannel.set("changeName", new ChangeName());
		mLuaChannel.set("changePosition", new ChangePosition());
		mLuaChannel.set("changeTopic", new ChangeTopic());
		mLuaChannel.set("createInvite", new CreateInvite());
	}
	
	private class ChangeName extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			try
			{
				mChannel.changeName(name.tojstring());
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangePosition extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue position)
		{
			try
			{
				mChannel.changePosition(position.toint());
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangeTopic extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			try
			{
				mChannel.changeTopic(name.tojstring());
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaInvite
	private class CreateInvite extends VarArgFunction
	{
		@Override
		public LuaValue invoke(Varargs args)
		{
			/*try
			{
				return (new LuaInvite(mChannel.createInvite(args.toint(1), args.toint(2), args.toboolean(3), args.toboolean(4)))).getTable();
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}*/
					
			return LuaValue.NIL;
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaChannel;
	}
}
