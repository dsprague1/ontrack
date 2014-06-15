package com.sleepycat.ontrack;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CurrTrackFragment extends ListFragment 
{
	private Track mCurrTrack;
	public static final String EXTRA_CURRENT_TRACK = "com.sleepycat.ontrack.currenttrack";
	private long startTime = 0L;
	private Handler customHandler = new Handler();
	
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private int mCurrTaskTime_Seconds;
    private int mCurrTaskTime_Minutes;
    private int mCurrTaskTime_Hours;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mCurrTrack = (Track)getArguments().getSerializable(EXTRA_CURRENT_TRACK);
		allItemsAdapter adapter = new allItemsAdapter(mCurrTrack.getAllItems());
		setListAdapter(adapter);
	}
	
	private void startItem(Item i)
	{
		i.getLength();
		if(i.getMagnitude() == "Hour" || i.getMagnitude() == "Hours")
		{	
			mCurrTaskTime_Hours = (int)i.getLength();
			mCurrTaskTime_Minutes = (int)((i.getLength()/mCurrTaskTime_Hours)*100);
		}
		else
		{
			mCurrTaskTime_Minutes = (int)i.getLength();
			mCurrTaskTime_Seconds = (int)((i.getLength()/mCurrTaskTime_Hours)*100);
		}
		
		startTime = System.currentTimeMillis();
		customHandler.postDelayed(updateTimerThread, 0);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		allItemsAdapter a = (allItemsAdapter)getListAdapter();
		a.setSelectedPosition(position);
		startItem(mCurrTrack.getAllItems().get(position));
	}
	
	private class allItemsAdapter extends ArrayAdapter<Item>
	{
		
		private int selectedPos = -1; //no position selected by default
		
		public void setSelectedPosition(int pos)
		{
			selectedPos = pos;
			// inform the view of this change
			notifyDataSetChanged();
		}
		
		public int getSelectedPosition()
		{return selectedPos;}
		
		public allItemsAdapter(ArrayList<Item> items)
		{super(getActivity(), 0, items);}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if(convertView == null)
			{convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_currenttrack, null);}
			
			Item i = mCurrTrack.getAllItems().get(position);
			TextView title = (TextView)convertView.findViewById(R.id.cTrack_itemtitle);
			title.setText(i.getTitle());
			TextView duration = (TextView)convertView.findViewById(R.id.cTrack_itemlength);
			duration.setText(i.getLength() + " " + i.getMagnitude());
			LinearLayout curr = (LinearLayout)convertView.findViewById(R.id.label);
			
	        // change the row color based on selected state
	        if(selectedPos == position)
	        	{curr.setBackgroundColor(Color.CYAN);}
	        else
	        	{curr.setBackgroundColor(Color.WHITE);}
			
			return convertView;
		}
	}
	
    private Runnable updateTimerThread = new Runnable() 
    {
        public void run() 
        {
            timeInMilliseconds = System.currentTimeMillis() - startTime;
 
            updatedTime = timeSwapBuff + timeInMilliseconds;
            
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hours = mins/60;
            
            if(hours == mCurrTaskTime_Hours && mins == mCurrTaskTime_Minutes)
            	Toast.makeText(getActivity(), "Time for the next item", Toast.LENGTH_SHORT).show();
            else if(mins == mCurrTaskTime_Minutes && secs == mCurrTaskTime_Seconds)
            	Toast.makeText(getActivity(), "Time for the next item", Toast.LENGTH_SHORT).show();
  
            customHandler.postDelayed(this, 0);
        }
    };

}
