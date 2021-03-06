package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.umn.fingagunz.gametime.domain.AttendanceCommitment;
import edu.umn.fingagunz.gametime.domain.Game;
import edu.umn.fingagunz.gametime.domain.Player;

// Set up the screen to view the details about a particular game
public class GameDetailActivity extends Activity {
	final private static int REPLY_ACCEPT = 0x00;
	final private static int REPLY_MAYBE = 0x01;
	final private static int REPLY_DECLINE = 0x02;

	private AttendanceCommitment attendanceCommitment;
	private Game game = new Game();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_detail);

		// set up click listeners. This is so if a user selects that they can accept or decline a game
		getAcceptImageView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setReply(REPLY_ACCEPT);
			}
		});

		getMaybeImageView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setReply(REPLY_MAYBE);
			}
		});

		getDeclineImageView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setReply(REPLY_DECLINE);
			}
		});

		// Get the intent to find the gameID's and playerID's
		Intent intent = getIntent();
		String gameId = intent.getStringExtra("gameId");
		String playerId = intent.getStringExtra("playerId");

		ParseQuery<Game> gameQuery = new ParseQuery<>(Game.class);
		gameQuery.whereEqualTo("objectId", gameId);
		try {
			game = gameQuery.getFirst();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Null check on the game date data. If available set the game date
		if (game.getGameDate() != null)
		{
			((TextView) findViewById(R.id.game_detail_date_label)).setText(new SimpleDateFormat("E MMM d, yyyy").format(game.getGameDate()));
			((TextView) findViewById(R.id.game_detail_time_label)).setText(new SimpleDateFormat("h:mm a").format(game.getGameDate()));
		}

		// If the game location is available set the game location
		if (game.getLocationDescription() != null)
		{
			((TextView) findViewById(R.id.game_detail_location_description)).setText(game.getLocationDescription());
		}

		// If the game address is available, set the game address
		if (game.getAddress() != null)
		{
			((TextView) findViewById(R.id.game_detail_location_address)).setText(game.getAddress());
		}

		// Create a player object and set the player ID
		Player player = new Player();
		player.setObjectId(playerId);

		ParseQuery<AttendanceCommitment> query = new ParseQuery<>(AttendanceCommitment.class);
		query.whereEqualTo("game", game);
		query.whereEqualTo("player", player);
		try {
			List<AttendanceCommitment> commitments = query.find();
			if (commitments.size() > 0)
			{
				attendanceCommitment = commitments.get(0);
			}
			else
			{
				// If no commitment exists, add a default one.
				attendanceCommitment = new AttendanceCommitment();
				attendanceCommitment.setPlayer(player);
				attendanceCommitment.setGame(game);
				attendanceCommitment.setRSVPCode("Maybe");
				attendanceCommitment.save();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String rsvpCode = attendanceCommitment.getRSVPCode();
		switch (rsvpCode) {
			case "Yes":
				setReplyIcons(REPLY_ACCEPT);
				break;

			case "No":
				setReplyIcons(REPLY_DECLINE);
				break;

			case "Maybe":
				setReplyIcons(REPLY_MAYBE);
				break;
		}
	}

	// Set up the ICONs used to reply to game attendence
	private void setReplyIcons(int reply) {
		getAcceptImageView().setImageResource(reply == REPLY_ACCEPT ? R.mipmap.ic_thumb_up_black_36dp : R.mipmap.ic_thumb_up_grey600_36dp);
		getMaybeImageView().setImageResource(reply == REPLY_MAYBE ? R.mipmap.ic_thumbs_up_down_black_36dp : R.mipmap.ic_thumbs_up_down_grey600_36dp);
		getDeclineImageView().setImageResource(reply == REPLY_DECLINE ? R.mipmap.ic_thumb_down_black_36dp : R.mipmap.ic_thumb_down_grey600_36dp);
	}

	// Set the reply in the GAMETIME app as yes no or maybe
	private void setReply(int reply) {
		String rsvpCode = reply == REPLY_ACCEPT ? "Yes" : (reply == REPLY_MAYBE ? "Maybe" : "No");
		attendanceCommitment.setRSVPCode(rsvpCode);
		try {
			attendanceCommitment.save();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		setReplyIcons(reply);
	}

	// Get the correct images!
	private ImageView getAcceptImageView() {
		return (ImageView) findViewById(R.id.game_detail_accept_icon);
	}

	private ImageView getMaybeImageView() {
		return (ImageView) findViewById(R.id.game_detail_maybe_icon);
	}

	private ImageView getDeclineImageView() {
		return (ImageView) findViewById(R.id.game_detail_decline_icon);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_game_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_edit:
				Intent intent = new Intent(this, AddEditGameActivity.class);
				intent.putExtra("GameObjectId", game.getObjectId());
				startActivity(intent);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
	}
}
