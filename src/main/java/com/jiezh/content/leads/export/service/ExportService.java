package com.jiezh.content.leads.export.service;

/**
 * 导出excel
 * @author liangds
 *
 */
public interface ExportService {
	public byte[] export2003() throws Exception;
	public byte[] export() throws Exception ;
}
