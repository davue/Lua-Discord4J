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
import java.util.List;
import java.util.Optional;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;
import de.luad4j.events.LuaErrorEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IInvite;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.MissingPermissionsException;

public class LuaGuild
{
	private static IGuild mGuild;
	private static LuaValue mLuaGuild;
	private static final Logger logger = LoggerFactory.getLogger(LuaMessage.class);	// Logger of this class
	
	public LuaGuild(IGuild guild)
	{
		mGuild = guild;
		
		mLuaGuild = LuaValue.tableOf();	
		mLuaGuild.set("banUser", new BanUser());
		mLuaGuild.set("changeAFKChannel", new ChangeAFKChannel());
		mLuaGuild.set("changeAFKTimeout", new ChangeAFKTimeout());
		mLuaGuild.set("changeIcon", new ChangeIcon());
		mLuaGuild.set("changeName", new ChangeName());
		mLuaGuild.set("changeRegion", new ChangeRegion());
		mLuaGuild.set("changeChannel", new CreateChannel());
		mLuaGuild.set("createRole", new CreateRole());
		mLuaGuild.set("createVoiceChannel", new CreateVoiceChannel());
		mLuaGuild.set("deleteGuild", new DeleteGuild());
		mLuaGuild.set("editUserRoles", new EditUserRoles());
		mLuaGuild.set("getAFKChannel", new GetAFKChannel());
		mLuaGuild.set("getAFKTimeout", new GetAFKTimeout());
		mLuaGuild.set("getAudioChannel", new GetAudioChannel());
		mLuaGuild.set("getBannedUsers", new GetBannedUsers());
		mLuaGuild.set("getChannelByID", new GetChannelByID());
		mLuaGuild.set("getChannels", new GetChannels());
		mLuaGuild.set("getCreationDate", new GetCreationDate());
		mLuaGuild.set("getEveryoneRole", new GetEveryoneRole());
		mLuaGuild.set("getIcon", new GetIcon());
		mLuaGuild.set("getIconURL", new GetIconURL());
		mLuaGuild.set("getID", new GetID());
		mLuaGuild.set("getInvites", new GetInvites());
		mLuaGuild.set("getName", new GetName());
		mLuaGuild.set("getOwner", new GetOwner());
		mLuaGuild.set("getOwnerID", new GetOwnerID());
		mLuaGuild.set("getRegion", new GetRegion());
		mLuaGuild.set("getRoleByID", new GetRoleByID());
		mLuaGuild.set("getRoles", new GetRoles());
		mLuaGuild.set("getUserByID", new GetUserByID());
		mLuaGuild.set("getUsers", new GetUsers());
		mLuaGuild.set("getUsersToBePruned", new GetUsersToBePruned());
		mLuaGuild.set("getVoiceChanelByID", new GetVoiceChannelByID());
		mLuaGuild.set("getVoiceChanels", new GetVoiceChannels());
		mLuaGuild.set("kickUser", new KickUser());
		mLuaGuild.set("leaveGuild", new LeaveGuild());
		mLuaGuild.set("pardonUser", new PardonUser());
		mLuaGuild.set("pruneUsers", new PruneUsers());
		mLuaGuild.set("transferOwnership", new TransferOwnership());
	}
	
	private class BanUser extends VarArgFunction
	{
		public LuaValue invoke(Varargs args)
		{
			try
			{
				if(args.isnumber(args.toint(2))) // If there are 2 arguments
				{
					mGuild.banUser(Main.mDiscordClient.getUserByID(args.tojstring(1)), args.toint(2));
				}
				else
				{
					mGuild.banUser(Main.mDiscordClient.getUserByID(args.tojstring(1)));
				}
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangeAFKChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelID)
		{
			try
			{
				mGuild.changeAFKChannel(Optional.ofNullable(Main.mDiscordClient.getVoiceChannelByID(channelID.tojstring())));
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}

	private class ChangeAFKTimeout extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue timeout)
		{
			try
			{
				mGuild.changeAFKTimeout(timeout.toint());
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangeIcon extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue filepath)
		{
			File file = new File(filepath.tojstring(1));
			try
			{
				mGuild.changeIcon(Optional.ofNullable(Image.forFile(file)));
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
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
				mGuild.changeName(name.tojstring());
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class ChangeRegion extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue regionID)
		{
			try
			{
				mGuild.changeRegion(Main.mDiscordClient.getRegionByID(regionID.tojstring()));
			}
			catch (HTTP429Exception | DiscordException | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaChannel
	private class CreateChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			/*try
			{
				return new LuaChannel(mGuild.createChannel(name.tojstring()));
			}
			catch (DiscordException | MissingPermissionsException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}*/
			
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaRole
	private class CreateRole extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			/*try
			{
				return new LuaRole(mGuild.createRole());
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}*/

			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaVoiceChannel
	private class CreateVoiceChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			/*try
			{
				return new LuaVoiceChannel(mGuild.createVoiceChannel(name.tojstring()));
			}
			catch (DiscordException | MissingPermissionsException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}*/
			
			return LuaValue.NIL;
		}
	}
	
	private class DeleteGuild extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				mGuild.deleteGuild();
			}
			catch (DiscordException | HTTP429Exception | MissingPermissionsException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class EditUserRoles extends TwoArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID, LuaValue roleIDTable)
		{
			IRole[] roles = new IRole[roleIDTable.length()];
			for(int i = 1; i < roleIDTable.length(); i++)
			{
				roles[i-1] = mGuild.getRoleByID(roleIDTable.get(i).tojstring());
			}
			
			try
			{
				mGuild.editUserRoles(Main.mDiscordClient.getUserByID(userID.tojstring()), roles);
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaChannel
	private class GetAFKChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			//return (new LuaChannel(mGuild.getAFKChannel())).getTable();
			return LuaValue.NIL;
		}
	}
	
	private class GetAFKTimeout extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mGuild.getAFKTimeout());
		}
	}
	
	// TODO: implement LuaAudioChannel
	private class GetAudioChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			/*try
			{
				return (new LuaAudioChannel(mGuild.getAudioChannel())).getTable();
			}
			catch(DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}*/
			
			return LuaValue.NIL;
		}
	}
	
	private class GetBannedUsers extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			List<IUser> bannedUsers;
			try
			{
				bannedUsers = mGuild.getBannedUsers();
				LuaValue luaBannedUsers = LuaValue.tableOf();
				for(IUser user : bannedUsers)
				{
					luaBannedUsers.set(luaBannedUsers.length()+1, (new LuaUser(user)).getTable());
				}
				
				return luaBannedUsers;
			}
			catch (HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaChannel
	private class GetChannelByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelID)
		{
			//return (new LuaChannel(mGuild.getChannelByID(channelID.tojstring()))).getTable();
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaChannel
	private class GetChannels extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			/*List<IChannel> channels = mGuild.getChannels();
			LuaValue luaChannels = LuaValue.tableOf();
			for(IChannel channel : channels)
			{
				luaChannels.set(luaChannels.length()+1, (new LuaChannel(channel)).getTable());
			}
			
			return luaChannels;*/
			
			return LuaValue.NIL;
		}
	}
	
	private class GetCreationDate extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mGuild.getCreationDate().toString());
		}
	}
	
	// TODO: implement LuaRole
	private class GetEveryoneRole extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			//return (new LuaRole(mGuild.getEveryoneRole())).getTable();
			return LuaValue.NIL;
		}
	}
	
	private class GetIcon extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mGuild.getIcon());
		}
	}
	
	private class GetIconURL extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mGuild.getIconURL());
		}
	}
	
	private class GetID extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mGuild.getID());
		}
	}
	
	// TODO: implement LuaInvite
	private class GetInvites extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			/*try
			{
				List<IInvite> invites = mGuild.getInvites();
				LuaValue luaInvites = LuaValue.tableOf();
				for(IInvite invite : invites)
				{
					luaInvites.set(luaInvites.length()+1, (new LuaInvite(invite)).getTable());
				}
				
				return luaInvites;
			}
			catch (DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			catch (LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new LuaErrorEvent(e.getMessage()));
			}*/
			
			return LuaValue.NIL;
		}
	}
	
	private class GetName extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mGuild.getName());
		}
	}
	
	private class GetOwner extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return (new LuaUser(mGuild.getOwner())).getTable();
		}
	}
	
	private class GetOwnerID extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mGuild.getOwnerID());
		}
	}
	
	// TODO: implement LuaRegion
	private class GetRegion extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			//return (new LuaRegion(mGuild.getRegion())).getTable();
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaRole
	private class GetRoleByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue roleID)
		{
			//return (new LuaRole(mGuild.getRoleByID(roleID.tojstring()))).getTable();
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaRole
	private class GetRoles extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			/*List<IRole> roles = mGuild.getRoles();
			LuaValue luaRoles = LuaValue.tableOf();
			for(IRole role : roles)
			{
				luaRoles.set(luaRoles.length()+1, (new LuaRole(role)).getTable());
			}
			
			return luaRoles;*/
			return LuaValue.NIL;
		}
	}
	
	private class GetUserByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue roleID)
		{
			return (new LuaUser(mGuild.getUserByID(roleID.tojstring()))).getTable();
		}
	}
	
	private class GetUsers extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				List<IUser> users = mGuild.getUsers();
				LuaValue luaUsers = LuaValue.tableOf();
				for(IUser user : users)
				{
					luaUsers.set(luaUsers.length()+1, (new LuaUser(user)).getTable());
				}
				
				return luaUsers;
			}
			catch(LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new LuaErrorEvent(e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetUsersToBePruned extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue days)
		{
			try
			{
				return LuaValue.valueOf(mGuild.getUsersToBePruned(days.toint()));
			}
			catch (DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetVoiceChannelByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelID)
		{
			//return (new LuaVoiceChannel(mGuild.getVoiceChannelByID(channelID.tojstring()))).getTable();
			return LuaValue.NIL;
		}
	}
	
	private class GetVoiceChannels extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			/*try
			{
				List<IVoiceChannel> channels = mGuild.getVoiceChannels();
				LuaValue luaChannels = LuaValue.tableOf();
				for(IVoiceChannel channel : channels)
				{
					luaChannels.set(luaChannels.length()+1, (new LuaVoiceChannel(channel)).getTable());
				}
				
				return luaChannels;
			}
			catch(LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new LuaErrorEvent(e.getMessage()));
			}*/
			
			return LuaValue.NIL;
		}
	}
	
	private class KickUser extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			try
			{
				mGuild.kickUser(Main.mDiscordClient.getUserByID(userID.tojstring()));
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class LeaveGuild extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				mGuild.leaveGuild();
			}
			catch (DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class PardonUser extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			try
			{
				mGuild.pardonUser(userID.tojstring());
			}
			catch (MissingPermissionsException | HTTP429Exception | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class PruneUsers extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue days)
		{
			try
			{
				mGuild.pruneUsers(days.toint());
			}
			catch (DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement reorderRoles()
	
	private class TransferOwnership extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			try
			{
				mGuild.transferOwnership(Main.mDiscordClient.getUserByID(userID.tojstring()));
			}
			catch (HTTP429Exception | MissingPermissionsException | DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaGuild;
	}
}
