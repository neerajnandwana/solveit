package com.solveit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletConfig extends GuiceServletContextListener {
	private static final Log LOG = LogFactory.getLog(GuiceServletConfig.class);
	private Injector injector;

	@Override
	protected Injector getInjector() {
		if (injector == null) {
			injector = Guice.createInjector(new ApplicationModule());
			LOG.info("GuiceServletConfig module initialized.");
		}
		return injector;
	}
}
