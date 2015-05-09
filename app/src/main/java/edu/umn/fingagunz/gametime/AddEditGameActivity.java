package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.umn.fingagunz.gametime.domain.AttendanceCommitment;
import edu.umn.fingagunz.gametime.domain.Game;
import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.domain.TeamMember;

//*************************************************************************************************
// AddEditGameActivity will control the screens to add games to the GameTime App.
//************************************************************************************************
public class AddEditGameActivity extends Activity implements OnDatePickerDialogDismissedListener, OnTimePickerDialogDismissedListener {

	// Create a new game object
	private Game game = new Game();
	private Boolean isEditingGame = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// Set the layout of the screen to the add edit game XML
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_game);

		// Grab where we are coming from
		Intent intent = getIntent();

		// Get the teamID from the inten and set the team object for this game
		String teamId = intent.getStringExtra("TeamObjectId");
		Team team = new Team();
		team.setObjectId(teamId);
		game.setTeam(team);

		// If there is already a game in this intent then we are editing
		if (intent.hasExtra("GameObjectId")) {
			isEditingGame = true;
			String gameId = intent.getStringExtra("GameObjectId");

			// Grab the old game data
			ParseQuery<Game> gameQuery = new ParseQuery<>(Game.class);
			gameQuery.whereEqualTo("objectId", gameId);
			try {
				game = gameQuery.getFirst();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			game.setGameDate(new Date());
		}

		// Update the views of date and time
		updateDateOnView();
		updateTimeOnView();
		((EditText) findViewById(R.id.game_edit_description)).setText(game.getLocationDescription());
		((EditText) findViewById(R.id.game_edit_address)).setText(game.getAddress());
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_add_edit_game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings)
//		{
//			return true;
//		}

		switch (item.getItemId()) {

			// If we are accepting the new game or edited game, save the data off
			case R.id.action_accept:

				// Set up the location and addresses
				game.setLocationDescription(((EditText) findViewById(R.id.game_edit_description)).getText().toString());
				game.setAddress(((EditText) findViewById(R.id.game_edit_address)).getText().toString());
				try {

					// Save the game to the server
					game.save();

					if (!isEditingGame) {
						ParseQuery<TeamMember> teamMembersQuery = new ParseQuery<>(TeamMember.class);
						teamMembersQuery.whereEqualTo("team", game.getTeam());
						List<TeamMember> teamMembers = teamMembersQuery.find();
						for (TeamMember teamMember : teamMembers) {
							AttendanceCommitment commitment = new AttendanceCommitment();
							commitment.setGame(game);
							commitment.setPlayer(teamMember.getPlayer());
							commitment.setRSVPCode("Maybe");
							commitment.saveInBackground();
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

				setResult(RESULT_OK);
				finish();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void onDatePickerClicked(View view) {
		DatePickerFragment datePicker = new DatePickerFragment(game);
		datePicker.SetDatePickerDismissedListener(this);
		datePicker.show(getFragmentManager(), "datePicker");
	}

	@Override
	public void onDatePickerDialogDismissed() {
		updateDateOnView();
	}

	private void updateDateOnView() {
		Date gameDate = game.getGameDate();
		if (gameDate != null) {
			((TextView) findViewById(R.id.game_date_label)).setText(new SimpleDateFormat("E MMM d, yyyy").format(game.getGameDate()));
		}
	}

	public void onTimePickerClicked(View view) {
		TimePickerFragment timePicker = new TimePickerFragment(game);
		timePicker.setTimePickerDismissedListener(this);
		timePicker.show(getFragmentManager(), "timePicker");
	}

	@Override
	public void onTimerPickerDialogDismissed() {
		updateTimeOnView();
	}

	// Update the view on the gametime app!
	private void updateTimeOnView() {
		Date gameDate = game.getGameDate();
		if (gameDate != null) {
			((TextView) findViewById(R.id.game_time_label)).setText(new SimpleDateFormat("h:mm a").format(game.getGameDate()));
		}
	}
}
