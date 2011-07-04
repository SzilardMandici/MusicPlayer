package com.szilard;

/**
 * this class holds the information about a specific song	
 */
public class Song {

	private String id;
	private String artist;
	private String title;
	private String data;
	private String display_name;
	private String album;
	
	
	public Song(String id, String album, String artist, String title, String data, String display_name)
	{
		this.id = id;
		this.artist = artist;
		this.title = title;
		this.album = album;
		this.data = data;
		this.display_name = display_name;
	}

	public String getData()
	{
		return data;
	}
}
