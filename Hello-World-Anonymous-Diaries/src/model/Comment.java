package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author 40147716
 *
 */
public class Comment implements Serializable
{
	private static final long serialVersionUID = 1L;
    private String commentID;
	private User author;
	private String text;
	private int upvote;
	private int downvote;
	private ArrayList<String> userUpVoted = new ArrayList<String>();
	private ArrayList<String> userDownVoted = new ArrayList<String>();

	//Constructors
	public Comment(User author, String text)
	{
		this.commentID = String.valueOf(System.currentTimeMillis());
		this.author = author;
		this.text = text;
		this.upvote = 0;
		this.downvote = 0;
		
	}
	
	//Getters and Setters

	public String getCommentID() {
		return commentID;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getUpvote() {
		return upvote;
	}

	public void setUpvote(int upvote) {
		this.upvote = upvote;
	}

	public int getDownvote() {
		return downvote;
	}

	public void setDownvote(int downvote) {
		this.downvote = downvote;
	}
	
	public ArrayList<String> getUserUpVoted() {
		return userUpVoted;
	}

	public void setUserUpVoted(ArrayList<String> userUpVoted) {
		this.userUpVoted = userUpVoted;
	}
	
	public ArrayList<String> getUserDownVoted() {
		return userDownVoted;
	}

	public void setUserDownVoted(ArrayList<String> userDownVoted) {
		this.userDownVoted = userDownVoted;
	}


}