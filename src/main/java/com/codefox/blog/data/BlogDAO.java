package com.codefox.blog.data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
//import org.hibernate.search.FullTextQuery;
//import org.hibernate.search.FullTextSession;
//import org.hibernate.search.Search;
//import org.hibernate.search.query.dsl.QueryBuilder;

import com.codefox.blog.api.Answer;
import com.codefox.blog.api.Question;
import com.codefox.blog.api.User;

public class BlogDAO {

    private static BlogDAO dao = null;

    private static final Object LOCK = new Object();

    private BlogDAO() {
/*        User user = new User();
        user.setEmail("ellora@cisco.com");
        user.setFirstName("Ellora");
        user.setLastName("Dash");
        user.setPassword("123456");
        user.setPhoneNumber(9986629247L);
        
        Question question = new Question();
        question.setUser(user);
        question.setTitle("This is the first question");
        question.setDescription("This is the first question and I dont know if this works well in storing in DB");
        
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setAnswerData("Even I dont know if this works well. Anyways all the very best");
        
        addUser(user);
        addQuestion(question);
        addAnswer(answer);
*/        
    }

    public static BlogDAO getInstance() {
        synchronized (LOCK) {
            if (dao == null) {
                dao = new BlogDAO();
            }
        }
        return dao;
    }

    public void addUser(User user) {
        Session session = HibernateUtil.currentSession();
        try {
            Transaction trx = session.beginTransaction();
            session.save(user);
            trx.commit();
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    public User getUser(int userId) {
        Session session = HibernateUtil.currentSession();
        try {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.idEq(userId));
            User user = (User) criteria.uniqueResult();
            System.out.println("BlogDAO.getUser() user = "+user);
            return user;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public User getUser(String email) {
        Session session = HibernateUtil.currentSession();
        try {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("email", email));
            User user = (User) criteria.uniqueResult();
            System.out.println("BlogDAO.getUser() user = "+user);
            return user;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public List<User> listUsers() {
        Session session = HibernateUtil.currentSession();
        try {
            return session.createCriteria(User.class).list();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void addQuestion(Question question) {
        Session session = HibernateUtil.currentSession();
        try {
            Transaction trx = session.beginTransaction();
            session.save(question);
            trx.commit();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public List<Question> listQuestions() {
        Session session = HibernateUtil.currentSession();
        try {
            return session.createCriteria(Question.class).list();
        } finally {
            
        }
    }

    public List<Question> findQuestions(String searchPattern) {
        Session session = HibernateUtil.currentSession();
        try {
        	Criteria criteria = session.createCriteria(Question.class);
        	Disjunction disCriteria = Restrictions.disjunction();
        	disCriteria.add(Restrictions.like("title", "%"+searchPattern+"%"));
        	disCriteria.add(Restrictions.like("description", "%"+searchPattern+"%"));
        	criteria.add(disCriteria);
            return criteria.list();
        } finally {
            HibernateUtil.closeSession();
        }
    }
    
    public Question getQuestion(int questionId) {
        Session session = HibernateUtil.currentSession();
        try {
            Criteria criteria = session.createCriteria(Question.class);
            criteria.add(Restrictions.idEq(questionId));
            Question question = (Question) criteria.uniqueResult();
            System.out.println("BlogDAO.getQuestion() = "+question);
            return question;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public List<Question> findQuestions(int userId) {
        Session session = HibernateUtil.currentSession();
        try {
            Criteria criteria = session.createCriteria(Question.class);
            criteria.add(Restrictions.eq("user.userId", userId));
            return criteria.list();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void addAnswer(Answer answer) {
        Session session = HibernateUtil.currentSession();
        try {
            Transaction trx = session.beginTransaction();
            session.save(answer);
            trx.commit();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public List<Answer> findAnswers(int questionId) {
        Session session = HibernateUtil.currentSession();
        try {
            Criteria criteria = session.createCriteria(Answer.class);
            criteria.add(Restrictions.eq("question.questionId", questionId));
            return criteria.list();
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
