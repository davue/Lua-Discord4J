package de.luad4j.audio.providers;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import sx.blah.discord.util.audio.providers.FileProvider;

public class TrackedFileProvider extends FileProvider
{
	private String mFilePath;
	
	public TrackedFileProvider(File file) throws IOException, UnsupportedAudioFileException
	{
		super(file);
		mFilePath = file.getAbsolutePath();
	}
	
	public String getSource()
	{
		return mFilePath;
	}
}
