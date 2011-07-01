package com.szilard;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MusicPlayerActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		Button playButton = (Button) findViewById(R.id.play);
		
		playButton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
    	String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
    	
    	Uri data = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    	String[] projection = {
    		MediaStore.Audio.Media._ID,
    		MediaStore.Audio.Media.ARTIST,
    		MediaStore.Audio.Media.TITLE,
    		MediaStore.Audio.Media.DATA,
    		MediaStore.Audio.Media.DISPLAY_NAME,
    		MediaStore.Audio.Media.DURATION
    	};
        
        
        Cursor cursor;
        cursor = this.managedQuery(
        		MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        		projection,
        		selection,
        		null,
        		null);

        	List<String> songs = new ArrayList<String>();
        	if (cursor != null)
        		
        	while(cursor.moveToNext()){
        		songs.add(cursor.getString(0) + "||" + cursor.getString(1) + "||" +   cursor.getString(2) + "||" +   cursor.getString(3) + "||" +  cursor.getString(4) + "||" +  cursor.getString(5));
        		Toast.makeText(this, cursor.getString(1) + cursor.getString(2) + cursor.getString(3) + cursor.getString(4) + cursor.getString(5), Toast.LENGTH_LONG).show();
        		try{
        		Thread.sleep(1000);
        		}
        		catch(InterruptedException e)
        		{
        			e.printStackTrace();
        		}
        		
        		data = Uri.parse(cursor.getString(3));
        		
        	}
        	else
        		Toast.makeText(this, "Bad luck", Toast.LENGTH_LONG).show();
		
        	MediaPlayer mp = MediaPlayer.create(this, data);
        	
        	mp.start();
        	
        	SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        	
        	
        	while (mp.isPlaying())
        	{
        		
        	sb.setProgress(mp.getCurrentPosition());
        	
        	}
		
	}
	

}
=======
import android.app.Activity;
import android.os.Bundle;

public class MusicPlayerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
>>>>>>> 103dac482af960895f58523854529fff3f03075c
