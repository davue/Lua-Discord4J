package de.luad4j.audio.providers;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

import sx.blah.discord.util.audio.providers.URLProvider;

public class TrackedURLProvider extends URLProvider
{
	private String mURL;
	
	public TrackedURLProvider(URL url) throws IOException, UnsupportedAudioFileException
	{
		super(url);
		mURL = url.toString();
	}
	
	public String getSource()
	{
		return mURL;
	}
}
