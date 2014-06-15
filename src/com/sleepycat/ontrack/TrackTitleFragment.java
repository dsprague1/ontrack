package com.sleepycat.ontrack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class TrackTitleFragment extends DialogFragment 
{
	private String mTitle = "";
	public static final String EXTRA_TRACK_TITLE = "com.sleepycat.ontrack.tracktitle";
	
	private void sendResult(int resultCode)
	{
		if(getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_TRACK_TITLE, mTitle);
		getTargetFragment().onActivityResult(-1, resultCode, i);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tracktitle, null);
		
		EditText titleText = (EditText)v.findViewById(R.id.tracknamefield);
		titleText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence c, int start, int before, int count) 
			{mTitle = c.toString();}
			public void beforeTextChanged(CharSequence c, int start, int count, int after) 
			{}
			public void afterTextChanged(Editable c) 
			{}
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
						else
							sendResult(1);
					}
				})
		.setNegativeButton(android.R.string.cancel, null)
		.create();
	}
}
