package com.jiezh.dao.base.codetype;

import com.jiezh.content.base.pub.web.GeneralBean;
import java.util.Date;

public class CodeItemVO extends GeneralBean {
	private String isnew;
	
	private String codeTypeName;
	
    private long codeItemId;

    private String codeType;

    private String name;

    private String value;

    private Integer seq;

    private String status;

    private Date createDate;

    private String assistant;

    public long getCodeItemId() {
        return codeItemId;
    }

    public void setCodeItemId(long codeItemId) {
        this.codeItemId = codeItemId;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

	public String getIsnew() {
		return isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	public String getCodeTypeName() {
		return codeTypeName;
	}

	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}
    
}