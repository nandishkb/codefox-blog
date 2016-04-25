package com.codefox.blog.rs;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.codefox.blog.api.User;
import com.codefox.blog.api.WebBlog;

/**
 * Authenitcation filter that will be invoked before every request to an API
 * resource
 *
 */
// Apply to all API urls
@WebFilter("/blog/*")
public class AuthFilter implements Filter {

	public void destroy() {
		System.out.println("AuthFilter.destroy()");
	}
	private WebBlog blog = null;
	
	private WebBlog getBlog() {
        if (blog == null) {
            blog = BlogFactory.getBlog();
        }
        return blog;
    }

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("AuthenticationManager.doFilter()");
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hres = (HttpServletResponse) res;
		// dont check for authenitcation on requests for signup
		// REPLACE WITH YOUR OWN URLS
		if (hreq.getRequestURI().contains("/techspace/user") && hreq.getMethod().equals("PUT")) {
			chain.doFilter(req, res);
			return;
		}
		
		System.out.println("hreq.getSession().getAttribute(\"user\") = " + hreq.getSession().getAttribute("user"));
		// Check if this request is already authenticated in a session
		if (hreq.getSession().getAttribute("user") != null) {
			// Authenitcated user, proceed with the api call
			chain.doFilter(req, res);
			return;
		}
		// Not an already authenticated user, check for credentials
		// Get basic auth header
		String basicAuthHeader = hreq.getHeader("Authorization");
		System.out.println("AuthFilter.doFilter() basicAuthHeader = "+basicAuthHeader);
		if (basicAuthHeader == null) {
			hres.sendError(401, "Unauthenticated");
			return;
		}
		// decode it to a form of Basic username:password
		if (basicAuthHeader != null && basicAuthHeader.startsWith("Basic")) {
			// Authorization: Basic base64credentials
			String base64Credentials = basicAuthHeader.substring(
					"Basic".length()).trim();
			
			String credentials = new String(Base64.decodeBase64(base64Credentials));
			// credentials = username:password
			// Split the user name and password
			String username = credentials.split(":")[0];
			String password = credentials.split(":")[1];
			System.out.println("AuthenticationManager.doFilter() received user name = "+username);
			// HARDCODED USERNAME CHECKING. REPLACE WITH DATABASE BASED
			// VERIFICATION LOGIC
			User user = getBlog().getUser(username);
			System.out.println("AuthenticationManager.doFilter() related user = "+user);
			if (user != null) {
				String pass = user.getPassword();
				if (password.equals(pass)) {
					// User is authenticated.. setup session and proceed
					System.out.println("AuthenticationManager.doFilter() authentication success");
					hreq.getSession().setAttribute("user", username);
					chain.doFilter(req, res);
					return;
				}
			}
		} else {
			hres.sendError(401, "Invalid authenitcation details");
			return;
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("AuthFilter.init()");
	}
}

