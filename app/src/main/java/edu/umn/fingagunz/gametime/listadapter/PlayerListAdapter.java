package edu.umn.fingagunz.gametime.listadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
			holder.playerName = (TextView)row.findViewById(R.id.player_row_player_name);
			holder.genderImage = (ImageView)row.findViewById(R.id.player_row_gender_icon);

			row.setTag(holder);
		} else {
			holder = (PlayerHolder)row.getTag();
		}

		Player player = data[position];
		holder.playerName.setText(player.getName());

		String gender = player.getGender();
		gender = (gender == null) ? "?" : gender;

		switch (gender)
		{
			case "M":
				holder.genderImage.setImageResource(R.mipmap.ic_gender_male_black_24dp);
				break;

			case "F":
				holder.genderImage.setImageResource(R.mipmap.ic_gender_female_black_24dp);
				break;

			case "?":
				holder.genderImage.setImageResource(R.mipmap.ic_gender_transgender_black_24dp);
				break;
		}

		return row;
	}

	static class PlayerHolder {
		TextView playerName;
		ImageView genderImage;
	}
}
