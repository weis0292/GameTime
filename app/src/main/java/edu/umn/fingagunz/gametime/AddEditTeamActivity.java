package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;

import edu.umn.fingagunz.gametime.domain.Sport;
import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.parse.queryadapter.SpinnerSportsParseQueryAdapter;


public class AddEditTeamActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_team);

		((Spinner)findViewById(R.id.add_edit_team_sports)).setAdapter(new SpinnerSportsParseQueryAdapter(this));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_add_edit_team, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_accept)
		{
			// Get the sport Parse Object
			Sport sport = (Sport)((Spinner)findViewById(R.id.add_edit_team_sports)).getSelectedItem();

			// Here we need to save the team information.
			Team newTeam = new Team();
			newTeam.setTeamName(((EditText)findViewById(R.id.add_edit_team_team_name)).getText().toString());
			newTeam.setSport(sport);
			try { newTeam.save(); }
			catch (ParseException ex) { }

			// Now navigate to the team detail page for this team
			Intent intent = new Intent(this, TeamDetailActivity.class);
			intent.putExtra("TeamObjectId", newTeam.getObjectId());
			startActivity(intent);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
