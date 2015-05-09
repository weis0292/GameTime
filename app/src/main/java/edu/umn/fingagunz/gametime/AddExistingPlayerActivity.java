package edu.umn.fingagunz.gametime;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import edu.umn.fingagunz.gametime.domain.AttendanceCommitment;
import edu.umn.fingagunz.gametime.domain.Game;
import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.domain.TeamMember;
import edu.umn.fingagunz.gametime.parse.queryadapter.PlayersParseQueryAdapter;

// Class to support adding an existing player from the Gametime servers
public class AddExistingPlayerActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_existing_player);

        ListAdapter adapter = new PlayersParseQueryAdapter(this);
        setListAdapter(adapter);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        // Figure out which item in the list was clicked
        super.onListItemClick(l, v, position, id);

        // get that player ID from the clicked position
        Player existingPlayer = (Player)v.getTag();

        // Get an intent to figure out which team the player is on
        Intent teamIdIntent = getIntent();
        String teamId = teamIdIntent.getStringExtra("TeamObjectId");

        Team team = new Team();
        team.setObjectId(teamId);
        try {
            team.fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Find possible existing team and existing player
        ParseQuery<TeamMember> query = new ParseQuery<>(TeamMember.class);
        query.whereEqualTo("team", team);
        query.whereEqualTo("player", existingPlayer);
        TeamMember newTeamMember = new TeamMember();
        try {
            newTeamMember = query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set the player to the team
        newTeamMember.setPlayer(existingPlayer);
        newTeamMember.setTeam(team);

        try
        {
	        newTeamMember.save();

	        ParseQuery<Game> gamesQuery = new ParseQuery<>(Game.class);
	        gamesQuery.whereEqualTo("team", team);
	        List<Game> games = gamesQuery.find();

	        for (Game game : games)
	        {
		        AttendanceCommitment commitment = new AttendanceCommitment();
		        commitment.setPlayer(newTeamMember.getPlayer());
		        commitment.setGame(game);
		        commitment.setRSVPCode("Maybe");
		        commitment.saveInBackground();
	        }
        }
        catch (ParseException ex) { }

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_existing_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
