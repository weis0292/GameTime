package edu.umn.fingagunz.gametime.parse.queryadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import edu.umn.fingagunz.gametime.R;
import edu.umn.fingagunz.gametime.domain.Game;
import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.domain.TeamMember;

/**
 * Created by Mike on 5/6/2015.
 */
public class PlayerGamesParseQueryAdapter extends ParseQueryAdapter<Game>
{
	public PlayerGamesParseQueryAdapter(Context context, final Player player)
	{
		super(context, new QueryFactory<Game>()
		{
			@Override
			public ParseQuery<Game> create()
			{
				ParseQuery<Game> gameQuery = ParseQuery.getQuery(Game.class);
				ParseQuery<TeamMember> teamMemberQuery = ParseQuery.getQuery(TeamMember.class);
				teamMemberQuery.whereEqualTo("player", player);
				gameQuery.whereMatchesKeyInQuery("team", "team", teamMemberQuery);
				return gameQuery;
			}
		});

		setTextKey("gameLocationDescription");
	}

	@Override
	public View getItemView(Game game, View v, ViewGroup parent)
	{
		if (v == null)
		{
			v = View.inflate(getContext(), R.layout.activity_teams_row, null);
		}

		super.getItemView(game, v, parent);

//		try
//		{
//			ParseObject team = object.getParseObject("team");
//			String teamName = team.fetchIfNeeded().getString("teamName");
//			((TextView)v.findViewById(R.id.teams_row_name_label)).setText(teamName);
//		}
//		catch(ParseException ex)
//		{
//			// I have absolutely no idea what to do here?!?!?!
//		}

		return v;
	}
}
