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
import edu.umn.fingagunz.gametime.domain.Player;

public class PlayersParseQueryAdapter extends ParseQueryAdapter<Player> {

    public PlayersParseQueryAdapter(Context context)
    {
        super(context, new ParseQueryAdapter.QueryFactory<Player>()
        {
            @Override
            public ParseQuery<Player> create()
            {
                ParseQuery<Player> query = ParseQuery.getQuery(Player.class);
                return query;
            }
        });

        setTextKey("playerName");

    }

    @Override
    public View getItemView(Player object, View v, ViewGroup parent) {

        if (v == null)
        {
            v = View.inflate(getContext(), R.layout.activity_add_existing_player_row, null);
        }

        super.getItemView(object, v, parent);

            v.setTag(object);
            ((TextView)v.findViewById(R.id.existing_player_name)).setText(object.getName());

        return v;
    }
}
