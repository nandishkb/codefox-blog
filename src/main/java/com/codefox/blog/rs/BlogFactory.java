package com.codefox.blog.rs;

import com.codefox.blog.api.WebBlog;
import com.codefox.blog.service.CodeFoxBlog;

public class BlogFactory {

	private static WebBlog blog;

	public static synchronized WebBlog getBlog() {
        if (blog == null) {
            blog = new CodeFoxBlog();
        }
        return blog;
    }
}
