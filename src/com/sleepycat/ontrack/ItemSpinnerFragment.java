package com.sleepycat.ontrack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ItemSpinnerFragment extends DialogFragment implements OnItemSelectedListener
{
	private TextView mSmallDuration;
	private Item mCurrentItem;
	private int mBig1, mBig2;
	private String mTitle = "", mDur1 = "", mDur2 = "";
	private ImageView mHourButton, mMinuteButton;
	private boolean mHourIn = true;
	public static final String EXTRA_NEW_ITEM = "com.sleepycat.ontrack.item";
	
	private void sendResult(int resultCode)
	{
		if(getTargetFragment() == null)
			return;
		
		double a = mBig1 + mBig2/100;
		String mag = "";
		if(mHourIn)
		{
			if(mBig1 == 1.0)
				mag = "Hour";
			else
				mag = "Hours";
		}
		else
		{
			if(mBig1 == 1.0)
				mag = "Minute";
			else
				mag = "Minutes";
		}
		
		mCurrentItem = new Item(mTitle, a, mag);
		
		Intent i = new Intent();
		i.putExtra(EXTRA_NEW_ITEM, mCurrentItem);
		getTargetFragment().onActivityResult(-1, resultCode, i);
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
	{
		if(parent.getId() == R.id.bigspinner1)
			mBig1 = Integer.parseInt(parent.getSelectedItem().toString());
		if(parent.getId() == R.id.bigspinner2)
			mBig2 = Integer.parseInt(parent.getSelectedItem().toString());
	}
	
	public void onNothingSelected(AdapterView<?> parent)
	{}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_item, null);
		ArrayAdapter<CharSequence> Realnumbers = ArrayAdapter.createFromResource(getActivity(), R.array.numbers_array, R.layout.spinner_row);
		ArrayAdapter<CharSequence> Fracnumbers = ArrayAdapter.createFromResource(getActivity(), R.array.decimal_array, R.layout.spinner_row);
		mHourButton = (ImageView)v.findViewById(R.id.hoursButton);
		mHourButton.setBackgroundResource(R.drawable.hoursbuttonin);
		mMinuteButton = (ImageView)v.findViewById(R.id.minutesButton);
		
		Spinner bigOne = (Spinner)v.findViewById(R.id.bigspinner1);
        bigOne.setAdapter(Realnumbers);
        bigOne.setOnItemSelectedListener(this);
		
		Spinner bigTwo = (Spinner)v.findViewById(R.id.bigspinner2);
        bigTwo.setAdapter(Fracnumbers);
        bigTwo.setOnItemSelectedListener(this);
		
		EditText titleText = (EditText)v.findViewById(R.id.itemTitle);
		titleText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence c, int start, int before, int count) 
			{mTitle = c.toString();}
			public void beforeTextChanged(CharSequence c, int start, int count, int after) 
			{}
			public void afterTextChanged(Editable c) 
			{}
		});
		
	    //set the ontouch listener
	    mHourButton.setOnTouchListener(new OnTouchListener() 
	    {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) 
	        {
	            switch (event.getAction()) 
	            {
	                case MotionEvent.ACTION_DOWN: 
	                {
	                    ImageView view = (ImageView) v;
	                	if(!mHourIn)
	                	{
	                		mHourButton.setImageResource(R.drawable.hoursbuttonin);
	                		mMinuteButton.setImageResource(R.drawable.minutesbuttonout);
	                	}
	                	mHourIn =! mHourIn;
	                	view.invalidate();
	                	break;
	                }
	                case MotionEvent.ACTION_UP:
	                case MotionEvent.ACTION_CANCEL: 
	                {break;}
	            }
	            return false;
	        }
	    });
	    
	    //set the ontouch listener
	    mMinuteButton.setOnTouchListener(new OnTouchListener() 
	    {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) 
	        {
	            switch (event.getAction()) 
	            {
	                case MotionEvent.ACTION_DOWN: 
	                {
	                	ImageView view = (ImageView) v;
	                	if(mHourIn)
	                	{
	                		mMinuteButton.setImageResource(R.drawable.minutesbuttonin);
	                		mHourButton.setImageResource(R.drawable.hoursbuttonout);
	                	}
	                	mHourIn =! mHourIn;
	                	view.invalidate();
	                	break;
	                }
	                case MotionEvent.ACTION_UP:
	                case MotionEvent.ACTION_CANCEL: 
	                {break;}
	            }
	            return false;
	        }
	    });
		
		return new AlertDialog.Builder(getActivity())
		.setView(v)
		.setTitle("New Item")
		.setPositiveButton(android.R.string.ok, 
				new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						if(mTitle == "")
						{
							Toast.makeText(getActivity(), "Please enter a valid title!", Toast.LENGTH_SHORT).show();
							return;
						}
						else if(mBig1 == 0 && mBig2 == 0)
						{
							Toast.makeText(getActivity(), "Please enter a valid duration!", Toast.LENGTH_SHORT).show();
							return;
						}
						else
							sendResult(0);
					}
				})
		.setNegativeButton(android.R.string.cancel, null)
		.create();
	}
}
