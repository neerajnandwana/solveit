package com.solveit.util;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.routines.EmailValidator;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.google.inject.Singleton;

@Singleton
public class AllowedEmails {
	private static final Log LOG = LogFactory.getLog(AllowedEmails.class);

	private final Set<String> allowedEmails;
	private final BloomFilter<CharSequence> filter;

	public AllowedEmails() throws IOException {
		List<String> emails = IOUtils.readLines(ClassUtil.getInputStreamFromClassPath(Const.VALID_EMAIL_FILE));
		filter = BloomFilter.create(Funnels.stringFunnel(), emails.size());
		ImmutableSet.Builder<String> builder = ImmutableSet.builder();
		for (String email : emails) {
			email = normalizeEmail(email);
			if (email.isEmpty()) {
				continue;
			}
			builder.add(email);
			filter.put(email);
		}
		this.allowedEmails = builder.build();
		LOG.info("allowed email list loaded.");
	}

	public boolean allowed(String email) {
		boolean valid = false;
		email = normalizeEmail(email);

		if (!EmailValidator.getInstance().isValid(email)) {
			return false;
		}
		if(filter.mightContain(email)){
			valid = allowedEmails.contains(email);
			if(!valid){
				LOG.debug("[BloomFilter] False Positive Occurred - filter says yes, but its not in file: "+email);
			}
		}
		return valid;
	}

	private String normalizeEmail(String email){
		return Strings.nullToEmpty(email).toLowerCase().trim();
	}
}