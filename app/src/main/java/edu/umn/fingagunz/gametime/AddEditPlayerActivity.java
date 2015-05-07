package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;

import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.domain.Sport;
import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.domain.TeamMember;
import edu.umn.fingagunz.gametime.parse.queryadapter.SpinnerSportsParseQueryAdapter;


public class AddEditPlayerActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_player);
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_add_edit_player, menu);
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
            Player newPlayer = new Player();
            newPlayer.setName(((EditText)findViewById(R.id.add_edit_player_name)).getText().toString());
            newPlayer.setPhone(((EditText)findViewById(R.id.add_edit_player_phone)).getText().toString());
            newPlayer.setAddress(((EditText)findViewById(R.id.add_edit_player_address)).getText().toString());
            newPlayer.setEmail(((EditText)findViewById(R.id.add_edit_player_email)).getText().toString());
            newPlayer.setGender(((Spinner)findViewById(R.id.add_edit_player_gender)).getSelectedItem().toString());


            //TODO  if this playerName already exists, treat this as an Update

            try { newPlayer.save(); }
            catch (ParseException ex) { }



            Intent teamIdIntent = getIntent();
            String teamId = teamIdIntent.getStringExtra("TeamObjectId");

            Team team = new Team();
            team.setObjectId(teamId);
            try { team.fetchIfNeeded(); }
            catch (ParseException e) { e.printStackTrace(); }

            TeamMember newTeamMember = new TeamMember();
            newTeamMember.setPlayer(newPlayer);
            newTeamMember.setTeam(team);

            try { newTeamMember.save(); }
            catch (ParseException ex){}

            // Now navigate to the team detail page for this team
            Intent intent = new Intent(this, TeamDetailActivity.class);
            intent.putExtra("TeamObjectId", team.getObjectId());
            startActivity(intent);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
