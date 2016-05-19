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

// TODO: implement wrapper for every event

public class EventHandler
{
	private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);

	@EventSubscriber
	public void onJavaError(JavaErrorEvent event)
	{
		if (Main.mLuaEnv.get("onJavaErrorEvent").isfunction())
		{
			Main.mLuaEnv.get("onJavaErrorEvent").call(event.getMessage());
		}
		else
		{
			logger.warn("[JAVA] onJavaErrorEvent(string: reason) undefined. It is recommended to define a JavaError event handler.");
		}
	}

	@EventSubscriber
	public void onPortData(PortDataEvent event)
	{
		if (Main.mLuaEnv.get("onPortData").isfunction())
		{
			Main.mLuaEnv.get("onPortData").call(event.getData());
		}
	}

	@EventSubscriber
	public void onAudioPlayEvent(AudioPlayEvent event)
	{
		if(Main.mLuaEnv.get("on"+event.getClass().getSimpleName()).isfunction())
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
				
				Main.mLuaEnv.get("on"+event.getClass().getSimpleName()).call(audio);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
		}
	}
	@EventSubscriber
	public void onAudioQueuedEvent(AudioQueuedEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue audio = LuaValue.tableOf();
				if (event.getFileSource().isPresent())
				{
					audio.set("file", event.getFileSource().get().getAbsolutePath());
				}

				if (event.getUrlSource().isPresent())
				{
					audio.set("url", event.getUrlSource().get().toExternalForm());
				}

				audio.set("format", event.getFormat().toString());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(audio);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	// AudioReceiveEvent not needed
	@EventSubscriber
	public void onAudioStopEvent(AudioStopEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue audio = LuaValue.tableOf();
				if (event.getFileSource().isPresent())
				{
					audio.set("file", event.getFileSource().get().getAbsolutePath());
				}

				if (event.getUrlSource().isPresent())
				{
					audio.set("url", event.getUrlSource().get().toExternalForm());
				}

				audio.set("format", event.getFormat().toString());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(audio);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onAudioUnqueuedEvent(AudioUnqueuedEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue audio = LuaValue.tableOf();
				if (event.getFileSource().isPresent())
				{
					audio.set("file", event.getFileSource().get().getAbsolutePath());
				}

				if (event.getUrlSource().isPresent())
				{
					audio.set("url", event.getUrlSource().get().toExternalForm());
				}

				audio.set("format", event.getFormat().toString());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(audio);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onChannelCreateEvent(ChannelCreateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction()
						.call((new LuaChannel(event.getChannel())).getTable());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onChannelDeleteEvent(ChannelDeleteEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction()
						.call((new LuaChannel(event.getChannel())).getTable());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onChannelUpdateEvent(ChannelUpdateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue channels = LuaValue.tableOf();
				channels.set("old", (new LuaChannel(event.getOldChannel())).getTable());
				channels.set("new", (new LuaChannel(event.getNewChannel())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(channels);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onDiscordDisconnectedEvent(DiscordDisconnectedEvent event)
	{
		logger.debug("DiscordDisconnectedEvent: " + event.getReason());
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(event.getReason().name());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onGameChangeEvent(GameChangeEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue games = LuaValue.tableOf();
				games.set("old", event.getOldGame().orElse("nil"));
				games.set("new", event.getNewGame().orElse("nil"));

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(games);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
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
		
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call((new LuaGuild(event.getGuild())).getTable());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onGuildLeaveEvent(GuildLeaveEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call((new LuaGuild(event.getGuild())).getTable());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onGuildTransferOwnershipEvent(GuildTransferOwnershipEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue guildTransferEvent = LuaValue.tableOf();
				guildTransferEvent.set("guild", (new LuaGuild(event.getGuild()).getTable()));
				guildTransferEvent.set("oldowner", (new LuaUser(event.getOldOwner()).getTable()));
				guildTransferEvent.set("newowner", (new LuaUser(event.getNewOwner()).getTable()));

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(guildTransferEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onGuildUnavailableEvent(GuildUnavailableEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction()
						.call((new LuaGuild(event.getGuild())).getTable());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onGuildUpdateEvent(GuildUpdateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue guilds = LuaValue.tableOf();
				guilds.set("old", (new LuaGuild(event.getOldGuild())).getTable());
				guilds.set("new", (new LuaGuild(event.getNewGuild())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(guilds);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventSubscriber
	public void onInviteReceivedEvent(InviteReceivedEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue inviteEvent = LuaValue.tableOf();
				inviteEvent.set("invite", (new LuaInvite(event.getInvite())).getTable());
				inviteEvent.set("message", (new LuaMessage(event.getMessage())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(inviteEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onMentionEvent(MentionEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call((new LuaMessage(event.getMessage())).getTable());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onMessageDeleteEvent(MessageDeleteEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction()
						.call((new LuaMessage(event.getMessage())).getTable());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction()
						.call((new LuaMessage(event.getMessage())).getTable());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onMessageSendEvent(MessageSendEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call((new LuaMessage(event.getMessage())).getTable());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onMessageUpdateEvent(MessageUpdateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue messages = LuaValue.tableOf();
				messages.set("old", (new LuaMessage(event.getOldMessage())).getTable());
				messages.set("new", (new LuaMessage(event.getOldMessage())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(messages);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	// ModuleDisabledEvent not needed
	// ModuleEnabledEvent not needed
	@EventSubscriber
	public void onPresenceUpdateEvent(PresenceUpdateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue presences = LuaValue.tableOf();
				presences.set("old", event.getOldPresence().name());
				presences.set("new", event.getNewPresence().name());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(presences);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onReadyEvent(ReadyEvent event)
	{
		logger.debug("ReadyEvent");
		
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call();
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onRoleCreateEvent(RoleCreateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue roleEvent = LuaValue.tableOf();
				roleEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
				roleEvent.set("role", (new LuaRole(event.getRole())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(roleEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onRoleDeleteEvent(RoleDeleteEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue roleEvent = LuaValue.tableOf();
				roleEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
				roleEvent.set("role", (new LuaRole(event.getRole())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(roleEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onRoleUpdateEvent(RoleUpdateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue roleEvent = LuaValue.tableOf();
				roleEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
				roleEvent.set("oldrole", (new LuaRole(event.getOldRole())).getTable());
				roleEvent.set("newrole", (new LuaRole(event.getNewRole())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(roleEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onTypingEvent(TypingEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue typingEvent = LuaValue.tableOf();
				typingEvent.set("channel", (new LuaChannel(event.getChannel())).getTable());
				typingEvent.set("user", (new LuaUser(event.getUser())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(typingEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onUserBanEvent(UserBanEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue userBanEvent = LuaValue.tableOf();
				userBanEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
				userBanEvent.set("user", (new LuaUser(event.getUser())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(userBanEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onUserJoinEvent(UserJoinEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue userJoinEvent = LuaValue.tableOf();
				userJoinEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
				userJoinEvent.set("user", (new LuaUser(event.getUser())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(userJoinEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onUserLeaveEvent(UserLeaveEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue userLeaveEvent = LuaValue.tableOf();
				userLeaveEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
				userLeaveEvent.set("user", (new LuaUser(event.getUser())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(userLeaveEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onUserPardonEvent(UserPardonEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue userPardonEvent = LuaValue.tableOf();
				userPardonEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
				userPardonEvent.set("user", (new LuaUser(event.getUser())).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(userPardonEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onUserRoleUpdateEvent(UserRoleUpdateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue userRoleUpdateEvent = LuaValue.tableOf();
				userRoleUpdateEvent.set("guild", (new LuaGuild(event.getGuild())).getTable());
				userRoleUpdateEvent.set("user", (new LuaUser(event.getUser())).getTable());

				LuaValue luaOldRoles = LuaValue.tableOf();
				List<IRole> oldRoles = event.getOldRoles();
				for (int i = 0; i < oldRoles.size(); i++)
				{
					luaOldRoles.set(i + 1, new LuaRole(oldRoles.get(i)).getTable());
				}
				userRoleUpdateEvent.set("oldRoles", luaOldRoles);

				LuaValue luaNewRoles = LuaValue.tableOf();
				List<IRole> newRoles = event.getNewRoles();
				for (int i = 0; i < newRoles.size(); i++)
				{
					luaNewRoles.set(i + 1, new LuaRole(newRoles.get(i)).getTable());
				}
				userRoleUpdateEvent.set("newRoles", luaNewRoles);

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(userRoleUpdateEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onUserUpdateEvent(UserUpdateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue userUpdateEvent = LuaValue.tableOf();
				userUpdateEvent.set("old", new LuaUser(event.getOldUser()).getTable());
				userUpdateEvent.set("new", new LuaUser(event.getNewUser()).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(userUpdateEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onUserVoiceChannelJoinEvent(UserVoiceChannelJoinEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue userVoiceChannelEvent = LuaValue.tableOf();
				userVoiceChannelEvent.set("user", new LuaUser(event.getUser()).getTable());
				userVoiceChannelEvent.set("channel", new LuaVoiceChannel(event.getChannel()).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(userVoiceChannelEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onUserVoiceChannelLeaveEvent(UserVoiceChannelLeaveEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue userVoiceChannelEvent = LuaValue.tableOf();
				userVoiceChannelEvent.set("user", new LuaUser(event.getUser()).getTable());
				userVoiceChannelEvent.set("channel", new LuaVoiceChannel(event.getChannel()).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(userVoiceChannelEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onUserVoiceChannelMoveEvent(UserVoiceChannelMoveEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue userVoiceChannelEvent = LuaValue.tableOf();
				userVoiceChannelEvent.set("user", new LuaUser(event.getUser()).getTable());
				userVoiceChannelEvent.set("oldchannel", new LuaVoiceChannel(event.getOldChannel()).getTable());
				userVoiceChannelEvent.set("newchannel", new LuaVoiceChannel(event.getNewChannel()).getTable());

				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(userVoiceChannelEvent);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onUserVoiceStateUpdateEvent(UserVoiceStateUpdateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				// TODO: implement this
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onVoiceChannelCreateEvent(VoiceChannelCreateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction()
						.call(new LuaVoiceChannel(event.getChannel()).getTable());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onVoiceChannelDeleteEvent(VoiceChannelDeleteEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction()
						.call(new LuaVoiceChannel(event.getVoiceChannel()).getTable());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onVoiceChannelUpdateEvent(VoiceChannelUpdateEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				LuaValue voiceChannels = LuaValue.tableOf();
				voiceChannels.set("old", new LuaVoiceChannel(event.getOldVoiceChannel()).getTable());
				voiceChannels.set("new", new LuaVoiceChannel(event.getNewVoiceChannel()).getTable());
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(voiceChannels);
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	@EventSubscriber
	public void onVoiceDisconnectedEvent(VoiceDisconnectedEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{
				Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).checkfunction().call(event.getReason().name());
			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
	// VoicePingEvent not needed
	@EventSubscriber
	public void onVoiceUserSpeakingEvent(VoiceUserSpeakingEvent event)
	{
		if (Main.mLuaEnv.get("on" + event.getClass().getSimpleName()).isfunction())
		{
			try
			{

			}
			catch (Exception e)
			{
				logger.error(e.getClass().getSimpleName() + ":" + e.getMessage());
				Main.mDiscordClient.getDispatcher()
						.dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			} 
		}
	}
}
