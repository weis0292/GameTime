package edu.umn.fingagunz.gametime;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import edu.umn.fingagunz.gametime.domain.Game;

/**
 * Created by Mike on 5/6/2015.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
{
	private Calendar calendar;
	private Game game;

	public TimePickerFragment(Calendar calendar, Game game)
	{
		this.calendar = calendar;
		this.game = game;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		return new TimePickerDialog(getActivity(), this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute)
	{
		Date date = game.getGameDate();
		date.setHours(hourOfDay);
		date.setMinutes(minute);
		game.setGameDate(date);
	}
}
