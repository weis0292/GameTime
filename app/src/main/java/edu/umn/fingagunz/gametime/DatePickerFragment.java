package edu.umn.fingagunz.gametime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import edu.umn.fingagunz.gametime.domain.Game;

/**
 * Created by Mike on 5/6/2015.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
	private Calendar calendar;
	private Game game;

	public DatePickerFragment(Calendar calendar, Game game)
	{
		this.calendar = calendar;
		this.game = game;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		return new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
	{
		// Get the date the game is currently set to
		Date date = game.getGameDate();
		// Update the values set by the user
		date.setYear(year);
		date.setMonth(monthOfYear);
		date.setDate(dayOfMonth);
		// Set the game date using the updated date
		game.setGameDate(date);
	}
}
