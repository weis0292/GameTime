package edu.umn.fingagunz.gametime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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
	private Game game;
	private OnDatePickerDialogDismissedListener dismissedListener;

	public DatePickerFragment(Game game)
	{
		this.game = game;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(game.getGameDate());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
	{
		// Get the date the game is currently set to
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(game.getGameDate());
		// Update the values set by the user
		calendar.set(year, monthOfYear, dayOfMonth);
		// Set the game date using the updated date
		game.setGameDate(calendar.getTime());
	}

	@Override
	public void onDismiss(DialogInterface dialog)
	{
		super.onDismiss(dialog);

		if(dismissedListener != null)
		{
			dismissedListener.onDatePickerDialogDismissed();
		}
	}

	public void SetDatePickerDismissedListener(OnDatePickerDialogDismissedListener dismissedListener)
	{
		this.dismissedListener = dismissedListener;
	}
}
