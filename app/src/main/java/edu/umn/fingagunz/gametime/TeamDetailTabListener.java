package edu.umn.fingagunz.gametime;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by WeispfeM on 4/24/2015.
 */
public class TeamDetailTabListener<T extends Fragment> implements ActionBar.TabListener
{
	private Fragment fragment;
	private final Activity activity;
	private final String tag;
	private final Class<T> genericClass;

	public TeamDetailTabListener(Activity activity, String tag, Class<T> genericClass)
	{
		this.activity = activity;
		this.tag = tag;
		this.genericClass = genericClass;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
	{
		if (fragment == null)
		{
			fragment = Fragment.instantiate(activity, genericClass.getName());
			ft.add(android.R.id.content, fragment, tag);
		}
		else
		{
			ft.attach(fragment);
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft)
	{
		if(fragment != null)
		{
			ft.detach(fragment);
		}
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft)
	{

	}
}
