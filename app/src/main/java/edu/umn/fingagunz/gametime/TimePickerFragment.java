package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
	private Game game;
	private OnTimePickerDialogDismissedListener dismissedListener;

	public TimePickerFragment(Game game)
	{
		this.game = game;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(game.getGameDate());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		Activity activity = getActivity();
		return new TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute)
	{
		// Get the date the game is currently set to
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(game.getGameDate());
		// Update the values set by the user
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		// Set the game date using the updated date
		game.setGameDate(calendar.getTime());
	}

	@Override
	public void onDismiss(DialogInterface dialog)
	{
		super.onDismiss(dialog);

		if (dismissedListener != null)
		{
			dismissedListener.onTimerPickerDialogDismissed();
		}
	}

	public void setTimePickerDismissedListener(OnTimePickerDialogDismissedListener dismissedListener)
	{
		this.dismissedListener = dismissedListener;
	}
}
