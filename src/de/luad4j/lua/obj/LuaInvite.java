package de.luad4j.lua.obj;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.luad4j.Main;
import de.luad4j.events.JavaErrorEvent;
import sx.blah.discord.handle.obj.IInvite;

public class LuaInvite
{
	private IInvite 	mInvite;
	private LuaValue	mLuaInvite;
	
	private static final Logger logger = LoggerFactory.getLogger(LuaUser.class);	// Logger of this class
	
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
			try
			{
				return (new LuaInviteResponse(mInvite.accept())).getTable();
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
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
				mInvite.delete();
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class Details extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			try
			{
				return (new LuaInviteResponse(mInvite.details())).getTable();
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				Main.mDiscordClient.getDispatcher().dispatch(new JavaErrorEvent(e.getClass().getSimpleName() + ":" + e.getMessage()));
			}
			
			return LuaValue.NIL;
		}
	}
	
	private class GetInviteCode extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mInvite.getInviteCode());
		}
	}
	
	private class GetXkcdPass extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mInvite.getXkcdPass());
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
				return LuaValue.valueOf(mInviteResponse.getChannelID());
			}
		}
		
		private class GetChannelName extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mInviteResponse.getChannelName());
			}
		}
		
		private class GetGuildID extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mInviteResponse.getGuildID());
			}
		}
		
		private class GetGuildName extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mInviteResponse.getGuildName());
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
