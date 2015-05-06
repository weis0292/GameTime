package edu.umn.fingagunz.gametime.domain;

import com.parse.ParseObject;

/**
 * Created by Jesse on 5/5/2015.
 */
public class Team extends ParseBackedDomainObject {

	public static final String PARSE_OBJECT_NAME = "Team";

	public Team(ParseObject backingObject) {
		super(backingObject);
	}


	public String getObjectId() {
		return backingObject.getObjectId();
	}

	public String getTeamName() {
		return (String)backingObject.get("teamName");
	}

}
