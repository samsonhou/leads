package com.jiezh.content.base.pub.exception;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
/**
 * Freemarker 异常
 * @author liangds
 *
 */
public class ViewException extends TemplateException{

	public ViewException(String description, Exception cause, Environment env) {
		super(description, cause, env);
	}

	private static final long serialVersionUID = 1L;
}
