package de.luad4j.lua.obj;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.luad4j.Main;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

public class LuaUser
{
	private IUser		mUser;			// IUser object of user inside Java
	private LuaValue	mLuaUser;		// Table: user object inside Lua
	
	// Constructor
	public LuaUser(IUser user)
	{	
		mUser = user;
		
		// Init Lua
		mLuaUser = LuaValue.tableOf();
		mLuaUser.set("getAvatar", new getAvatar());
		mLuaUser.set("getAvatarURL", new getAvatarURL());
		mLuaUser.set("getClient", new getClient());
		mLuaUser.set("getCreationDate", new getCreationDate());
		mLuaUser.set("getDiscriminator", new getDiscriminator());
		mLuaUser.set("getGame", new getGame());
		mLuaUser.set("getID", new getID());
		mLuaUser.set("getName", new getName());
		mLuaUser.set("getPresence", new getPresence());
		mLuaUser.set("getRolesForGuildID", new getRolesForGuildID());
		mLuaUser.set("getVoiceChannel", new getVoiceChannel());
		mLuaUser.set("isBot", new isBot());
		mLuaUser.set("mention", new mention());
		mLuaUser.set("moveToVoiceChannel", new moveToVoiceChannel());
	}
	
	class getAvatar extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getAvatar());
		}
	}
	
	class getAvatarURL extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getAvatarURL());
		}
	}
	
	// TODO: implement LuaClient object
	class getClient extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			//return (new LuaClient(mUser.getClient())).getTable();
			return LuaValue.NIL;
		}
	}
	
	class getCreationDate extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getCreationDate().toString());
		}
	}
	
	class getDiscriminator extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getDiscriminator());
		}
	}
	
	class getGame extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			if(mUser.getGame().isPresent())
			{
				return LuaValue.valueOf(mUser.getGame().get());
			}
			return LuaValue.NIL;
		}
	}
	
	class getID extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getID());
		}
	}
	
	class getName extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.getName());
		}
	}
	
	// TODO: implement LuaPresence object
	class getPresence extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			//return (new LuaPresence(mMain.getPresence())).getTable();
			return LuaValue.NIL;
		}
	}
	
	// TODO: somehow implement a list in lua
	class getRolesForGuildID extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue guildid) 
		{
			//return LuaValue.valueOf(mUser.getPresence());
			return LuaValue.NIL;
		}
	}
	
	// TODO: implement LuaVoiceChannel object
	class getVoiceChannel extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			if(mUser.getVoiceChannel().isPresent())
			{
				//return (new LuaVoiceChannel(mUser.getVoiceChannel().get())).getTable();
				return LuaValue.NIL;
			}
			return LuaValue.NIL;
		}
	}
	
	class isBot extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.isBot());
		}
	}
	
	class mention extends ZeroArgFunction
	{
		@Override
		public LuaValue call() 
		{
			return LuaValue.valueOf(mUser.mention());
		}
	}
	
	class moveToVoiceChannel extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue channelid) 
		{
			try 
			{
				mUser.moveToVoiceChannel(Main.mDiscordClient.getVoiceChannelByID(channelid.tojstring()));
			} 
			catch (DiscordException e) 
			{
				return LuaValue.valueOf("DiscordException");
			} 
			catch (HTTP429Exception e) 
			{
				return LuaValue.valueOf("HTTP429Exception");
			} 
			catch (MissingPermissionsException e) 
			{
				return LuaValue.valueOf("MissingPermissionsException");
			}

			return LuaValue.NIL;
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaUser;
	}
}
