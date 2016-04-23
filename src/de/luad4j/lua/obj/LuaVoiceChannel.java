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

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;
import sx.blah.discord.handle.obj.IVoiceChannel;

public class LuaVoiceChannel extends LuaChannel
{	
	private final IVoiceChannel mVoiceChannel;	// VoiceChannel object inside Java
	
	public LuaVoiceChannel(IVoiceChannel channel)
	{
		// Call parent constructor
		super(channel);
		mVoiceChannel = channel;
		
		// Init Lua
		super.mLuaChannel.set("getAudioChannel", new GetAudioChannel());
		super.mLuaChannel.set("isConnected", new IsConnected());
		super.mLuaChannel.set("join", new Join());
		super.mLuaChannel.set("leave", new Leave());
	}
	
	private class GetAudioChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				return (new LuaAudioChannel(mVoiceChannel.getAudioChannel())).getTable();
			}
			catch (Exception e)
			{
				mLogger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class IsConnected extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mVoiceChannel.isConnected());
		}
	}
	
	private class Join extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			mVoiceChannel.join();
			return LuaValue.NIL;
		}
	}
	
	private class Leave extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			mVoiceChannel.leave();
			return LuaValue.NIL;
		}
	}
}
