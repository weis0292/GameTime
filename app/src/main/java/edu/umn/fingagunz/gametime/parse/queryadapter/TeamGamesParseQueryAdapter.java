package edu.umn.fingagunz.gametime.parse.queryadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import edu.umn.fingagunz.gametime.R;
import edu.umn.fingagunz.gametime.domain.AttendanceCommitment;
import edu.umn.fingagunz.gametime.domain.Game;
import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.util.CurrentUserUtil;

/**
 * Created by Jesse on 5/7/2015.
 */
public class TeamGamesParseQueryAdapter extends ParseQueryAdapter<Game> {

	public TeamGamesParseQueryAdapter(Context context, final Team team) {
		super(context, new QueryFactory<Game>() {
			@Override
			public ParseQuery<Game> create() {
				ParseQuery<Game> gameQuery = ParseQuery.getQuery(Game.class);
				gameQuery.whereEqualTo("team", team);

				return gameQuery;
			}
		});

		setTextKey("gameLocationDescription");
	}

	@Override
	public View getItemView(Game game, View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.game_row, null);
		}

		super.getItemView(game, v, parent);

        v.setTag(game);

        Player player = CurrentUserUtil.getCurrentPlayer(getContext());
        ParseQuery<AttendanceCommitment> query = new ParseQuery<>(AttendanceCommitment.class);
        query.whereEqualTo("game", game);
        query.whereEqualTo("player", player);
        AttendanceCommitment attendanceCommitment = new AttendanceCommitment();
        try {
            attendanceCommitment = query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String rsvpCode = attendanceCommitment.getRSVPCode();
        if(rsvpCode!=null) {
            switch (rsvpCode) {
                case "Yes":
                    ((ImageView) v.findViewById(R.id.game_attendance_commitment)).setImageResource(R.mipmap.ic_thumb_up_black_24dp);
                    break;

                case "No":
                    ((ImageView) v.findViewById(R.id.game_attendance_commitment)).setImageResource(R.mipmap.ic_thumb_down_black_24dp);
                    break;

                case "Maybe":
                    ((ImageView) v.findViewById(R.id.game_attendance_commitment)).setImageResource(R.mipmap.ic_thumbs_up_down_black_24dp);
                    break;
            }
        }
        else{
            ((ImageView) v.findViewById(R.id.game_attendance_commitment)).setImageResource(R.mipmap.ic_thumbs_up_down_grey600_24dp);
        }
		((TextView) v.findViewById(R.id.game_location_description_label)).setText(game.getLocationDescription());
        //((TextView)v.findViewById(R.id.game_dateTime_label)).setText(game.getGameDate().toString());

		return v;
	}
}
