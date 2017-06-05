package model;

public class Search extends SmartSerializable  // YIXAUN HE 40191202
{
	private static final long serialVersionUID = 1L;
	private String searchPersonHistory = "";
	private String searchDiaryHistory = "";


public Search(String searchPersonHistory, String searchDiaryHistory)
{
	this.searchPersonHistory = searchPersonHistory;
	this.searchDiaryHistory = searchDiaryHistory;
	
}
	public String getSearchDiaryHistory()
	{
		return searchDiaryHistory;
	}
	public String getSearchPersonHistory()
	{
		return searchPersonHistory;
	}
	public void setSearchPersonHistory(String searchPersonHistory)
	{
		this.searchPersonHistory = searchPersonHistory;
	}
	public void setSearchDiaryHistory(String searchDiaryHistory)
	{
		this.searchDiaryHistory = searchDiaryHistory;
	}
}
