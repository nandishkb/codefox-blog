package com.codefox.blog.rs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codefox.blog.api.Answer;
import com.codefox.blog.api.Question;
import com.codefox.blog.api.User;
import com.codefox.blog.api.WebBlog;

@Path("/techspace")
public class BlogController {

    private WebBlog blog = null;

    public BlogController() {
        
    }
    
    private WebBlog getBlog() {
        if (blog == null) {
            blog = BlogFactory.getBlog();
        }
        return blog;
    }
    
    @GET
    @Path("/user/login")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response login(@Context HttpServletRequest request) {
    	String userName = (String) request.getSession().getAttribute("user");
    	System.out.println("BlogController.login() login successful");
    	User user = getBlog().getUser(userName);
    	return Response.ok().entity(user).build();
    }
    
    
    @PUT
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addUser(User user) {
        System.out.println("BlogController.addUser()" + user);
        getBlog().addUser(user);
        System.out.println("BlogController.addUser() success");
        return Response.ok().build();
    }
    
    @GET
    @Path("/user/{email}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUser(@PathParam("email") String email) {
        User user = getBlog().getUser(email);
        return Response.ok().entity(user).build();
    }
    
    @GET
    @Path("/user")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listUsers() {
        List<User> userList = getBlog().listUsers();
        GenericEntity<List<User>> listEntity = new GenericEntity<List<User>>(userList) {};
        return Response.ok().entity(listEntity).build();
    }
    
    @PUT
    @Path("/question")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addQuestion(Question question) {
        System.out.println("BlogController.addQuestion()");
        getBlog().addQuestion(question);
        System.out.println("BlogController.addQuestion() success");
        return Response.ok().build();
    }
    
    @GET
    @Path("/question/{searchString}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findQuestions(@PathParam("searchString") String searchString) {
        List<Question> listOfQuestions = getBlog().findQuestions(searchString);
        GenericEntity<List<Question>> genQuestionList = new GenericEntity<List<Question>>(listOfQuestions) {};
        return Response.ok().entity(genQuestionList).build();
    }
    
    @GET
    @Path("/question/user/{userId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findQuestions(@PathParam("userId") int userId) {
        List<Question> listOfQuestions = getBlog().findQuestions(userId);
        GenericEntity<List<Question>> genQuestionList = new GenericEntity<List<Question>>(listOfQuestions) {};
        return Response.ok().entity(genQuestionList).build();
    }
    
    @GET
    @Path("/question")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listQuestions() {
        List<Question> listOfQuestions = getBlog().listQuestions();
        GenericEntity<List<Question>> genQuestionList = new GenericEntity<List<Question>>(listOfQuestions) {};
        return Response.ok().entity(genQuestionList).build();
    }
    
    @PUT
    @Path("/answer")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addAnswer(Answer answer) {
        System.out.println("BlogController.addAnswer()");
        getBlog().addAnswer(answer);
        System.out.println("BlogController.addAnswer() success");
        return Response.ok().build();
    }
    
    @GET
    @Path("/answer/{questionId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listAnswers(@PathParam("questionId") int questionId) {
        List<Answer> listOfAnswers = getBlog().findAnswers(questionId);
        GenericEntity<List<Answer>> genAnswerList = new GenericEntity<List<Answer>>(listOfAnswers) {};
        return Response.ok().entity(genAnswerList).build();
    }
    
    @GET
    @Path("/user/logout")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response logout(@Context HttpServletRequest request) {
    	request.getSession().removeAttribute("user");
    	System.out.println("BlogController.logout() logout successful");
    	return Response.ok().build();
    }
}
