package com.sleepycat.ontrack;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewTrackFragment extends Fragment
{
	public static final int REQUEST_ITEM = 0;
	public static final int REQUEST_TITLE = 1;
	private static ArrayList<Item> mAllItems;
	private SwipeListView mTheListView;
	private ItemAdapter mAdapter;
	private String mTrackTitle;
	private boolean isHour = false;
	
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
	//data passing
	public interface OnDataPass 
	{public void onDataPass(Track result);}
		
	OnDataPass dataPasser;
		
	@Override
	public void onAttach(Activity a) 
	{
		super.onAttach(a);
		dataPasser = (OnDataPass)a;
	}
		
	private double convertTime(double l)
	{return l/60;}
	
	public void passData(Track result) 
	{dataPasser.onDataPass(result);}
		
	private void makeTrack()
	{
		double totalLength = 0;
		String mag = "Minutes";
		if(mAllItems.size() == 1)
			totalLength = mAllItems.get(0).getLength();
		else
			for(int i = 0; i < mAllItems.size()-1; i++)
			{totalLength+=mAllItems.get(i).getLength();}
		
		if(!isHour && totalLength > 60)
			totalLength = convertTime(totalLength);
		
		if(!isHour && totalLength == 1.0)
			mag = "Minute";
		else if(isHour && totalLength == 1.0)
			mag = "Hour";
		else if(isHour && totalLength != 1.0)
			mag = "Hours";
		
		Track result = new Track(mTrackTitle, totalLength,mAllItems, mag);
		passData(result);
	}
	
	private void checkButton()
	{
		if(mAllItems.size() == 0)
		{
			Toast.makeText(getActivity(), "Please add at least one item to your track.", Toast.LENGTH_LONG).show();
			return;
		}
		//call dialog to set title for track before going back to feed view
    	FragmentManager fm = getActivity().getSupportFragmentManager();
    	TrackTitleFragment dialog = new TrackTitleFragment();
    	dialog.setTargetFragment(NewTrackFragment.this, REQUEST_TITLE);
    	dialog.show(fm, "Track title");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
	    inflater.inflate(R.menu.newtrackmenu, menu);
	    super.onCreateOptionsMenu(menu,inflater);
	}
	
    @Override
    public void onPrepareOptionsMenu(Menu menu) 
    {
        menu.findItem(R.id.action_check).setVisible(true);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(mAllItems != null)
			checkButton();
		return super.onOptionsItemSelected(item);
	}
	
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode != Activity.RESULT_OK)
			return;
		if(resultCode == REQUEST_ITEM)
		{
			Item item = (Item)data
				.getSerializableExtra(ItemSpinnerFragment.EXTRA_NEW_ITEM);
			
			if(item.getMagnitude() == "Hour" || item.getMagnitude() == "Hours")
				isHour = true;
			
			mAllItems.add(item);
			
			ItemAdapter iA = (ItemAdapter)mTheListView.getAdapter();
			iA.notifyDataSetChanged();
		}
		if(resultCode == REQUEST_TITLE)
		{
			String s = (String)data.getSerializableExtra(TrackTitleFragment.EXTRA_TRACK_TITLE);
			mTrackTitle = s;
			makeTrack();
		}
	}
	
	private class ItemAdapter extends ArrayAdapter<Item>
	{
		public ItemAdapter(ArrayList<Item> items)
		{
			super(getActivity(), 0, items);
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{// If we weren't given a view, inflate one
			if (convertView == null) 
			{
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.fragment_item, null);
			}
		
			Item i = getItem(position);
			TextView titleView = (TextView)convertView.findViewById(R.id.titleField); 
			titleView.setText(i.getTitle());
			TextView timeView = (TextView)convertView.findViewById(R.id.timerField);
			timeView.setText("Duration: " + Double.toString(i.getLength()) + " " + i.getMagnitude());
			convertView.setTag(position);
			/*RadioButton mediaButton = (RadioButton)convertView.findViewById(R.id.recordButton);
			mediaButton.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{Toast.makeText(getActivity(), "Media recording has not been implemented yet.", Toast.LENGTH_SHORT).show();}
			});*/
			
			return convertView;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_newtrack, parent, false);
		mAllItems = new ArrayList<Item>();
	     //get the image view
	    ImageView plus = (ImageView)v.findViewById(R.id.plus_button);
	    mTheListView = (SwipeListView)v.findViewById(R.id.swipe_listview);
	    mTheListView.setChoiceMode(mTheListView.CHOICE_MODE_SINGLE);
	    //set the ontouch listener
	    plus.setOnTouchListener(new OnTouchListener() 
	    {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) 
	        {
	            switch (event.getAction()) 
	            {
	                case MotionEvent.ACTION_DOWN: 
	                {
	                    ImageView view = (ImageView) v;
	                    //overlay is black with transparency of 0x77 (119)
	                    view.getDrawable().setColorFilter(0x77444444,PorterDuff.Mode.SRC_ATOP);
	                    view.invalidate();
	                    break;
	                }
	                case MotionEvent.ACTION_UP:
	                case MotionEvent.ACTION_CANCEL: 
	                {
	                    ImageView view = (ImageView) v;
	                    //clear the overlay
	                    view.getDrawable().clearColorFilter();
	                    view.invalidate();
	                    
	                    //call dialog to make new item
	                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	                    {
	                    	FragmentManager fm = getActivity().getSupportFragmentManager();
	                    	ItemPickerFragment dialog = new ItemPickerFragment();
	                    	dialog.setTargetFragment(NewTrackFragment.this, REQUEST_ITEM);
	                    	dialog.show(fm, "New item"); 
	                    }
	                    else
	                    {
	                    	FragmentManager fm = getActivity().getSupportFragmentManager();
	                    	ItemSpinnerFragment dialog = new ItemSpinnerFragment();
	                    	dialog.setTargetFragment(NewTrackFragment.this, REQUEST_ITEM);
	                    	dialog.show(fm, "New item");
	                    }
	                    break;
	                }
	            }
	            return true;
	        }
	    });
		
	    mAdapter = new ItemAdapter(mAllItems);
	    mTheListView.setAdapter(mAdapter);
        mTheListView.setSwipeListViewListener(new BaseSwipeListViewListener() 
        {
            @Override
            public void onOpened(int position, boolean toRight) 
            {}

            @Override
            public void onClosed(int position, boolean fromRight) 
            {}

            @Override
            public void onListChanged() 
            {}

            @Override
            public void onMove(int position, float x) 
            {}

            @Override
            public void onStartOpen(int position, int action, boolean right) 
            {Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));}

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) {
                    mAllItems.remove(position);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        
        
		return v;
	}
	
}
