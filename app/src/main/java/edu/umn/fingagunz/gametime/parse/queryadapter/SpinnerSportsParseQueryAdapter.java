package edu.umn.fingagunz.gametime.parse.queryadapter;

import android.content.Context;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

import edu.umn.fingagunz.gametime.R;

/**
 * Created by Mike on 5/5/2015.
 */
public class SpinnerSportsParseQueryAdapter extends ParseQueryAdapter<ParseObject>
{
	public SpinnerSportsParseQueryAdapter(Context context)
	{
		super(context, "Sport");

		// Set what to show in the drop down box.
		setTextKey("sportName");
	}
}
