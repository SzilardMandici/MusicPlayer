package com.szilard;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;

public class MusicSelection extends ListActivity{

	private List<String> musicList;
	
	private final int MAX_SONGS = 10000; // this constant denotes the maximal number of songs
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		musicList = getMp3FilesFromSDCard();
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, musicList));
		
	}
	
	private List<String> getMp3FilesFromSDCard() {
		
		List<String> result = new ArrayList<String>();
		
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
			result.add(cursor.getString(4));
		}
		
		
		return result;
	}

	
}
