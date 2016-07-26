package com.jiezh.content.base.pub.exception;

import java.io.Writer;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
/**
 * Freemarker 异常
 * @author liangds
 *
 */
public class FreemarkerExceptionHandler implements TemplateExceptionHandler{

	@Override
	public void handleTemplateException(TemplateException te, Environment paramEnvironment,
			Writer paramWriter) throws TemplateException {
		throw new ViewException(te.getMessage(),te,paramEnvironment);
	}

}
