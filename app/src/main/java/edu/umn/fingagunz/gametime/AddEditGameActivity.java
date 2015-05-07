package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.umn.fingagunz.gametime.domain.Game;
import edu.umn.fingagunz.gametime.domain.Team;


public class AddEditGameActivity extends Activity implements OnDatePickerDialogDismissedListener, OnTimePickerDialogDismissedListener
{
	private Game game = new Game();
	private Team team = new Team();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_game);

		Intent intent = getIntent();

		String teamId = intent.getStringExtra("TeamObjectId");
		team.setObjectId(teamId);
		try { team.fetchIfNeeded(); }
		catch (ParseException e) { e.printStackTrace(); }

		if (intent.hasExtra("GameObjectId"))
		{
			String gameId = intent.getStringExtra("GameObjectId");
			game.setObjectId(gameId);
			try { game.fetchIfNeeded(); }
			catch (ParseException e) { e.printStackTrace(); }
		}
		else
		{
			game.setGameDate(new Date());
		}

		updateDateOnView();
		updateTimeOnView();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_add_edit_game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings)
//		{
//			return true;
//		}

		switch (item.getItemId())
		{
			case R.id.action_accept:
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void onDatePickerClicked(View view)
	{
		DatePickerFragment datePicker = new DatePickerFragment(game);
		datePicker.SetDatePickerDismissedListener(this);
		datePicker.show(getFragmentManager(), "datePicker");
	}

	@Override
	public void onDatePickerDialogDismissed()
	{
		updateDateOnView();
	}

	private void updateDateOnView()
	{
		((TextView)findViewById(R.id.game_date_label)).setText(new SimpleDateFormat("E MMM d, yyyy").format(game.getGameDate()));
	}

	public void onTimePickerClicked(View view)
	{
		TimePickerFragment timePicker = new TimePickerFragment(game);
		timePicker.setTimePickerDismissedListener(this);
		timePicker.show(getFragmentManager(), "timePicker");
	}

	@Override
	public void onTimerPickerDialogDismissed()
	{
		updateTimeOnView();
	}

	private void updateTimeOnView()
	{
		((TextView)findViewById(R.id.game_time_label)).setText(new SimpleDateFormat("h:mm a").format(game.getGameDate()));
	}
}
