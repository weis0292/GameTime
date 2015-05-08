package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseQuery;

import edu.umn.fingagunz.gametime.domain.AttendanceCommitment;
import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.domain.Sport;
import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.domain.TeamMember;
import edu.umn.fingagunz.gametime.parse.queryadapter.SpinnerSportsParseQueryAdapter;
import edu.umn.fingagunz.gametime.util.CurrentUserUtil;


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


            Intent playerIdIntent = getIntent();
            String playerId = playerIdIntent.getStringExtra("playerId");
            Player player = new Player();
            player.setObjectId(playerId);
            try { player.fetchIfNeeded(); }
            catch (ParseException e) { e.printStackTrace(); }

            ParseQuery<TeamMember> query = new ParseQuery<>(TeamMember.class);
            query.whereEqualTo("team", newTeam);
            query.whereEqualTo("player", player);
            TeamMember newTeamMember = new TeamMember();
            try {
                newTeamMember = query.getFirst();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            newTeamMember.setPlayer(player);
            newTeamMember.setIsCaptain(true);
            newTeamMember.setTeam(newTeam);

            try { newTeamMember.save(); }
            catch (ParseException ex){}

			// Now navigate to the team detail page for this team
            setResult(RESULT_OK);
            finish();

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
