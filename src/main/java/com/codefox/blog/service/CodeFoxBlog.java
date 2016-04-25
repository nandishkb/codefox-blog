package com.codefox.blog.service;

import java.util.List;

import com.codefox.blog.api.Answer;
import com.codefox.blog.api.BlogException;
import com.codefox.blog.api.Question;
import com.codefox.blog.api.User;
import com.codefox.blog.api.WebBlog;
import com.codefox.blog.data.BlogDAO;

public class CodeFoxBlog implements WebBlog {

    public CodeFoxBlog() {
    }
    
    private BlogDAO dao = BlogDAO.getInstance();

    @Override
    public void addQuestion(Question question) throws BlogException {
    	User user = null;
    	if (question.getUser() == null) {
    		user = dao.getUser(question.getUserId());
    		question.setUser(user);
    	}
        dao.addQuestion(question);
    }

    @Override
    public List<Question> findQuestions(String searchString) {
        return dao.findQuestions(searchString);
    }

    @Override
    public List<Question> listQuestions() {
        return dao.listQuestions();
    }

    @Override
    public void addUser(User user) {
        if (user == null || user.getEmail().equals("") || user.getEmail() == null ||
                user.getPhoneNumber() <= 0 || user.getPassword() == null || user.getPassword().length() < 6) {
            throw new BlogException("Invalid User Data");
        }
        if (dao.getUser(user.getEmail()) != null) {
            throw new BlogException("Duplicate User");
        }
        dao.addUser(user);
    }

    @Override
    public User getUser(String email) {
        return dao.getUser(email);
    }

    @Override
    public List<User> listUsers() {
        return dao.listUsers();
    }

    @Override
    public void addAnswer(Answer answer) {
    	User user = null;
    	if (answer.getUser() == null) {
			user = dao.getUser(answer.getUserId());
    		answer.setUser(user);
    	}
    	if (answer.getQuestion() == null) {
    		Question question = dao.getQuestion(answer.getQuestionId());
    		answer.setQuestion(question);
    	}
    	System.out.println("CodeFoxBlog.addAnswer() "+answer);
        dao.addAnswer(answer);
    }

    @Override
    public List<Question> findQuestions(int userId) {
        return dao.findQuestions(userId);
    }

    
    @Override
    public List<Answer> findAnswers(int questionId) {
        return dao.findAnswers(questionId);
    }

}
