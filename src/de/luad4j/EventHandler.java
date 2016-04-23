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

package de.luad4j;

import java.util.List;

import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.IRole;
import de.luad4j.events.*;
import de.luad4j.lua.obj.LuaChannel;
import de.luad4j.lua.obj.LuaGuild;
import de.luad4j.lua.obj.LuaInvite;
import de.luad4j.lua.obj.LuaMessage;
import de.luad4j.lua.obj.LuaRole;
import de.luad4j.lua.obj.LuaUser;
import de.luad4j.lua.obj.LuaVoiceChannel;

import org.luaj.vm2.LuaValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventHandler
{
	private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);

	// Custom Events
	@EventSubscriber
	public void onLuaError(LuaErrorEvent event)
	{
		if (Main.mLuaEnv.get("onLuaError").isfunction())
		{
			Main.mLuaEnv.get("onLuaError").call(event.getMessage());
		}
		else
		{
			logger.warn("[JAVA] onLuaError(string: reason) undefined. It is recommended to define a LuaError event handler.");
		}
	}

	@EventSubscriber
	public void onJavaError(LuaErrorEvent event)
	{
		if (Main.mLuaEnv.get("onLuaError").isfunction())
		{
			Main.mLuaEnv.get("onLuaError").call(event.getMessage());
		}
		else
		{
			logger.warn("[JAVA] onJavaError(string: reason) undefined. It is recommended to define a JavaError event handler.");
		}
	}

	@EventSubscriber
	public void onPortData(PortDataEvent event)
	{
		if (Main.mLuaEnv.get("onPortData").isfunction())
		{
			Main.mLuaEnv.get("onPortData").call(event.getMessage());
		}
	}

	@EventSubscriber
	public void onAudioPlayEvent(AudioPlayEvent event)
	{
		try
		{
			LuaValue audio = LuaValue.tableOf();
			if(event.getFileSource().isPresent())
			{
				audio.set("file", event.getFileSource().get().getAbsolutePath());
			}
			
			if(event.getUrlSource().isPresent())
			{
				audio.set("url", event.getUrlSource().get().toExternalForm());
			}
			
			audio.set("format", event.getFormat().toString());
			
			Main.mLuaEnv.get("onAudioPlay").checkfunction().call(audio);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onAudioQueuedEvent(AudioQueuedEvent event)
	{
		try
		{
			LuaValue audio = LuaValue.tableOf();
			if(event.getFileSource().isPresent())
			{
				audio.set("file", event.getFileSource().get().getAbsolutePath());
			}
			
			if(event.getUrlSource().isPresent())
			{
				audio.set("url", event.getUrlSource().get().toExternalForm());
			}
			
			audio.set("format", event.getFormat().toString());
			
			Main.mLuaEnv.get("onAudioQueued").checkfunction().call(audio);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	// AudioReceiveEvent not needed
	@EventSubscriber
	public void onAudioStopEvent(AudioStopEvent event)
	{
		try
		{
			LuaValue audio = LuaValue.tableOf();
			if(event.getFileSource().isPresent())
			{
				audio.set("file", event.getFileSource().get().getAbsolutePath());
			}
			
			if(event.getUrlSource().isPresent())
			{
				audio.set("url", event.getUrlSource().get().toExternalForm());
			}
			
			audio.set("format", event.getFormat().toString());
			
			Main.mLuaEnv.get("onAudioStop").checkfunction().call(audio);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onAudioUnqueuedEvent(AudioUnqueuedEvent event)
	{
		try
		{
			LuaValue audio = LuaValue.tableOf();
			if(event.getFileSource().isPresent())
			{
				audio.set("file", event.getFileSource().get().getAbsolutePath());
			}
			
			if(event.getUrlSource().isPresent())
			{
				audio.set("url", event.getUrlSource().get().toExternalForm());
			}
			
			audio.set("format", event.getFormat().toString());
			
			Main.mLuaEnv.get("onAudioUnqueued").checkfunction().call(audio);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onChannelCreateEvent(ChannelCreateEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onChannelCreate").checkfunction().call((new LuaChannel(event.getChannel())).getTable());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onChannelDeleteEvent(ChannelDeleteEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onChannelDelete").checkfunction().call((new LuaChannel(event.getChannel())).getTable());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onChannelUpdateEvent(ChannelUpdateEvent event)
	{
		try
		{
			LuaValue channels = LuaValue.tableOf();
			channels.set("old", (new LuaChannel(event.getOldChannel())).getTable());
			channels.set("new", (new LuaChannel(event.getNewChannel())).getTable());
			
			Main.mLuaEnv.get("onChannelUpdate").checkfunction().call(channels);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onDiscordDisconnectedEvent(DiscordDisconnectedEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onDiscordDisconnected").checkfunction().call(event.getReason().name());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onGameChangeEvent(GameChangeEvent event)
	{
		try
		{
			LuaValue games = LuaValue.tableOf();
			games.set("old", event.getOldGame().orElse("nil"));
			games.set("new", event.getNewGame().orElse("nil"));
			
			Main.mLuaEnv.get("onGameChange").checkfunction().call(games);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onGuildCreateEvent(GuildCreateEvent event)
	{
		if (!Main.mAlreadyInitialized) // Don't initialize again, if another guild was created
		{
			Main.initializeLua();
			Main.mAlreadyInitialized = true;
		}
		
		try
		{
			Main.mLuaEnv.get("onGuildCreate").checkfunction().call((new LuaGuild(event.getGuild())).getTable());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onGuildLeaveEvent(GuildLeaveEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onGuildLeave").checkfunction().call((new LuaGuild(event.getGuild())).getTable());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onGuildTransferOwnershipEvent(GuildTransferOwnershipEvent event)
	{
		try
		{
			LuaValue guildTransferEvent = LuaValue.tableOf();
			guildTransferEvent.set("guild", (new LuaGuild(event.getGuild()).getTable()));
			guildTransferEvent.set("oldowner", (new LuaUser(event.getOldOwner()).getTable()));
			guildTransferEvent.set("newowner", (new LuaUser(event.getNewOwner()).getTable()));
			
			Main.mLuaEnv.get("onGuildTransferOwnership").checkfunction().call(guildTransferEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onGuildUnavailableEvent(GuildUnavailableEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onGuildUnavailable").checkfunction().call((new LuaGuild(event.getGuild())).getTable());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onGuildUpdateEvent(GuildUpdateEvent event)
	{
		try
		{
			LuaValue guilds = LuaValue.tableOf();
			guilds.set("old", (new LuaGuild(event.getOldGuild())).getTable());
			guilds.set("new", (new LuaGuild(event.getNewGuild())).getTable());
			
			Main.mLuaEnv.get("onGuildUpdate").checkfunction().call(guilds);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onInviteReceivedEvent(InviteReceivedEvent event)
	{
		try
		{
			LuaValue inviteEvent = LuaValue.tableOf();
			inviteEvent.set("invite", (new LuaInvite(event.getInvite())).getTable());
			inviteEvent.set("message", (new LuaMessage(event.getMessage())).getTable());
			
			Main.mLuaEnv.get("onInviteReceived").checkfunction().call(inviteEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onMentionEvent(MentionEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onMention").checkfunction().call((new LuaMessage(event.getMessage())).getTable());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onMessageDeleteEvent(MessageDeleteEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onMessageDelete").checkfunction().call((new LuaMessage(event.getMessage())).getTable());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onMessageReceived").checkfunction().call((new LuaMessage(event.getMessage())).getTable());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onMessageSendEvent(MessageSendEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onMessageSend").checkfunction().call((new LuaMessage(event.getMessage())).getTable());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onMessageUpdateEvent(MessageUpdateEvent event)
	{
		try
		{
			LuaValue messages = LuaValue.tableOf();
			messages.set("old", (new LuaMessage(event.getOldMessage())).getTable());
			messages.set("new", (new LuaMessage(event.getOldMessage())).getTable());
			
			Main.mLuaEnv.get("onMessageUpdate").checkfunction().call(messages);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	// ModuleDisabledEvent not needed
	// ModuleEnabledEvent not needed
	@EventSubscriber
	public void onPresenceUpdateEvent(PresenceUpdateEvent event)
	{
		try
		{
			LuaValue presences = LuaValue.tableOf();
			presences.set("old", event.getOldPresence().name());
			presences.set("new", event.getNewPresence().name());
			
			Main.mLuaEnv.get("onPresenceUpdate").checkfunction().call(presences);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	// ReadyEvent not needed
	@EventSubscriber
	public void onRoleCreateEvent(RoleCreateEvent event)
	{
		try
		{
			LuaValue roleEvent = LuaValue.tableOf();
			roleEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
			roleEvent.set("role", (new LuaRole(event.getRole())).getTable());
			
			Main.mLuaEnv.get("onRoleCreate").checkfunction().call(roleEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onRoleDeleteEvent(RoleDeleteEvent event)
	{
		try
		{
			LuaValue roleEvent = LuaValue.tableOf();
			roleEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
			roleEvent.set("role", (new LuaRole(event.getRole())).getTable());
			
			Main.mLuaEnv.get("onRoleDelete").checkfunction().call(roleEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onRoleUpdateEvent(RoleUpdateEvent event)
	{
		try
		{
			LuaValue roleEvent = LuaValue.tableOf();
			roleEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
			roleEvent.set("oldrole", (new LuaRole(event.getOldRole())).getTable());
			roleEvent.set("newrole", (new LuaRole(event.getNewRole())).getTable());
			
			Main.mLuaEnv.get("onRoleUpdate").checkfunction().call(roleEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onTypingEvent(TypingEvent event)
	{
		try
		{
			LuaValue typingEvent = LuaValue.tableOf();
			typingEvent.set("channel", (new LuaChannel(event.getChannel())).getTable());
			typingEvent.set("user", (new LuaUser(event.getUser())).getTable());
			
			Main.mLuaEnv.get("onTyping").checkfunction().call(typingEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onUserBanEvent(UserBanEvent event)
	{
		try
		{
			LuaValue userBanEvent = LuaValue.tableOf();
			userBanEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
			userBanEvent.set("user", (new LuaUser(event.getUser())).getTable());
			
			Main.mLuaEnv.get("onUserBan").checkfunction().call(userBanEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onUserJoinEvent(UserJoinEvent event)
	{
		try
		{
			LuaValue userJoinEvent = LuaValue.tableOf();
			userJoinEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
			userJoinEvent.set("user", (new LuaUser(event.getUser())).getTable());
			
			Main.mLuaEnv.get("onUserJoin").checkfunction().call(userJoinEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onUserLeaveEvent(UserLeaveEvent event)
	{
		try
		{
			LuaValue userLeaveEvent = LuaValue.tableOf();
			userLeaveEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
			userLeaveEvent.set("user", (new LuaUser(event.getUser())).getTable());

			Main.mLuaEnv.get("onUserLeave").checkfunction().call(userLeaveEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onUserPardonEvent(UserPardonEvent event)
	{
		try
		{
			LuaValue userPardonEvent = LuaValue.tableOf();
			userPardonEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
			userPardonEvent.set("user", (new LuaUser(event.getUser())).getTable());

			Main.mLuaEnv.get("onUserPardon").checkfunction().call(userPardonEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onUserRoleUpdateEvent(UserRoleUpdateEvent event)
	{
		try
		{
			LuaValue userRoleUpdateEvent = LuaValue.tableOf();
			userRoleUpdateEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
			userRoleUpdateEvent.set("user", (new LuaUser(event.getUser())).getTable());
			
			LuaValue luaOldRoles = LuaValue.tableOf();
			List<IRole> oldRoles = event.getOldRoles();
			for(int i = 0; i < oldRoles.size(); i++)
			{
				luaOldRoles.set(i+1, new LuaRole(oldRoles.get(i)).getTable());
			}
			userRoleUpdateEvent.set("oldRoles", luaOldRoles);
			
			LuaValue luaNewRoles = LuaValue.tableOf();
			List<IRole> newRoles = event.getNewRoles();
			for(int i = 0; i < newRoles.size(); i++)
			{
				luaNewRoles.set(i+1, new LuaRole(newRoles.get(i)).getTable());
			}
			userRoleUpdateEvent.set("newRoles", luaNewRoles);

			Main.mLuaEnv.get("onUserRoleUpdate").checkfunction().call(userRoleUpdateEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onUserUpdateEvent(UserUpdateEvent event)
	{
		try
		{
			LuaValue userUpdateEvent = LuaValue.tableOf();
			userUpdateEvent.set("old", new LuaUser(event.getOldUser()).getTable());
			userUpdateEvent.set("new", new LuaUser(event.getNewUser()).getTable());
			
			Main.mLuaEnv.get("onUserUpdate").checkfunction().call(userUpdateEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onUserVoiceChannelJoinEvent(UserVoiceChannelJoinEvent event)
	{
		try
		{
			LuaValue userVoiceChannelEvent = LuaValue.tableOf();
			userVoiceChannelEvent.set("user", new LuaUser(event.getUser()).getTable());
			userVoiceChannelEvent.set("channel", new LuaVoiceChannel(event.getChannel()).getTable());
			
			Main.mLuaEnv.get("onUserVoiceChannelJoin").checkfunction().call(userVoiceChannelEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onUserVoiceChannelLeaveEvent(UserVoiceChannelLeaveEvent event)
	{
		try
		{
			LuaValue userVoiceChannelEvent = LuaValue.tableOf();
			userVoiceChannelEvent.set("user", new LuaUser(event.getUser()).getTable());
			userVoiceChannelEvent.set("channel", new LuaVoiceChannel(event.getChannel()).getTable());
			
			Main.mLuaEnv.get("onUserVoiceChannelJoin").checkfunction().call(userVoiceChannelEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onUserVoiceChannelMoveEvent(UserVoiceChannelMoveEvent event)
	{
		try
		{
			LuaValue userVoiceChannelEvent = LuaValue.tableOf();
			userVoiceChannelEvent.set("user", new LuaUser(event.getUser()).getTable());
			userVoiceChannelEvent.set("oldchannel", new LuaVoiceChannel(event.getOldChannel()).getTable());
			userVoiceChannelEvent.set("newchannel", new LuaVoiceChannel(event.getNewChannel()).getTable());
			
			Main.mLuaEnv.get("onUserVoiceChannelJoin").checkfunction().call(userVoiceChannelEvent);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onUserVoiceStateUpdateEvent(UserVoiceStateUpdateEvent event)
	{
		try
		{
			// TODO: implement this
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onVoiceChannelCreateEvent(VoiceChannelCreateEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onVoiceChannelCreate").checkfunction().call(new LuaVoiceChannel(event.getChannel()).getTable());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onVoiceChannelDeleteEvent(VoiceChannelDeleteEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onVoiceChannelCreate").checkfunction().call(new LuaVoiceChannel(event.getVoiceChannel()).getTable());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onVoiceChannelUpdateEvent(VoiceChannelUpdateEvent event)
	{
		try
		{
			LuaValue voiceChannels = LuaValue.tableOf();
			voiceChannels.set("old", new LuaVoiceChannel(event.getOldVoiceChannel()).getTable());
			voiceChannels.set("new", new LuaVoiceChannel(event.getNewVoiceChannel()).getTable());
			Main.mLuaEnv.get("onVoiceChannelCreate").checkfunction().call(voiceChannels);
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	@EventSubscriber
	public void onVoiceDisconnectedEvent(VoiceDisconnectedEvent event)
	{
		try
		{
			Main.mLuaEnv.get("onVoiceChannelCreate").checkfunction().call(event.getReason().name());
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
	// VoicePingEvent not needed
	@EventSubscriber
	public void onVoiceUserSpeakingEvent(VoiceUserSpeakingEvent event)
	{
		try
		{
			
		}
		catch (Exception e)
		{
			logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
			Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
		}
	}
}
