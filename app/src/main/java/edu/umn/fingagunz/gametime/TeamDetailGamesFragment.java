package edu.umn.fingagunz.gametime;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import edu.umn.fingagunz.gametime.domain.Game;
import edu.umn.fingagunz.gametime.domain.Team;
import edu.umn.fingagunz.gametime.parse.queryadapter.TeamGamesParseQueryAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamDetailGamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamDetailGamesFragment extends ListFragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

    String currentUserId = "";

	public TeamDetailGamesFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment TeamDetailGamesFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static TeamDetailGamesFragment newInstance(String param1, String param2) {
		TeamDetailGamesFragment fragment = new TeamDetailGamesFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}

		setListAdapter(createListAdapter());

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_team_detail_games, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
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
		inflater.inflate(R.menu.menu_team_detail_games, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.team_detail_menu_add_game) {

            /*

            TODO

            // leaving this here in case it breaks something....
            //  if not, this can be deleted...

			Activity activity = getActivity();
			Intent intent = new Intent(activity, AddEditGameActivity.class);
			intent.putExtra("TeamObjectId", ((TeamDetailActivity) activity).getSelectedTeam().getObjectId());
			startActivity(intent);
            */

            Activity activity = getActivity();
            Intent intent = new Intent(activity, GameDetailActivity.class);
            intent.putExtra("gameId", ((TeamDetailActivity) activity).getSelectedTeam().getObjectId());
            intent.putExtra("playerId", currentUserId);
            startActivity(intent);

		}

		return false; // http://developer.android.com/reference/android/app/Fragment.html#onOptionsItemSelected(android.view.MenuItem)
	}

	@Override
	public void onResume() {
		super.onResume();
		setListAdapter(createListAdapter());
	}

	private TeamGamesParseQueryAdapter createListAdapter() {
		TeamDetailActivity activity = (TeamDetailActivity) getActivity();
		Team team = activity.getSelectedTeam();
		return new TeamGamesParseQueryAdapter(activity, team);
	}

}
