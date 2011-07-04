package com.szilard;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MusicSelection extends ListActivity{

	private List<String> musicList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		musicList = getMp3FilesFromSDCard();
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, musicList));
		
	}
	
	
	 
	private List<String> getMp3FilesFromSDCard() {
	//here we get the list of songs stored on the SD card and we update our singleton class 	
		List<String> result = new ArrayList<String>();
		
		SingletonPlaylist playlist;
		
		playlist = SingletonPlaylist.getInstance();
		
		
		
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor cursor;
		String[] projection = 
		{
			MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.DATA,
			MediaStore.Audio.Media.DISPLAY_NAME
		};
		
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
		
		
		cursor = this.managedQuery(uri, projection, selection, null, null);
		
		cursor.moveToFirst();
		
		while (cursor.moveToNext())
		{
			result.add(cursor.getString(5));
			playlist.addSong(new Song(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
		}
		
		
		return result;
	}

	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		//this is how we get the title of the selected song but as we use a singleton class
		//there is no need to pass it to the other activity
		
		//Object o = getListAdapter().getItem(position);
		//String title = o.toString();
		
		
		Intent returnIntent = new Intent();
		
		//set the extra information when returning to MusicPlayerActivity
		returnIntent.putExtra("SELECTED_MUSIC",Integer.toString(position));
		setResult(RESULT_OK, returnIntent);
		
		finish();
		
		
	}

	
	
	
}
