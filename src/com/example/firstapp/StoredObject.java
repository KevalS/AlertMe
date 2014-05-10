package com.example.firstapp;

import java.io.Serializable;

import android.os.Parcelable;

public class StoredObject implements Serializable{
	private String email;
	private String pswd;
	private String name;
	private boolean chkbox1 = false;
	private boolean chkbox2= false;
	private boolean chkbox3= false;
	private boolean chkbox4= false;
	double plotDealLat;
	double plotDealLong;
	public void SetEmail(String email)
	{
		this.email = email;
	}
	public void SetPswd(String pswd)
	{
		this.pswd = pswd;
	}
	public void SetName(String name)
	{
		this.name = name;
	}
	public void SetChkbox1(boolean ischeck)
	{
		this.chkbox1 = ischeck;
	}
	public void SetChkbox2(boolean ischeck)
	{
		this.chkbox2 = ischeck;
	}
	public void SetChkbox3(boolean ischeck)
	{
		this.chkbox3 = ischeck;
	}
	public void SetChkbox4(boolean ischeck)
	{
		this.chkbox4 = ischeck;
	}
	public void SetPlotDealLat(double lat)
	{
		this.plotDealLat = lat;
	}
	public void SetPlotDealLong(double longitude)
	{
		this.plotDealLong = longitude;
	}
	public String getEmail()
	{
		return email;
	}
	public String getPswd()
	{
		return pswd;
	}
	public String getName()
	{
		return name;
	}
	public boolean getChkbox1()
	{
		return chkbox1;
	}
	public boolean getChkbox2()
	{
		return chkbox2;
	}
	public boolean getChkbox3()
	{
		return chkbox3;
	}
	public boolean getChkbox4()
	{
		return chkbox4;
	}
	public double getDealLat()
	{
		return plotDealLat;
	}
	public double getDealLong()
	{
		return plotDealLong;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
