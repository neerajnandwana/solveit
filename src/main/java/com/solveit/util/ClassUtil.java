package com.solveit.util;

import java.io.InputStream;

import com.google.common.base.Supplier;

public class ClassUtil {
	private ClassUtil(){}

	public static final Supplier<ClassLoader> THREAD_CL = new Supplier<ClassLoader>() {
		@Override
		public ClassLoader get() {
			return Thread.currentThread().getContextClassLoader();
		}
	};

	public static final Supplier<ClassLoader> CLASS_CL = new Supplier<ClassLoader>() {
		@Override
		public ClassLoader get() {
			return ClassUtil.class.getClassLoader();
		}
	};

	public static final Supplier<ClassLoader> SYSTEM_CL = new Supplier<ClassLoader>() {
		@Override
		public ClassLoader get() {
			return ClassLoader.getSystemClassLoader();
		}
	};

	public static InputStream getInputStreamFromClassPath(String name) {
		InputStream stream = ClassUtil.THREAD_CL.get().getResourceAsStream(name);
		if(stream == null){
			stream = ClassUtil.CLASS_CL.get().getResourceAsStream(name);
		}
		if(stream == null){
			stream = ClassUtil.SYSTEM_CL.get().getResourceAsStream(name);
		}
		return stream;
	}
}
