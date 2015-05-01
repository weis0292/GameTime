package edu.umn.fingagunz.gametime;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by Mike on 4/30/2015.
 */
public class TeamsParseQueryAdapter extends ParseQueryAdapter<ParseObject>
{
	public TeamsParseQueryAdapter(Context context)
	{
		super(context, "Team");
	}

	@Override
	public View getItemView(ParseObject object, View v, ViewGroup parent)
	{
		if (v == null)
		{
			v = View.inflate(getContext(), R.layout.activity_teams_row, null);
		}

		super.getItemView(object, v, parent);

		((TextView)v.findViewById(R.id.teams_row_name_label)).setText(object.getString("teamName"));

		return v;
	}
}
