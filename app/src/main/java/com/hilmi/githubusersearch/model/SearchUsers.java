package com.hilmi.githubusersearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchUsers{

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("incomplete_results")
	private boolean incompleteResults;

	@SerializedName("items")
	private ArrayList<SearchUserItem> items;

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setIncompleteResults(boolean incompleteResults){
		this.incompleteResults = incompleteResults;
	}

	public boolean isIncompleteResults(){
		return incompleteResults;
	}

	public void setItems(ArrayList<SearchUserItem> items){
		this.items = items;
	}

	public ArrayList<SearchUserItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"SearchUsers{" + 
			"total_count = '" + totalCount + '\'' + 
			",incomplete_results = '" + incompleteResults + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}