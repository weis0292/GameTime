package edu.umn.fingagunz.gametime.listadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.umn.fingagunz.gametime.R;
import edu.umn.fingagunz.gametime.domain.Player;

/**
 * Created by Jesse on 5/5/2015.
 */
public class PlayerListAdapter extends ArrayAdapter<Player> {

	private Context context;
	private Player[] data;
	private int layoutResourceId;

	public PlayerListAdapter(Context context, int resource, Player[] objects) {
		super(context, resource, objects);

		this.layoutResourceId = resource;
		this.data = objects;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		PlayerHolder holder = null;

		if(row == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();

			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new PlayerHolder();
			holder.playerName = (TextView)row.findViewById(R.id.team_detail_players_row_name);

			row.setTag(holder);
		} else {
			holder = (PlayerHolder)row.getTag();
		}

		Player player = data[position];
		holder.playerName.setText(player.getName());

		return row;
	}

	static class PlayerHolder {
		TextView playerName;
	}
}
