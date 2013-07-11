package com.solveit.web;

import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.inject.Provider;
import com.solveit.data.Answer;
import com.solveit.data.store.Store;
import com.solveit.util.Const;
import com.solveit.util.AllowedEmails;

public class BaseServlet extends HttpServlet implements Const{
	private static final long serialVersionUID = 1L;

	protected final Map<String, Answer> QA;
	protected final Provider<ServletRequest> reqProvider;
	protected final LinkedList<String> pageList;
	protected final Store store;
	protected final AllowedEmails emails;

	public BaseServlet(Map<String, Answer> qA, Provider<ServletRequest> reqProvider, Store store, AllowedEmails emails) {
		this.QA = qA;
		this.reqProvider = reqProvider;
		this.pageList = new LinkedList<String>(QA.keySet());
		this.store = store;
		this.emails = emails;
	}

	protected String getClientIPAddr() {
		HttpServletRequest req = (HttpServletRequest) reqProvider.get();
        String ipAddress = req.getHeader("x-forwarded-for");
        if (ipAddress == null) {
            ipAddress = req.getHeader("X_FORWARDED_FOR");
            if (ipAddress == null){
                ipAddress = req.getRemoteAddr();
            }
        }
        return ipAddress;
	}

	protected String getQuestionPageName(){
		String url = getValue(URL_ATTR);
		return FluentIterable
			.from(Splitter.on('/').trimResults().omitEmptyStrings().split(url))
			.last().get();
	}

	protected String nextPage(String currentPage){
		int index = pageList.indexOf(currentPage);
		if( index < pageList.size()-1){
			return pageList.get(index + 1);
		} else {
			return WINNER_PAGE;
		}
	}

	protected String getValue(String name){
		String value = (String) reqProvider.get().getParameter(name);
		if(value.length() > MAX_INPUT_SIZE){
			value = value.substring(0, MAX_INPUT_SIZE);
		}
		return value;
	}
}
