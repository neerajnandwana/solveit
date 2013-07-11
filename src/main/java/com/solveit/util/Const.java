package com.solveit.util;


public interface Const {
	String APPLICATION_PROPERTIES = "application.properties";
	int MAX_INPUT_SIZE = 100;
	String ANSWER_ATTR = "answer";
	String URL_ATTR = "url";
	String PAGE_SEQUENCE = Props.get("sequence");
	String WINNER_PAGE = Props.get("winner.page");
	String FINAL_PAGE = Props.get("final.page");
	String WINNER_LIST = Props.get("winner.list");
	String ERROR_MSG = Props.get("error.msg");
	String DATA_FILE = Props.get("data.file");
	String VALID_EMAIL_FILE = Props.get("valid.emails");
    boolean SHOW_UNIQUE_WINNERS = Boolean.parseBoolean(Props.get("show.unique.winners"));
}
