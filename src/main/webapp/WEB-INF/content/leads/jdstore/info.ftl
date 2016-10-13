<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		<title>新增交易明细</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/jdstore/save.do" method="post" name="inputForm" id="inputForm" class="form-horizontal">
		<@token />
		<input type="hidden" id="id" name="id" value="${vo.id}" />
		<input type="hidden" id="oper" name="oper" value="${oper!''}" />
		<input type="hidden" id="msg" value="${msg!''}" />
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">京东店铺交易明细</div>
			  	<div class="panel-body">
			  		<div class="col-sm-12">
			  	 		<div class="float-e-margins">
			  	 	 		<div class="ibox-content" style="padding:0 0 0 0">
				  	 	 	 	<div class="form-group">
	                                <label class="col-sm-2 control-label">客户姓名</label>
	                                <div class="col-sm-4 ">
	                                    <input type="text" name="clientName" value="${vo.clientName!''}" datatype="*" maxlength="50" nullmsg="请填写客户姓名" class="form-control">
	                                </div>
	                                <label class="col-sm-2 control-label ">手机号</label>
	                                <div class="col-sm-4">
	                                  <input type="text" name="tel" value="${vo.tel!''}" datatype="m" maxlength="11" nullmsg="请填写手机号" errormsg="请填写正确的手机号" class="form-control" >
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">身份证号</label>
	                                <div class="col-sm-4 ">
	                                    <input type="text" name="idcard" value="${vo.idcard!''}" datatype="*" nullmsg="请填写身份证号" maxlength="18" class="form-control">
	                                </div>
	                                <label class="col-sm-2 control-label ">活动产品类型</label>
	                                <div class="col-sm-4">
	                                  <input type="text" name="productType" value="${vo.productType!''}" datatype="*" nullmsg="请填写活动产品类型" maxlength="20" class="form-control" >
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">京东订单号</label>
	                                <div class="col-sm-4 ">
	                                    <input type="text" name="orderNo" value="${vo.orderNo!''}" datatype="*" nullmsg="请填写京东订单号" maxlength="20" class="form-control">
	                                </div>
	                                <label class="col-sm-2 control-label ">订单状态</label>
	                                <div class="col-sm-4">
	                                	<input type="text" name="orderStatus" value="${vo.orderStatus!''}" datatype="*" nullmsg="请填写订单状态" maxlength="20" class="form-control">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">订单金额</label>
	                                <div class="col-sm-4 ">
	                                    <input type="text" name="orderAmount" value="${vo.orderAmount!''}" datatype="money" nullmsg="请填写订单金额" errormsg="订单金额只能为数字" class="form-control">
	                                </div>
	                                <label class="col-sm-2 control-label ">支付完成时间</label>
	                                <div class="col-sm-4">
	                                  <input type="text" name="payTime" readonly value="${(vo.payTime?string("yyyy-MM-dd"))!}" id="payTime" datatype="*" nullmsg="请选择支付完成时间" class="form-control layer-date" >
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">门店名称</label>
	                                <div class="col-sm-4 ">
	                                	<input id="name" name ="storeName" type="text" value="${vo.storeName!''}" datatype="*" nullmsg="请选择门店" class='form-control' readonly  placeholder="选择门店"  onclick="showMenu();" />
                                		<input id="organId" name ="storeId" type="hidden"  value="${vo.storeId!''}"  />
	                                </div>
	                                <label class="col-sm-2 control-label ">签约对象</label>
	                                <div class="col-sm-4">
	                                	<input type="text" name="signedUser" value="${vo.signedUser!''}" datatype="*" nullmsg="请填写签约对象" maxlength="100" class="form-control" >
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">合同号</label>
	                                <div class="col-sm-4 ">
	                                    <input type="text" name="contractNo" value="${vo.contractNo!''}" datatype="*" nullmsg="请填写合同号" maxlength="50" class="form-control">
	                                </div>
	                                <label class="col-sm-2 control-label ">车型</label>
	                                <div class="col-sm-4">
	                                	<input type="text" name="carType" value="${vo.carType!''}" datatype="*" nullmsg="请填写车型" maxlength="20" class="form-control">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">车架号</label>
	                                <div class="col-sm-4 ">
	                                	<input type="text" name="carVin" value="${vo.carVin!''}" datatype="len17" errormsg="车架号无效" maxlength="17" class="form-control">
	                                </div>
	                            </div>
			  	 	 	 	</div>
			  	 		</div>
			  		</div>
			  		
			  		<div class="form-group">
                        <div class="col-sm-4 col-sm-offset-8">
                               <input type="button" value="关 闭"  onclick="closeTab();" class="btn btn-sm btn-primary zd-btn-pd1" />                                  
                               &nbsp; &nbsp; 
                               <input type="submit" value="提 交" class="btn btn-sm btn-primary zd-btn-pd1" />
                         </div>
                    </div>
                    
			 	</div>
			</div>
		</div>
	</form>
	
	<div id="menuContent" class="menuContent" style="display:none;  position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:340px; height: 300px;"></ul>
    </div>
    
	<#include "/pub/footer_res_detail.ftl"/>
	<#include "/pub/organ_tree.ftl"/>
	<script type="text/javascript">
	
		jQuery(document).ready(function() {
			//表单校验
			$("#inputForm").Validform({
				tiptype : 1,
				showAllError : true,
				postonce : true,
				datatype : {
					"money" : /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/,
					"len17" : function(value){
						if(value.length>0){
							var str = value.replace(/[ ]/g,"");
					  		return str.length==17;
						}
						return true;
				  	}
				}
			});
			//信息提示
			var msg = $("#msg").val();
			if(msg.length>0){
				layer.alert(msg);
			}
		});
		
		var payTime = {
    		elem:"#payTime",
    		format:"YYYY-MM-DD",
    		max:"2099-06-16"
    	};
    	laydate(payTime);
	</script>
	</body>
</html>