package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import edu.umn.fingagunz.gametime.domain.Player;
import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.domain.TeamMember;
import edu.umn.fingagunz.gametime.parse.queryadapter.TeamPlayersParseQueryAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamDetailPlayersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamDetailPlayersFragment extends ListFragment {

	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";


	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment TeamDetailPlayersFragment.
	 */
	public static TeamDetailPlayersFragment newInstance(String param1, String param2) {
		TeamDetailPlayersFragment fragment = new TeamDetailPlayersFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public TeamDetailPlayersFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}

		Activity activity = getActivity();
		if (activity instanceof TeamDetailActivity) {
			Team team = ((TeamDetailActivity) activity).getSelectedTeam();

			ParseQuery<TeamMember> teamMemberQuery = ParseQuery.getQuery(TeamMember.class);
			List<TeamMember> teamMembers = null;
			try {
				teamMembers = teamMemberQuery.whereEqualTo("team", team).find();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (teamMembers != null && teamMembers.size() > 0) {
				ArrayList<Player> players = new ArrayList<Player>();

				for (TeamMember member : teamMembers) {
					players.add(member.getPlayer());
				}

				setListAdapter(createListAdapter());
			}
		}

		setHasOptionsMenu(true);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_team_detail_players, container, false);
	}

	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_team_detail_players, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.team_detail_menu_add_player) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setTitle("Add a New GameTime Player...");
            dialogBuilder.setPositiveButton
                    (
                            "Select Existing Player",
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Activity activity = getActivity();
                                    Intent intent = new Intent(activity, AddExistingPlayerActivity.class);
                                    intent.putExtra("TeamObjectId", ((TeamDetailActivity) activity).getSelectedTeam().getObjectId());
                                    startActivity(intent);
                                }
                            }
                    );
            dialogBuilder.setNegativeButton("Create New Player",
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Activity activity = getActivity();
                            Intent intent = new Intent(activity, AddEditPlayerActivity.class);
                            intent.putExtra("TeamObjectId", ((TeamDetailActivity) activity).getSelectedTeam().getObjectId());
                            startActivity(intent);
                        }
                    });
            dialogBuilder.show();


		}

		return false; // http://developer.android.com/reference/android/app/Fragment.html#onOptionsItemSelected(android.view.MenuItem)
	}

	@Override
	public void onResume() {
		super.onResume();
		setListAdapter(createListAdapter());
	}

	private TeamPlayersParseQueryAdapter createListAdapter() {
		TeamDetailActivity activity = (TeamDetailActivity) getActivity();
		Team team = activity.getSelectedTeam();
		return new TeamPlayersParseQueryAdapter(activity, team);
	}
}
