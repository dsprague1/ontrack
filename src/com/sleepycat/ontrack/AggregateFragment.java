package com.sleepycat.ontrack;

import java.util.ArrayList;
import java.util.UUID;

import com.sleepycat.ontrack.NewTrackFragment.OnDataPass;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AggregateFragment extends ListFragment 
{
	public static String EXTRA_TRACK = "com.sleepycat.ontrack.trackarray";
	private ArrayList<Track> mAllTracks;
	
	//data passing
	public interface OnDataPass 
	{public void onDataPass(int position);}
		
	OnDataPass dataPasser;
		
	@Override
	public void onAttach(Activity a) 
	{
		super.onAttach(a);
		dataPasser = (OnDataPass)a;
	}
	
	public void passData(int position) 
	{dataPasser.onDataPass(position);}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mAllTracks = (ArrayList<Track>)getArguments().getSerializable(EXTRA_TRACK);
		TrackAdapter adapter = new TrackAdapter(mAllTracks);
		setListAdapter(adapter);
	}
	
	/*public static AggregateFragment newInstance()
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TRACK, 0);
		
		AggregateFragment fragment = new AggregateFragment();
		fragment.setArguments(args);
		
		return fragment;
	}*/
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{passData(position);}
	
	private class TrackAdapter extends ArrayAdapter<Track>
	{
		public TrackAdapter(ArrayList<Track> tracks)
		{super(getActivity(), 0, tracks);}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if(convertView == null)
			{convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_aggregate, null);}
			
			Track t = mAllTracks.get(position);
			TextView titleView = (TextView)convertView.findViewById(R.id.title_label);
			titleView.setText(t.getTitle());
			TextView itemsView = (TextView)convertView.findViewById(R.id.items_label);
			itemsView.setText(Integer.toString(t.getAllItems().size()) + " items");
			TextView lengthView = (TextView)convertView.findViewById(R.id.length_label);
			lengthView.setText(Double.toString(t.getLength()) + " " + t.getMagnitude());
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
