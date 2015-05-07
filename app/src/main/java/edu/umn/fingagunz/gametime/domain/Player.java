package edu.umn.fingagunz.gametime.domain;

import com.parse.ParseClassName;
import com.parse.ParseException;
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

    public void setName(String name) { put("playerName", name); }
    public void setPhone(String phone) { put("playerPhone", phone); }
    public void setAddress(String address) { put("playerAddress", address); }
    public void setEmail(String email) { put("playerEmail", email); }
    public void setGender(String gender) { put("playerGender", gender); }

}
