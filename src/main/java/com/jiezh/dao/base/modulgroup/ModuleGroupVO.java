package com.jiezh.dao.base.modulgroup;

import com.jiezh.content.base.pub.web.GeneralBean;
import java.util.Date;

public class ModuleGroupVO extends GeneralBean{
    private long groupId;

    private String groupName;

    private long groupOrder;

    private String remark;

    private Date createDate;
    
    private long  temp=0;
    
    
	public long getTemp() {
		return temp;
	}

	
	public void setTemp(long temp) {
		this.temp = temp;
	}

	

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

   

	public long getGroupOrder() {
		return groupOrder;
	}

	public void setGroupOrder(long groupOrder) {
		this.groupOrder = groupOrder;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	/***
	
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	
	* @date 2015年12月18日 下午3:55:17 
	
	* <p>Title: toString</p> 
	
	* <p>Description: </p> 
	
	* @return 
	
	* @see java.lang.Object#toString() 
	
	*/ 
	@Override
	public String toString() {
		return "ModuleGroupVO [groupId=" + groupId + ", groupName=" + groupName + ", groupOrder=" + groupOrder
				+ ", remark=" + remark + ", createDate=" + createDate + "]";
	}
    
}