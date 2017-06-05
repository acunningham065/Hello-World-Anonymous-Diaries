package views;

import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class SignUp extends DynamicWebPage
{

	public SignUp(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}

	public boolean process(WebRequest toProcess)
	{
		if(toProcess.path.equalsIgnoreCase("SignUp.html"))
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
			stringToSendToWebBrowser += "    <link href=\"Signin.css\" rel=\"stylesheet\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "  </head>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <body>\n";
			stringToSendToWebBrowser += "    <div class=\"navbar navbar-default navbar-static-top\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"navbar-header\">\n";
			stringToSendToWebBrowser += "          <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-ex-collapse\">\n";
			stringToSendToWebBrowser += "            <span class=\"sr-only\">Toggle navigation</span>\n";
			stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
			stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
			stringToSendToWebBrowser += "            <span class=\"icon-bar\"></span>\n";
			stringToSendToWebBrowser += "          </button>\n";
			stringToSendToWebBrowser += "          <a class=\"navbar-brand\" href=\"#\">Hello World</a>\n";
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
			stringToSendToWebBrowser += "              <li class=\"Login.html\">Login</li>\n";
			stringToSendToWebBrowser += "              <li>\n";
			stringToSendToWebBrowser += "                <a href=\"Search.html\">Search</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "              <li>\n";
			stringToSendToWebBrowser += "                <a href=\"Profile.html\">Profile</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "            </ul>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <h2 contenteditable=\"true\" class=\"text-primary\">Sign Up</h2>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"section\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <form class=\"form-horizontal\" role=\"form\">\n";
			stringToSendToWebBrowser += "              <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"inputFirstName\" class=\"control-label\">First Name</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <input type=\"text\" class=\"form-control\" id=\"inputFirstName\" placeholder=\"First Name\">\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"inputSecondName\" class=\"control-label\">Second Name</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <input type=\"text\" class=\"form-control\" id=\"inputSecondName\" placeholder=\"Second Name\">\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"inputEmail3\" class=\"control-label\">Email</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <input type=\"email\" class=\"form-control\" id=\"inputEmail3\" placeholder=\"Email\">\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"inputPassword3\" class=\"control-label\">Password</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <input type=\"password\" class=\"form-control\" id=\"inputPassword3\" placeholder=\"New Password \">\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"inputPassword4\" class=\"control-label\">Password</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <input type=\"password\" class=\"form-control\" id=\"inputPassword4\" placeholder=\"Re-enter password\">\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group has-success\">\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-2\">\n";
			stringToSendToWebBrowser += "                  <label for=\"inputUsername\" class=\"control-label\">Username</label>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "                <div class=\"col-sm-10\">\n";
			stringToSendToWebBrowser += "                  <input type=\"text\" class=\"form-control\" id=\"inputUsername\" placeholder=\"Enter Username\">\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\"></div>\n";
			stringToSendToWebBrowser += "            </form>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <form action=\"/action_page.php\" method=\"get\">\n";
			stringToSendToWebBrowser += "          <input type=\"checkbox\" name=\"subcribe\" value=\"Agree\">I agree to the terms and conditions\n";
			stringToSendToWebBrowser += "          <br>\n";
			stringToSendToWebBrowser += "          <input type=\"checkbox\" name=\"subcribe\" value=\"Don't agree\">I do not agree to the terms and condtions\n";
			stringToSendToWebBrowser += "          <br>\n";
			stringToSendToWebBrowser += "          <input type=\"checkbox\" name=\"subcribe\" value=\"subcribtion\" checked=\"\">I want to recieve the weekly newsletter</form>\n";
			stringToSendToWebBrowser += "        <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-sm-offset-2 col-sm-10\">\n";
			stringToSendToWebBrowser += "            <button type=\"submit\" class=\"btn btn-default\">Submit</button>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			
			//FOOTER OF THE WEBSITE
			stringToSendToWebBrowser += "    <footer class=\"section section-primary\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-sm-6\">\n";
			stringToSendToWebBrowser += "            <h1>Hello World</h1>\n";
			stringToSendToWebBrowser += "  			<p>Contact Us\n";
			stringToSendToWebBrowser += "              <br>Telephone: 0800 6781 643\n";
			stringToSendToWebBrowser += "              <br>Email: helloworld@helloworld.com \n";
			stringToSendToWebBrowser += "              <br>Hello World LTD,\n";
			stringToSendToWebBrowser += "              <br>University Road, Belfast, BT7 1NN,\n";
			stringToSendToWebBrowser += "              <br>Northern Ireland, United Kingdom\n";
			stringToSendToWebBrowser += " 		      </p>\n";
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
			stringToSendToWebBrowser += "                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-instagram text-inverse\"></i></a>\n";
			stringToSendToWebBrowser += "                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-twitter text-inverse\"></i></a>\n";
			stringToSendToWebBrowser += "                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-facebook text-inverse\"></i></a>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </footer>\n";
			stringToSendToWebBrowser += "  </body>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</html>\n";
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;
		}
		return false;
	}

}
