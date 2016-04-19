package de.luad4j.lua.obj;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IInvite;
import sx.blah.discord.handle.obj.Permissions;
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
		mLuaChannel.set("delete", new Delete());
		mLuaChannel.set("getCreationDate", new GetCreationDate());
		mLuaChannel.set("getGuild", new GetGuild());
		mLuaChannel.set("getID", new GetID());
		mLuaChannel.set("getInvites", new GetInvites());
		mLuaChannel.set("getLastReadMessage", new GetLastReadMessage());
		mLuaChannel.set("getLastReadMessageID", new GetLastReadMessageID());
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
	
	private class Delete extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				mChannel.delete();
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetCreationDate extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mChannel.getCreationDate().toString());
		}
	}
	
	private class GetGuild extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return (new LuaGuild(mChannel.getGuild())).getTable();
		}
	}
	
	private class GetID extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mChannel.getID());
		}
	}
	
	private class GetInvites extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			/*try
			{
				List<IInvite> invites = mChannel.getInvites();
				LuaValue luaInvite = LuaValue.tableOf();
				for(IInvite invite : invites)
				{
					LuaValue.set(luaInvite.length(), (new LuaInvite(invite)).getTable());
				}
			}
			catch (LuaError | DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}*/
			
			return LuaValue.NIL;
		}
	}
	
	private class GetLastReadMessage extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return (new LuaMessage(mChannel.getLastReadMessage())).getTable();
		}
	}
	
	private class GetLastReadMessageID extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mChannel.getLastReadMessageID());
		}
	}
	
	private class GetMessageByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue messageID)
		{
			return (new LuaMessage(mChannel.getMessageByID(messageID.tojstring()))).getTable();
		}
	}
	
	// TODO: implement MessageList
	private class GetMessages extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.NIL;
		}
	}
	
	private class GetModifiedRolePermissions extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue roleID)
		{
			try
			{
				EnumSet<Permissions> permissions = mChannel.getModifiedPermissions(mChannel.getGuild().getRoleByID(roleID.tojstring()));
				LuaValue luaPermissions = LuaValue.tableOf();
				
				for (Permissions permission : permissions)
				{
					luaPermissions.set(luaPermissions.length()+1, permission.name());
				}
				
				return luaPermissions;
			}
			catch (LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetModifiedUserPermissions extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			try
			{
				EnumSet<Permissions> permissions = mChannel.getModifiedPermissions(Main.mDiscordClient.getUserByID(userID.tojstring()));
				LuaValue luaPermissions = LuaValue.tableOf();
				
				for (Permissions permission : permissions)
				{
					luaPermissions.set(luaPermissions.length()+1, permission.name());
				}
				
				return luaPermissions;
			}
			catch (LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetName extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mChannel.getName());
		}
	}
	
	private class GetPosition extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mChannel.getPosition());
		}
	}
	
	private class GetRoleOverrides extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				Map<String,IChannel.PermissionOverride> permissionOverrides = mChannel.getRoleOverrides();
				LuaValue luaPermissionOverrides = LuaValue.tableOf();
				
				for(Map.Entry<String,IChannel.PermissionOverride> permissionOverride : permissionOverrides.entrySet())
				{
					luaPermissionOverrides.set(permissionOverride.getKey(), (new LuaPermissionOverride(permissionOverride.getValue())).getTable());
				}
				
				return luaPermissionOverrides;
			}
			catch (LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetUserOverrides extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				Map<String,IChannel.PermissionOverride> permissionOverrides = mChannel.getUserOverrides();
				LuaValue luaPermissionOverrides = LuaValue.tableOf();
				
				for(Map.Entry<String,IChannel.PermissionOverride> permissionOverride : permissionOverrides.entrySet())
				{
					luaPermissionOverrides.set(permissionOverride.getKey(), (new LuaPermissionOverride(permissionOverride.getValue())).getTable());
				}
				
				return luaPermissionOverrides;
			}
			catch (LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class LuaPermissionOverride
	{
		private IChannel.PermissionOverride mPermissionOverride;
		private LuaValue					mLuaPermissionOverride;
		
		public LuaPermissionOverride(IChannel.PermissionOverride permOverride)
		{
			mPermissionOverride = permOverride;
			
			mLuaPermissionOverride = LuaValue.tableOf();
			mLuaPermissionOverride.set("allow", new Allow());
			mLuaPermissionOverride.set("deny", new Deny());
		}
		
		public class Allow extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				try
				{
					EnumSet<Permissions> permissions = mPermissionOverride.allow();
					LuaValue luaPermissions = LuaValue.tableOf();
					
					for (Permissions permission : permissions)
					{
						luaPermissions.set(luaPermissions.length()+1, permission.name());
					}
					
					return luaPermissions;
				}
				catch (LuaError e)
				{
					logger.error(e.getMessage());
					Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
				}
				
				return LuaValue.NIL;
			}
		}
		
		public class Deny extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				try
				{
					EnumSet<Permissions> permissions = mPermissionOverride.deny();
					LuaValue luaPermissions = LuaValue.tableOf();
					
					for (Permissions permission : permissions)
					{
						luaPermissions.set(luaPermissions.length()+1, permission.name());
					}
					
					return luaPermissions;
				}
				catch (LuaError e)
				{
					logger.error(e.getMessage());
					Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
				}
				
				return LuaValue.NIL;
			}
		}
		
		public LuaValue getTable()
		{
			return mLuaPermissionOverride;
		}
	}

	public LuaValue getTable()
	{
		return mLuaChannel;
	}
}
