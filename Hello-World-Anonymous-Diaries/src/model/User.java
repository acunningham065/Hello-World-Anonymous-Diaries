package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Aaron - 40176468
 *
 */
public class User implements Serializable
{
	//Declare instance variables
	private static final long serialVersionUID = 1L;
	private String uniqueID = "";
	private String userName = "";
	private String password = "";
	private String gender = "";
	private String emailAddress = "";
	private LocalDate dateOfBirth;
	private String educationLevel = "";
	private String countryOfResidence = "";
	private String profilePictureAddress = "default-avatar.png";
	
	//-------------------------------- Constructor(s) --------------------------------\\
	
	/**
	 * @param uniqueID
	 * @param userName
	 * @param password
	 * @param gender
	 * @param emailAddress
	 * @param dateOfBirth
	 * @param educationLevel
	 * @param countryOfResidence
	 */
	public User(String userName, String password, String gender, String emailAddress, LocalDate dateOfBirth, String educationLevel, String countryOfResidence, String profilePictureAddress)
	{
		this.uniqueID = String.valueOf(System.currentTimeMillis());
		this.userName = userName;
		this.password = password;
		this.gender = gender;
		this.emailAddress = emailAddress;
		this.dateOfBirth = dateOfBirth;
		this.educationLevel = educationLevel;
		this.countryOfResidence = countryOfResidence;
		this.profilePictureAddress = profilePictureAddress;
		
	}//End Constructor

	//-------------------------------- Getters/Accessors --------------------------------\\
	
	/**
	 * @return the uniqueID
	 */
	public String getUniqueID()
	{
		return uniqueID;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() 
	{
		return password;
	}

	/**
	 * @return the gender
	 */
	public String getGender()
	{
		return gender;
	}
	
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return emailAddress;
	}

	/**
	 * @return the dateOfBirth
	 */
	public LocalDate getDateOfBirth()
	{
		return dateOfBirth;
	}
	
	/**
	 * @return the educationLevel
	 */
	public String getEducationLevel()
	{
		return educationLevel;
	}

	/**
	 * @return the countryOfResidence
	 */
	public String getCountryOfResidence()
	{
		return countryOfResidence;
	}
	
	/**
	 * @return the profilePictureAddress
	 */
	public String getProfilePictureAddress() {
		return profilePictureAddress;
	}

	//-------------------------------- Setters/Mutators --------------------------------\\
	
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(LocalDate dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}
	
	/**
	 * @param educationLevel the educationLevel to set
	 */
	public void setEducationLevel(String educationLevel)
	{
		this.educationLevel = educationLevel;
	}

	/**
	 * @param countryOfResidence the countryOfResidence to set
	 */
	public void setCountryOfResidence(String countryOfResidence)
	{
		this.countryOfResidence = countryOfResidence;
	}

	/**
	 * @param profilePictureAddress the profilePictureAddress to set
	 */
	public void setProfilePictureAddress(String profilePictureAddress) 
	{
		this.profilePictureAddress = profilePictureAddress;
	}
	
}//End User