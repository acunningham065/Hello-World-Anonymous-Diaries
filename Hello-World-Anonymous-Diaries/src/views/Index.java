package views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.h2.mvstore.MVMap;

import model.Post;
import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class Index extends DynamicWebPage
{

	public Index(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}

	public boolean process(WebRequest toProcess)
	{
		//Open the database maps for post and user
		MVMap<String, Post> postsStored = db.s.openMap("Post");
		List<String> postKeys = postsStored.keyList();

		MVMap<String, User> usersStored = db.s.openMap("User");

		String userUniqueID = toProcess.cookies.get("userID"); 
		String password = toProcess.cookies.get("password");

		if(toProcess.path.equalsIgnoreCase("Index.html"))
		{
			//null indicates that the user hasn't logged in
			if(userUniqueID != null)
			{
				//Get the user from the database
				User user = usersStored.get(userUniqueID);

				//If the user is still null the user doesn't exist OR the password stored in the cookies doesn't match the one on the database
				if((user == null)||(!user.getPassword().contentEquals(password)))
				{
					String stringToSendToWebBrowser = "";
					stringToSendToWebBrowser += "<!DOCTYPE html>\n";
					stringToSendToWebBrowser += "<html>\n";
					stringToSendToWebBrowser += "  <head>\n";
					stringToSendToWebBrowser += "  <base href=\"//localhost:8080/\"/>\n";
					stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
					stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
					stringToSendToWebBrowser += "    <title>Session Error</title>\n";
					stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
					stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
					stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
					stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
					stringToSendToWebBrowser += "    <link href=\"index.css\" rel=\"stylesheet\" type=\"text/css\">\n";				
					stringToSendToWebBrowser += "  </head>\n";
					stringToSendToWebBrowser += "  <script>\n";	
					stringToSendToWebBrowser += "      function clearLoginCookie()\n";
					stringToSendToWebBrowser += "      {\n";
					stringToSendToWebBrowser += "          document.cookie='userID=;expires=' + new Date(0).toGMTString();\n";
					stringToSendToWebBrowser += "          document.cookie='password=;expires=' + new Date(0).toGMTString();\n";
					stringToSendToWebBrowser += "      }\n";
					stringToSendToWebBrowser += "  </script>\n";

					stringToSendToWebBrowser += "  <body onload=\"clearLoginCookie()\">\n";
					stringToSendToWebBrowser += headerAndNavBarGeneration(userUniqueID, password, usersStored);
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
					stringToSendToWebBrowser += footerGeneration();
					stringToSendToWebBrowser += "  </body>\n";
					stringToSendToWebBrowser += "</html>\n";

					toProcess.r = new WebResponse(WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
					return true;

				}//End If

			}//End If

			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<!DOCTYPE html>\n";
			stringToSendToWebBrowser += "<html>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <head>\n";
			stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
			stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
			stringToSendToWebBrowser += "    <title>Home Page</title>\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
			stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "    <link href=\"index.css\" rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "  </head>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <script>\n";

			//Post Async Load Scripts

			stringToSendToWebBrowser += "    function asyncLoad()\n";
			stringToSendToWebBrowser += "    {\n";
			stringToSendToWebBrowser += "        if (window.XMLHttpRequest)\n";
			stringToSendToWebBrowser += "        {// code for IE7+, Firefox, Chrome, Opera, Safari\n";
			stringToSendToWebBrowser += "            xmlhttp=new XMLHttpRequest();\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        else\n";
			stringToSendToWebBrowser += "        {// code for IE6, IE5\n";
			stringToSendToWebBrowser += "            xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        var title = document.getElementById(\"title\").value;\n";
			stringToSendToWebBrowser += "        var post = document.getElementById(\"post\").value;\n";
			stringToSendToWebBrowser += "        var tags = document.getElementById(\"tags\").value;\n";
			stringToSendToWebBrowser += "        var privacy = document.getElementById(\"privacy\").value;\n";
			stringToSendToWebBrowser += "        var language = document.getElementById(\"language\").value;\n";
			stringToSendToWebBrowser += "        xmlhttp.onreadystatechange=myDataLoadedFunction;\n";
			stringToSendToWebBrowser += "        //This line actually makes the request to the server\n";
			stringToSendToWebBrowser += "        xmlhttp.open(\"GET\",'/Index.html/Async?title='+title+'&post='+post+'&tags='+tags+'&privacy='+privacy+'&language='+language,true);\n";
			stringToSendToWebBrowser += "        xmlhttp.send();\n";
			stringToSendToWebBrowser += "        document.getElementById(\"postForm\").reset()";
			stringToSendToWebBrowser += "    }\n";

			stringToSendToWebBrowser += "	function myDataLoadedFunction()\n";
			stringToSendToWebBrowser += "	{\n";
			stringToSendToWebBrowser += "		if (xmlhttp.readyState==4 && xmlhttp.status==200)\n";
			stringToSendToWebBrowser += "		{\n";
			stringToSendToWebBrowser += "	        //This line will set the html between the <div> tags\n";
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
			stringToSendToWebBrowser += "        xmlhttp.open(\"GET\",'/Index.html/AsyncEmoticon?postID='+postID+'&happyVotes='+happyVotes+'&sadVotes='+sadVotes+'&angryVotes='+angryVotes+'&laughingVotes='+laughingVotes+'&loveVotes='+loveVotes,true);\n";
			stringToSendToWebBrowser += "        xmlhttp.send();\n";
			stringToSendToWebBrowser += "    }\n";

			stringToSendToWebBrowser += "    function myEmoticonDataLoadedFunction()\n";
			stringToSendToWebBrowser += "    {\n";
			stringToSendToWebBrowser += "      if (xmlhttp.readyState==4 && xmlhttp.status==200)\n";
			stringToSendToWebBrowser += "      {\n";
			stringToSendToWebBrowser += "        //This line will set the html between the <div> tags\n";
			stringToSendToWebBrowser += "        //with what was returned by the webserver\n";
			stringToSendToWebBrowser += "        //this will make the page redraw\n";
			stringToSendToWebBrowser += "         document.getElementById(\"post\" + postID + \"emoticondiv\").innerHTML = xmlhttp.responseText;\n";
			stringToSendToWebBrowser += "      }\n";
			stringToSendToWebBrowser += "    }\n";

			//Post Sort Async Load Scripts

			stringToSendToWebBrowser += "    function asyncSort(sortOption)\n";
			stringToSendToWebBrowser += "    {\n";
			stringToSendToWebBrowser += "        if (window.XMLHttpRequest)\n";
			stringToSendToWebBrowser += "        {// code for IE7+, Firefox, Chrome, Opera, Safari\n";
			stringToSendToWebBrowser += "            xmlhttp=new XMLHttpRequest();\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        else\n";
			stringToSendToWebBrowser += "        {// code for IE6, IE5\n";
			stringToSendToWebBrowser += "            xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        xmlhttp.onreadystatechange=myDataSortedFunction;\n";
			stringToSendToWebBrowser += "        //This line actually makes the request to the server\n";
			stringToSendToWebBrowser += "        xmlhttp.open(\"GET\",'/Index.html/AsyncSort?sortOption='+sortOption,true);\n";
			stringToSendToWebBrowser += "        xmlhttp.send();\n";
			stringToSendToWebBrowser += "    }\n";

			stringToSendToWebBrowser += "	function myDataSortedFunction()\n";
			stringToSendToWebBrowser += "	{\n";
			stringToSendToWebBrowser += "		if (xmlhttp.readyState==4 && xmlhttp.status==200)\n";
			stringToSendToWebBrowser += "		{\n";
			stringToSendToWebBrowser += "	        //This line will set the html between the <div> tags\n";
			stringToSendToWebBrowser += "   	    //with what was returned by the webserver\n";
			stringToSendToWebBrowser += " 		    //this will make the page redraw\n";
			stringToSendToWebBrowser += "           document.getElementById(\"postsdiv\").innerHTML = xmlhttp.responseText;\n";
			stringToSendToWebBrowser += "       }\n";
			stringToSendToWebBrowser += "   }\n";

			stringToSendToWebBrowser += "  </script>\n";
			stringToSendToWebBrowser += "  <body>\n";

			stringToSendToWebBrowser += headerAndNavBarGeneration(userUniqueID, password, usersStored);

			stringToSendToWebBrowser += "	 <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <form class=\"form-horizontal\" role=\"form\" action=\"Index.html/SubmitPost\" method=\"GET\" id = \"postForm\" accept-charset=\"UTF-8\">\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"title\" class=\"control-label\">Title</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <input type=\"text\" class=\"form-control\" id=\"title\" name=\"title\" placeholder=\"Title\" required>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"post\" class=\"control-label\">Post</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <textarea class=\"form-control input-md\" id=\"post\" name=\"post\" rows=\"3\" placeholder=\"Post\" required></textarea>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"tags\" class=\"control-label\">Tags (comma separated)</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                <input type=\"text\" class=\"form-control\" id=\"tags\" name=\"tags\" placeholder=\"Tags (e.g. English, Funny, University, Lecture)\" required>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"privacy\" class=\"control-label\">Privacy Options</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                <select class=\"form-control\" id=\"privacy\" name=\"privacy\"\" required>\n";
			stringToSendToWebBrowser += "                <option value = \"Anonymous\">Anonymous</option>\n";

			if (userUniqueID != null)
			{
				User user = usersStored.get(userUniqueID);
				if (user != null)
				{
					stringToSendToWebBrowser += "                <option value = \"" + userUniqueID + "\">" + user.getUserName() + "</option>\n";

				}//End If

			}//End If
			stringToSendToWebBrowser += "                </select>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"language\" class=\"control-label\">Language</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                <select class=\"form-control\" id=\"language\" name=\"language\"\" required>\n";
			stringToSendToWebBrowser += "                  <option value = \"English\">English</option>\n";
			stringToSendToWebBrowser += "                  <option value = \"Espanol\">Espa&#241ol</option>\n";
			stringToSendToWebBrowser += "                  <option value = \"Francais\">Fran&#231ais</option>\n";
			stringToSendToWebBrowser += "                  <option value = \"Italiano\">Italiano</option>\n";
			stringToSendToWebBrowser += "                  <option value = \"Deutsche\">Deutsche</option>\n";
			stringToSendToWebBrowser += "                  <option value = \"Polskie\">Polskie</option>\n";
			stringToSendToWebBrowser += "                  <option value = \"Turk\">T&#252rk</option>\n";
			stringToSendToWebBrowser += "                  <option value = \"Portugues\">Portugu&#234s</option>\n";
			stringToSendToWebBrowser += "                  <option value = \"Svenska\">Svenska</option>\n";
			stringToSendToWebBrowser += "                  <option value = \"Dansk\">Dansk</option>\n";
			stringToSendToWebBrowser += "                  <option value = \"Norsk\">Norsk</option>\n";
			stringToSendToWebBrowser += "                  <option value = \"Nederlands\">Nederlands</option>\n";
			stringToSendToWebBrowser += "                </select>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-12\">\n";
			stringToSendToWebBrowser += "                  <button type=\"submit\" class=\"btn btn-block btn-default\" onclick=\"asyncLoad();return false;\">Post</button>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "            </form>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			//stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "          <hr>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <div class=\"btn-group\">\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncSort('rated');return false;\" class=\"btn btn-default\">Top Rated Today</a>\n";
			stringToSendToWebBrowser += "              <a onclick=\"asyncSort('recent');return false;\" class=\"btn btn-default\">Most Recent</a>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "          <hr>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    <div id=\"postsdiv\">\n";

			//null indicates that the user hasn't logged in
			if(userUniqueID != null)
			{
				for (String key : postKeys) 
				{
					Post post = postsStored.get(key);

					stringToSendToWebBrowser += postPublishingGeneration(post, true);

				}//End For

			}
			else
			{
				for (String key : postKeys) 
				{
					Post post = postsStored.get(key);

					stringToSendToWebBrowser += postPublishingGeneration(post, false);

				}//End For

			}//End If				

			stringToSendToWebBrowser += "    </div>\n";

			//FOOTER OF THE WEBSITE
			stringToSendToWebBrowser += footerGeneration();
			stringToSendToWebBrowser += "  </body>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</html>\n";

			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;

		}
		else if(toProcess.path.equalsIgnoreCase("Index.html/Async"))
		{
			String postTitle = "", postText = "", postTagString = "", postPrivacy = "", postLanguage = "";
			ArrayList<String> postTags = new ArrayList<String>();

			postTitle = toProcess.params.get("title");
			postText = toProcess.params.get("post");
			postTagString = toProcess.params.get("tags");
			postPrivacy = toProcess.params.get("privacy");
			postLanguage = toProcess.params.get("language");

			String[] tags = postTagString.split(",");

			for (String tag : tags)
			{
				postTags.add(tag);

			}//End For

			Post newPost = null;

			if (postPrivacy.equalsIgnoreCase("Anonymous"))
			{
				newPost = new Post(null, postTitle, postText, postLanguage, postTags);
			}
			else
			{
				newPost = new Post(usersStored.get(postPrivacy), postTitle, postText, postLanguage, postTags);					
			}//End If				


			postsStored.put(newPost.getUniquePostID(), newPost);
			db.commit();


			postsStored = db.s.openMap("Post");
			postKeys = postsStored.keyList();

			String stringToSendToWebBrowser = "";
			//null indicates that the user hasn't logged in
			if(userUniqueID != null)
			{
				for (String key : postKeys) 
				{
					Post post = postsStored.get(key);

					stringToSendToWebBrowser += postPublishingGeneration(post, true);

				}//End For
			}
			else
			{
				for (String key : postKeys) 
				{
					Post post = postsStored.get(key);

					stringToSendToWebBrowser += postPublishingGeneration(post, false);

				}//End For

			}//End If


			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;

		}
		else if(toProcess.path.equalsIgnoreCase("Index.html/AsyncEmoticon"))
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

		}
		else if(toProcess.path.equalsIgnoreCase("Index.html/AsyncSort"))
		{
			String sortOption = toProcess.params.get("sortOption");
			Post tempPost = null;
			ArrayList<Post> postList = new ArrayList<Post>();

			//Get the day's posts
			for(String key: postKeys)
			{
				tempPost = postsStored.get(key);

				if(tempPost.getDateTimePosted().isAfter(LocalDateTime.now().minusDays(1)))
				{
					tempPost.calculateTotalVotes();
					postList.add(tempPost);

				}//End If

			}//End For
			
			//Sort appropriately
			switch (sortOption)
			{
				case "rated":
					
					for (int i = 0; i < postList.size(); i++) 
					{
						for (int j = 1; j < (postList.size() - i); j++) 
						{

							if (postList.get(j - 1).getTotalVotes() < postList.get(j).getTotalVotes()) 
							{
								tempPost = postList.get(j - 1);
								postList.remove(j - 1); 
								postList.add((j - 1), postList.get(j-1));
								postList.remove(j);
								postList.add(j, tempPost);
								
							}//End If

						}//End For

					}//End For
					
					break;

				case "recent":
					
					for (int i = 0; i < postList.size(); i++) 
					{
						for (int j = 1; j < (postList.size() - i); j++) 
						{

							if (postList.get(j).getDateTimePosted().isAfter(postList.get(j-1).getDateTimePosted())) 
							{
								tempPost = postList.get(j - 1);
								postList.remove(j - 1); 
								postList.add((j - 1), postList.get(j-1));
								postList.remove(j);
								postList.add(j, tempPost);
								
							}//End If

						}//End For

					}//End For

					break;

			}//End Switch
			
			String stringToSendToWebBrowser = "";
			
			//null indicates that the user hasn't logged in
			if(userUniqueID != null)
			{
				for (Post post : postList) 
				{
					stringToSendToWebBrowser += postPublishingGeneration(post, true);

				}//End For
			}
			else
			{
				for (Post post : postList)  
				{
					stringToSendToWebBrowser += postPublishingGeneration(post, false);

				}//End For

			}//End If


			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;

		}//End If

		return false;

	}//End Index

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("hh:mm a - dd/MM/yyyy");

	/**
	 * @param post
	 * @return The HTML for a post
	 */
	public static String postPublishingGeneration(Post post, boolean emoticonClickable)
	{

		String stringToSendToWebBrowser = "";
		stringToSendToWebBrowser += "      <div class=\"container\">\n";
		stringToSendToWebBrowser += "        <div class=\"row\">\n";
		stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
		stringToSendToWebBrowser += "            <ul class=\"media-list\">\n";
		stringToSendToWebBrowser += "              <li class=\"media\">\n";
		if (post.getAuthor() == null)
		{
			stringToSendToWebBrowser += "                <a class=\"pull-left\" href=\"#\"><img src=\"default-avatar.png\" height=\"64\" width=\"64\"></a>\n";
		}
		else
		{
			stringToSendToWebBrowser += "                <a class=\"pull-left\" href=\"Profile.html/" + post.getAuthor().getUniqueID() + "\" class=\"pull-left\"><img src=\"" + post.getAuthor().getProfilePictureAddress() + "\" height=\"64\" width=\"64\"></a>\n";
		}//End If		
		stringToSendToWebBrowser += "                <div class=\"media-body\">\n";
		stringToSendToWebBrowser += "                  <a href=\"Post.html/" + post.getUniquePostID() + "\"><h4 class=\"media-heading\">" + post.getTitle() + "</h4></a>\n";
		stringToSendToWebBrowser += "                  <p>" + post.getPostContents() + "</p>\n";
		stringToSendToWebBrowser += "                  <p style=\"color:grey\"> " + post.getTagString() + "</p>\n";
		stringToSendToWebBrowser += "                  <p style=\"color:grey\"> " + dateFormatter.format(post.getDateTimePosted()) + "</p>\n";
		stringToSendToWebBrowser += "                </div>\n";
		stringToSendToWebBrowser += "              </li>\n";
		stringToSendToWebBrowser += "              <li class=\"media\"></li>\n";
		stringToSendToWebBrowser += "            </ul>\n";
		stringToSendToWebBrowser += "          </div>\n";
		stringToSendToWebBrowser += "        </div>\n";
		stringToSendToWebBrowser += "      </div>\n";
		stringToSendToWebBrowser += "      <div class=\"container\">\n";
		stringToSendToWebBrowser += "        <div class=\"row\">\n";

		if (emoticonClickable)
		{
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <img src=\"Happy.png\" height=\"64\" width=\"64\" onclick = \"asyncLoadEmoticon(" + post.getUniquePostID() + ", 'happy');return false;\">\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <img src=\"Angry.png\" height=\"64\" width=\"64\" onclick = \"asyncLoadEmoticon(" + post.getUniquePostID() + ", 'angry');return false;\">\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <img src=\"Laughing.png\" height=\"64\" width=\"64\" onclick = \"asyncLoadEmoticon(" + post.getUniquePostID() + ", 'laughing');return false;\">\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <img src=\"Sad.png\" height=\"64\" width=\"64\" onclick = \"asyncLoadEmoticon(" + post.getUniquePostID() + ", 'sad');return false;\">\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <img src=\"Love.png\" height=\"64\" width=\"64\" onclick = \"asyncLoadEmoticon(" + post.getUniquePostID() + ", 'love');return false;\">\n";
			stringToSendToWebBrowser += "          </div>\n";

		}
		else
		{
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <img src=\"Happy.png\" height=\"64\" width=\"64\">\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <img src=\"Angry.png\" height=\"64\" width=\"64\">\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <img src=\"Laughing.png\" height=\"64\" width=\"64\">\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <img src=\"Sad.png\" height=\"64\" width=\"64\">\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "            <img src=\"Love.png\" height=\"64\" width=\"64\">\n";
			stringToSendToWebBrowser += "          </div>\n";

		}//End If

		stringToSendToWebBrowser += "        </div>\n";
		stringToSendToWebBrowser += "    <div id=\"post" + post.getUniquePostID() + "emoticondiv\">\n";
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
		stringToSendToWebBrowser += "      </div>\n";
		stringToSendToWebBrowser += "    </div>\n";
		stringToSendToWebBrowser += "      <div class=\"row\">\n";
		stringToSendToWebBrowser += "        <div class=\"col-md-12\">\n";
		stringToSendToWebBrowser += "          <hr>\n";
		stringToSendToWebBrowser += "        </div>\n";
		stringToSendToWebBrowser += "      </div>\n";
		stringToSendToWebBrowser += "    </div>\n";

		return stringToSendToWebBrowser;

	}//End postPublishingGeneration


	/**
	 * @param userUniqueID 
	 * @param password 
	 * @param usersStored 
	 * @return The Header and Navigation HTML
	 */
	public static String headerAndNavBarGeneration(Object userUniqueID, String password, MVMap<String, User> usersStored)
	{
		String stringToSendToWebBrowser = "";

		//null indicates that the user hasn't logged in
		if(userUniqueID != null)
		{
			//Get the user from the database
			User user = usersStored.get(userUniqueID);

			//If the user is still null the user doesn't exist OR the password stored in the cookies doesn't match the one on the database
			if((user == null)||(!user.getPassword().contentEquals(password)))
			{
				stringToSendToWebBrowser += "    <div class=\"navbar navbar-default navbar-static-top\">\n";
				stringToSendToWebBrowser += "      <div class=\"container\">\n";
				stringToSendToWebBrowser += "        <div class=\"navbar-header\">\n";
				stringToSendToWebBrowser += "          <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-ex-collapse\">\n";
				stringToSendToWebBrowser += "            <span class=\"sr-only\">Toggle navigation</span>\n";
				stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
				stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
				stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
				stringToSendToWebBrowser += "          </button>\n";
				stringToSendToWebBrowser += "          <a class=\"navbar-brand\" href=\"Index.html\"><span>Hello World</span></a>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "        <div class=\"collapse navbar-collapse\" id=\"navbar-ex-collapse\">\n";
				stringToSendToWebBrowser += "          <ul class=\"nav navbar-nav navbar-right\"></ul>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";
				stringToSendToWebBrowser += "    <div class=\"section\">\n";
				stringToSendToWebBrowser += "      <div class=\"container\">\n";
				stringToSendToWebBrowser += "        <div class=\"row\">\n";
				stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
				stringToSendToWebBrowser += "            <ul class=\"breadcrumb\">\n";
				stringToSendToWebBrowser += "              <li>\n";
				stringToSendToWebBrowser += "                <a href=\"Index.html\">Home</a>\n";
				stringToSendToWebBrowser += "              </li>\n";
				stringToSendToWebBrowser += "              <li>\n";
				stringToSendToWebBrowser += "                <a href=\"Trending.html\">Trending</a>\n";
				stringToSendToWebBrowser += "              </li>\n";
				stringToSendToWebBrowser += "              <li>\n";
				stringToSendToWebBrowser += "                <a href=\"Languages.html\">Languages</a>\n";
				stringToSendToWebBrowser += "              </li>\n";
				stringToSendToWebBrowser += "              <li>\n";
				stringToSendToWebBrowser += "                <a href=\"Login.html\">Log In/Sign Up</a>\n";
				stringToSendToWebBrowser += "              </li>\n";
				stringToSendToWebBrowser += "              <li>\n";
				stringToSendToWebBrowser += "                <a href=\"Search.html\">Search</a>\n";
				stringToSendToWebBrowser += "              </li>\n";
				stringToSendToWebBrowser += "            </ul>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";
			}	
			else
			{		
				stringToSendToWebBrowser += "  <script>\n";	
				stringToSendToWebBrowser += "      function clearLoginCookie()\n";
				stringToSendToWebBrowser += "      {\n";
				stringToSendToWebBrowser += "          document.cookie='userID=;path=/;expires=' + new Date(0).toGMTString();\n";
				stringToSendToWebBrowser += "          document.cookie='password=;path=/;expires=' + new Date(0).toGMTString();\n";
				stringToSendToWebBrowser += "      }\n";
				stringToSendToWebBrowser += "  </script>\n";
				stringToSendToWebBrowser += "    <div class=\"navbar navbar-default navbar-static-top\">\n";
				stringToSendToWebBrowser += "      <div class=\"container\">\n";
				stringToSendToWebBrowser += "        <div class=\"navbar-header\">\n";
				stringToSendToWebBrowser += "          <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-ex-collapse\">\n";
				stringToSendToWebBrowser += "            <span class=\"sr-only\">Toggle navigation</span>\n";
				stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
				stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
				stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
				stringToSendToWebBrowser += "          </button>\n";
				stringToSendToWebBrowser += "          <a class=\"navbar-brand\" href=\"Index.html\"><span>Hello World</span></a>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "        <div class=\"collapse navbar-collapse\" id=\"navbar-ex-collapse\">\n";
				stringToSendToWebBrowser += "          <ul class=\"nav navbar-nav navbar-right\"></ul>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";
				stringToSendToWebBrowser += "    <div class=\"section\">\n";
				stringToSendToWebBrowser += "      <div class=\"container\">\n";
				stringToSendToWebBrowser += "        <div class=\"row\">\n";
				stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
				stringToSendToWebBrowser += "            <ul class=\"breadcrumb\">\n";
				stringToSendToWebBrowser += "              <li>\n";
				stringToSendToWebBrowser += "                <a href=\"Index.html\">Home</a>\n";
				stringToSendToWebBrowser += "              </li>\n";
				stringToSendToWebBrowser += "              <li>\n";
				stringToSendToWebBrowser += "                <a href=\"Trending.html\">Trending</a>\n";
				stringToSendToWebBrowser += "              </li>\n";
				stringToSendToWebBrowser += "              <li>\n";
				stringToSendToWebBrowser += "                <a href=\"Languages.html\">Languages</a>\n";
				stringToSendToWebBrowser += "              </li>\n";
				stringToSendToWebBrowser += "              <li>\n";
				stringToSendToWebBrowser += "                <a href=\"Search.html\">Search</a>\n";
				stringToSendToWebBrowser += "              </li>\n";
				stringToSendToWebBrowser += "              <li>\n";
				stringToSendToWebBrowser += "                <a href=\"Profile.html/" + user.getUniqueID() + "\">Profile</a>\n";
				stringToSendToWebBrowser += "              </li>\n";
				stringToSendToWebBrowser += "              <li onclick = \"clearLoginCookie()\">\n";
				stringToSendToWebBrowser += "                <a href=\"Index.html\">Log Out</a>\n";
				stringToSendToWebBrowser += "              </li>\n";
				stringToSendToWebBrowser += "            </ul>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";

			}//End If

		}
		else
		{
			stringToSendToWebBrowser += "  <script>\n";	
			stringToSendToWebBrowser += "      function clearLoginCookie()\n";
			stringToSendToWebBrowser += "      {\n";
			stringToSendToWebBrowser += "          document.cookie='userID=;expires=' + new Date(0).toGMTString();\n";
			stringToSendToWebBrowser += "          document.cookie='password=;expires=' + new Date(0).toGMTString();\n";
			stringToSendToWebBrowser += "      }\n";
			stringToSendToWebBrowser += "  </script>\n";
			stringToSendToWebBrowser += "    <div class=\"navbar navbar-default navbar-static-top\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"navbar-header\">\n";
			stringToSendToWebBrowser += "          <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-ex-collapse\">\n";
			stringToSendToWebBrowser += "            <span class=\"sr-only\">Toggle navigation</span>\n";
			stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
			stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
			stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
			stringToSendToWebBrowser += "          </button>\n";
			stringToSendToWebBrowser += "          <a class=\"navbar-brand\" href=\"Index.html\"><span>Hello World</span></a>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <div class=\"collapse navbar-collapse\" id=\"navbar-ex-collapse\">\n";
			stringToSendToWebBrowser += "          <ul class=\"nav navbar-nav navbar-right\"></ul>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <ul class=\"breadcrumb\">\n";
			stringToSendToWebBrowser += "              <li>\n";
			stringToSendToWebBrowser += "                <a href=\"Index.html\">Home</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "              <li>\n";
			stringToSendToWebBrowser += "                <a href=\"Trending.html\">Trending</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "              <li>\n";
			stringToSendToWebBrowser += "                <a href=\"Languages.html\">Languages</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "              <li>\n";
			stringToSendToWebBrowser += "                <a href=\"Login.html\">Log In/Sign Up</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "              <li>\n";
			stringToSendToWebBrowser += "                <a href=\"Search.html\">Search</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "            </ul>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";

		}//End If

		return stringToSendToWebBrowser;

	}//End headerAndNavBarGeneration


	/**
	 * @return The Footer HTML
	 */
	public static String footerGeneration()
	{
		String stringToSendToWebBrowser = "";
		stringToSendToWebBrowser += "    <footer class=\"section section-primary\">\n";
		stringToSendToWebBrowser += "      <div class=\"container\">\n";
		stringToSendToWebBrowser += "        <div class=\"row\">\n";
		stringToSendToWebBrowser += "          <div class=\"col-sm-6\">\n";
		stringToSendToWebBrowser += "            <h1>Hello World</h1>\n";
		stringToSendToWebBrowser += "            <p>Contact Us\n";
		stringToSendToWebBrowser += "              <br>Telephone: 0800 6781 643\n";
		stringToSendToWebBrowser += "              <br>Email: helloworld@helloworld.com\n";
		stringToSendToWebBrowser += "              <br>Hello World LTD,\n";
		stringToSendToWebBrowser += "              <br>University Road, Belfast, BT7 1NN,\n";
		stringToSendToWebBrowser += "              <br>Northern Ireland, United Kingdom</p>\n";
		stringToSendToWebBrowser += "          </div>\n";
		stringToSendToWebBrowser += "          <div class=\"col-sm-6\">\n";
		stringToSendToWebBrowser += "            <p class=\"text-info text-right\">\n";
		stringToSendToWebBrowser += "              <br>\n";
		stringToSendToWebBrowser += "              <br>\n";
		stringToSendToWebBrowser += "            </p>\n";
		stringToSendToWebBrowser += "            <div class=\"row\">\n";
		stringToSendToWebBrowser += "              <div class=\"col-md-12 hidden-lg hidden-md hidden-sm text-left\">\n";
		stringToSendToWebBrowser += "                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-instagram text-inverse\"></i></a>\n";
		stringToSendToWebBrowser += "                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-twitter text-inverse\"></i></a>\n";
		stringToSendToWebBrowser += "                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-facebook text-inverse\"></i></a>\n";
		stringToSendToWebBrowser += "                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-github text-inverse\"></i></a>\n";
		stringToSendToWebBrowser += "              </div>\n";
		stringToSendToWebBrowser += "            </div>\n";
		stringToSendToWebBrowser += "            <div class=\"row\">\n";
		stringToSendToWebBrowser += "              <div class=\"col-md-12 hidden-xs text-right\">\n";
		stringToSendToWebBrowser += "                <a href=\"https://www.instagram.com/?hl=en\"><i class=\"fa fa-3x fa-fw fa-instagram text-inverse\"></i></a>\n";
		stringToSendToWebBrowser += "                <a href=\"https://twitter.com/?lang=en-gb\"><i class=\"fa fa-3x fa-fw fa-twitter text-inverse\"></i></a>\n";
		stringToSendToWebBrowser += "                <a href=\"https://en-gb.facebook.com/\"><i class=\"fa fa-3x fa-fw fa-facebook text-inverse\"></i></a>\n";
		stringToSendToWebBrowser += "              </div>\n";
		stringToSendToWebBrowser += "            </div>\n";
		stringToSendToWebBrowser += "          </div>\n";
		stringToSendToWebBrowser += "        </div>\n";
		stringToSendToWebBrowser += "      </div>\n";
		stringToSendToWebBrowser += "    </footer>\n";

		return stringToSendToWebBrowser;

	}//End footerGeneration


}