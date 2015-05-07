package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseQuery;

import edu.umn.fingagunz.gametime.domain.AttendanceCommitment;
import edu.umn.fingagunz.gametime.domain.Game;
import edu.umn.fingagunz.gametime.domain.Player;

public class GameDetailActivity extends Activity
{
	final private static int REPLY_ACCEPT = 0x00;
	final private static int REPLY_MAYBE = 0x01;
	final private static int REPLY_DECLINE = 0x02;

	private AttendanceCommitment attendanceCommitment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_detail);

		getAcceptImageView().setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				setReply(REPLY_ACCEPT);
			}
		});

		getMaybeImageView().setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				setReply(REPLY_MAYBE);
			}
		});

		getDeclineImageView().setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				setReply(REPLY_DECLINE);
			}
		});

		Intent intent = getIntent();
		String gameId = intent.getStringExtra("gameId");
		String playerId = intent.getStringExtra("currentUserId");
		Game game = new Game();
		game.setObjectId(gameId);
		try { game.fetchIfNeeded(); }
		catch (ParseException e) { e.printStackTrace(); }

		Player player = new Player();
		player.setObjectId(playerId);

		ParseQuery<AttendanceCommitment> query = new ParseQuery<>(AttendanceCommitment.class);
		query.whereEqualTo("game", game);
		query.whereEqualTo("player", player);
		try { attendanceCommitment = query.getFirst(); }
		catch (ParseException e) { e.printStackTrace(); }

		String rsvpCode = attendanceCommitment.getRSVPCode();
		switch (rsvpCode)
		{
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

	private void setReplyIcons(int reply)
	{
		getAcceptImageView().setImageResource(reply == REPLY_ACCEPT ? R.mipmap.ic_thumb_up_black_24dp : R.mipmap.ic_thumb_up_grey600_24dp);
		getMaybeImageView().setImageResource(reply == REPLY_MAYBE ? R.mipmap.ic_thumbs_up_down_black_24dp : R.mipmap.ic_thumbs_up_down_grey600_24dp);
		getDeclineImageView().setImageResource(reply == REPLY_DECLINE ? R.mipmap.ic_thumb_down_black_24dp : R.mipmap.ic_thumb_down_grey600_24dp);
	}

	private void setReply(int reply)
	{
		String rsvpCode = reply == REPLY_ACCEPT ? "Yes" : (reply == REPLY_MAYBE ? "Maybe" : "No");
		attendanceCommitment.setRSVPCode(rsvpCode);
		try { attendanceCommitment.save(); }
		catch (ParseException e) { e.printStackTrace(); }
		setReplyIcons(reply);
	}

	private ImageView getAcceptImageView()
	{
		return (ImageView)findViewById(R.id.game_detail_accept_icon);
	}

	private ImageView getMaybeImageView()
	{
		return (ImageView)findViewById(R.id.game_detail_maybe_icon);
	}

	private ImageView getDeclineImageView()
	{
		return (ImageView)findViewById(R.id.game_detail_decline_icon);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_game_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_edit:
				return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
