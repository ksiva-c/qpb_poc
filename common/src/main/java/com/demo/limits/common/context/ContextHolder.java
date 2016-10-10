package com.demo.limits.common.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContextHolder {
	private static final ThreadLocal<ApiContext> holder = new InheritableThreadLocal<ApiContext>();

	public static void set(ApiContext context) {
		holder.set(context);
	}

	public static void unset() {
		holder.remove();
	}

	public static ApiContext get() {
		return holder.get();
	}
}