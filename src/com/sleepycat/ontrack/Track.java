package com.sleepycat.ontrack;

import java.util.ArrayList;

public class Track 
{
	private String mTitle;
	private double mLength;
	private ArrayList<Item> mAllItems;
	
	public Track(String title, double length, ArrayList<Item> items)
	{
		mTitle = title;
		mLength = length;
		mAllItems = items;
	}

	public ArrayList<Item> getAllItems() {
		return mAllItems;
	}

	public void setAllItems(ArrayList<Item> allItems) {
		mAllItems = allItems;
	}

	public String getTitle() 
	{return mTitle;}

	public double getLength() {
		return mLength;
	}

}
