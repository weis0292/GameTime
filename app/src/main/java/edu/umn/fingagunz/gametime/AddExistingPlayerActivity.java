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

import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.domain.TeamMember;
import edu.umn.fingagunz.gametime.parse.queryadapter.PlayersParseQueryAdapter;


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
        super.onListItemClick(l, v, position, id);

        Player existingPlayer = (Player)v.getTag();

        Intent teamIdIntent = getIntent();
        String teamId = teamIdIntent.getStringExtra("TeamObjectId");

        Team team = new Team();
        team.setObjectId(teamId);
        try {
            team.fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TeamMember newTeamMember = new TeamMember();
        newTeamMember.setPlayer(existingPlayer);
        newTeamMember.setTeam(team);

        try { newTeamMember.save(); }
        catch (ParseException ex){}

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
