package com.google.sps.servlets;
import com.google.sps.servlets.UserComment;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
@WebServlet("/login")
public class LoginStatus extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String userURL = "";
    response.setContentType("text/html");
    UserService userService = UserServiceFactory.getUserService();
    boolean loggedin = userService.isUserLoggedIn();
   
    
    if (loggedin) {
      String urlToRedirectToAfterUserLogsOut = "/";
      userURL = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
          
    } else {
        String urlToRedirectToAfterUserLogsIn = "/";
       userURL = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
    }
        UserComment userstatus = new UserComment(loggedin,userURL);
        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(userstatus));
  }
  
}




 