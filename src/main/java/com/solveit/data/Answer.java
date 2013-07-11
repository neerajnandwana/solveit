package com.solveit.data;

import com.google.common.base.CharMatcher;
import com.google.common.base.Supplier;

public class Answer implements Supplier<String>{
	private final String answer;

	public Answer(String answer) {
		this.answer = sanitize(answer);
	}

	@Override
	public String get() {
		return answer;
	}

	public boolean fuzzyMatch(String ans) {
		if(ans == null){
			return false;
		}
		ans = sanitize(ans);
		return answer.equalsIgnoreCase(ans);
	}

	@Override
	public String toString() {
		return answer;
	}
	
	public String sanitize(String in) {
		if(in == null){
			return in ;
		}
		return CharMatcher.JAVA_LETTER_OR_DIGIT.negate().removeFrom(in);
	}
}
