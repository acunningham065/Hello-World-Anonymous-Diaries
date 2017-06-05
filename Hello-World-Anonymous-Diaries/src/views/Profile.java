package views;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.h2.mvstore.MVMap;

import model.Post;
import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class Profile extends DynamicWebPage
{

	public Profile(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}

	public boolean process(WebRequest toProcess)
	{		
		MVMap<String, User> usersStored = db.s.openMap("User");		
		ArrayList<Post> userPosts = new ArrayList<Post>();
		
		MVMap<String, Post> postsStored = db.s.openMap("Post");
		List<String> postKeys = postsStored.keyList();
		
		String userUniqueID = toProcess.cookies.get("userID"); 
		String password = toProcess.cookies.get("password");
		

		if(toProcess.path.equalsIgnoreCase("Profile.html"))
		{
			
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<!DOCTYPE html>\n";
			stringToSendToWebBrowser += "<html>  \n";
			stringToSendToWebBrowser += "  <head>\n";
			stringToSendToWebBrowser += "  <base href=\"//localhost:8080/\"/>";
			stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
			stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
			stringToSendToWebBrowser += "    <title>Profile</title>\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
			stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "    <link href=\"profile.css\" rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "  </head>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <body>\n";
			stringToSendToWebBrowser += Index.headerAndNavBarGeneration(userUniqueID, password, usersStored);
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";			
			stringToSendToWebBrowser += "          <div class=\"col-md-4\">\n";
			stringToSendToWebBrowser += "            <h1>This page is only accesible via typing in the URL. If you are looking for a specific post try clicking on the title</h1>\n";
			stringToSendToWebBrowser += "            <a href = \"Index.html\"><h2>Click here to return to the Home Page</h2>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <hr>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";

			//FOOTER OF THE WEBSITE
			stringToSendToWebBrowser += Index.footerGeneration();
			stringToSendToWebBrowser += "  </body>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</html>\n";
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;
		}
		else if(toProcess.path.startsWith("Profile.html/") && Pattern.matches("\\d{13,19}", toProcess.path.substring(13)))
		{			
			String userProfileID = toProcess.path.substring(13);
			User userProfile = usersStored.get(userProfileID);	

			if (userProfile != null)
			{
				for (String key : postKeys) 
				{
					Post post = postsStored.get(key);

					if (post.getAuthor() != null)
					{
						if (post.getAuthor().getUniqueID().equals(userProfile.getUniqueID())) 
						{
							userPosts.add(post);
						}//End If	
					}//End If
					
					

				}//End For

				String stringToSendToWebBrowser = "";
				stringToSendToWebBrowser += "<!DOCTYPE html>\n";
				stringToSendToWebBrowser += "<html>  \n";
				stringToSendToWebBrowser += "  <base href=\"//localhost:8080/\"/>";
				stringToSendToWebBrowser += "  <head>\n";
				stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
				stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
				stringToSendToWebBrowser += "    <title>" + userProfile.getUserName() + "</title>\n";
				stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
				stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
				stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
				stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
				stringToSendToWebBrowser += "    <link href=\"profile.css\" rel=\"stylesheet\" type=\"text/css\">\n";
				stringToSendToWebBrowser += "  </head>\n";
				stringToSendToWebBrowser += "  \n";
				stringToSendToWebBrowser += "  <body>\n";
				stringToSendToWebBrowser += Index.headerAndNavBarGeneration(userUniqueID, password, usersStored);
				stringToSendToWebBrowser += "    <div class=\"section\">\n";
				stringToSendToWebBrowser += "      <div class=\"container\">\n";
				stringToSendToWebBrowser += "        <div class=\"row\">\n";
				stringToSendToWebBrowser += "          <div class=\"col-md-4\">\n";
				stringToSendToWebBrowser += "            <img src=\""+ userProfile.getProfilePictureAddress() + "\" class=\"img-responsive\">\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "          <div class=\"col-md-4\">\n";
				stringToSendToWebBrowser += "            <h1>" + userProfile.getUserName() + "</h1>\n";
				stringToSendToWebBrowser += "            <h4>Date of Birth: " + userProfile.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "</h4>\n";
				stringToSendToWebBrowser += "            <h4>Gender: " + userProfile.getGender() + "</h4>\n";
				stringToSendToWebBrowser += "            <h4>Education Level: " + userProfile.getEducationLevel() + "</h4>\n";
				stringToSendToWebBrowser += "            <h4>Current Place Of Residence: " + userProfile.getCountryOfResidence() + "</h4>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";
				stringToSendToWebBrowser += "    <div class=\"section\">\n";
				stringToSendToWebBrowser += "      <div class=\"container\">\n";
				stringToSendToWebBrowser += "        <div class=\"row\">\n";
				stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
				stringToSendToWebBrowser += "            <hr>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";
				stringToSendToWebBrowser += "    <div class=\"section\">\n";
				stringToSendToWebBrowser += "      <div class=\"container\">\n";
				stringToSendToWebBrowser += "        <div class=\"row\">\n";
				stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
				stringToSendToWebBrowser += "            <h1>Posts:\n";
				stringToSendToWebBrowser += "              <br>\n";
				stringToSendToWebBrowser += "            </h1>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";

				//null indicates that the user hasn't logged in
				if(userUniqueID != null)
				{
					//Get the user from the database
					User user = usersStored.get(userUniqueID);

					//If the user is still null the user doesn't exist OR the password stored in the cookies doesn't match the one on the database
					if((user == null)||(!user.getPassword().contentEquals(password)))
					{
						stringToSendToWebBrowser = "";
						stringToSendToWebBrowser += "<!DOCTYPE html>\n";
						stringToSendToWebBrowser += "<html>\n";
						stringToSendToWebBrowser += "  <head>\n";
						stringToSendToWebBrowser += "  <base href=\"//localhost:8080/\"/>";
						stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
						stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
						stringToSendToWebBrowser += "    <title>Session Error</title>\n";
						stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
						stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
						stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
						stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
						stringToSendToWebBrowser += "    <link href=\"Profile.css\" rel=\"stylesheet\" type=\"text/css\">\n";				
						stringToSendToWebBrowser += "  </head>\n";
						stringToSendToWebBrowser += "  <script>\n";	
						stringToSendToWebBrowser += "      function clearLoginCookie()\n";
						stringToSendToWebBrowser += "      {\n";
						stringToSendToWebBrowser += "          document.cookie='userID=;expires=' + new Date(0).toGMTString();\n";
						stringToSendToWebBrowser += "          document.cookie='password=;expires=' + new Date(0).toGMTString();\n";
						stringToSendToWebBrowser += "      }\n";
						stringToSendToWebBrowser += "  </script>\n";

						stringToSendToWebBrowser += "  <body onload=\"clearLoginCookie()\">\n";
						stringToSendToWebBrowser += Index.headerAndNavBarGeneration(userUniqueID, password, usersStored);
						stringToSendToWebBrowser += "	 <div class=\"section\">\n";
						stringToSendToWebBrowser += "      <div class=\"container\">\n";
						stringToSendToWebBrowser += "        <div class=\"row\">\n";
						stringToSendToWebBrowser += "          <div class=\"col-md-10\">\n";
						stringToSendToWebBrowser += "            <div class=\"page-header text-danger\">\n";
						stringToSendToWebBrowser += "              <h2 class=\"text-primary\">Sorry, there seems to have been an error in the transfer of your cookies </h2>\n";
						stringToSendToWebBrowser += "              <a href = \"Index.html\"><h2 class=\"text-primary\">Please click here to return to the index page</h2></a>\n";
						stringToSendToWebBrowser += "            </div>\n";
						stringToSendToWebBrowser += "          </div>\n";
						stringToSendToWebBrowser += "        </div>\n";
						stringToSendToWebBrowser += "      </div>\n";
						stringToSendToWebBrowser += "    </div>\n";
						stringToSendToWebBrowser += Index.footerGeneration();
						stringToSendToWebBrowser += "  </body>\n";
						stringToSendToWebBrowser += "</html>\n";


						toProcess.r = new WebResponse(WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
						return true;

					}
					else
					{
						for (Post post : userPosts) 
						{
							stringToSendToWebBrowser += Index.postPublishingGeneration(post, true);
						}

					}//End If

				}
				else
				{
					for (Post post : userPosts) 
					{
						stringToSendToWebBrowser += Index.postPublishingGeneration(post, false);
					}

				}//End If


				//FOOTER OF THE WEBSITE
				stringToSendToWebBrowser += Index.footerGeneration();
				stringToSendToWebBrowser += "  </body>\n";
				stringToSendToWebBrowser += "\n";
				stringToSendToWebBrowser += "</html>\n";
				toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser);
				return true;
				
			}//End If			

		}//End If


		return false;
	}

}