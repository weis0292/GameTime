package edu.umn.fingagunz.gametime.domain;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Created by Mike on 5/5/2015.
 */
@ParseClassName("AttendanceCommitment")
public class AttendanceCommitment extends ParseObject
{
	public Player getPlayer()
	{
		Player player = null;
		try { player = getParseObject("player").fetchIfNeeded(); }
		catch (ParseException e) { e.printStackTrace(); }
		return player;
	}
	public void setPlayer(Player player) { put("player", player); }

	public Game getGame()
	{
		Game game = null;
		try { game = getParseObject("game").fetchIfNeeded(); }
		catch (ParseException e) { e.printStackTrace(); }
		return game;
	}
	public void setGame(Game game) { put("game", game); }

	public String getRSVPCode() { return getString("rsvpCode"); }
	public void setRSVPCode(String rsvpCode) { put("rsvpCode", rsvpCode); }
}
