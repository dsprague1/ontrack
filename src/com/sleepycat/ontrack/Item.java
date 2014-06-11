package com.sleepycat.ontrack;

import java.io.Serializable;

public class Item implements Serializable
{
	private String mTitle;
	private double mLength;
	private String mMagnitude;
	private static int serialVersionUID = 03062014;
	
	public Item(String title, double length, String mag)
	{
		mTitle = title;
		mLength = length;
		mMagnitude = mag;
	}
	
	public String getMagnitude() {
		return mMagnitude;
	}

	public void setMagnitude(String magnitude) {
		mMagnitude = magnitude;
	}

	public String getTitle() 
	{return mTitle;}

	public void setTitle(String title) 
	{mTitle = title;}

	public double getLength() 
	{return mLength;}

	public void setLength(double length) 
	{mLength = length; }
}
