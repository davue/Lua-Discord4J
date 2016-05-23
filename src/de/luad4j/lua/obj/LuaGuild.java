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

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import de.luad4j.Main;
import de.luad4j.lua.LuaHelper;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IInvite;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.Image;

public class LuaGuild
{
	private final IGuild	mGuild;		// Guild object inside Java
	private final LuaValue 	mLuaGuild;	// Lua implementation of Guild
	
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
		mLuaGuild.set("getVoiceChannelByID", new GetVoiceChannelByID());
		mLuaGuild.set("getVoiceChannels", new GetVoiceChannels());
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
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				if (args.isnumber(args.toint(2))) // If there are 2 arguments
				{
					mGuild.banUser(Main.mDiscordClient.getUserByID(args.tojstring(1)), args.toint(2));
				}
				else
				{
					mGuild.banUser(Main.mDiscordClient.getUserByID(args.tojstring(1)));
				}
				return LuaValue.NIL;
			});
		}
	}
	
	private class ChangeAFKChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelID)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mGuild.changeAFKChannel(
						Optional.ofNullable(Main.mDiscordClient.getVoiceChannelByID(channelID.tojstring())));
				return LuaValue.NIL;
			});
		}
	}

	private class ChangeAFKTimeout extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue timeout)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mGuild.changeAFKTimeout(timeout.toint());
				return LuaValue.NIL;
			});
		}
	}
	
	private class ChangeIcon extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue filepath)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				File file = new File(filepath.tojstring(1));
				mGuild.changeIcon(Optional.ofNullable(Image.forFile(file)));
				return LuaValue.NIL;
			});
		}
	}
	
	private class ChangeName extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mGuild.changeName(name.tojstring());
				return LuaValue.NIL;
			});
		}
	}
	
	private class ChangeRegion extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue regionID)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mGuild.changeRegion(Main.mDiscordClient.getRegionByID(regionID.tojstring()));
				return LuaValue.NIL;
			});
		}
	}
	
	private class CreateChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				return (new LuaChannel(mGuild.createChannel(name.tojstring()))).getTable();
			});
		}
	}
	
	private class CreateRole extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				return (new LuaRole(mGuild.createRole())).getTable();
			});
		}
	}
	
	private class CreateVoiceChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				return (new LuaVoiceChannel(mGuild.createVoiceChannel(name.tojstring()))).getTable();
			});
		}
	}
	
	private class DeleteGuild extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mGuild.deleteGuild();
				return LuaValue.NIL;
			});
		}
	}
	
	private class EditUserRoles extends TwoArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID, LuaValue roleIDTable)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				IRole[] roles = new IRole[roleIDTable.length()];
				for (int i = 1; i < roleIDTable.length(); i++)
				{
					roles[i - 1] = mGuild.getRoleByID(roleIDTable.get(i).tojstring());
				}
				mGuild.editUserRoles(Main.mDiscordClient.getUserByID(userID.tojstring()), roles);
				return LuaValue.NIL;
			});
		}
	}
	
	private class GetAFKChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaChannel(mGuild.getAFKChannel())).getTable();
			});
		}
	}
	
	private class GetAFKTimeout extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mGuild.getAFKTimeout());
			});
		}
	}
	
	private class GetAudioChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaAudioChannel(mGuild.getAudioChannel())).getTable();
			});
		}
	}
	
	private class GetBannedUsers extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				List<IUser> bannedUsers;
				bannedUsers = mGuild.getBannedUsers();
				LuaValue luaBannedUsers = LuaValue.tableOf();
				for (IUser user : bannedUsers)
				{
					luaBannedUsers.set(luaBannedUsers.length() + 1, (new LuaUser(user)).getTable());
				}
				return luaBannedUsers;
			});
		}
	}
	
	private class GetChannelByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelID)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaChannel(mGuild.getChannelByID(channelID.tojstring()))).getTable();
			});
		}
	}
	
	private class GetChannels extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				List<IChannel> channels = mGuild.getChannels();
				LuaValue luaChannels = LuaValue.tableOf();
				for (IChannel channel : channels)
				{
					luaChannels.set(luaChannels.length() + 1, (new LuaChannel(channel)).getTable());
				}
				return luaChannels;
			});
		}
	}
	
	private class GetCreationDate extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mGuild.getCreationDate().toString());
			});
		}
	}
	
	private class GetEveryoneRole extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaRole(mGuild.getEveryoneRole())).getTable();
			});
		}
	}
	
	private class GetIcon extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mGuild.getIcon());
			});
		}
	}
	
	private class GetIconURL extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mGuild.getIconURL());
			});
		}
	}
	
	private class GetID extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mGuild.getID());
			});
		}
	}
	
	private class GetInvites extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				List<IInvite> invites = mGuild.getInvites();
				LuaValue luaInvites = LuaValue.tableOf();
				for (IInvite invite : invites)
				{
					luaInvites.set(luaInvites.length() + 1, (new LuaInvite(invite)).getTable());
				}
				return luaInvites;
			});
		}
	}
	
	private class GetName extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mGuild.getName());
			});
		}
	}
	
	private class GetOwner extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaUser(mGuild.getOwner())).getTable();
			});
		}
	}
	
	private class GetOwnerID extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mGuild.getOwnerID());
			});
		}
	}
	
	private class GetRegion extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaRegion(mGuild.getRegion())).getTable();
			});
		}
	}
	
	private class GetRoleByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue roleID)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaRole(mGuild.getRoleByID(roleID.tojstring()))).getTable();
			});
		}
	}
	
	private class GetRoles extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				List<IRole> roles = mGuild.getRoles();
				LuaValue luaRoles = LuaValue.tableOf();
				for (IRole role : roles)
				{
					luaRoles.set(luaRoles.length() + 1, (new LuaRole(role)).getTable());
				}
				return luaRoles;
			});
		}
	}
	
	private class GetUserByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue roleID)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaUser(mGuild.getUserByID(roleID.tojstring()))).getTable();
			});
		}
	}
	
	private class GetUsers extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				List<IUser> users = mGuild.getUsers();
				LuaValue luaUsers = LuaValue.tableOf();
				for (IUser user : users)
				{
					luaUsers.set(luaUsers.length() + 1, (new LuaUser(user)).getTable());
				}
				return luaUsers;
			});
		}
	}
	
	private class GetUsersToBePruned extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue days)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mGuild.getUsersToBePruned(days.toint()));
			});
		}
	}
	
	private class GetVoiceChannelByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelID)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaVoiceChannel(mGuild.getVoiceChannelByID(channelID.tojstring()))).getTable();
			});
		}
	}
	
	private class GetVoiceChannels extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				List<IVoiceChannel> channels = mGuild.getVoiceChannels();
				LuaValue luaChannels = LuaValue.tableOf();
				for (IVoiceChannel channel : channels)
				{
					luaChannels.set(luaChannels.length() + 1, (new LuaVoiceChannel(channel)).getTable());
				}
				return luaChannels;
			});
		}
	}
	
	private class KickUser extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mGuild.kickUser(Main.mDiscordClient.getUserByID(userID.tojstring()));
				return LuaValue.NIL;
			});
		}
	}
	
	private class LeaveGuild extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mGuild.leaveGuild();
				return LuaValue.NIL;
			});
		}
	}
	
	private class PardonUser extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mGuild.pardonUser(userID.tojstring());
				return LuaValue.NIL;
			});
		}
	}
	
	private class PruneUsers extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue days)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mGuild.pruneUsers(days.toint());
				return LuaValue.NIL;
			});
		}
	}
	
	// TODO: implement reorderRoles()
	
	private class TransferOwnership extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mGuild.transferOwnership(Main.mDiscordClient.getUserByID(userID.tojstring()));
				return LuaValue.NIL;
			});
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaGuild;
	}
}
