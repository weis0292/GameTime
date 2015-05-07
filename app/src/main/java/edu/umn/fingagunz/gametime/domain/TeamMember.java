package edu.umn.fingagunz.gametime.domain;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Created by Jesse on 5/5/2015.
 */
@ParseClassName("TeamMember")
public class TeamMember extends ParseObject {
	public Player getPlayer() {
		try {
			return (Player)getParseObject("player").fetchIfNeeded();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
    public void setPlayer(Player player){
        put("player", player);
    }
    public void setIsCaptain(boolean isCaptain){
        put("isCaptain", isCaptain);
    }
    public void setTeam(Team team){
        put("team", team);
    }
}
