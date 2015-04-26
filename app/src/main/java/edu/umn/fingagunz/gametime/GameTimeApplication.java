package edu.umn.fingagunz.gametime;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Mike on 4/25/2015.
 */
public class GameTimeApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();

		Parse.enableLocalDatastore(this);
		Parse.initialize(this, "d3YN2GzWnbqMLtFXoEobThU12JprVLqRlbnchxNX", "UOJJ0lafxi85sVjHaRQObwRZjAq9v9TDFJBK6KDE");
	}
}
