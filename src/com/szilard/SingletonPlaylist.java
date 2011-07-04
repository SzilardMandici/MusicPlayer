package com.szilard;

import java.util.ArrayList;
import java.util.List;

/** this class is used for storing the current playlist available on the phone
 *  this is set in the MusicSelection class and read in MusicPlayerActivity for performing
 *  the next and previous song options     
 */
public class SingletonPlaylist {

	private static List<Song> playlist;
	private static SingletonPlaylist uniqueInstance; 
	
	private SingletonPlaylist()
	{
		playlist = new ArrayList<Song>();
	}
	
	public static SingletonPlaylist getInstance()
	{
		if (uniqueInstance == null)
			uniqueInstance = new SingletonPlaylist();
		
		return uniqueInstance;
		
	}
	
	public void addSong(Song song)
	{
		playlist.add(song);
	}
	
	public Song getSongById(int id)
	{
		return playlist.get(id);
	}
	
	
	
}
