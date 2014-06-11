package com.sleepycat.ontrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SplashFragment extends Fragment 
{
	private static int SPLASH_TIME_OUT = 1000;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{super.onCreate(savedInstanceState);}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_splash, parent, false);
		
		Button mMove = (Button)v.findViewById(R.id.temp);
		mMove.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(getActivity(), MainActivity.class);
				startActivity(i);
			}
		});
		
		return v;
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
	}
}
