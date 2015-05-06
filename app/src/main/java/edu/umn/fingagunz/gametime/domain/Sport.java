package edu.umn.fingagunz.gametime.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Mike on 5/5/2015.
 */
@ParseClassName("Sport")
public class Sport extends ParseObject
{
	public String getName() { return getString("sportName"); }
	public String getIcon() { return getString("sportIcon"); }
}
