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
import de.luad4j.lua.LuaHelper;
import sx.blah.discord.handle.obj.IInvite;

public class LuaInvite
{
	private final IInvite 	mInvite;	// Invite Object inside Java
	private final LuaValue	mLuaInvite;	// Lua implementation of Invite
	
	public LuaInvite(IInvite invite)
	{
		mInvite = invite;
		
		// Init Lua
		mLuaInvite = LuaValue.tableOf();
		mLuaInvite.set("accept", new Accept());
		mLuaInvite.set("delete", new Delete());
		mLuaInvite.set("details", new Details());
		mLuaInvite.set("getInviteCode", new GetInviteCode());
		mLuaInvite.set("getXkcdPass", new GetXkcdPass());
	}
	
	private class Accept extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				return (new LuaInviteResponse(mInvite.accept())).getTable();
			});
		}
	}
	
	private class Delete extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mInvite.delete();
				return LuaValue.NIL;
			});
		}
	}
	
	private class Details extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				return (new LuaInviteResponse(mInvite.details())).getTable();
			});
		}
	}
	
	private class GetInviteCode extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mInvite.getInviteCode());
			});
		}
	}
	
	private class GetXkcdPass extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mInvite.getXkcdPass());
			});
		}
	}
	
	private class LuaInviteResponse
	{
		private IInvite.InviteResponse 	mInviteResponse;
		private LuaValue				mLuaInviteResponse;
		
		public LuaInviteResponse(IInvite.InviteResponse inviteResponse)
		{
			mInviteResponse = inviteResponse;
			
			// Init Lua
			mLuaInviteResponse = LuaValue.tableOf();
			mLuaInviteResponse.set("getChannelID", new GetChannelID());
			mLuaInviteResponse.set("getChannelName", new GetChannelName());
			mLuaInviteResponse.set("getGuildID", new GetGuildID());
			mLuaInviteResponse.set("getGuildName", new GetGuildName());
		}
		
		private class GetChannelID extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					return LuaValue.valueOf(mInviteResponse.getChannelID());
				});
			}
		}
		
		private class GetChannelName extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					return LuaValue.valueOf(mInviteResponse.getChannelName());
				});
			}
		}
		
		private class GetGuildID extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					return LuaValue.valueOf(mInviteResponse.getGuildID());
				});
			}
		}
		
		private class GetGuildName extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					return LuaValue.valueOf(mInviteResponse.getGuildName());
				});
			}
		}
		
		public LuaValue getTable()
		{
			return mLuaInvite;
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaInvite;
	}
}
