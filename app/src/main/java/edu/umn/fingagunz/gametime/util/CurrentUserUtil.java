package edu.umn.fingagunz.gametime.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.parse.ParseException;
import com.parse.ParseQuery;

import edu.umn.fingagunz.gametime.domain.Player;

/**
 * Created by Jesse on 5/7/2015.
 */
public class CurrentUserUtil {
	private static Player currentPlayer = null;

	public static void resetCurrentPlayer() {
		currentPlayer = null;
	}

	public static Player getCurrentPlayer(Context context) {
		if (currentPlayer == null) {
			String currentUser = getCurrentUser(context);
			if (currentUser == null || "".equals(currentUser)) {
				return null;
			}

			Player player = null;

			ParseQuery<Player> query = new ParseQuery<>(Player.class);
			query.whereEqualTo("playerName", currentUser);
			try {
				player = query.getFirst();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			currentPlayer = player;
		}

		return currentPlayer;
	}

	public static String getCurrentUser(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("profile_preferences", Context.MODE_PRIVATE);
		return preferences.getString("profile_name", "");
	}
}
