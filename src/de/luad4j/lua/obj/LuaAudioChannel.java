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
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import sx.blah.discord.handle.AudioChannel;

public class LuaAudioChannel
{
	private final AudioChannel 	mAudioChannel;		// AudioChannel inside Java
	private final LuaValue 		mLuaAudioChannel;	// Lua implementation of AudioChannel
	
	public LuaAudioChannel(AudioChannel audioChannel)
	{
		mAudioChannel = audioChannel;
		
		// Init Lua
		mLuaAudioChannel = LuaValue.tableOf();
		mLuaAudioChannel.set("clearQueue", new ClearQueue());
		mLuaAudioChannel.set("getAudioMetaData", new GetAudioMetaData());
		mLuaAudioChannel.set("getQueueSize", new GetQueueSize());
		mLuaAudioChannel.set("isPaused", new IsPaused());
		mLuaAudioChannel.set("pause", new Pause());
		mLuaAudioChannel.set("queueFile", new QueueFile());
		mLuaAudioChannel.set("queueURL", new QueueURL());
		mLuaAudioChannel.set("resume", new Resume());
		mLuaAudioChannel.set("setVolume", new SetVolume());
		mLuaAudioChannel.set("skip", new Skip());
		mLuaAudioChannel.set("unqueue", new Unqueue());
	}
	
	private class ClearQueue extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			mAudioChannel.clearQueue();
			return LuaValue.NIL;
		}
	}
	
	// TODO: needs testing, not sure if this works
	private class GetAudioMetaData extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return (new LuaAudioMetaData(mAudioChannel.getAudioData(0).metaData)).getTable();
		}
	}
	
	private class GetQueueSize extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mAudioChannel.getQueueSize());
		}
	}
	
	private class IsPaused extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaValue.valueOf(mAudioChannel.isPaused());
		}
	}
	
	private class Pause extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			mAudioChannel.pause();
			return LuaValue.NIL;
		}
	}
	
	private class QueueFile extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue filePath)
		{
			mAudioChannel.queueFile(filePath.tojstring());
			return LuaValue.NIL;
		}
	}
	
	private class QueueURL extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue url)
		{
			mAudioChannel.queueUrl(url.tojstring());
			return LuaValue.NIL;
		}
	}
	
	private class Resume extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			mAudioChannel.resume();
			return LuaValue.NIL;
		}
	}
	
	private class SetVolume extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue volume)
		{
			mAudioChannel.setVolume(volume.tofloat());
			return LuaValue.NIL;
		}
	}
	
	private class Skip extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			mAudioChannel.skip();
			return LuaValue.NIL;
		}
	}
	
	private class Unqueue extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue index)
		{
			mAudioChannel.unqueue(index.toint());
			return LuaValue.NIL;
		}
	}
	
	private class LuaAudioMetaData
	{
		private AudioChannel.AudioMetaData 	mAudioMetaData;
		private LuaValue					mLuaAudioMetaData;
		
		public LuaAudioMetaData(AudioChannel.AudioMetaData metadata)
		{
			mAudioMetaData = metadata;
			
			// Init Lua
			mLuaAudioMetaData = LuaValue.tableOf();
			mLuaAudioMetaData.set("getChannels", new GetChannels());
			mLuaAudioMetaData.set("getFileSource", new GetFileSource());
			mLuaAudioMetaData.set("getFormat", new GetFormat());
			mLuaAudioMetaData.set("startedReading", new StartedReading());
			mLuaAudioMetaData.set("getURLSource", new getURLSource());
		}
		
		private class GetChannels extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mAudioMetaData.channels);
			}
		}
		
		private class GetFileSource extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mAudioMetaData.fileSource.getAbsolutePath());
			}
		}
		
		private class GetFormat extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mAudioMetaData.format.toString());
			}
		}
		
		private class StartedReading extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mAudioMetaData.startedReading);
			}
		}
		
		private class getURLSource extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaValue.valueOf(mAudioMetaData.urlSource.toExternalForm());
			}
		}
		
		public LuaValue getTable()
		{
			return mLuaAudioMetaData;
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaAudioChannel;
	}
}
