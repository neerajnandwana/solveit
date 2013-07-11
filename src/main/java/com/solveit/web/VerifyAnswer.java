package com.solveit.web;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.solveit.data.Answer;
import com.solveit.data.store.Store;
import com.solveit.util.AllowedEmails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Singleton
public class VerifyAnswer extends BaseServlet{
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(VerifyAnswer.class);

	@Inject
	public VerifyAnswer(Map<String, Answer> qA, Provider<ServletRequest> reqProvider, Store fileStore, AllowedEmails emails) {
		super(qA, reqProvider, fileStore, emails);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String currentPage = getQuestionPageName();
		String nextPage = currentPage;
		String ans = getValue(ANSWER_ATTR);
		String error = ERROR_MSG;
		if(currentPage.equalsIgnoreCase(WINNER_PAGE)) {
            if (emails.allowed(ans)) {
                store.saveWinner(ans, getClientIPAddr());
                LOG.debug("ip= " + getClientIPAddr() + " winner: " + ans);
                resp.sendRedirect(FINAL_PAGE);
                return;
            } else {
                error = "Invalid email id. Try again...";
            }
        }

		Answer rightAns = QA.get(currentPage);
		if(rightAns != null && rightAns.fuzzyMatch(ans)){
			nextPage = nextPage(currentPage);
		} else {
			nextPage = currentPage+"?error="+error;
		}

		LOG.debug("ip= "+getClientIPAddr()+" rightAns= "+rightAns+"\tinputAns= "+ans+"\tcurrentPage= "+currentPage+"\tnextPage= "+nextPage);
		resp.sendRedirect(nextPage);
	}
}
