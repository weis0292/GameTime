package edu.umn.fingagunz.gametime;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import edu.umn.fingagunz.gametime.domain.AttendanceCommitment;
import edu.umn.fingagunz.gametime.domain.Game;
import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.domain.Sport;
import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.domain.TeamMember;

/**
 * Created by Mike on 4/25/2015.
 */
public class GameTimeApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();

		// register ParseObject subclasses
		ParseObject.registerSubclass(AttendanceCommitment.class);
		ParseObject.registerSubclass(Game.class);
		ParseObject.registerSubclass(Player.class);
		ParseObject.registerSubclass(Sport.class);
		ParseObject.registerSubclass(Team.class);
		ParseObject.registerSubclass(TeamMember.class);

		//Parse.enableLocalDatastore(this);
		Parse.initialize(this, "d3YN2GzWnbqMLtFXoEobThU12JprVLqRlbnchxNX", "UOJJ0lafxi85sVjHaRQObwRZjAq9v9TDFJBK6KDE");
	}
}
