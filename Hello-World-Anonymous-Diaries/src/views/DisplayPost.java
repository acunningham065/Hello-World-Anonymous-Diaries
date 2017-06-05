package views;

import java.util.List;
import java.util.regex.Pattern;

import org.h2.mvstore.MVMap;

import model.Comment;
import model.Post;
import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class DisplayPost extends DynamicWebPage
{

	public DisplayPost(DatabaseInterface db,FileStoreInterface fs)
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

		if(toProcess.path.equalsIgnoreCase("Post.html"))
		{
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<html>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <head>\n";
			stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
			stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
			stringToSendToWebBrowser += "    <title>Post</title>\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
			stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "    <link href=\"Post.css\" rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "  </head>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <script>\n";	
			stringToSendToWebBrowser += "      function clearLoginCookie()\n";
			stringToSendToWebBrowser += "      {\n";
			stringToSendToWebBrowser += "          document.cookie='userID=;expires=' + new Date(0).toGMTString();\n";
			stringToSendToWebBrowser += "          document.cookie='password=;expires=' + new Date(0).toGMTString();\n";
			stringToSendToWebBrowser += "      }\n";
			stringToSendToWebBrowser += "  </script>\n";
			stringToSendToWebBrowser += "  <body>\n";
			stringToSendToWebBrowser += Index.headerAndNavBarGeneration(userUniqueID, password, usersStored);
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "			<h1>This page is only accesible via typing in the URL. If you are looking for a specific post try clicking on the title</h1>\n";
			stringToSendToWebBrowser += "           <a href = \"Index.html\"><h2>Click here to return to the Home Page</h2>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "          <hr>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";			
			//FOOTER OF THE WEBSITE
			stringToSendToWebBrowser += Index.footerGeneration();
			stringToSendToWebBrowser += "  </body>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</html>\n";

			toProcess.r = new WebResponse(WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser);

			return true;

		}
		else if(toProcess.path.startsWith("Post.html/") && Pattern.matches("\\d{13,19}", toProcess.path.substring(10)))
		{			
			String postUniqueID = toProcess.path.substring(10);
			Post post = null;		

			for (String key : postKeys) 
			{
				if (key.equals(postUniqueID)) 
				{
					post = postsStored.get(postUniqueID);			
				}//End If
			}//End For


			if (post != null)
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
						stringToSendToWebBrowser += "  <base href=\"//localhost:8080/\"/>";
						stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
						stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
						stringToSendToWebBrowser += "    <title>Session Error</title>\n";
						stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
						stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
						stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
						stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
						stringToSendToWebBrowser += "    <link href=\"Post.css\" rel=\"stylesheet\" type=\"text/css\">\n";				
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

					}//End If

				}//End If

				String stringToSendToWebBrowser = "";
				stringToSendToWebBrowser += "<html>\n";
				stringToSendToWebBrowser += "  <base href=\"//localhost:8080/\"/>\n";
				stringToSendToWebBrowser += "  <head>\n";
				stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
				stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
				stringToSendToWebBrowser += "    <title>"+ post.getTitle() +"</title>\n";
				stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
				stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
				stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
				stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
				stringToSendToWebBrowser += "    <link href=\"Post.css\" rel=\"stylesheet\" type=\"text/css\">\n";
				stringToSendToWebBrowser += "  </head>\n";
				stringToSendToWebBrowser += "  \n";
				stringToSendToWebBrowser += "  <script>\n";	

				stringToSendToWebBrowser += "      function clearLoginCookie()\n";
				stringToSendToWebBrowser += "      {\n";
				stringToSendToWebBrowser += "          document.cookie='userID=;expires=' + new Date(0).toGMTString();\n";
				stringToSendToWebBrowser += "          document.cookie='password=;expires=' + new Date(0).toGMTString();\n";
				stringToSendToWebBrowser += "      }\n";

				//General Async Load				

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
				stringToSendToWebBrowser += "        var comment = document.getElementById(\"comment\").value;\n";
				stringToSendToWebBrowser += "        var postID = document.getElementById(\"postID\").value;\n";
				stringToSendToWebBrowser += "        xmlhttp.onreadystatechange=myDataLoadedFunction;\n";
				stringToSendToWebBrowser += "        //This line actually makes the request to the server\n";
				stringToSendToWebBrowser += "        xmlhttp.open(\"GET\",'/Post.html/Async?comment='+comment+'&postID='+postID,true);\n";
				stringToSendToWebBrowser += "        xmlhttp.send();\n";
				stringToSendToWebBrowser += "    }\n";

				stringToSendToWebBrowser += "	function myDataLoadedFunction()\n";
				stringToSendToWebBrowser += "	{\n";
				stringToSendToWebBrowser += "		if (xmlhttp.readyState==4 && xmlhttp.status==200)\n";
				stringToSendToWebBrowser += "		{\n";
				stringToSendToWebBrowser += "		    clearForm();\n";
				stringToSendToWebBrowser += "	        //This line will set the html between the <body> tags\n";
				stringToSendToWebBrowser += "   	    //with what was returned by the webserver\n";
				stringToSendToWebBrowser += " 		    //this will make the page redraw\n";
				stringToSendToWebBrowser += "           document.getElementById(\"commentsdiv\").innerHTML = xmlhttp.responseText;\n";
				stringToSendToWebBrowser += "       }\n";
				stringToSendToWebBrowser += "   }\n";

				//Upvote/Downvote Async Load

				stringToSendToWebBrowser += "    var postID;\n";
				stringToSendToWebBrowser += "    var commentID;\n";
				stringToSendToWebBrowser += "    function asyncLoadVote(postIDParam, commentIDParam, vote)\n";
				stringToSendToWebBrowser += "    {\n";
				stringToSendToWebBrowser += "        commentID = commentIDParam\n";
				stringToSendToWebBrowser += "        if (window.XMLHttpRequest)\n";
				stringToSendToWebBrowser += "        {// code for IE7+, Firefox, Chrome, Opera, Safari\n";
				stringToSendToWebBrowser += "            xmlhttp=new XMLHttpRequest();\n";
				stringToSendToWebBrowser += "        }\n";
				stringToSendToWebBrowser += "            else\n";
				stringToSendToWebBrowser += "        {// code for IE6, IE5\n";
				stringToSendToWebBrowser += "            xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");\n";
				stringToSendToWebBrowser += "        }\n";				
				stringToSendToWebBrowser += "        //This line actually makes the request to the server\n";
				stringToSendToWebBrowser += "        xmlhttp.onreadystatechange=myVoteDataLoadedFunction;\n";
				stringToSendToWebBrowser += "        xmlhttp.open(\"GET\",'/Post.html/AsyncVote?postID='+postIDParam+'&commentID='+commentID+'&vote='+vote,true);\n";
				stringToSendToWebBrowser += "        xmlhttp.send();\n";
				stringToSendToWebBrowser += "    }\n";

				stringToSendToWebBrowser += "    function myVoteDataLoadedFunction()\n";
				stringToSendToWebBrowser += "    {\n";
				stringToSendToWebBrowser += "      if (xmlhttp.readyState==4 && xmlhttp.status==200)\n";
				stringToSendToWebBrowser += "      {\n";
				stringToSendToWebBrowser += "        //This line will set the html between the <body> tags\n";
				stringToSendToWebBrowser += "        //with what was returned by the webserver\n";
				stringToSendToWebBrowser += "        //this will make the page redraw\n";
				stringToSendToWebBrowser += "         document.getElementById(\"comment\" + commentID + \"votediv\").innerHTML = xmlhttp.responseText;\n";
				stringToSendToWebBrowser += "      }\n";
				stringToSendToWebBrowser += "    }\n";

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
				stringToSendToWebBrowser += "        xmlhttp.open(\"GET\",'/Post.html/AsyncEmoticon?postID='+postID+'&happyVotes='+happyVotes+'&sadVotes='+sadVotes+'&angryVotes='+angryVotes+'&laughingVotes='+laughingVotes+'&loveVotes='+loveVotes,true);\n";
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

				//Delete Async Load				

				stringToSendToWebBrowser += "    function asyncDelete(commentID, postID)\n";
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
				stringToSendToWebBrowser += "        xmlhttp.open(\"GET\",'/Post.html/AsyncDelete?commentID='+commentID+'&postID='+postID,true);\n";
				stringToSendToWebBrowser += "        xmlhttp.send();\n";
				stringToSendToWebBrowser += "    }\n";

				stringToSendToWebBrowser += "    function alertReport()\n";
				stringToSendToWebBrowser += "    {\n";
				stringToSendToWebBrowser += "        alert('This comment has been reported and will be reviewed by staff');\n";
				stringToSendToWebBrowser += "    }\n";
				
				stringToSendToWebBrowser += "    function clearForm()\n";
				stringToSendToWebBrowser += "    {\n";
				stringToSendToWebBrowser += "        document.getElementById(\"commentForm\").reset()";
				stringToSendToWebBrowser += "    }\n";

				stringToSendToWebBrowser += "  </script>\n";
				stringToSendToWebBrowser += "  <body>\n";

				stringToSendToWebBrowser += Index.headerAndNavBarGeneration(userUniqueID, password, usersStored);


				if(userUniqueID != null)
				{
					stringToSendToWebBrowser += Index.postPublishingGeneration(post, true);

				}
				else
				{
					stringToSendToWebBrowser += Index.postPublishingGeneration(post, false);

				}//End If

				stringToSendToWebBrowser += "    <div class=\"container\">\n";
				stringToSendToWebBrowser += "      <div id=\"commentsdiv\">\n";


				for (Comment comment : post.getComments()) 
				{
					stringToSendToWebBrowser += commentPublishingGeneration(userUniqueID, comment, post.getUniquePostID());

				}//End For

				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";
				stringToSendToWebBrowser += "      <div class=\"container\">\n";
				stringToSendToWebBrowser += "        <div class=\"row\">\n";
				stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
				stringToSendToWebBrowser += "            <div class=\"panel panel-primary\">\n";
				stringToSendToWebBrowser += "              <div class=\"panel-heading\">\n";
				stringToSendToWebBrowser += "                <h3 class=\"panel-title\">Comment</h3>\n";
				stringToSendToWebBrowser += "              </div>\n";
				stringToSendToWebBrowser += "              <div class=\"panel-body\">\n";

				if (userUniqueID != null)
				{					
					stringToSendToWebBrowser += "                <form class=\"form-horizontal\" role=\"form\" action = \"AddComment\" method = \"GET\" id = \"commentForm\">\n";
					stringToSendToWebBrowser += "                  <div class=\"form-group\">\n";
					stringToSendToWebBrowser += "                    <div class=\"col-sm-12\">\n";
					stringToSendToWebBrowser += "                      <input type=\"text\" class=\"form-control\" id=\"comment\" name = \"comment\" placeholder=\"Enter Comment\" required>\n";
					stringToSendToWebBrowser += "					   <input type=\"hidden\" id=\"postID\" name=\"postID\" value=\"" + post.getUniquePostID() + "\">";
					stringToSendToWebBrowser += "                    </div>\n";
					stringToSendToWebBrowser += "                  </div>\n";
					stringToSendToWebBrowser += "                  <div class=\"form-group\">\n";
					stringToSendToWebBrowser += "                    <div class=\"col-sm-12\">\n";
					stringToSendToWebBrowser += "                      <button type=\"submit\" class=\"btn btn-block btn-default\" onclick=\"asyncLoad(); return false;\">Submit</button>\n";
					stringToSendToWebBrowser += "                    </div>\n";
					stringToSendToWebBrowser += "                  </div>\n";
					stringToSendToWebBrowser += "                </div>\n";
					stringToSendToWebBrowser += "                </form>\n";

				}
				else //If user is not logged in provide link to sign up / log in page
				{
					stringToSendToWebBrowser += "                  <a href = \"Login.html\"><p>You must be logged in to comment on posts</p></a>\n";
				}//End If

				stringToSendToWebBrowser += "              </div>\n";
				stringToSendToWebBrowser += "            </div>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";

				//FOOTER OF THE WEBSITE
				stringToSendToWebBrowser += Index.footerGeneration();
				stringToSendToWebBrowser += "  </body>\n";
				stringToSendToWebBrowser += "\n";
				stringToSendToWebBrowser += "</html>\n";
				toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser);
				return true;

			}//End If

		}
		else if(toProcess.path.equalsIgnoreCase("Post.html/Async"))
		{			
			String commentText = toProcess.params.get("comment");
			String postID = toProcess.params.get("postID");
			Post post = postsStored.get(postID);

			post.getComments().add(new Comment(usersStored.get(userUniqueID), commentText));
			postsStored.put(postID, post);
			db.commit();

			String stringToSendToWebBrowser = "";

			for (Comment comment : post.getComments()) 
			{
				stringToSendToWebBrowser += commentPublishingGeneration(userUniqueID, comment, post.getUniquePostID());

			}//End For


			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;

		}
		else if(toProcess.path.equalsIgnoreCase("Post.html/AsyncVote"))
		{			
			boolean vote = Boolean.parseBoolean(toProcess.params.get("vote"));
			String postID = toProcess.params.get("postID");
			String commentID = toProcess.params.get("commentID");
			Post post = postsStored.get(postID);
			Comment comment = null;

			for (Comment commentStored : post.getComments())
			{
				if (commentStored.getCommentID().equals(commentID))
				{
					comment = commentStored;
				} //End If

			}//End For

			String stringToSendToWebBrowser = "";

			if (!comment.getUserUpVoted().contains(userUniqueID) && !comment.getUserDownVoted().contains(userUniqueID))
			{
				if (vote)
				{
					comment.setUpvote(comment.getUpvote() + 1);
					comment.getUserUpVoted().add(userUniqueID);
				}
				else
				{
					comment.setDownvote(comment.getDownvote() + 1);
					comment.getUserDownVoted().add(userUniqueID);

				}//End If				

			}
			else
			{
				if (comment.getUserUpVoted().contains(userUniqueID) && !vote)
				{
					comment.setUpvote(comment.getUpvote() - 1);
					comment.getUserUpVoted().remove(userUniqueID);
					comment.setDownvote(comment.getDownvote() + 1);
					comment.getUserDownVoted().add(userUniqueID);
				}
				else if (comment.getUserUpVoted().contains(userUniqueID) && vote)
				{
					comment.setUpvote(comment.getUpvote() - 1);
					comment.getUserUpVoted().remove(userUniqueID);
				}
				else if (comment.getUserDownVoted().contains(userUniqueID) && vote)
				{					
					comment.setDownvote(comment.getDownvote() - 1);
					comment.getUserDownVoted().remove(userUniqueID);
					comment.setUpvote(comment.getUpvote() + 1);
					comment.getUserUpVoted().add(userUniqueID);
				}
				else if (comment.getUserDownVoted().contains(userUniqueID) && !vote)
				{
					comment.setDownvote(comment.getDownvote() - 1);
					comment.getUserDownVoted().remove(userUniqueID);

				}//End If

			}//End If

			postsStored.put(postID, post);			
			db.commit();

			if (comment.getUserUpVoted().contains(userUniqueID))
			{
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                  <i class=\"fa fa-arrow-up fa-fw fa-lg\" style=\"color:#57B38A\" onclick = \"asyncLoadVote(" + postID + ", " + commentID + ", " + true + ")\"></i>\n";
				stringToSendToWebBrowser += "                </div>\n";
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";

				if (comment.getUpvote() - comment.getDownvote() > 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#57B38A\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else if (comment.getUpvote() - comment.getDownvote() < 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#EF4040\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else 
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#707070\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";									
				}//End If

				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "                 <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                  <i class=\"fa fa-arrow-down fa-fw fa-lg\"style=\"color:grey\" onclick = \"asyncLoadVote(" + postID + ", " + commentID + ", " + false + ")\"></i>\n";
				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "               </div>\n";
				stringToSendToWebBrowser += "            </div>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";	
				stringToSendToWebBrowser += "    </div>\n";

			}
			else if (comment.getUserDownVoted().contains(userUniqueID))
			{
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                  <i class=\"fa fa-arrow-up fa-fw fa-lg\" style=\"color:grey\" onclick = \"asyncLoadVote(" + postID + ", " + commentID + ", " + true + ")\"></i>\n";
				stringToSendToWebBrowser += "                </div>\n";
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";

				if (comment.getUpvote() - comment.getDownvote() > 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#57B38A\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else if (comment.getUpvote() - comment.getDownvote() < 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#EF4040\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else 
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#707070\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";									
				}//End If

				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "                 <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                   <i class=\"fa fa-arrow-down fa-fw fa-lg\"style=\"color:#EF4040\"  onclick = \"asyncLoadVote(" + postID + ", " + commentID + ", " + false + ")\"></i>\n";
				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "               </div>\n";
				stringToSendToWebBrowser += "            </div>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";

			}
			else
			{
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                  <i class=\"fa fa-arrow-up fa-fw fa-lg\" style=\"color:grey\" onclick = \"asyncLoadVote(" + postID + ", " + commentID + ", " + true + ")\"></i>\n";
				stringToSendToWebBrowser += "                </div>\n";
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";

				if (comment.getUpvote() - comment.getDownvote() > 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#57B38A\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else if (comment.getUpvote() - comment.getDownvote() < 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#EF4040\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else 
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#707070\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";									
				}//End If

				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "                 <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                   <i class=\"fa fa-arrow-down fa-fw fa-lg\"style=\"color:grey\"  onclick = \"asyncLoadVote(" + postID + ", " + commentID + ", " + false + ")\"></i>\n";
				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "               </div>\n";
				stringToSendToWebBrowser += "            </div>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";

			}//End If

			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser);
			return true;

		}
		else if(toProcess.path.equalsIgnoreCase("Post.html/AsyncEmoticon"))
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
		else if(toProcess.path.equalsIgnoreCase("Post.html/AsyncEdit"))
		{
			
			
			
		}
		else if(toProcess.path.equalsIgnoreCase("Post.html/AsyncDelete"))
		{
			String commentID = toProcess.params.get("commentID");
			String postID = toProcess.params.get("postID");
			Post post = postsStored.get(postID);
			Comment comment = null;
			
			for (Comment commentStored : post.getComments())
			{
				if (commentStored.getCommentID().equals(commentID))
				{
					comment = commentStored;
				} //End If

			}//End For
			
			if (userUniqueID != null)
			{
				
				if (userUniqueID.equals(comment.getAuthor().getUniqueID()))
				{
					
					post.getComments().remove(comment);
					postsStored.put(postID, post);			
					db.commit();
					System.out.println("Author delete success");

				}
				else
				{
					System.out.println("Non-author delete attempt");
					
				}//End If

			}
			else
			{
				System.out.println("Anon delete attempt");
				
			}//End If
			
			
			
			
			String stringToSendToWebBrowser = "";

			for (Comment commentStored : post.getComments()) 
			{
				stringToSendToWebBrowser += commentPublishingGeneration(userUniqueID, commentStored, post.getUniquePostID());

			}//End For
			
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;
			
		}//End If

		return false;

	}//End process


	public String commentPublishingGeneration(String userUniqueID, Comment comment, String postID)
	{
		String stringToSendToWebBrowser = "";

		stringToSendToWebBrowser += "        <div class=\"row\">\n";
		stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
		stringToSendToWebBrowser += "            <div class=\"panel panel-primary\">\n";
		stringToSendToWebBrowser += "              <div class=\"panel-heading\">\n";
		stringToSendToWebBrowser += "                <div class=\"col-md-11\">\n";
		stringToSendToWebBrowser += "                  <h3 class=\"panel-title\">"+ comment.getAuthor().getUserName() + "</h3>\n";			
		stringToSendToWebBrowser += "                </div>\n";
		stringToSendToWebBrowser += "                <div class=\"btn-group btn-group-sm\">\n";
		stringToSendToWebBrowser += "                  <a class=\"btn btn-primary dropdown-toggle\" data-toggle=\"dropdown\"> Options <span class=\"fa fa-caret-down\"></span></a>\n";
		stringToSendToWebBrowser += "                  <ul class=\"dropdown-menu\" role=\"menu\">\n";
		stringToSendToWebBrowser += "                    <li onclick=\"asyncEdit(" + comment.getCommentID() + ", " + postID + ")\" style=\"color:black; padding: 4px 8px; cursor: pointer;\">Edit</li>\n";
		stringToSendToWebBrowser += "                    <li onclick=\"asyncDelete(" + comment.getCommentID() + ", " + postID + ")\" style=\"color:black; padding: 4px 8px; cursor: pointer;\">Delete</li>\n";
		stringToSendToWebBrowser += "                    <li onclick=\"alertReport()\" style=\"color:black; padding: 4px 8px; cursor: pointer;\">Report</li>\n";
		stringToSendToWebBrowser += "                  </ul>\n";
		stringToSendToWebBrowser += "                </div>\n";
		stringToSendToWebBrowser += "              </div>\n";

		if (comment.getUpvote() - comment.getDownvote() >= -5)
		{						
			stringToSendToWebBrowser += "              <div class=\"panel-body\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-md-11\">\n";
			stringToSendToWebBrowser += "                  <p>" + comment.getText() + "</p>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "                <div id = \"comment" + comment.getCommentID() + "votediv\">\n";

		}
		else
		{			
			stringToSendToWebBrowser += "              <div class=\"panel-body\">\n";
			stringToSendToWebBrowser += "                <a data-toggle=\"collapse\" href=\"#" + comment.getCommentID() + "\"><p class=\"col-md-11\">Comment Hidden due to low score. Click to reveal</p></a>\n";
			stringToSendToWebBrowser += "                <div id=\"" + comment.getCommentID() + "\" class=\"panel-collapse collapse panel-body\">\n";
			stringToSendToWebBrowser += "                  <p>" + comment.getText() + "</p>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              <div class=\"col-md-1\">\n";
			stringToSendToWebBrowser += "                <div id = \"comment" + comment.getCommentID() + "votediv\">\n";

		}//End If

		//null indicates that the user hasn't logged in
		if(userUniqueID != null)
		{
			if (comment.getUserUpVoted().contains(userUniqueID))
			{
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                  <i class=\"fa fa-arrow-up fa-fw fa-lg\" style=\"color:#57B38A\" onclick = \"asyncLoadVote(" + postID + ", " + comment.getCommentID() + ", " + true + ")\"></i>\n";
				stringToSendToWebBrowser += "                </div>\n";
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";

				if (comment.getUpvote() - comment.getDownvote() > 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#57B38A\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else if (comment.getUpvote() - comment.getDownvote() < 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#EF4040\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else 
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#707070\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";									
				}//End If

				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "                 <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                  <i class=\"fa fa-arrow-down fa-fw fa-lg\"style=\"color:grey\" onclick = \"asyncLoadVote(" + postID + ", " + comment.getCommentID() + ", " + false + ")\"></i>\n";
				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "               </div>\n";
				stringToSendToWebBrowser += "            </div>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";	
				stringToSendToWebBrowser += "    </div>\n";

			}
			else if (comment.getUserDownVoted().contains(userUniqueID))
			{
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                  <i class=\"fa fa-arrow-up fa-fw fa-lg\" style=\"color:grey\" onclick = \"asyncLoadVote(" + postID + ", " + comment.getCommentID() + ", " + true + ")\"></i>\n";
				stringToSendToWebBrowser += "                </div>\n";
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";

				if (comment.getUpvote() - comment.getDownvote() > 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#57B38A\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else if (comment.getUpvote() - comment.getDownvote() < 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#EF4040\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else 
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#707070\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";									
				}//End If

				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "                 <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                   <i class=\"fa fa-arrow-down fa-fw fa-lg\"style=\"color:#EF4040\"  onclick = \"asyncLoadVote(" + postID + ", " + comment.getCommentID() + ", " + false + ")\"></i>\n";
				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "               </div>\n";
				stringToSendToWebBrowser += "            </div>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";

			}
			else
			{
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                  <i class=\"fa fa-arrow-up fa-fw fa-lg\" style=\"color:grey\"  onclick = \"asyncLoadVote(" + postID + ", " + comment.getCommentID() + ", " + true + ")\"></i>\n";
				stringToSendToWebBrowser += "                </div>\n";
				stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";

				if (comment.getUpvote() - comment.getDownvote() > 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#57B38A\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else if (comment.getUpvote() - comment.getDownvote() < 0)
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#EF4040\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
				}
				else 
				{
					stringToSendToWebBrowser += "                 <p style=\"color:#707070\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";									
				}//End If

				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "                 <div class=\"row text-center\">\n";
				stringToSendToWebBrowser += "                  <i class=\"fa fa-arrow-down fa-fw fa-lg\"style=\"color:grey\" onclick = \"asyncLoadVote(" + postID + ", " + comment.getCommentID() + ", " + false + ")\"></i>\n";
				stringToSendToWebBrowser += "                 </div>\n";
				stringToSendToWebBrowser += "               </div>\n";
				stringToSendToWebBrowser += "            </div>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";

			}//End If

		}
		else
		{
			stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";
			stringToSendToWebBrowser += "                  <i class=\"fa fa-arrow-up fa-fw fa-lg\" style=\"color:grey\" onclick = \"asyncLoadVote(" + postID + ", " + comment.getCommentID() + ", " + true + ")\"></i>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"row text-center\">\n";

			if (comment.getUpvote() - comment.getDownvote() > 0)
			{
				stringToSendToWebBrowser += "                 <p style=\"color:#57B38A\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
			}
			else if (comment.getUpvote() - comment.getDownvote() < 0)
			{
				stringToSendToWebBrowser += "                 <p style=\"color:#EF4040\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";
			}
			else 
			{
				stringToSendToWebBrowser += "                 <p style=\"color:#707070\"><strong>" + (comment.getUpvote() - comment.getDownvote()) + "</strong></p>\n";									
			}//End If

			stringToSendToWebBrowser += "                 </div>\n";
			stringToSendToWebBrowser += "                 <div class=\"row text-center\">\n";
			stringToSendToWebBrowser += "                  <i class=\"fa fa-arrow-down fa-fw fa-lg\"style=\"color:grey\"  onclick = \"asyncLoadVote(" + postID + ", " + comment.getCommentID() + ", " + false + ")\"></i>\n";
			stringToSendToWebBrowser += "                 </div>\n";
			stringToSendToWebBrowser += "               </div>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";

		}//End If

		return stringToSendToWebBrowser;

	}//End commentGeneration

}
