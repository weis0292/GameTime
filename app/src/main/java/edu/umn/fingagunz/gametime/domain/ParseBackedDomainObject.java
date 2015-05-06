package edu.umn.fingagunz.gametime.domain;

import com.parse.ParseObject;

/**
 * Created by Jesse on 5/5/2015.
 */
public class ParseBackedDomainObject {
	protected ParseObject backingObject;

	public ParseBackedDomainObject(ParseObject parseObject) {
		if(parseObject == null) {
			throw new IllegalStateException("must back with a parse object");
		}

		this.backingObject = parseObject;
	}
}
