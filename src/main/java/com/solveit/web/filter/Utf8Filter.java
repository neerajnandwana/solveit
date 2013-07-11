package com.solveit.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.CharEncoding;

import com.google.inject.Singleton;

/**
 * Utility class for UTF-8 Servlet Filter. This filter would be used to
 * consistently set the character encoding as UTF-8.
 * 
 */
@Singleton
public class Utf8Filter implements Filter {

	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding(CharEncoding.UTF_8);
		chain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {}

}
