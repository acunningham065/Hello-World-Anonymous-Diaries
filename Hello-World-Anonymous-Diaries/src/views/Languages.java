package views;

import java.util.ArrayList;
import java.util.List;

import org.h2.mvstore.MVMap;

import model.Post;
import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class Languages extends DynamicWebPage
{

	public Languages(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}


	public boolean process(WebRequest toProcess)
	{
		MVMap<String, Post> postsStored = db.s.openMap("Post");
		List<String> postKeys = postsStored.keyList();

		MVMap<String, User> usersStored = db.s.openMap("User");

		String userUniqueID = toProcess.cookies.get("userID"); 
		String password = toProcess.cookies.get("password");

		if(toProcess.path.equalsIgnoreCase("Languages.html"))
		{
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<html>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <head>\n";
			stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
			stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
			stringToSendToWebBrowser += "    <title>Languages</title>\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
			stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "    <link href=\"Languages.css\" rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "  </head>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <script>\n";
			
			//Post Async Load Scripts

			stringToSendToWebBrowser += "    function asyncLoad(lang)\n";
			stringToSendToWebBrowser += "    {\n";
			stringToSendToWebBrowser += "        if (window.XMLHttpRequest)\n";
			stringToSendToWebBrowser += "        {// code for IE7+, Firefox, Chrome, Opera, Safari\n";
			stringToSendToWebBrowser += "            xmlhttp=new XMLHttpRequest();\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        else\n";
			stringToSendToWebBrowser += "        {// code for IE6, IE5\n";
			stringToSendToWebBrowser += "            xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        xmlhttp.onreadystatechange=myDataLoadedFunction;\n";
			stringToSendToWebBrowser += "        //This line actually makes the request to the server\n";
			stringToSendToWebBrowser += "        xmlhttp.open(\"GET\",'/Languages.html/AsyncLanguage?language='+lang,true);\n";
			stringToSendToWebBrowser += "        xmlhttp.send();\n";
			stringToSendToWebBrowser += "    }\n";
			
			stringToSendToWebBrowser += "	function myDataLoadedFunction()\n";
			stringToSendToWebBrowser += "	{\n";
			stringToSendToWebBrowser += "		if (xmlhttp.readyState==4 && xmlhttp.status==200)\n";
			stringToSendToWebBrowser += "		{\n";
			stringToSendToWebBrowser += "	        //This line will set the html between the <body> tags\n";
			stringToSendToWebBrowser += "   	    //with what was returned by the webserver\n";
			stringToSendToWebBrowser += " 		    //this will make the page redraw\n";
			stringToSendToWebBrowser += "           document.getElementById(\"postsdiv\").innerHTML = xmlhttp.responseText;\n";
			stringToSendToWebBrowser += "       }\n";
			stringToSendToWebBrowser += "   }\n";	
			
			//Emoticon Async Load Scripts
			
			stringToSendToWebBrowser += "    var postID;\n";
			stringToSendToWebBrowser += "    function asyncLoadEmoticon(postIDParam, emoticonType)\n";
			stringToSendToWebBrowser += "    {\n";
			stringToSendToWebBrowser += "    postID = postIDParam\n";
			stringToSendToWebBrowser += "        if (window.XMLHttpRequest)\n";
			stringToSendToWebBrowser += "        {// code for IE7+, Firefox, Chrome, Opera, Safari\n";
			stringToSendToWebBrowser += "            xmlhttp=new XMLHttpRequest();\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "            else\n";
			stringToSendToWebBrowser += "        {// code for IE6, IE5\n";
			stringToSendToWebBrowser += "            xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        var divElements = document.getElementById(\"post\" + postID + \"emoticondiv\").children\n";
			stringToSendToWebBrowser += "        var elements = [];\n";
			stringToSendToWebBrowser += "        for(i = 0; i < divElements[0].children.length; i++)\n";
			stringToSendToWebBrowser += "        {\n";
			stringToSendToWebBrowser += "            elements.push(parseInt(divElements[0].children[i].innerText));\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        var happyVotes = elements[0];\n";
			stringToSendToWebBrowser += "        var angryVotes = elements[1];\n";
			stringToSendToWebBrowser += "        var laughingVotes = elements[2];\n";
			stringToSendToWebBrowser += "        var sadVotes = elements[3];\n";
			stringToSendToWebBrowser += "        var loveVotes = elements[4];\n\n";
			stringToSendToWebBrowser += "        switch(emoticonType)\n";
			stringToSendToWebBrowser += "        {\n";
			stringToSendToWebBrowser += "            case 'happy': happyVotes += 1;\n";
			stringToSendToWebBrowser += "                break;\n";
			stringToSendToWebBrowser += "            case 'sad': sadVotes += 1;\n";
			stringToSendToWebBrowser += "                break;\n";
			stringToSendToWebBrowser += "            case 'angry': angryVotes += 1;\n";
			stringToSendToWebBrowser += "                break;\n";
			stringToSendToWebBrowser += "            case 'laughing': laughingVotes += 1;\n";			
			stringToSendToWebBrowser += "                break;\n";
			stringToSendToWebBrowser += "            case 'love': loveVotes += 1;\n";			
			stringToSendToWebBrowser += "                break;\n";
			stringToSendToWebBrowser += "        }\n\n";
			stringToSendToWebBrowser += "        //This line actually makes the request to the server\n";
			stringToSendToWebBrowser += "        xmlhttp.onreadystatechange=myEmoticonDataLoadedFunction;\n";
			stringToSendToWebBrowser += "        xmlhttp.open(\"GET\",'/Languages.html/AsyncEmoticon?postID='+postID+'&happyVotes='+happyVotes+'&sadVotes='+sadVotes+'&angryVotes='+angryVotes+'&laughingVotes='+laughingVotes+'&loveVotes='+loveVotes,true);\n";
			stringToSendToWebBrowser += "        xmlhttp.send();\n";
			stringToSendToWebBrowser += "    }\n";
			
			stringToSendToWebBrowser += "    function myEmoticonDataLoadedFunction()\n";
			stringToSendToWebBrowser += "    {\n";
			stringToSendToWebBrowser += "      if (xmlhttp.readyState==4 && xmlhttp.status==200)\n";
			stringToSendToWebBrowser += "      {\n";
			stringToSendToWebBrowser += "        //This line will set the html between the <body> tags\n";
			stringToSendToWebBrowser += "        //with what was returned by the webserver\n";
			stringToSendToWebBrowser += "        //this will make the page redraw\n";
			stringToSendToWebBrowser += "         document.getElementById(\"post\" + postID + \"emoticondiv\").innerHTML = xmlhttp.responseText;\n";
			stringToSendToWebBrowser += "      }\n";
			stringToSendToWebBrowser += "    }\n";
			
			stringToSendToWebBrowser += "  </script>\n";
			stringToSendToWebBrowser += "  \n";
			
			stringToSendToWebBrowser += "  <body>\n";
			stringToSendToWebBrowser += Index.headerAndNavBarGeneration(userUniqueID, password, usersStored);
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <div class=\"page-header text-danger\">\n";
			stringToSendToWebBrowser += "              <h2 class=\"text-primary\">Languages</h2>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <div class=\"page-header text-primary\">\n";
			stringToSendToWebBrowser += "              <h4>Below click on the language that you would like to search</h4>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <div class=\"btn-group\">\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('English');return false;\" class=\"btn btn-default\">English</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('Espanol');return false;\" class=\"btn btn-default\">Espa&#241ol</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('Francais');return false;\" class=\"btn btn-default\">Fran&#231ais</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('Italiano');return false;\" class=\"btn btn-default\">Italiano</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('Deutsche');return false;\" class=\"btn btn-default\">Deutsche</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('Polskie');return false;\" class=\"btn btn-default\">Polskie</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('Turk');return false;\" class=\"btn btn-default\">T&#252rk</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('Portugues');return false;\" class=\"btn btn-default\">Portugu&#234s</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('Svenska');return false;\" class=\"btn btn-default\">Svenska</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('Dansk');return false;\" class=\"btn btn-default\">Dansk</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('Norsk');return false;\" class=\"btn btn-default\">Norsk</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncLoad('Nederlands');return false;\" class=\"btn btn-default\">Nederlands</a>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"section\" id=\"postsdiv\">\n";
			stringToSendToWebBrowser += "    </div>\n";

			//FOOTER OF THE WEBSITE
			stringToSendToWebBrowser += Index.footerGeneration();
			stringToSendToWebBrowser += "  </body>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</html>\n";
			stringToSendToWebBrowser += "  </body>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</html>\n";
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;
		}
		else if(toProcess.path.equalsIgnoreCase("Languages.html/AsyncLanguage"))
		{
			String languageSelected = toProcess.params.get("language").toLowerCase();
			Post tempPost = null;
			
			ArrayList<Post> postList = new ArrayList<Post>();
			
			for (String key:postKeys)
			{
				tempPost = postsStored.get(key);
				
				if (tempPost.getLanguage().equalsIgnoreCase(languageSelected))
				{
					postList.add(tempPost);
					
				}//End if
				
			}//End For

			String stringToSendToWebBrowser = "";
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
					stringToSendToWebBrowser += "    <link href=\"Languages.css\" rel=\"stylesheet\" type=\"text/css\">\n";				
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
					for(Post post: postList)
					{
						stringToSendToWebBrowser += Index.postPublishingGeneration(post, true);

					}//End For

				}//End If

			}
			else
			{
				for(Post post: postList)
				{
					stringToSendToWebBrowser += Index.postPublishingGeneration(post, false);

				}//End For

			}//End If

			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;
			
		}else if(toProcess.path.equalsIgnoreCase("Languages.html/AsyncEmoticon"))
		{			
			Post post = null;
			post = postsStored.get(toProcess.params.get("postID"));
			
			//If the user has not yet voted on this post then set the votes
			if(!post.getUsersRated().contains(toProcess.cookies.get("userID")))
			{		
				if (post.getAuthor() != null)
				{
					if(!post.getAuthor().getUniqueID().equals(toProcess.cookies.get("userID")))
					{
						post.setHappyVotes(Integer.parseInt(toProcess.params.get("happyVotes")));
						post.setSadVotes(Integer.parseInt(toProcess.params.get("sadVotes")));
						post.setAngryVotes(Integer.parseInt(toProcess.params.get("angryVotes")));
						post.setLaughingVotes(Integer.parseInt(toProcess.params.get("laughingVotes")));
						post.setLoveVotes(Integer.parseInt(toProcess.params.get("loveVotes")));
						post.getUsersRated().add(toProcess.cookies.get("userID"));
						postsStored.put(post.getUniquePostID(), post);
						db.commit();

					}//End If

				}
				else
				{
					post.setHappyVotes(Integer.parseInt(toProcess.params.get("happyVotes")));
					post.setSadVotes(Integer.parseInt(toProcess.params.get("sadVotes")));
					post.setAngryVotes(Integer.parseInt(toProcess.params.get("angryVotes")));
					post.setLaughingVotes(Integer.parseInt(toProcess.params.get("laughingVotes")));
					post.setLoveVotes(Integer.parseInt(toProcess.params.get("loveVotes")));
					post.getUsersRated().add(toProcess.cookies.get("userID"));
					postsStored.put(post.getUniquePostID(), post);
					db.commit();
					
				}//End If
				
			}//End If
			
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <h5 style=\"text-align:center\" id = \"happyVotes\">" + post.getHappyVotes() + "</h5>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <h5 style=\"text-align:center\" id = \"angryVotes\">" + post.getAngryVotes() + "</h5>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <h5 style=\"text-align:center\" id = \"laughingVotes\">" + post.getLaughingVotes() + "</h5>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <h5 style=\"text-align:center\" id = \"sadVotes\">" + post.getSadVotes() + "</h5>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <h5 style=\"text-align:center\" id = \"loveVotes\">" + post.getLoveVotes() + "</h5>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			
			
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;
			
		}//End If
		

		return false;
	}

}