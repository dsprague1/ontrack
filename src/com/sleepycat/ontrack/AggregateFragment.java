package com.sleepycat.ontrack;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AggregateFragment extends ListFragment 
{
	public static String EXTRA_TRACK = "com.sleepycat.ontrack.trackarray";
	private ArrayList<Track> mAllTracks;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mAllTracks = new ArrayList<Track>();
		TrackAdapter adapter = new TrackAdapter(mAllTracks);
		setListAdapter(adapter);
	}
	
	public static AggregateFragment newInstance()
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TRACK, 0);
		
		AggregateFragment fragment = new AggregateFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	//TODO:load TrackView fragment
	{}
	
	private class TrackAdapter extends ArrayAdapter<Track>
	{
		public TrackAdapter(ArrayList<Track> tracks)
		{super(getActivity(), 0, tracks);}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if(convertView == null)
			{convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_aggregate, null);}
			
			return convertView;
		}
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser)
		{
			
		}
	}
}
