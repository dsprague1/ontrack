package com.sleepycat.ontrack;

import net.simonvt.numberpicker.NumberPicker;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemPickerFragment extends DialogFragment 
{
	
	private TextView mSmallDuration;
	private Item mCurrentItem;
	private int mBig1 = 0, mBig2 = 0;
	private String mTitle = "";
	private boolean mHourIn = true;
	private ImageView mHourButton, mMinuteButton;
	private String Rnum[]; //= getResources().getStringArray(R.array.numbers_array);
	private String Dnum[]; //= getResources().getStringArray(R.array.decimal_array);
	public static final String EXTRA_NEW_ITEM = "com.sleepycat.ontrack.item";
	
	private void adjustSmallDuration(int dur)
	{
		if(dur == 0)
			mSmallDuration.setText("Minute(s)");
		else
			mSmallDuration.setText("Second(s)");
	}
	
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
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_item, null);
		Rnum = getResources().getStringArray(R.array.numbers_array);
		Dnum = getResources().getStringArray(R.array.decimal_array);
		mHourButton = (ImageView)v.findViewById(R.id.hoursButton);
		mMinuteButton = (ImageView)v.findViewById(R.id.minutesButton);
		
		NumberPicker bigPickerOne  = (NumberPicker)v.findViewById(R.id.bigpicker1);
		bigPickerOne.setMaxValue(16);
		bigPickerOne.setMinValue(0);
		bigPickerOne.setDisplayedValues(Rnum);
		bigPickerOne.setWrapSelectorWheel(true);
        bigPickerOne.setOnValueChangedListener( new NumberPicker.
                OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) 
                {mBig1 = Integer.parseInt(Rnum[newVal]);}
            });
        
		NumberPicker bigPickerTwo = (NumberPicker)v.findViewById(R.id.bigpicker2);
		bigPickerTwo.setMaxValue(3);
		bigPickerTwo.setMinValue(0);
		bigPickerTwo.setDisplayedValues(Dnum);
		bigPickerTwo.setWrapSelectorWheel(true);
        bigPickerTwo.setOnValueChangedListener( new NumberPicker.
                OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) 
                {mBig2 = Integer.parseInt(Dnum[newVal]);}
            });
		
		EditText titleText = (EditText)v.findViewById(R.id.itemTitle);
		titleText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence c, int start, int before, int count) 
			{mTitle = c.toString();}
			public void beforeTextChanged(CharSequence c, int start, int count, int after) 
			{}
			public void afterTextChanged(Editable c) 
			{}
		});
		
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
							sendResult(0);}
				})
		.setNegativeButton(android.R.string.cancel, null)
		.create();
	}
}
