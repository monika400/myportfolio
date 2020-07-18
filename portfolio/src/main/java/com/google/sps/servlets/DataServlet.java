// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.ArrayList;
import com.google.gson.Gson;
/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    
    private ArrayList<String> comments = new ArrayList<String>();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
       
         
         
         
         Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        ArrayList<Comment> comments = new ArrayList<Comment>();
        for (Entity entity : results.asIterable()) {
            String email = (String) entity.getProperty("email");
            String commentText = (String) entity.getProperty("commentText");
            long timestamp = (long) entity.getProperty("timestamp");

            Comment currcomment = new Comment(email,commentText, timestamp);
            comments.add(currcomment);
        }

        
        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(comments));

    

    
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;");
        PrintWriter out = response.getWriter();
        UserService userService = UserServiceFactory.getUserService();
        String email = "";
        if (userService.isUserLoggedIn()){
             email = userService.getCurrentUser().getEmail();
        }
        
        
        
        
        String newComment = request.getParameter("new-comment");
        
    
        

        Entity commentEntity = new Entity("Comment");
        commentEntity.setProperty("email",email);
        commentEntity.setProperty("commentText", newComment);
        commentEntity.setProperty("timestamp", System.currentTimeMillis());

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentEntity);
        response.sendRedirect("/about.html");
         
        

        
        



    
        
    }
  
  
}
class Comment {

  private String email; 
  private String commentText;
  private long timestamp;

  public Comment(String email,String commentText, long timestamp) {
    this.email = email;
    this.commentText = commentText;
    this.timestamp = timestamp;
  }
}
