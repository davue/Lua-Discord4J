package de.luad4j.lua.obj;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import de.luad4j.audio.TrackedAudioPlayer;
import de.luad4j.audio.providers.TrackedFileProvider;
import de.luad4j.audio.providers.TrackedURLProvider;
import de.luad4j.lua.LuaHelper;
import sx.blah.discord.handle.audio.IAudioProvider;
import sx.blah.discord.util.audio.AudioPlayer;

public class LuaAudioPlayer
{
	private final TrackedAudioPlayer mAudioPlayer;
	private final LuaValue mLuaAudioPlayer;
	
	public LuaAudioPlayer(TrackedAudioPlayer audioPlayer)
	{
		mAudioPlayer = audioPlayer;
		
		// Init Lua
		mLuaAudioPlayer = LuaValue.tableOf();
		mLuaAudioPlayer.set("getChannels", new GetChannels());
		mLuaAudioPlayer.set("getCurrentTrack", new GetCurrentTrack());
		mLuaAudioPlayer.set("getPlaylist", new GetPlaylist());
		mLuaAudioPlayer.set("getVolume", new GetVolume());
		mLuaAudioPlayer.set("isLooping", new IsLooping());
		mLuaAudioPlayer.set("isPaused", new IsPaused());
		mLuaAudioPlayer.set("isReady", new IsReady());
		mLuaAudioPlayer.set("playlistSize", new PlaylistSize());
		mLuaAudioPlayer.set("queueFile", new QueueFile());
		mLuaAudioPlayer.set("queueURL", new QueueURL());
		mLuaAudioPlayer.set("setLoop", new SetLoop());
		mLuaAudioPlayer.set("setPaused", new SetPaused());
		mLuaAudioPlayer.set("setVolume", new SetVolume());
		mLuaAudioPlayer.set("shuffle", new Shuffle());
		mLuaAudioPlayer.set("skip", new Skip());
		mLuaAudioPlayer.set("skipTo", new SkipTo());
	}
	
	private class GetChannels extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mAudioPlayer.getChannels());
			});
		}
	}
	
	private class GetCurrentTrack extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaTrack(mAudioPlayer.getCurrentTrack()).getTable());
			});
		}
	}
	
	private class GetPlaylist extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				List<AudioPlayer.Track> tracks = mAudioPlayer.getPlaylist();
				LuaValue luaTracks = LuaValue.tableOf();
				for(AudioPlayer.Track track : tracks)
				{
					luaTracks.set(luaTracks.length()+1, (new LuaTrack(track)).getTable());
				}
				return luaTracks;
			});
		}
	}
	
	private class GetVolume extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mAudioPlayer.getVolume());
			});
		}
	}
	
	private class IsLooping extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mAudioPlayer.isLooping());
			});
		}
	}
	
	private class IsPaused extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mAudioPlayer.isPaused());
			});
		}
	}
	
	private class IsReady extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mAudioPlayer.isReady());
			});
		}
	}
	
	private class PlaylistSize extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mAudioPlayer.playlistSize());
			});
		}
	}
	
	private class QueueFile extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue filepath)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				File file = new File(filepath.tojstring());
				return new LuaTrack(mAudioPlayer.queue(file)).getTable();
			});
		}
	}
	
	private class QueueURL extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue urlpath)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				URL url = new URL(urlpath.tojstring());
				return new LuaTrack(mAudioPlayer.queue(url)).getTable();
			});
		}
	}
	
	private class SetLoop extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue loop)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				mAudioPlayer.setLoop(loop.toboolean());
				return LuaValue.NIL;
			});
		}
	}
	
	private class SetPaused extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue paused)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				mAudioPlayer.setPaused(paused.toboolean());
				return LuaValue.NIL;
			});
		}
	}
	
	private class SetVolume extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue volume)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				mAudioPlayer.setVolume(volume.tofloat());
				return LuaValue.NIL;
			});
		}
	}
	
	private class Shuffle extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				mAudioPlayer.shuffle();
				return LuaValue.NIL;
			});
		}
	}
	
	private class Skip extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				mAudioPlayer.skip();
				return LuaValue.NIL;
			});
		}
	}
	
	private class SkipTo extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue skipto)
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				mAudioPlayer.skipTo(skipto.toint());
				return LuaValue.NIL;
			});
		}
	}
	
	private class LuaTrack
	{
		private final AudioPlayer.Track mTrack;
		private final LuaValue mLuaTrack;
		
		private LuaValue mTitle = LuaValue.NIL;
		
		public LuaTrack(AudioPlayer.Track track)
		{
			mTrack = track;
			
			// Init Lua
			mLuaTrack = LuaValue.tableOf();
			mLuaTrack.set("fastForward", new FastForward());
			mLuaTrack.set("fastForwardTo", new FastForwardTo());
			mLuaTrack.set("getChannels", new GetChannels());
			mLuaTrack.set("getCurrentTrackTime", new GetCurrentTrackTime());
			mLuaTrack.set("getTotalTrackTime", new GetTotalTrackTime());
			mLuaTrack.set("rewind", new Rewind());
			mLuaTrack.set("rewindTo", new RewindTo());
			
			// Custom functions
			mLuaTrack.set("getSource", new GetSource());
			mLuaTrack.set("setTitle", new SetTitle());
			mLuaTrack.set("getTitle", new GetTitle());
		}
		
		private class FastForward extends OneArgFunction
		{
			@Override
			public LuaValue call(LuaValue time)
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					mTrack.fastForward(time.tolong());
					return LuaValue.NIL;
				});
			}
		}
		
		private class FastForwardTo extends OneArgFunction
		{
			@Override
			public LuaValue call(LuaValue time)
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					mTrack.fastForwardTo(time.tolong());
					return LuaValue.NIL;
				});
			}
		}
		
		private class GetChannels extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					return LuaValue.valueOf(mTrack.getChannels());
				});
			}
		}
		
		private class GetCurrentTrackTime extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					return LuaValue.valueOf(mTrack.getCurrentTrackTime());
				});
			}
		}
		
		private class GetTotalTrackTime extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					return LuaValue.valueOf(mTrack.getCurrentTrackTime());
				});
			}
		}
		
		private class Rewind extends OneArgFunction
		{
			@Override
			public LuaValue call(LuaValue time)
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					mTrack.rewind(time.tolong());
					return LuaValue.NIL;
				});
			}
		}
		
		private class RewindTo extends OneArgFunction
		{
			@Override
			public LuaValue call(LuaValue time)
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					mTrack.rewindTo(time.tolong());
					return LuaValue.NIL;
				});
			}
		}
		
		private class GetSource extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					IAudioProvider provider = mTrack.getProvider();
					if(provider instanceof TrackedFileProvider)
					{
						return LuaValue.valueOf(((TrackedFileProvider) provider).getSource());
					}
					else if(provider instanceof TrackedURLProvider)
					{
						return LuaValue.valueOf(((TrackedURLProvider) provider).getSource());
					}
					else
					{
						return LuaValue.NIL;
					}
				});
			}
		}
		
		private class SetTitle extends OneArgFunction
		{
			@Override
			public LuaValue call(LuaValue title)
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					mTitle = title;
					return LuaValue.NIL;
				});
			}
		}
		
		private class GetTitle extends ZeroArgFunction
		{
			@Override
			public LuaValue call()
			{
				return LuaHelper.handleExceptions(this.getClass(), () -> {
					return mTitle;
				});
			}
		}
		
		public LuaValue getTable()
		{
			return mLuaTrack;
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaAudioPlayer;
	}
}
