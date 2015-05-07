package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseException;

import java.util.Calendar;
import java.util.Date;

import edu.umn.fingagunz.gametime.domain.Game;


public class AddEditGameActivity extends Activity
{
	private Game game = new Game();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_game);

		Intent intent = getIntent();
		if (intent.hasExtra("gameId"))
		{
			String gameId = intent.getStringExtra("gameId");
			game.setObjectId(gameId);
			try { game.fetchIfNeeded(); }
			catch (ParseException e) { e.printStackTrace(); }
		}
		else
		{
			game.setGameDate(new Date());
		}
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
		DialogFragment datePicker = new DatePickerFragment(game);
		datePicker.show(getFragmentManager(), "datePicker");
	}
}
