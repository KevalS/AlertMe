package com.example.firstapp;

public class Item {
	private String title;
    private String description;
    private String deals;
    private int ID;
 
    public Item(String title, String description,String deal, int id) {
        super();
        this.title = title;
        this.description = description;
        deals = deal;
        ID = id;
    }
    String getTitle()
    {
    	return title;
    }
    String getDescription()
    {
    	return description;
    }
    String getDeals()
    {
    	return deals;
    }
    int getID()
    {
    	return ID;
    }
    // getters and setters...   
}