package com.solveit;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Splitter;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.solveit.data.Answer;
import com.solveit.data.store.FileStore;
import com.solveit.data.store.Store;
import com.solveit.util.AllowedEmails;
import com.solveit.util.Const;
import com.solveit.util.Props;
import com.solveit.web.VerifyAnswer;
import com.solveit.web.filter.Utf8Filter;

public class ApplicationModule extends ServletModule{
	private static final Log LOG = LogFactory.getLog(ApplicationModule.class);

	@Override
	protected void configureServlets() {
		super.configureServlets();

		filter("/*").through(Utf8Filter.class);

		serve("/VerifyAnswer").with(VerifyAnswer.class);

		bind(Store.class).to(FileStore.class);
		bind(AllowedEmails.class);

		LOG.info("ApplicationModule initialized.");
	}

	@Provides @Singleton
	Map<String, Answer> getQuestionAnswer(){
		Map<String, Answer> map = new LinkedHashMap<String, Answer>();
		Iterable<String> seq = Splitter.on(',').trimResults().split(Const.PAGE_SEQUENCE);
		for(String s: seq){
			map.put(s, new Answer(Props.get(s)));
		}
		return map;
	}
}
