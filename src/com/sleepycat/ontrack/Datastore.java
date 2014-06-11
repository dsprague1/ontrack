package com.sleepycat.ontrack;

import java.util.ArrayList;

import android.content.Context;

public class Datastore 
{
	private ArrayList<Track> mStoredTracks;
	
	private static Datastore sStoredData;
	private Context mContext;
	
	private Datastore(Context appContext)
	{
		mContext = appContext;
		mStoredTracks = new ArrayList<Track>();
	}
	
	public static Datastore get(Context c)
	{
		if(sStoredData == null)
			sStoredData = new Datastore(c);
		return sStoredData;
	}

	public ArrayList<Track> getStoredTracks() 
	{return mStoredTracks;}

	public void setStoredTracks(ArrayList<Track> storedTracks) 
	{mStoredTracks = storedTracks;}
}
