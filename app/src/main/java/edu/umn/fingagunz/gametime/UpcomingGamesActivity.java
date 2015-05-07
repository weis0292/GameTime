package edu.umn.fingagunz.gametime;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.ParseException;

import edu.umn.fingagunz.gametime.domain.Game;
import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.parse.queryadapter.PlayerGamesParseQueryAdapter;


public class UpcomingGamesActivity extends ListActivity
{

    String currentUserId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upcoming_games);

		Intent intent = getIntent();
        currentUserId = intent.getStringExtra("playerId");

		Player player = new Player();
		player.setObjectId(currentUserId);
		try { player.fetchIfNeeded(); }
		catch (ParseException e) { e.printStackTrace(); }
		ListAdapter adapter = new PlayerGamesParseQueryAdapter(this, player);
        setListAdapter(adapter);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_upcoming_games, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);

		Game game = (Game)v.getTag();
		Intent intent = new Intent(this, GameDetailActivity.class);
		intent.putExtra("gameId", game.getObjectId());
		intent.putExtra("playerId", currentUserId);
		startActivity(intent);
	}
}
