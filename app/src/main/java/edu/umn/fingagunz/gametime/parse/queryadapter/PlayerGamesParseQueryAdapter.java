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
			v = View.inflate(getContext(), R.layout.game_row, null);
		}

		super.getItemView(game, v, parent);

		v.setTag(game);
		((TextView)v.findViewById(R.id.game_location_description_label)).setText(game.getLocationDescription());
		((TextView)v.findViewById(R.id.game_dateTime_label)).setText(game.getGameDate().toString());

		return v;
	}
}
