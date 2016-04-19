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
import java.util.Optional;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRegion;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.Image;
import sx.blah.discord.handle.obj.IVoiceChannel;

// Client is a global lua table -> no getClient() lua implementation needed for other objects

public class LuaClient
{
	private static IDiscordClient mClient; // Client object inside Java
	private static LuaValue mLuaClient; // Table: Client object inside Lua
	
	private static final Logger logger = LoggerFactory.getLogger(LuaMessage.class);	// Logger of this class

	public LuaClient()
	{
		// Init only once
		if (mClient == null)
		{
			mClient = Main.mDiscordClient;
		}

		if (mLuaClient == null)
		{
			// Init Lua
			mLuaClient = LuaValue.tableOf();
			mLuaClient.set("changeAvatar", new ChangeAvatar());
			mLuaClient.set("changeEmail", new ChangeEmail());
			mLuaClient.set("changePassword", new ChangePassword());
			mLuaClient.set("changeUsername", new ChangeUsername());
			mLuaClient.set("createGuild", new CreateGuild());
			mLuaClient.set("getChannelByID", new GetChannelByID());
			mLuaClient.set("getChannels", new GetChannels());
			mLuaClient.set("getConnectedVoiceChannels", new GetConnectedVoiceChannels());
			mLuaClient.set("getGuildByID", new GetGuildByID());
			mLuaClient.set("geGuilds", new GetGuilds());
			mLuaClient.set("getInviteForCode", new GetInviteForCode());
			mLuaClient.set("getLaunchTime", new GetLaunchTime());
			mLuaClient.set("getOrCreatePMChannel", new GetOrCreatePMChannel());
			mLuaClient.set("getOutUser", new GetOurUser());
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
			mLuaClient.set("updatePresence", new UpdatePresence());
		}
	}

	private static class ChangeAvatar extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue filepath)
		{
			try
			{
				if (filepath != LuaValue.NIL)
				{
					File file = new File(filepath.tojstring());
					mClient.changeAvatar(Image.forFile(file));
				}
				else
				{
					mClient.changeAvatar(Image.defaultAvatar());
				}
			}
			catch (DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}

	private static class ChangeEmail extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue email)
		{
			try
			{
				mClient.changeEmail(email.tojstring());
			}
			catch (DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}

	private static class ChangePassword extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue password)
		{
			try
			{
				mClient.changePassword(password.tojstring());
			}
			catch (DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}

	private static class ChangeUsername extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue username)
		{
			try
			{
				mClient.changeUsername(username.tojstring());
			}
			catch (DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}

	private static class CreateGuild extends VarArgFunction
	{
		@Override
		public LuaValue invoke(Varargs args)
		{
			try
			{
				File file = new File(args.tojstring(1));
				IGuild guild = mClient.createGuild(args.tojstring(1), mClient.getRegionByID(args.tojstring(2)), Optional.ofNullable(Image.forFile(file)));
				return (new LuaGuild(guild)).getTable();
			}
			catch (DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}

	private static class GetChannelByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelID)
		{
			return (new LuaChannel(mClient.getChannelByID(channelID.tojstring()))).getTable();
		}
	}
	
	private static class GetChannels extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue includePrivate)
		{
			try
			{
				Collection<IChannel> channels = mClient.getChannels(includePrivate.toboolean());
				LuaValue luaChannels = LuaValue.tableOf();
				for(IChannel channel : channels)
				{
					luaChannels.set(luaChannels.length()+1, new LuaChannel(channel).getTable());
				}
				
				return luaChannels;
			}
			catch(LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaVoiceChannel
	private static class GetConnectedVoiceChannels extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				/*List<IVoiceChannel> channels = mClient.getConnectedVoiceChannels();
				LuaValue luaChannels = LuaValue.tableOf();
				for(IChannel channel : channels)
				{
					luaChannels.set(luaChannels.length()+1, new LuaVoiceChannel(channel));
				}
				return luaChannels;*/
			}
			catch(LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}

			return LuaValue.NIL;
		}
	}
	
	private static class GetGuildByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue guildID)
		{
			return (new LuaGuild(mClient.getGuildByID(guildID.tojstring()))).getTable();
		}
	}
	
	private static class GetGuilds extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				List<IGuild> guilds = mClient.getGuilds();
				LuaValue luaGuilds = LuaValue.tableOf();
				for(IGuild guild : guilds)
				{
					luaGuilds.set(luaGuilds.length()+1, new LuaGuild(guild).getTable());
				}
				return luaGuilds;
			}
			catch(LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}

			return LuaValue.NIL;
		}
	}
	
	private static class GetInviteForCode extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue code)
		{
			return (new LuaInvite(mClient.getInviteForCode(code.tojstring()))).getTable();
		}
	}
	
	private static class GetLaunchTime extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mClient.getLaunchTime().toString());
		}
	}

	// TODO: implement LuaPrivateChannel
	private static class GetOrCreatePMChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			//return (new LuaPrivateChannel(mClient.getOrCreatePMChannel(mClient.getUserByID(userID.tojstring())))).getTable();
			return LuaValue.NIL;
		}
	}
	
	private static class GetOurUser extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return (new LuaUser(mClient.getOurUser())).getTable();
		}
	}
	
	// TODO: implement LuaRegion
	private static class GetRegionByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue regionID)
		{
			//return (new LuaRegion(mClient.getRegionByID(regionID.tojstring()))).getTable();
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaRegion
	private static class GetRegions extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			/*try
			{
				List<IRegion> regions = mClient.getRegions();
				
				LuaValue luaRegions = LuaValue.tableOf();
				for(IRegion region : regions)
				{
					luaRegions.set(luaRegions.length(), new LuaRegion(region));
				}
				return luaRegions;
			}
			catch (LuaError | DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}*/
			
			return LuaValue.NIL;
		}
	}
	
	private static class GetResponseTime extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mClient.getResponseTime());
		}
	}
	
	private static class GetToken extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mClient.getToken());
		}
	}
	
	private static class GetUserByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue userID)
		{
			return (new LuaUser(mClient.getUserByID(userID.tojstring()))).getTable();
		}
	}
	
	// TODO: implement LuaVoiceChannel
	private static class GetVoiceChannelByID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelID)
		{
			//return (new LuaVoiceChannel(mClient.getVoiceChannelByID(channelID.tojstring()))).getTable();
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaVoiceChannel
	private static class GetVoiceChannels extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				/*Collection<IVoiceChannel> channels = mClient.getVoiceChannels();
				LuaValue luaChannels = LuaValue.tableOf();
				for(IChannel channel : channels)
				{
					luaChannels.set(luaChannels.length()+1, new LuaVoiceChannel(channel));
				}
				return luaChannels;*/
			}
			catch(LuaError e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private static class IsBot extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mClient.isBot());
		}
	}
	
	private static class IsReady extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mClient.isReady());
		}
	}
	
	private static class Login extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				mClient.login();
			}
			catch (DiscordException e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private static class Logout extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				mClient.logout();
			}
			catch (DiscordException | HTTP429Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private static class UpdatePresence extends TwoArgFunction
	{
		@Override
		public LuaValue call(LuaValue isIdle, LuaValue game)
		{
			mClient.updatePresence(isIdle.toboolean(), Optional.ofNullable(game.tojstring()));
			return LuaValue.NIL;
		}
	}

	static public LuaValue getTable()
	{
		return mLuaClient;
	}
}
