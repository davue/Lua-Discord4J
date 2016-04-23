/*
 * Lua-Discord4J - Lua wrapper for Discord4J Discord API
 * Copyright (C) 2016
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.luad4j.lua.obj;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

public class LuaChannel
{
	protected final IChannel mChannel;		// Channel inside Java
	protected final LuaValue mLuaChannel;	// Lua implementation of Channel
	
	protected static final Logger mLogger = LoggerFactory.getLogger(LuaChannel.class);	// Logger of this class
	
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
		mLuaChannel.set("getMessageByID", new GetMessageByID());
		mLuaChannel.set("getMessages", new GetMessages());
		mLuaChannel.set("getModifiedRolePermissions", new GetModifiedRolePermissions());
		mLuaChannel.set("getModifiedUserPermissions", new GetModifiedUserPermissions());
		mLuaChannel.set("getName", new GetName());
		mLuaChannel.set("getPosition", new GetPosition());
		mLuaChannel.set("getRoleOverrides", new GetRoleOverrides());
		mLuaChannel.set("getTopic", new GetTopic());
		mLuaChannel.set("getTypingStatus", new GetTypingStatus());
		mLuaChannel.set("getUserOverrides", new GetUserOverrides());
		mLuaChannel.set("isPrivate", new IsPrivate());
		mLuaChannel.set("mention", new Mention());
		mLuaChannel.set("overrideRolePermissions", new OverrideRolePermissions());
		mLuaChannel.set("overrideUserPermissions", new OverrideUserPermissions());
		mLuaChannel.set("removeRolePermissionsOverride", new RemoveRolePermissionsOverride());
		mLuaChannel.set("removeUserPermissionsOverride", new RemoveUserPermissionsOverride());
		mLuaChannel.set("sendFile", new SendFile());
		mLuaChannel.set("sendMessage", new SendMessage());
		mLuaChannel.set("toggleTypingStatus", new ToggleTypingStatus());
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
				mLogger.error(e.getMessage());
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
				mLogger.error(e.getMessage());
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
				mLogger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class CreateInvite extends VarArgFunction
	{
		@Override
		public LuaValue invoke(Varargs args)
		{
			try
			{
				return (new LuaInvite(mChannel.createInvite(args.toint(1), args.toint(2), args.toboolean(3), args.toboolean(4)))).getTable();
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				mLogger.error(e.getMessage());
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
				mChannel.delete();
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				mLogger.error(e.getMessage());
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
	
	private class GetMessageByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue messageID)
		{
			return (new LuaMessage(mChannel.getMessageByID(messageID.tojstring()))).getTable();
		}
	}
	
	private class GetMessages extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return (new LuaMessageList(mChannel.getMessages())).getTable();
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
				mLogger.error(e.getMessage());
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
				mLogger.error(e.getMessage());
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
				mLogger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetTopic extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mChannel.getTopic());
		}
	}
	
	private class GetTypingStatus extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mChannel.getTypingStatus());
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
				mLogger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class IsPrivate extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mChannel.isPrivate());
		}
	}
	
	private class Mention extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mChannel.mention());
		}
	}
	
	private class OverrideRolePermissions extends ThreeArgFunction
	{
		@Override
		public LuaValue call(LuaValue roleID, LuaValue toAddTable, LuaValue toRemoveTable)
		{
			try
			{
				// Parse permissions from lua to java
				LuaValue k = LuaValue.NIL;
				
				EnumSet<Permissions> toAdd = EnumSet.noneOf(Permissions.class);
				while ( true ) 
				{
					Varargs n = toAddTable.next(k);
					if ( (k = n.arg1()).isnil() )
						break;
					LuaValue v = n.arg(2);
					toAdd.add(Permissions.valueOf(v.tojstring()));
				}
				
				k = LuaValue.NIL;
				EnumSet<Permissions> toRemove = EnumSet.noneOf(Permissions.class);
				while ( true ) 
				{
					Varargs n = toRemoveTable.next(k);
					if ( (k = n.arg1()).isnil() )
						break;
					LuaValue v = n.arg(2);
					toRemove.add(Permissions.valueOf(v.tojstring()));
				}
				
				mChannel.overrideRolePermissions(mChannel.getGuild().getRoleByID(roleID.tojstring()), toAdd, toRemove);
			}
			catch (NullPointerException | IllegalArgumentException | LuaError | MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				mLogger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class OverrideUserPermissions extends ThreeArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID, LuaValue toAddTable, LuaValue toRemoveTable)
		{
			try
			{
				// Parse permissions from lua to java
				LuaValue k = LuaValue.NIL;
				
				EnumSet<Permissions> toAdd = EnumSet.noneOf(Permissions.class);
				while ( true ) 
				{
					Varargs n = toAddTable.next(k);
					if ( (k = n.arg1()).isnil() )
						break;
					LuaValue v = n.arg(2);
					toAdd.add(Permissions.valueOf(v.tojstring()));
				}
				
				k = LuaValue.NIL;
				EnumSet<Permissions> toRemove = EnumSet.noneOf(Permissions.class);
				while ( true ) 
				{
					Varargs n = toRemoveTable.next(k);
					if ( (k = n.arg1()).isnil() )
						break;
					LuaValue v = n.arg(2);
					toRemove.add(Permissions.valueOf(v.tojstring()));
				}
				
				mChannel.overrideUserPermissions(Main.mDiscordClient.getUserByID(userID.tojstring()), toAdd, toRemove);
			}
			catch (NullPointerException | IllegalArgumentException | LuaError | MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				mLogger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class RemoveRolePermissionsOverride extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue roleID)
		{
			try
			{
				mChannel.removePermissionsOverride(mChannel.getGuild().getRoleByID(roleID.tojstring()));
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				mLogger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class RemoveUserPermissionsOverride extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			try
			{
				mChannel.removePermissionsOverride(Main.mDiscordClient.getUserByID(userID.tojstring()));
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				mLogger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class SendFile extends VarArgFunction
	{
		@Override
		public LuaValue invoke(Varargs args)
		{
			try
			{
				File file = new File(args.tojstring(1));
				
				if (args.narg() == 1)
				{
					mChannel.sendFile(file);
				}
				else if(args.narg() > 1)
				{
					mChannel.sendFile(file, args.tojstring(2));
				}
			}
			catch (NullPointerException | IOException | MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				mLogger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}

			return LuaValue.NIL;
		}
	}
	
	private class SendMessage extends VarArgFunction
	{
		@Override
		public LuaValue invoke(Varargs args)
		{
			try
			{
				if (args.narg() == 1)
				{
					mChannel.sendMessage(args.tojstring(1));
				}
				else if(args.narg() > 1)
				{
					mChannel.sendMessage(args.tojstring(1), args.toboolean(2));
				}
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				mLogger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}

			return LuaValue.NIL;
		}
	}
	
	private class ToggleTypingStatus extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			mChannel.toggleTypingStatus();
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
					mLogger.error(e.getMessage());
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
					mLogger.error(e.getMessage());
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
