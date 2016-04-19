package de.luad4j.lua.obj;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;

import java.awt.Color;
import java.util.EnumSet;

import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;

public class LuaRole
{
	private IRole 		mRole;
	private LuaValue 	mLuaRole;
	
	private static final Logger logger = LoggerFactory.getLogger(LuaMessage.class);	// Logger of this class
	
	public LuaRole(IRole role)
	{
		mRole = role;
		
		// Init Lua
		mLuaRole = LuaValue.tableOf();
		mLuaRole.set("changeColor", new ChangeColor());
		mLuaRole.set("changeHoist", new ChangeHoist());
		mLuaRole.set("changeName", new ChangeName());
		mLuaRole.set("changePermissions", new ChangePermissions());
		mLuaRole.set("delete", new Delete());
		mLuaRole.set("getColor", new GetColor());
		mLuaRole.set("getCreationDate", new GetCreationDate());
		mLuaRole.set("getGuild", new GetGuild());
		mLuaRole.set("getID", new GetID());
		mLuaRole.set("getName", new GetName());
		mLuaRole.set("getPermissions", new GetPermissions());
		mLuaRole.set("getPosition", new GetPosition());
		mLuaRole.set("isHoisted", new IsHoisted());
		mLuaRole.set("isManaged", new IsManaged());
	}
	
	private class ChangeColor extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue color)
		{
			try
			{
				mRole.changeColor(Color.decode(color.tojstring()));
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangeHoist extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue hoist)
		{
			try
			{
				mRole.changeHoist(hoist.toboolean());
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangeName extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			try
			{
				mRole.changeName(name.tojstring());
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangePermissions extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue luaPermissions)
		{
			try
			{
				// Parse permissions from lua to java
				LuaValue k = LuaValue.NIL;
				
				EnumSet<Permissions> permissions = EnumSet.noneOf(Permissions.class);
				while ( true ) 
				{
					Varargs n = luaPermissions.next(k);
					if ( (k = n.arg1()).isnil() )
						break;
					LuaValue v = n.arg(2);
					permissions.add(Permissions.valueOf(v.tojstring()));
				}
				
				mRole.changePermissions(permissions);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class Delete extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				mRole.delete();
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetColor extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mRole.getColor().getRGB());
		}
	}
	
	private class GetCreationDate extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mRole.getCreationDate().toString());
		}
	}
	
	private class GetGuild extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return (new LuaGuild(mRole.getGuild())).getTable();
		}
	}
	
	private class GetID extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mRole.getID());
		}
	}
	
	private class GetName extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mRole.getName());
		}
	}
	
	private class GetPermissions extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				EnumSet<Permissions> permissions = mRole.getPermissions();
				LuaValue luaPermissions = LuaValue.tableOf();
				
				for (Permissions permission : permissions)
				{
					luaPermissions.set(luaPermissions.length()+1, permission.name());
				}
				
				return luaPermissions;
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetPosition extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mRole.getPosition());
		}
	}
	
	private class IsHoisted extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mRole.isHoisted());
		}
	}
	
	private class IsManaged extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mRole.isManaged());
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaRole;
	}
}
