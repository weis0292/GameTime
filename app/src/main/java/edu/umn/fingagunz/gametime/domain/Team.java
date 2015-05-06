package edu.umn.fingagunz.gametime.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Jesse on 5/5/2015.
 */
@ParseClassName("Team")
public class Team extends ParseObject {

	public String getTeamName() {
		return getString("teamName");
	}
	public void setTeamName(String teamName) { put("teamName", teamName); }

	public Sport getSport()
	{
		Sport sport = null;
		try { sport = getParseObject("sport").fetchIfNeeded(); }
		catch(Exception ex) { }
		return sport;
	}
	public void setSport(Sport sport) { put("sport", sport); }
}
