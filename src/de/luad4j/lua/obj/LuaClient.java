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
import java.util.Collection;
import java.util.List;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

import de.luad4j.Main;
import de.luad4j.lua.LuaHelper;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRegion;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.audio.AudioPlayer;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Status;

// Client is a global lua table -> no getClient() lua implementation needed for other objects

public class LuaClient
{
	private static final IDiscordClient 	mClient = Main.mDiscordClient; 		// Client object inside Java
	private static final LuaValue 			mLuaClient = LuaValue.tableOf(); 	// Table: Client object inside Lua
	
	public LuaClient()
	{
		// Init Lua
		mLuaClient.set("changeAvatar", new ChangeAvatar());
		mLuaClient.set("changeEmail", new ChangeEmail());
		mLuaClient.set("changePassword", new ChangePassword());
		mLuaClient.set("changePresence", new ChangePresence());
		mLuaClient.set("changeUsername", new ChangeUsername());
		mLuaClient.set("createGuild", new CreateGuild());
		mLuaClient.set("getChannelByID", new GetChannelByID());
		mLuaClient.set("getChannels", new GetChannels());
		mLuaClient.set("getConnectedVoiceChannels", new GetConnectedVoiceChannels());
		mLuaClient.set("getGuildByID", new GetGuildByID());
		mLuaClient.set("getGuilds", new GetGuilds());
		mLuaClient.set("getInviteForCode", new GetInviteForCode());
		mLuaClient.set("getLaunchTime", new GetLaunchTime());
		mLuaClient.set("getOrCreatePMChannel", new GetOrCreatePMChannel());
		mLuaClient.set("getOurUser", new GetOurUser());
		mLuaClient.set("getRegionByID", new GetRegionByID());
		mLuaClient.set("getRegions", new GetRegions());
		mLuaClient.set("getResponseTime", new GetResponseTime());
		mLuaClient.set("getToken", new GetToken());
		mLuaClient.set("getUserByID", new GetUserByID());
		mLuaClient.set("getVoiceChannelByID", new GetVoiceChannelByID());
		mLuaClient.set("getVoiceChannels", new GetVoiceChannels());
		mLuaClient.set("isBot", new IsBot());
		mLuaClient.set("isReady", new IsReady());
		mLuaClient.set("login", new Login());
		mLuaClient.set("logout", new Logout());
		
		// Implement status builders
		mLuaClient.set("clearStatus", new ClearStatus());
		mLuaClient.set("setGame", new SetGame());
		mLuaClient.set("setStream", new SetStream());
		
		// Implement audio player builder
		mLuaClient.set("getAudioPlayerForGuild", new GetAudioPlayerForGuild());
	}

	private static class ChangeAvatar extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue filepath)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				if (filepath != LuaValue.NIL)
				{
					File file = new File(filepath.tojstring());
					mClient.changeAvatar(Image.forFile(file));
				}
				else
				{
					mClient.changeAvatar(Image.defaultAvatar());
				} 
				return LuaValue.NIL;
			});
		}
	}

	private static class ChangeEmail extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue email)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mClient.changeEmail(email.tojstring());
				return LuaValue.NIL;
			});
		}
	}

	private static class ChangePassword extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue password)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mClient.changePassword(password.tojstring());
				return LuaValue.NIL;
			});
		}
	}
	
	private static class ChangePresence extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue isIdle)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mClient.changePresence(isIdle.toboolean());
				return LuaValue.NIL;
			});
		}
	}

	// changeStatus not implemented, see status changer methods below
	
	private static class ChangeUsername extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue username)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mClient.changeUsername(username.tojstring());
				return LuaValue.NIL;
			});
		}
	}

	private static class CreateGuild extends VarArgFunction
	{
		@Override
		public LuaValue invoke(Varargs args)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				if(args.narg() == 2)
				{
					IGuild guild = mClient.createGuild(args.tojstring(1), mClient.getRegionByID(args.tojstring(2)));
					return (new LuaGuild(guild)).getTable();
				}
				else if(args.narg() == 3)
				{
					File file = new File(args.tojstring(1));
					IGuild guild = mClient.createGuild(args.tojstring(1), mClient.getRegionByID(args.tojstring(2)), Image.forFile(file));
					return (new LuaGuild(guild)).getTable();
				}
				else
				{
					return LuaValue.NIL;
				}
			});
		}
	}

	private static class GetChannelByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelID)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaChannel(mClient.getChannelByID(channelID.tojstring()))).getTable();
			});
		}
	}
	
	private static class GetChannels extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue includePrivate)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				Collection<IChannel> channels = mClient.getChannels(includePrivate.toboolean());
				LuaValue luaChannels = LuaValue.tableOf();
				for (IChannel channel : channels)
				{
					luaChannels.set(luaChannels.length() + 1, new LuaChannel(channel).getTable());
				}
				return luaChannels;
			});
		}
	}
	
	private static class GetConnectedVoiceChannels extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				List<IVoiceChannel> channels = mClient.getConnectedVoiceChannels();
				LuaValue luaChannels = LuaValue.tableOf();
				for (IVoiceChannel channel : channels)
				{
					luaChannels.set(luaChannels.length() + 1, (new LuaVoiceChannel(channel)).getTable());
				}
				return luaChannels;
			});
		}
	}
	
	private static class GetGuildByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue guildID)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaGuild(mClient.getGuildByID(guildID.tojstring()))).getTable();
			});
		}
	}
	
	private static class GetGuilds extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				List<IGuild> guilds = mClient.getGuilds();
				LuaValue luaGuilds = LuaValue.tableOf();
				for (IGuild guild : guilds)
				{
					luaGuilds.set(luaGuilds.length() + 1, new LuaGuild(guild).getTable());
				}
				return luaGuilds;
			});
		}
	}
	
	private static class GetInviteForCode extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue code)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaInvite(mClient.getInviteForCode(code.tojstring()))).getTable();
			});
		}
	}
	
	private static class GetLaunchTime extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mClient.getLaunchTime().toString());
			});
		}
	}

	private static class GetOrCreatePMChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				return (new LuaPrivateChannel(mClient.getOrCreatePMChannel(mClient.getUserByID(userID.tojstring()))))
						.getTable();
			});
		}
	}
	
	private static class GetOurUser extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaUser(mClient.getOurUser())).getTable();
			});
		}
	}
	
	private static class GetRegionByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue regionID)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaRegion(mClient.getRegionByID(regionID.tojstring()))).getTable();
			});
		}
	}
	
	private static class GetRegions extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				List<IRegion> regions = mClient.getRegions();
				LuaValue luaRegions = LuaValue.tableOf();
				for (IRegion region : regions)
				{
					luaRegions.set(luaRegions.length(), (new LuaRegion(region)).getTable());
				}
				return luaRegions;
			});
		}
	}
	
	private static class GetResponseTime extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mClient.getResponseTime());
			});
		}
	}
	
	private static class GetToken extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mClient.getToken());
			});
		}
	}
	
	private static class GetUserByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaUser(mClient.getUserByID(userID.tojstring()))).getTable();
			});
		}
	}
	
	private static class GetVoiceChannelByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelID)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaVoiceChannel(mClient.getVoiceChannelByID(channelID.tojstring()))).getTable();
			});
		}
	}
	
	private static class GetVoiceChannels extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				Collection<IVoiceChannel> channels = mClient.getVoiceChannels();
				LuaValue luaChannels = LuaValue.tableOf();
				for (IVoiceChannel channel : channels)
				{
					luaChannels.set(luaChannels.length() + 1, (new LuaVoiceChannel(channel)).getTable());
				}
				return luaChannels;
			});
		}
	}
	
	private static class IsBot extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mClient.isBot());
			});
		}
	}
	
	private static class IsReady extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mClient.isReady());
			});
		}
	}
	
	private static class Login extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				mClient.login();
				return LuaValue.NIL;
			});
		}
	}
	
	private static class Logout extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mClient.logout();
				return LuaValue.NIL;
			});
		}
	}
	
	// Status changer methods
	private static class ClearStatus extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mClient.changeStatus(Status.empty());
				return LuaValue.NIL;
			});
		}
	}
	
	private static class SetGame extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue game)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mClient.changeStatus(Status.game(game.tojstring()));
				return LuaValue.NIL;
			});
		}
	}
	
	private static class SetStream extends TwoArgFunction
	{
		@Override
		public LuaValue call(LuaValue message, LuaValue url)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mClient.changeStatus(Status.stream(message.tojstring(), url.tojstring()));
				return LuaValue.NIL;
			});
		}
	}
	
	private static class GetAudioPlayerForGuild extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue guildID)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				AudioPlayer.getAudioPlayerForGuild(mClient.getGuildByID(guildID.tojstring()));
				return LuaValue.NIL;
			});
		}
	}

	static public LuaValue getTable()
	{
		return mLuaClient;
	}
}
