package edu.umn.fingagunz.gametime.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.parse.ParseException;
import com.parse.ParseQuery;

import edu.umn.fingagunz.gametime.R;
import edu.umn.fingagunz.gametime.domain.Player;

public class CurrentUserUtil {

    public static Player getCurrentUser() {
        ParseQuery<Player> query = new ParseQuery<>(Player.class);
        query.whereEqualTo("playerName", getProfileName());
        Player currentUser = null;

        try {
            currentUser = query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentUser;
    }

    private final Context context;
    private final SharedPreferences sharedPrefs;
    public CurrentUserUtil(Context context){
        this.context = context;
        sharedPrefs = context.getSharedPreferences("name", 0);
    }

    public static String getProfileName() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.preferences_profile), Context.MODE_PRIVATE);
        return preferences.getString(getString(R.string.profile_name_key), "");
    }
}