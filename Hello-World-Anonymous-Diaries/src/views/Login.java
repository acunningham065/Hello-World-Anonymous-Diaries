package views;

import java.time.LocalDate;
import java.util.List;

import org.h2.mvstore.MVMap;

import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class Login extends DynamicWebPage {

	public Login(DatabaseInterface db, FileStoreInterface fs) {
		super(db, fs);
	}

	public boolean process(WebRequest toProcess)
	{
		MVMap<String, User> usersStored = db.s.openMap("User");
		List<String> userKeys = usersStored.keyList();


		if(toProcess.path.equalsIgnoreCase("Login.html"))
		{
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<html>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <head>\n";
			stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
			stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
			stringToSendToWebBrowser += "    <title>Log In/Sign Up</title>\n";
			stringToSendToWebBrowser += "    <link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css\">\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <script src=\"https://code.jquery.com/jquery-1.12.4.js\"></script>\n";
			stringToSendToWebBrowser += "    <script src=\"https://code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>\n";
			stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
			stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "    <link href=\"Login2.css\" rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "  </head>\n";
			stringToSendToWebBrowser += "  <script>\n";
			stringToSendToWebBrowser += "    $(function() {\n";
			stringToSendToWebBrowser += "      $(\"#datepicker\").datepicker({ \n";
			stringToSendToWebBrowser += "         changeMonth: true, changeYear: true, minDate: \"-100y\", maxDate: \"-13y\"});\n";
			stringToSendToWebBrowser += "    } );\n";	
			
			stringToSendToWebBrowser += "	 var usernameValid = false;\n";
			stringToSendToWebBrowser += "	 var emailValid = false;\n";
			
			//Password Check Scripts
			
			stringToSendToWebBrowser += "    function checkPassword()\n";
			stringToSendToWebBrowser += "    {";
			stringToSendToWebBrowser += "        var password1 = document.getElementById('password1');\n";
			stringToSendToWebBrowser += "        var password2 = document.getElementById('password2');\n";
			stringToSendToWebBrowser += "        var submitButton = document.getElementById('signUpSubmitButton');\n";
			stringToSendToWebBrowser += "        var matchColor = \"#64FF64\";\n";
			stringToSendToWebBrowser += "        var mismatchColor = \"#FF6464\";\n";
						
			stringToSendToWebBrowser += "        if(password1.value == password2.value)\n";
			stringToSendToWebBrowser += "        {\n";
			stringToSendToWebBrowser += "            password2.style.backgroundColor = matchColor;\n";
     		stringToSendToWebBrowser += "            submitButton.disabled = false;\n";
			stringToSendToWebBrowser += "        }else{\n";
     		stringToSendToWebBrowser += "            password2.style.backgroundColor = mismatchColor;\n";
     		stringToSendToWebBrowser += "            submitButton.disabled = true;\n";
			stringToSendToWebBrowser += "        }\n";
			
			stringToSendToWebBrowser += "        if(password1.value === '')\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            password1.style.backgroundColor = 'rgb(255, 255, 255)'\n";
			stringToSendToWebBrowser += "        }\n";
			
			stringToSendToWebBrowser += "        if(password2.value === '')\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            password2.style.backgroundColor = 'rgb(255, 255, 255)'\n";
			stringToSendToWebBrowser += "        }\n";
			
			stringToSendToWebBrowser += "        if(!usernameValid || !emailValid)\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            submitButton.disabled = true;\n";
			stringToSendToWebBrowser += "        }else{\n";
     		stringToSendToWebBrowser += "            submitButton.disabled = false;\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        if(password1.value.length < 8 || password1.value.length > 16)\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            submitButton.disabled = true;\n";
			stringToSendToWebBrowser += "        }\n";			
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			
			//Username Check Scripts
			
			stringToSendToWebBrowser += "    function asyncUsernameCheck()\n";
			stringToSendToWebBrowser += "    {\n";
			stringToSendToWebBrowser += "        if (window.XMLHttpRequest)\n";
			stringToSendToWebBrowser += "        {// code for IE7+, Firefox, Chrome, Opera, Safari\n";
			stringToSendToWebBrowser += "            xmlhttp=new XMLHttpRequest();\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        else\n";
			stringToSendToWebBrowser += "        {// code for IE6, IE5\n";
			stringToSendToWebBrowser += "            xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        var username = document.getElementById(\"username\").value;\n";
			stringToSendToWebBrowser += "        xmlhttp.onreadystatechange=myUsernameCheckedFunction;\n";
			stringToSendToWebBrowser += "        //This line actually makes the request to the server\n";
			stringToSendToWebBrowser += "        xmlhttp.open(\"GET\",'/Login.html/AsyncUsernameCheck?username='+username,true);\n";
			stringToSendToWebBrowser += "        xmlhttp.send();\n";
			stringToSendToWebBrowser += "    }\n";

			stringToSendToWebBrowser += "	function myUsernameCheckedFunction()\n";
			stringToSendToWebBrowser += "	{\n";
			stringToSendToWebBrowser += "		if (xmlhttp.readyState==4 && xmlhttp.status==200)\n";
			stringToSendToWebBrowser += "		{\n";
			stringToSendToWebBrowser += "	        //This line will set the html between the <input> tags\n";
			stringToSendToWebBrowser += "   	    //with what was returned by the webserver\n";
			stringToSendToWebBrowser += " 		    //this will make the page redraw\n";
			stringToSendToWebBrowser += "           document.getElementById(\"username\").outerHTML = xmlhttp.responseText;\n";
			stringToSendToWebBrowser += "           usernameFieldCheck()\n";
			stringToSendToWebBrowser += "       }\n";
			stringToSendToWebBrowser += "   }\n";
			
			stringToSendToWebBrowser += "    function usernameFieldCheck()\n";
			stringToSendToWebBrowser += "    {\n";		
			stringToSendToWebBrowser += "        if(document.getElementById(\"username\").value === '')\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            document.getElementById(\"username\").style.backgroundColor = 'rgb(255, 255, 255)';\n";
     		stringToSendToWebBrowser += "    		 checkPassword();\n";
			stringToSendToWebBrowser += "        }\n";
			
			stringToSendToWebBrowser += "        if(document.getElementById(\"username\").style.backgroundColor == 'rgb(100, 255, 100)')\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            usernameValid = true;\n";
     		stringToSendToWebBrowser += "            document.getElementById(\"usernameError\").innerHTML = \"\";\n";
     		stringToSendToWebBrowser += "    		 checkPassword();\n";
			stringToSendToWebBrowser += "        }else if(document.getElementById(\"username\").style.backgroundColor == 'rgb(255, 100, 100)'){\n";
     		stringToSendToWebBrowser += "            usernameValid = false;\n";
     		stringToSendToWebBrowser += "            document.getElementById(\"usernameError\").innerHTML = \"Username already exists\";\n";
			stringToSendToWebBrowser += "        }else{\n";
     		stringToSendToWebBrowser += "            usernameValid = false;\n";
     		stringToSendToWebBrowser += "            document.getElementById(\"usernameError\").innerHTML = \"\";\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "    }\n";
						
			//Email Check Scripts
			
			stringToSendToWebBrowser += "    function checkEmail()\n";
			stringToSendToWebBrowser += "    {";
			stringToSendToWebBrowser += "        var email1 = document.getElementById('email1');\n";
			stringToSendToWebBrowser += "        var email2 = document.getElementById('email2');\n";
			stringToSendToWebBrowser += "        var submitButton = document.getElementById('signUpSubmitButton');\n";
			stringToSendToWebBrowser += "        var matchColor = \"#64FF64\";\n";
			stringToSendToWebBrowser += "        var mismatchColor = \"#FF6464\";\n";
			
			stringToSendToWebBrowser += "        if(email1.value == email2.value)\n";
			stringToSendToWebBrowser += "        {\n";
			stringToSendToWebBrowser += "            email2.style.backgroundColor = matchColor;\n";
     		stringToSendToWebBrowser += "            submitButton.disabled = false;\n";
			stringToSendToWebBrowser += "        }else{\n";
     		stringToSendToWebBrowser += "            email2.style.backgroundColor = mismatchColor;\n";
     		stringToSendToWebBrowser += "            submitButton.disabled = true;\n";
			stringToSendToWebBrowser += "        }\n";
			
			
			
			stringToSendToWebBrowser += "        if(email1.value === '')\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            email1.style.backgroundColor = 'rgb(255, 255, 255)';\n";
			stringToSendToWebBrowser += "        }\n";
			
			stringToSendToWebBrowser += "        if(email2.value === '')\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            email2.style.backgroundColor = 'rgb(255, 255, 255)';\n";
			stringToSendToWebBrowser += "        }\n";
			
			stringToSendToWebBrowser += "        if(!usernameValid || !emailValid)\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            submitButton.disabled = true;\n";
			stringToSendToWebBrowser += "        }else{\n";
     		stringToSendToWebBrowser += "            submitButton.disabled = false;\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			
			stringToSendToWebBrowser += "    function asyncEmailCheck()\n";
			stringToSendToWebBrowser += "    {\n";
			stringToSendToWebBrowser += "        if (window.XMLHttpRequest)\n";
			stringToSendToWebBrowser += "        {// code for IE7+, Firefox, Chrome, Opera, Safari\n";
			stringToSendToWebBrowser += "            xmlhttp=new XMLHttpRequest();\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        else\n";
			stringToSendToWebBrowser += "        {// code for IE6, IE5\n";
			stringToSendToWebBrowser += "            xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        var email = document.getElementById(\"email1\").value;\n";
			stringToSendToWebBrowser += "        xmlhttp.onreadystatechange=myEmailCheckedFunction;\n";
			stringToSendToWebBrowser += "        //This line actually makes the request to the server\n";
			stringToSendToWebBrowser += "        xmlhttp.open(\"GET\",'/Login.html/AsyncEmailCheck?email='+email,true);\n";
			stringToSendToWebBrowser += "        xmlhttp.send();\n";
			stringToSendToWebBrowser += "    }\n";

			stringToSendToWebBrowser += "	function myEmailCheckedFunction()\n";
			stringToSendToWebBrowser += "	{\n";
			stringToSendToWebBrowser += "		if (xmlhttp.readyState==4 && xmlhttp.status==200)\n";
			stringToSendToWebBrowser += "		{\n";
			stringToSendToWebBrowser += "	        //This line will set the html between the <input> tags\n";
			stringToSendToWebBrowser += "   	    //with what was returned by the webserver\n";
			stringToSendToWebBrowser += " 		    //this will make the page redraw\n";
			stringToSendToWebBrowser += "           document.getElementById(\"email1\").outerHTML = xmlhttp.responseText;\n";
			stringToSendToWebBrowser += "           emailFieldCheck()\n";
			stringToSendToWebBrowser += "       }\n";
			stringToSendToWebBrowser += "   }\n";
			
			stringToSendToWebBrowser += "    function emailFieldCheck()\n";
			stringToSendToWebBrowser += "    {\n";
			stringToSendToWebBrowser += "        if(document.getElementById(\"email1\").value === '')\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            document.getElementById(\"email1\").style.backgroundColor = 'rgb(255, 255, 255);'\n";
     		stringToSendToWebBrowser += "    		 checkEmail();\n";
     		stringToSendToWebBrowser += "    		 checkPassword();\n"; 
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "        if(document.getElementById(\"email2\").value === '')\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            document.getElementById(\"email2\").style.backgroundColor = 'rgb(255, 255, 255);';\n";
     		stringToSendToWebBrowser += "    		 checkEmail();\n";
     		stringToSendToWebBrowser += "    		 checkPassword();\n"; 
			stringToSendToWebBrowser += "        }\n";
			
			stringToSendToWebBrowser += "        if(document.getElementById(\"email1\").style.backgroundColor == 'rgb(100, 255, 100)')\n";
			stringToSendToWebBrowser += "        {\n";
     		stringToSendToWebBrowser += "            emailValid = true;\n";
     		stringToSendToWebBrowser += "            document.getElementById(\"emailError\").innerHTML = \"\";\n";
     		stringToSendToWebBrowser += "    		 checkEmail();\n";
     		stringToSendToWebBrowser += "    		 checkPassword();\n";     		
			stringToSendToWebBrowser += "        }else if(document.getElementById(\"email1\").style.backgroundColor == 'rgb(255, 100, 100)') {\n";
     		stringToSendToWebBrowser += "            emailValid = false;\n";
     		stringToSendToWebBrowser += "            document.getElementById(\"emailError\").innerHTML = \"Email is already registered\";\n";
			stringToSendToWebBrowser += "        }else{\n";
     		stringToSendToWebBrowser += "            emailValid = false;\n";
     		stringToSendToWebBrowser += "            document.getElementById(\"emailError\").innerHTML = \"\";\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "    }\n";
			

			stringToSendToWebBrowser += "  </script>\n";
			stringToSendToWebBrowser += "  <body>\n";
			stringToSendToWebBrowser += Index.headerAndNavBarGeneration("", "", usersStored);
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "              <h2 class=\"text-primary\">Login</h2>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <form class=\"form-horizontal\" role=\"form\" action = \"LoggingIn\" method = \"GET\">\n";
			stringToSendToWebBrowser += "              <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"loggingEmail\" class=\"control-label\" style=\"text-align:left\">Email</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <input type=\"email\" class=\"form-control\" id=\"loggingInEmail\" name = \"loggingInEmail\" placeholder=\"Email\" required>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"loggingInPassword\" class=\"control-label\" style=\"text-align:left\">Password</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <input type=\"password\" class=\"form-control\" id=\"loggingInPassword\" name = \"loggingInPassword\" placeholder=\"Password\" required>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-offset-2 col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <div class=\"checkbox\">\n";
			stringToSendToWebBrowser += "                    <label>\n";
			stringToSendToWebBrowser += "                      <input type=\"checkbox\">Remember me</label>\n";
			stringToSendToWebBrowser += "                  </div>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-offset-2 col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <button type=\"submit\" class=\"btn btn-default\">Sign in</button>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "            </form>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container-fluid\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12 text-center\">\n";
			stringToSendToWebBrowser += "            <hr>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <h2 class=\"text-primary\">Sign up</h2>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <form class=\"form-horizontal\" role=\"form\" action = \"SigningUp\" method = \"GET\">\n";
			stringToSendToWebBrowser += "              <div class=\"section\">\n";
			stringToSendToWebBrowser += "                <div class=\"container\">\n";
			stringToSendToWebBrowser += "                  <div class=\"row\">\n";
			stringToSendToWebBrowser += "                    <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "                      <div class=\"form-group has-success\" style=\"text-align:left\">\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                          <label for=\"username\" class=\"control-label\">Username</label>\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                          <input type=\"text\" class=\"form-control\" id=\"username\" name = \"username\" onchange=\"asyncUsernameCheck()\"  placeholder=\"Enter Username\" required>\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                          <div class=\"col-sm-2\" style=\"color:red\" id=\"usernameError\"></div>\n";			
			stringToSendToWebBrowser += "                      </div>\n";
			stringToSendToWebBrowser += "                      <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                          <label for=\"email1\" class=\"control-label\" style=\"text-align:left\">Email</label>\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                          <input type=\"email\" class=\"form-control\" id=\"email1\" name = \"email\" onchange = \"asyncEmailCheck()\"  placeholder=\"Email\" required>\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-2\" style=\"color:red\" id=\"emailError\"></div>\n";
			stringToSendToWebBrowser += "                      </div>\n";
			stringToSendToWebBrowser += "                      <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                          <label for=\"email2\" class=\"control-label\" style=\"text-align:left\">Re-type Email</label>\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                          <input type=\"email\" class=\"form-control\" id=\"email2\" onchange = \"checkEmail()\" placeholder=\"Re-enter Email\" required>\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                      </div>\n";
			stringToSendToWebBrowser += "                      <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                          <label for=\"password1\" class=\"control-label\" style=\"text-align:left\">Password</label>\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                          <input type=\"password\" class=\"form-control\" id=\"password1\" name = \"password\" onkeyup = \"checkPassword()\"  placeholder=\"New Password\" required oninvalid=\"this.setCustomValidity('Please enter a password that is 8-16 characters long')\">\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                      </div>\n";
			stringToSendToWebBrowser += "                      <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                          <label for=\"password2\" class=\"control-label\" style=\"text-align:left\">Re-type Password</label>\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                          <input type=\"password\" class=\"form-control\" id=\"password2\" name = \"passwordReType\" placeholder=\"Re-enter password\" onkeyup = \"checkPassword()\" required>\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                      </div>\n";
			stringToSendToWebBrowser += "                      <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                          <label for=\"datepicker\" class=\"control-label\" style=\"text-align:left\">Date Of Birth</label>\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                          <input type=\"text\" class=\"form-control\" id=\"datepicker\" name = \"dateOfBirth\" required>\n";
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                      </div>\n";
			stringToSendToWebBrowser += "                      <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                          <label for=\"country\" class=\"control-label\" style=\"text-align:left\">Country of Residence</label>\n"; 
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                          <select class = \"form-control\" id=\"country\" name = \"countryOfRes\" required> \n";
			stringToSendToWebBrowser += "                              <option value=\"United Kingdom\">United Kingdom</option>\n";
			stringToSendToWebBrowser += "                              <option value=\"Ireland\">Ireland</option>\n";
			stringToSendToWebBrowser += "                              <option value=\"Spain\"> Spain</option>\n";
			stringToSendToWebBrowser += "                              <option value=\"France\"> France</option>\n";
			stringToSendToWebBrowser += "                              <option value=\"Poland\"> Poland</option>\n";
			stringToSendToWebBrowser += "                              <option value =\"Italy\"> Italy</option>\n";
			stringToSendToWebBrowser += "                              <option value =\"Germany\"> Germany</option >\n";
			stringToSendToWebBrowser += "                              <option value =\"Turkey\"> Turkey</option>\n";
			stringToSendToWebBrowser += "                              <option value =\"Portugal\"> Portugal</option>\n";
			stringToSendToWebBrowser += "                              <option value =\"Sweden\"> Sweden</option>\n";
			stringToSendToWebBrowser += "                              <option value =\"Denmark\"> Denmark</option>\n";
			stringToSendToWebBrowser += "                              <option value =\"Norway\"> Norway</option>\n";
			stringToSendToWebBrowser += "                              <option value =\"Canada\"> Canada</option>\n";
			stringToSendToWebBrowser += "                              <option value =\"North America\"> North America</option >\n";
			stringToSendToWebBrowser += "                              <option value =\"South America\"> South America</option>\n";
			stringToSendToWebBrowser += "                              <option value = \"Australia/New Zealand\" > Australia/New Zealand </option>\n"; 
			stringToSendToWebBrowser += "                          </select>\n";			
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                      </div>\n";			
			stringToSendToWebBrowser += "                      <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                          <label for=\"education\" class=\"control-label\" style=\"text-align:left\">Education Level</label>\n"; 
			stringToSendToWebBrowser += "                        </div>\n";
			stringToSendToWebBrowser += "                        <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                          <select class = \"form-control\" id=\"education\" name = \"educationLevel\" required> \n";
			stringToSendToWebBrowser += "                          <option value = \"GCSE\" > GCSE </option >\n";
			stringToSendToWebBrowser += "  						   <option value = \"A Level\" > A Level </option>\n";
			stringToSendToWebBrowser += "                          <option value =\"University\"> University</option>\n";
			stringToSendToWebBrowser += "                          </select>\n";			
			stringToSendToWebBrowser += "                      </div>\n";
			stringToSendToWebBrowser += "                    </div>\n";
			stringToSendToWebBrowser += "                  <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                    <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                      <label for=\"gender\" class=\"control-label\" style=\"text-align:left\">Gender</label>\n";
			stringToSendToWebBrowser += "                    </div>\n";
			stringToSendToWebBrowser += "                    <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                      <select class = \"form-control\" id=\"gender\" name = \"gender\" required> \n";
			stringToSendToWebBrowser += "                        <option value=\"Male\">Male</option>\n";
			stringToSendToWebBrowser += "                        <option value=\"Female\">Female</option>\n";
			stringToSendToWebBrowser += "                        <option value=\"Non-Binary\">Non-Binary</option>\n";
			stringToSendToWebBrowser += "                      </select>\n";
			stringToSendToWebBrowser += "                    </div>\n";
			stringToSendToWebBrowser += "                  </div>\n";
			stringToSendToWebBrowser += "                  <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                    <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                      <label for=\"avatarColour\" class=\"control-label\" style=\"text-align:left\">Avatar Colour</label>\n";
			stringToSendToWebBrowser += "                    </div>\n";
			stringToSendToWebBrowser += "                    <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                      <select class = \"form-control\" id=\"avatarColour\" name = \"avatarColour\" required> \n";
			stringToSendToWebBrowser += "                        <option value=\"default\">Grey</option>\n";
			stringToSendToWebBrowser += "                        <option value=\"blue\">Blue</option>\n";
			stringToSendToWebBrowser += "                        <option value=\"green\">Green</option>\n";
			stringToSendToWebBrowser += "                        <option value=\"pink\">Pink</option>\n";
			stringToSendToWebBrowser += "                        <option value=\"red\">Red</option>\n";
			stringToSendToWebBrowser += "                        <option value=\"yellow\">Yellow</option>\n";
			stringToSendToWebBrowser += "                      </select>\n";
			stringToSendToWebBrowser += "                    </div>\n";
			stringToSendToWebBrowser += "                  </div>\n";
			stringToSendToWebBrowser += "                  <div class=\"form-group\"></div>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"T&C\" class=\"control-label\" style=\"text-align:left\">I agree to the terms and conditions</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                  <input type=\"checkbox\" id=\"T&C\" name=\"T&C\" value=\"Agree\" required>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"subscribe\" class=\"control-label\" style=\"text-align:left\">I want to recieve the weekly newsletter</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-8\">\n";
			stringToSendToWebBrowser += "                  <input type=\"checkbox\" id=\"subscribe\" name=\"subscribe\" value=\"subscription\">\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"col-sm-offset-2 col-sm-8\">\n";
			stringToSendToWebBrowser += "            <button type=\"submit\" class=\"btn btn-default\" id = \"signUpSubmitButton\" disabled=\"true\">Submit</button>\n";
			stringToSendToWebBrowser += "			</form>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </div>\n";

			//FOOTER OF THE WEBSITE
			stringToSendToWebBrowser += Index.footerGeneration();
			stringToSendToWebBrowser += "  </body>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</html>\n";
			toProcess.r = new WebResponse(WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser);
			return true;
		}
		else if(toProcess.path.equalsIgnoreCase("LoggingIn"))
		{
			User userLoggingIn = null;
			boolean userFound = false;
			boolean passwordMatch = false;

			for (String key : userKeys)
			{
				userLoggingIn = usersStored.get(key);

				if (userLoggingIn.getEmailAddress().equals(toProcess.params.get("loggingInEmail")))
				{
					userFound = true;

					if (userLoggingIn.getPassword().equals(toProcess.params.get("loggingInPassword")))
					{
						passwordMatch = true;

						String stringToSendToWebBrowser = "";
						stringToSendToWebBrowser += "<html>\n";
						stringToSendToWebBrowser += "  \n";
						stringToSendToWebBrowser += "  <head>\n";
						stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
						stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
						stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
						stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
						stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
						stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
						stringToSendToWebBrowser += "    <link href=\"Login2.css\" rel=\"stylesheet\" type=\"text/css\">\n";
						stringToSendToWebBrowser += "  </head>\n";
						stringToSendToWebBrowser += "  <script>\n";
						stringToSendToWebBrowser += "      function setCookie(cname, cvalue, exdays)\n";
						stringToSendToWebBrowser += "      {\n";
						stringToSendToWebBrowser += "          var d = new Date();\n";
						stringToSendToWebBrowser += "          d.setTime(d.getTime() + (exdays*24*60*60*1000));\n";
						stringToSendToWebBrowser += "          var expires = 'expires='+d.toUTCString();\n";
						stringToSendToWebBrowser += "          document.cookie = cname + '=' + cvalue + ';' + expires + ';path=/';\n";
						stringToSendToWebBrowser += "      }\n";
						stringToSendToWebBrowser += "      function saveLoginCookie()\n";
						stringToSendToWebBrowser += "      {\n";
						stringToSendToWebBrowser += "          setCookie('userID','" + userLoggingIn.getUniqueID() + "',365);\n";
						stringToSendToWebBrowser += "          setCookie('password','" + userLoggingIn.getPassword() + "',365);\n";
						stringToSendToWebBrowser += "      }\n";						
						stringToSendToWebBrowser += "  </script>\n";
						stringToSendToWebBrowser += "  \n";
						stringToSendToWebBrowser += "  <body onload=\"saveLoginCookie()\">\n";
						stringToSendToWebBrowser += Index.headerAndNavBarGeneration(userLoggingIn.getUniqueID(), userLoggingIn.getPassword(), usersStored);
						stringToSendToWebBrowser += "    <div class=\"section\">\n";
						stringToSendToWebBrowser += "      <div class=\"container\">\n";
						stringToSendToWebBrowser += "        <div class=\"row\">\n";
						stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
						stringToSendToWebBrowser += "            <div class=\"page-header text-danger\">\n";
						stringToSendToWebBrowser += "              <h2 class=\"text-primary\">Thank you for logging in " + userLoggingIn.getUserName() + "</h2>\n";
						stringToSendToWebBrowser += "              <a href = \"Profile.html/" + userLoggingIn.getUniqueID() + "\"><h2 class=\"text-primary\">Link to your profile</h2></a>\n";
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
						
						toProcess.r = new WebResponse(WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser);
						return true;

					}//End If (Password Check)

				}//End If (Email Check)
				
				
				if(!userFound || !passwordMatch)
				{
					String stringToSendToWebBrowser = "";
					stringToSendToWebBrowser += "<html>\n";
					stringToSendToWebBrowser += "  \n";
					stringToSendToWebBrowser += "  <head>\n";
					stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
					stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
					stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
					stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
					stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
					stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
					stringToSendToWebBrowser += "    <link href=\"Login2.css\" rel=\"stylesheet\" type=\"text/css\">\n";
					stringToSendToWebBrowser += "  </head>\n";
					stringToSendToWebBrowser += "  <body>\n";
					stringToSendToWebBrowser += Index.headerAndNavBarGeneration(userLoggingIn.getUniqueID(), userLoggingIn.getPassword(), usersStored);
					stringToSendToWebBrowser += "    <div class=\"section\">\n";
					stringToSendToWebBrowser += "      <div class=\"container\">\n";
					stringToSendToWebBrowser += "        <div class=\"row\">\n";
					stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
					stringToSendToWebBrowser += "            <div class=\"page-header text-danger\">\n";
					stringToSendToWebBrowser += "              <h2 class=\"text-primary\">The email address or password entered is not correct</h2>\n";
					stringToSendToWebBrowser += "              <a href = \"Login.html\"><h2 class=\"text-primary\">Please try again</h2></a>\n";
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
					
					toProcess.r = new WebResponse(WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser);
					return true;
					
				}//End If

			}//End For

		}
		else if(toProcess.path.equalsIgnoreCase("SigningUp"))
		{			
			String username = toProcess.params.get("username"); 
			String userEmail = toProcess.params.get("email"); 
			String userPassword = toProcess.params.get("password");
			String userDOBString = toProcess.params.get("dateOfBirth");
			String countryOfRes = toProcess.params.get("countryOfRes");
			String eduLevel = toProcess.params.get("educationLevel"); 
			String userGender = toProcess.params.get("gender");
			String userAvatarColour = toProcess.params.get("avatarColour");

			//Check if username already exists
			
			
			String[] DOBPartsString = userDOBString.split("/");
			int[] DOBParts = new int[3];
			
			for (int i = 0; i < DOBPartsString.length; i++)
			{				
				DOBParts[i] = Integer.valueOf(DOBPartsString[i]);
			} //End For
			
			LocalDate userDOB = LocalDate.of(DOBParts[2], DOBParts[0], DOBParts[1]);

			User newUser = new User(username, userPassword, userGender, userEmail, userDOB, eduLevel, countryOfRes, userAvatarColour +"-avatar.png");
			usersStored.put(newUser.getUniqueID(), newUser);
			db.commit();

			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<html>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <head>\n";
			stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
			stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <script type=\"text/javascript\" src=\"http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n";
			stringToSendToWebBrowser += "    <link href=\"http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css\"\n";
			stringToSendToWebBrowser += "    rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "    <link href=\"Login2.css\" rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "  </head>\n";
			stringToSendToWebBrowser += "  <script>\n";
			stringToSendToWebBrowser += "      function setCookie(cname, cvalue, exdays)\n";
			stringToSendToWebBrowser += "      {\n";
			stringToSendToWebBrowser += "          var d = new Date();\n";
			stringToSendToWebBrowser += "          d.setTime(d.getTime() + (exdays*24*60*60*1000));\n";
			stringToSendToWebBrowser += "          var expires = 'expires='+d.toUTCString();\n";
			stringToSendToWebBrowser += "          document.cookie = cname + '=' + cvalue + ';' + expires + ';path=/';\n";
			stringToSendToWebBrowser += "      }\n";
			stringToSendToWebBrowser += "      function saveLoginCookie()\n";
			stringToSendToWebBrowser += "      {\n";
			stringToSendToWebBrowser += "          setCookie('userID','" + newUser.getUniqueID() + "',365);\n";
			stringToSendToWebBrowser += "          setCookie('password','" + newUser.getPassword() + "',365);\n";
			stringToSendToWebBrowser += "      }\n";						
			stringToSendToWebBrowser += "  </script>\n";
			stringToSendToWebBrowser += "  <script>\n";	
			stringToSendToWebBrowser += "      function clearLoginCookie()\n";
			stringToSendToWebBrowser += "      {\n";
			stringToSendToWebBrowser += "          document.cookie='userID=;expires=' + new Date(0).toGMTString();\n";
			stringToSendToWebBrowser += "          document.cookie='password=;expires=' + new Date(0).toGMTString();\n";
			stringToSendToWebBrowser += "      }\n";
			stringToSendToWebBrowser += "  </script>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <body onload=\"saveLoginCookie()\">\n";
			stringToSendToWebBrowser += Index.headerAndNavBarGeneration(newUser.getUniqueID(), newUser.getPassword(), usersStored);
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <div class=\"page-header text-danger\">\n";
			stringToSendToWebBrowser += "              <h2 class=\"text-primary\">Thank you for Signing Up " + newUser.getUserName() + "</h2>\n";
			stringToSendToWebBrowser += "              <a href = \"Profile.html/" + newUser.getUniqueID() + "\"><h2 class=\"text-primary\">Link to your profile</h2></a>\n";
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
			toProcess.r = new WebResponse(WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser);
			return true;

		}
		else if(toProcess.path.equalsIgnoreCase("Login.html/AsyncUsernameCheck"))
		{
			String username = toProcess.params.get("username");
			boolean usernameFree = true;
			
			for (String key : userKeys)
			{
				User user = usersStored.get(key);
				
				if(user.getUserName().equals(username))
				{
					usernameFree = false;
					
				}//End If
					
			}//End For
			
			String stringToSendToWebBrowser = "";
			
			if (usernameFree)
			{
				stringToSendToWebBrowser += "                          <input type=\"text\" class=\"form-control\" style=\"background-color:#64FF64\" id=\"username\" name = \"username\" onchange=\"asyncUsernameCheck()\"  placeholder=\"Enter Username\" value=\"" + username + "\" required>\n";
			}
			else
			{
				stringToSendToWebBrowser += "                          <input type=\"text\" class=\"form-control\" style=\"background-color:#FF6464\" id=\"username\" name = \"username\" onchange=\"asyncUsernameCheck()\" placeholder=\"Enter Username\" value=\"" + username + "\" required>\n";
			} //End If
			
			toProcess.r = new WebResponse(WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser);
			return true;
			
		}
		else if(toProcess.path.equalsIgnoreCase("Login.html/AsyncEmailCheck"))
		{
			String email = toProcess.params.get("email");
			boolean emailFree = true;
			
			for (String key : userKeys)
			{
				User user = usersStored.get(key);
				
				if(user.getEmailAddress().equals(email))
				{
					emailFree = false;
					
				}//End If
					
			}//End For
			
			String stringToSendToWebBrowser = "";
			
			if (emailFree)
			{
				stringToSendToWebBrowser += "                          <input type=\"text\" class=\"form-control\" style=\"background-color:#64FF64\" id=\"email1\" name = \"email\" onchange=\"asyncEmailCheck()\"  placeholder=\"Email\" value=\"" + email + "\" required>\n";
			}
			else
			{
				stringToSendToWebBrowser += "                          <input type=\"text\" class=\"form-control\" style=\"background-color:#FF6464\" id=\"email1\" name = \"email\" onchange=\"asyncEmailCheck()\"  placeholder=\"Email\" value=\"" + email + "\" required>\n";
			} //End If
			
			toProcess.r = new WebResponse(WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser);
			return true;
			
		}//End If

		return false;
	}

}
