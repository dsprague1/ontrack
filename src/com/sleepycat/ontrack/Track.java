package com.sleepycat.ontrack;

import java.io.Serializable;
import java.util.ArrayList;

public class Track implements Serializable
{
	private String mTitle;
	private double mLength;
	private ArrayList<Item> mAllItems;
	private String magnitude;
	private static int serialVersionUID = 12062014;
	
	public Track(String title, double length, ArrayList<Item> items, String mag)
	{
		mTitle = title;
		mLength = length;
		mAllItems = items;
		magnitude = mag;
	}

	public String getMagnitude() {
		return magnitude;
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
