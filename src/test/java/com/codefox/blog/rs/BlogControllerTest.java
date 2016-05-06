package com.codefox.blog.rs;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.codefox.blog.api.User;
import com.codefox.blog.data.BlogDAO;
import com.codefox.blog.service.CodeFoxBlog;

import junit.framework.TestCase;

public class BlogControllerTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testGetUserWithEmail() {
        BlogDAO dao = mock(BlogDAO.class);
        
        User user = new User();
        user.setEmail("nandibal@cisco.com");
        user.setFirstName("nandish");
        user.setLastName("kb");
        user.setPassword("1231@!@@#");
        user.setPhoneNumber(9741701970L);
        
        when(dao.getUser("nandibal@cisco.com")).thenReturn(user);
        
        
        CodeFoxBlog blog = new CodeFoxBlog();
        blog.setBlogDao(dao);
        User u = blog.getUser("nandibal@cisco.com");
        Assert.assertEquals("nandish", u.getFirstName());
    }

}
