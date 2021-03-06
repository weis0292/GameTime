package edu.umn.fingagunz.gametime;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by Mike on 4/30/2015.
 */
public class TeamsParseQueryAdapter extends ParseQueryAdapter<ParseObject>
{
	public TeamsParseQueryAdapter(Context context, final ParseObject player)
	{
		super(context, new QueryFactory<ParseObject>()
		{
			@Override
			public ParseQuery<ParseObject> create()
			{
				ParseQuery<ParseObject> query = ParseQuery.getQuery("TeamMember");
				query.whereEqualTo("player", player);
				return query;
			}
		});
	}

	@Override
	public View getItemView(ParseObject object, View v, ViewGroup parent)
	{
		if (v == null)
		{
			v = View.inflate(getContext(), R.layout.activity_teams_row, null);
		}

		super.getItemView(object, v, parent);

		try
		{
			ParseObject team = object.getParseObject("team");
			String teamName = team.fetchIfNeeded().getString("teamName");
			v.setTag(team);
			((TextView)v.findViewById(R.id.teams_row_name_label)).setText(teamName);
			ParseObject sport = team.getParseObject("sport").fetchIfNeeded();
			((TextView)v.findViewById(R.id.teams_row_sport_label)).setText(sport.getString("sportName"));
		}
		catch(ParseException ex)
		{
			// I have absolutely no idea what to do here?!?!?!
		}


		return v;
	}
}
