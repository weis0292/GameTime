package edu.umn.fingagunz.gametime.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Jesse on 5/5/2015.
 */
@ParseClassName("Player")
public class Player extends ParseObject {

	public String getName() {
		return getString("playerName");
	}

	public String getPhone() {
		return getString("playerPhone");
	}

	public String getEmail() {
		return getString("playerEmail");
	}

}
