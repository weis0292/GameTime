package edu.umn.fingagunz.gametime.domain;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Mike on 5/5/2015.
 */
@ParseClassName("Game")
public class Game extends ParseObject {
	public Team getTeam() {
		Team team = null;
		try {
			team = getParseObject("team").fetchIfNeeded();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return team;
	}

	public void setTeam(Team team) {
		put("team", team);
	}

	public Date getGameDate() {
		return getDate("gameDate");
	}

	public void setGameDate(Date date) {
		put("gameDate", date);
	}

//	public String getGameTime() { return getString("gameTime"); }
//	public void setGameTime(String gameTime) { put("gameTime", gameTime); }

	public String getLocationDescription() {
		return getString("gameLocationDescription");
	}

	public void setLocationDescription(String locationDescription) {
		put("gameLocationDescription", locationDescription);
	}

	public String getAddress() {
		return getString("gameAddress");
	}

	public void setAddress(String address) {
		put("gameAddress", address);
	}
}
