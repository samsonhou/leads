package com.jiezh.dao.base.codetype;

import com.jiezh.content.base.pub.web.GeneralBean;
import java.util.Date;

public class CodeTypeVO extends GeneralBean {
	private String isnew; 
	
    private String codeType;

    private String name;

    private Date createDate;

    private String status;
    
    private String type;
    
    private String txtSql;
    
    


	/**
	 * @return the txtSql
	 */
	public String getTxtSql() {
		return txtSql;
	}

	/**
	 * @param txtSql the txtSql to set
	 */
	public void setTxtSql(String txtSql) {
		this.txtSql = txtSql;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	/**
	 * @return the isnew
	 */
	public String getIsnew() {
		return isnew;
	}

	/**
	 * @param isnew the isnew to set
	 */
	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	/***
	
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	
	* @date 2015年12月11日 下午5:59:34 
	
	* <p>Title: toString</p> 
	
	* <p>Description: </p> 
	
	* @return 
	
	* @see java.lang.Object#toString() 
	
	*/ 
	@Override
	public String toString() {
		return "CodeTypeVO [isnew=" + isnew + ", codeType=" + codeType + ", name=" + name + ", createDate=" + createDate
				+ ", status=" + status + "]";
	}
    
}