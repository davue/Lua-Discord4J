package de.luad4j.audio;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

import de.luad4j.audio.providers.TrackedFileProvider;
import de.luad4j.audio.providers.TrackedURLProvider;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.audio.AudioPlayer;

public class TrackedAudioPlayer extends AudioPlayer
{
	public TrackedAudioPlayer(IGuild guild)
	{
		super(guild);
	}
	
	@Override
	public Track queue(File file) throws IOException, UnsupportedAudioFileException 
	{
		Track track = new Track(new TrackedFileProvider(file));
		queue(track);
		return track;
	}
	
	@Override
	public Track queue(URL url) throws IOException, UnsupportedAudioFileException 
	{
		Track track = new Track(new TrackedURLProvider(url));
		queue(track);
		return track;
	}
}
