package com.szilard;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;


public class MusicPlayerActivity extends Activity implements OnClickListener, Runnable, OnSeekBarChangeListener {
	
	
	
	//an enumeration used to determine the different states of the player
	public enum State {playing, stopped, paused}
	long nr;
	private State currentState;
	private MediaPlayer currentSong;
	private Button playButton;
	private Button stopButton;
	private Button nextButton;
	private Button prevButton;
	private SingletonPlaylist playlist;
	private SeekBar seekBar;
	private TextView currentMoment;
	private Handler handler;
	private String threadOutput = new String();
	private Runnable foregroundTask;
	//some constants:
	private final int SELECTION_REQUEST = 1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		//setting the private Button variables and the seekBar
		playButton = (Button) findViewById(R.id.play);
		stopButton = (Button) findViewById(R.id.stop);
		prevButton = (Button) findViewById(R.id.prev);
		nextButton = (Button) findViewById(R.id.next);
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		currentMoment = (TextView) findViewById(R.id.currenttime); 
		
		//setting the OnClickListener to each of them by letting the class implement 
		//this interface
		playButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
		prevButton.setOnClickListener(this);
		
		seekBar.setOnSeekBarChangeListener(this);
		
		
		//initializing some other variables
		playlist = SingletonPlaylist.getInstance();
		currentState = State.stopped;
	
		//setting the timer to update the textview
		Timer myTimer = new Timer();
	
	
		myTimer.schedule(new TimerTask()
		{

			@Override
			public void run() {
				threadMethod();		
			}
			
		},0,20);
		

	}
	////these 2 methods do the work I want to achieve
	private void threadMethod() {
		
		if (currentState == State.playing)
		{
		int amount = currentSong.getCurrentPosition();
		amount = amount / 1000;
		
		threadOutput = "";
		threadOutput = threadOutput + (Integer.toString(amount / 60));
		threadOutput = threadOutput + " : ";
		threadOutput = threadOutput + (Integer.toString(amount % 60));
		this.runOnUiThread(Timer_Tick);
		}
	
	}

	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			if (!currentMoment.getText().equals(threadOutput))
			currentMoment.setText(threadOutput);
			
		}
	};
	///end of the 2 methods

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);
		
		return true;
		
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId())
		{
		case R.id.list: 
		//I have to start a new activity where the user will select the song which 
		//is to be played
		startListActivity();
		break;
		
		case R.id.exit:
			finish();
		break;
					
		}
		
		return true;
	
	}



	private void startListActivity() {
		
		Intent listIntent;
		
		listIntent = new Intent();
		listIntent.setClass(this, MusicSelection.class);
		startActivityForResult(listIntent, SELECTION_REQUEST);
	}
	
	



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		
		switch(requestCode)
		{
			case SELECTION_REQUEST:
				if (resultCode == RESULT_OK)
				{
					String response = data.getStringExtra("SELECTED_MUSIC");
					playSelectedSong(Integer.parseInt(response));	
				}
			break;
				
		}
		
	}



	private void playSelectedSong(int songId) {
		
		if (currentState == State.playing)
			currentSong.stop();
		
		Song localSong = playlist.getSongById(songId);
		String uriString = localSong.getData();
		Uri uri = Uri.parse(uriString);	
		currentSong = MediaPlayer.create(this, uri);
		currentSong.start();
		currentState = State.playing;
		
	}



	@Override
	public void onClick(View arg0) {
	
		switch(arg0.getId())
		{
		
			
		}
		
	}


	private void setSongProgression(int progression) {		
		currentSong.seekTo(progression);
	}


	private int calculateProgression(int progress) {
		//easy math... calculating the progression in the song using the progress of the seekBar
		return (progress * currentSong.getDuration()) / seekBar.getMax();
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		//I want to change things only if the change in progress is done by the user
		//because the other case will be handled separately
		if (fromUser)
		{
			int progression = calculateProgression(seekBar.getProgress());
			setSongProgression(progression);
			
		}
	}



	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	//if I start tracking the touch initiated by the user I have to pause the player and the
	//song and continue only when the touch is over
		currentSong.pause();
		currentState = State.paused;
		
		Toast.makeText(this, Integer.toString((int)nr), Toast.LENGTH_SHORT).show();
		
	}



	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		//when tracking the touch is over we resume playing the song 
		currentSong.start();		
		currentState = State.playing;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
