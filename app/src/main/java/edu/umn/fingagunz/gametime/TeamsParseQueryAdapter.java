package edu.umn.fingagunz.gametime;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 4/30/2015.
 */
public class TeamsParseQueryAdapter extends ParseQueryAdapter<ParseObject>
{
	public TeamsParseQueryAdapter(Context context, final String playerId)
	{
		super(context, "Team");
//		super (context, new QueryFactory<ParseObject>()
//		{
//			@Override
//			public ParseQuery<ParseObject> create()
//			{
//				//return null;
//				ParseQuery<ParseObject> teamMemberQuery = new ParseQuery<>("TeamMember");
//				final ParseQuery<ParseObject> teamQuery = new ParseQuery<>("Team");
//				teamMemberQuery.whereEqualTo("playerId", playerId);
////				teamMemberQuery.findInBackground(new FindCallback<ParseObject>()
////				{
////					@Override
////					public void done(List<ParseObject> list, ParseException e)
////					{
////						//teamQuery.whereContainedIn("TeamId", list);
////						//teamQuery.whereContainedIn("teamId", list);
////						//teamQuery.contain
////						ArrayList<String> stuff = new ArrayList<>();
////						stuff.add("vVBdPGdgO7");
////						teamQuery.whereContainedIn("teamId", stuff);
////					}
////				});
//				try
//				{
//					List<ParseObject> things = teamMemberQuery.find();
//					List<String> ids = new ArrayList<>();
//					for(ParseObject o : things)
//					{
//						ids.add(o.getString("teamId"));
//					}
//					teamQuery.whereContainedIn("teamId", ids);
//				}
//				catch(ParseException ex)
//				{
//				}
//
//				return teamQuery;
//			}
//		});
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
