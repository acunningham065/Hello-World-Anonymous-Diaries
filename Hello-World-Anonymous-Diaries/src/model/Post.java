package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post implements Serializable
{
	//Declare instance variables
	private static final long serialVersionUID = 1L;
	private String uniquePostID = "";
	private User author;
	private String title = "";
	private String postContents = "";
	private String language = "";
	private LocalDateTime dateTimePosted = LocalDateTime.now();
	private int happyVotes = 0;
	private int sadVotes = 0;
	private int laughingVotes = 0;
	private int loveVotes = 0;
	private int angryVotes = 0;
    private int totalVotes = 0;
	private ArrayList<String> postTags = new ArrayList<String>();
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	private ArrayList<String> usersRated = new ArrayList<String>();
	
	//-------------------------------- Constructor --------------------------------\\
	
	public Post(User author, String title, String postContents, String language, ArrayList<String> postTags) 
	{
		this.uniquePostID = String.valueOf(System.currentTimeMillis());
		this.author = author;
		this.title = title;
		this.postContents = postContents;
		this.language = language;
		this.postTags = new ArrayList<String>(postTags);
	}
	
	//-------------------------------- Getters/Accessors --------------------------------\\
	
	public String getUniquePostID() {
		return uniquePostID;
	}


	public User getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public String getPostContents() {
		return postContents;
	}
	
	public String getLanguage() {
		return language;
	}

	public LocalDateTime getDateTimePosted() {
		return dateTimePosted;
	}

	public int getHappyVotes() {
		return happyVotes;
	}


	public int getSadVotes() {
		return sadVotes;
	}


	public int getLaughingVotes() {
		return laughingVotes;
	}


	public int getLoveVotes() {
		return loveVotes;
	}


	public int getAngryVotes() {
		return angryVotes;
	}


        public int getTotalVotes() {
		return totalVotes;
	}


	public ArrayList<String> getPostTags() {
		return postTags;
	}


	public ArrayList<Comment> getComments() {
		return comments;
	}
	
	public ArrayList<String> getUsersRated() {
		return usersRated;
	}

	//-------------------------------- Setters/Mutators --------------------------------\\

	public void setAuthor(User author) {
		this.author = author;
	}

	
	public void setTitle(String title) {
		this.title = title;
	}

	
	public void setPostContents(String postContents) {
		this.postContents = postContents;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}

	
	public void setDateTimePosted(LocalDateTime dateTimePosted) {
		this.dateTimePosted = dateTimePosted;
	}

	
	public void setHappyVotes(int happyVotes) {
		this.happyVotes = happyVotes;
	}


	public void setSadVotes(int sadVotes) {
		this.sadVotes = sadVotes;
	}


	public void setLaughingVotes(int laughingVotes) {
		this.laughingVotes = laughingVotes;
	}


	public void setLoveVotes(int loveVotes) {
		this.loveVotes = loveVotes;
	}


	public void setAngryVotes(int angryVotes) {
		this.angryVotes = angryVotes;
	}


	public void setPostTags(ArrayList<String> postTags) {
		this.postTags = new ArrayList<>(postTags);
	}


	public void setComments(ArrayList<Comment> comments) {
		this.comments = new ArrayList<>(comments);
	}
	
	public void setUsersRated(ArrayList<String> usersRated) {
		this.usersRated = new ArrayList<>(usersRated);
	}

	
	//-------------------------------- Other Methods --------------------------------\\
	public String getTagString()
	{
		String tagString = "";
		
		for (String tag : postTags)
		{
			tagString += "#" + tag + " ";
			
		}//End For
		
		return tagString;
		
	}//getTagString	


    public void calculateTotalVotes()
	{
		this.totalVotes = this.happyVotes + this.sadVotes + this.laughingVotes  + this.angryVotes + this.loveVotes;
		
	}//End calculateTotalVotes
	
	
}