package edu.umn.fingagunz.gametime.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Jesse on 5/5/2015.
 */
@ParseClassName("TeamMember")
public class TeamMember extends ParseObject {
	public Player getPlayer() {
		return (Player)get("player");
	}
}
