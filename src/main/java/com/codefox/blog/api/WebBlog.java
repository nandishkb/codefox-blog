package com.codefox.blog.api;

import java.util.List;

public interface WebBlog {
    
    public void addUser(User user);

    public User getUser(String email);

    public List<User> listUsers();

    public void addQuestion(Question question) throws BlogException;

    public List<Question> findQuestions(String searchString);
    
    public List<Question> findQuestions(int userId);

    public List<Question> listQuestions();
    
    public void addAnswer(Answer answer);

    public List<Answer> findAnswers(int questionId);
}
