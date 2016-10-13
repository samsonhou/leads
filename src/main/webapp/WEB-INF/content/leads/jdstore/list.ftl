<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>京东店铺交易明细</title>
		 <style type="text/css">
		 	.modal-dialog {
		 		z-index: 1200;
		 		width: 750px;
		 		height: 350px;
				margin: 30px auto;
				position: relative;
		 	}
		 	
		 	.modal-content {
		 		height: 350px;
		 	}
		 </style>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/jdstore/query.do" method="post" name="searchForm" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">查询条件</div>
				<div class="panel-body" style="padding-bottom: 0px;">
			  		<div class="col-sm-12">
			  			<div class="float-e-margins">
			  	 	 		<div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 		<div class="form-group">
                                	<label class="col-sm-2 control-label">姓名</label>
                                	<div class="col-sm-4 ">
                                    	<input type="text" name="clientName" value="${queryVO.clientName!''}" class="form-control">
                                	</div>
                                	<label class="col-sm-2 control-label  required">手机号</label>
                                	<div class="col-sm-4">
                                  		<input type="text" name="tel" value="${queryVO.tel!''}" class="form-control" >
                                	</div>
                            	</div>
                            	<div class="form-group">
                                	<label class="col-sm-2 control-label">身份证号</label>
	                                <div class="col-sm-4">
	                                   <input type="text" name="idcard" value="${queryVO.idcard!''}" class="form-control" >
	                                </div>
	                                <label class="col-sm-2 control-label">订单编号</label>
	                                <div class="col-sm-4">
	                                   <input type="text" name="orderNo" value="${queryVO.orderNo!''}" class="form-control" >
	                                </div>
                            	</div>
                            	<div class="form-group">
	                                <label class="col-sm-2 control-label  required">合同号</label>
                                	<div class="col-sm-4">
                                    	<input type="text" name="contractNo" value="${queryVO.contractNo!''}" class="form-control" >
                                	</div>
                            	</div>
                            	<div class="form-group">
                            		<label class="col-sm-2 control-label"></label>
                                	<div class="col-sm-4"></div>
                                	<label class="col-sm-2 control-label"></label>
	                                <div class="col-sm-4">
		                            	<input type="button" onclick="doSearch();" id="searchBtn" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
		                            	&nbsp;&nbsp;
		                            	<input type="button" onclick="forAdd();" id="addBtn" value="新 增" class="btn btn-primary btn-sm zd-btn-pd1">
		                            	&nbsp;&nbsp;
		                            	<input type="button" onclick="export2Excel();" value="批量导出" class="btn btn-primary btn-sm zd-btn-pd1"> 
		                            </div>
                            	</div>
			  	 	 	 	</div>
			  	 	 	</div>
			  	 	</div>
			  	</div>
			</div>
			
			<div class="panel panel-default table-responsive ziding-td">
				 <table class="table table-condensed table-bordered table-striped table-hover" >
					 <thead>
					 	<tr>
					 		<th style="text-align: center;width: 50px;">序号</th>
					 		<th style="text-align: center;">操作</th>
					 		<th style="text-align: center;">客户姓名</th>
					 		<th style="text-align: center;">身份证号</th>
                            <th style="text-align: center;">手机号</th>
                            <th style="text-align: center;">活动产品类型</th>
                            <th style="text-align: center;">京东订单号</th>
                            <th style="text-align: center;">订单状态</th>
                            <th style="text-align: center;">订单金额</th>
                            <th style="text-align: center;">支付完成时间</th>
                            <th style="text-align: center;">门店名称</th>
                            <th style="text-align: center;">签约主体</th>
                            <th style="text-align: center;">合同号</th>
                            <th style="text-align: center;">车型</th>
                            <th style="text-align: center;">车架号</th>
					 	</tr>
					 </thead>
					 <tbody>
					 	<#list page.list as vo>
							<tr>
								<td style="text-align: center;">${vo_index+1}</td>
								<td><a href="#" onclick="forUpdate('${vo.id}');">修改</a></td>
								<td>${vo.clientName}</td>
                                <td>${vo.idcard}</td>
                                <td>${vo.tel}</td>
                                <td>${vo.productType}</td>
                                <td>${vo.orderNo}</td>
                                <td>${vo.orderStatus}</td>
                                <td>${vo.orderAmount}</td>
                                <td>${(vo.payTime?string("yyyy-MM-dd"))!}</td>
                                <td>${vo.storeName}</td>
                                <td>${vo.signedUser}</td>
                                <td>${vo.contractNo}</td>
                                <td>${vo.carType}</td>
                                <td>${vo.carVin}</td>
                            </tr>  
                        </#list>
					 </tbody>
				</table>
			</div>	
			<@pages url="${contextPath}/leads/mileage/matchingQuery.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />	
		</div>
		<input id="msg" type="hidden" value="${msg}">
	</form>
	
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
	
		//条件查询
		function doSearch(){
			searchForm.action="${contextPath}/leads/jdstore/matchingQuery.do";
			searchForm.submit();
		}
		
		function forAdd(){
			openNewTab("${contextPath}/leads/jdstore/forAdd.do","新增交易明细");
		}
		
		function forUpdate(id){
			openNewTab("${contextPath}/leads/jdstore/forUpdate.do?id="+id,"修改交易明细");
		}
		
		
		//数据导出
		function export2Excel(){
			layer.confirm('确定导出符合查询条件的数据吗？', {icon: 3, title:'提示'}, function(index){
				searchForm.action="${contextPath}/leads/jdstore/export.do";
				searchForm.submit();
    			layer.close(index);
			});
		}
		
		
		//获取已选数据
		function getCheckedItems(){
			var items=[];
			jQuery("[name = selectItem]:checkbox").each(function(){
				if(this.checked){
					items.push(jQuery(this).val());
				}
			});
			return items;
		}
		
		
		jQuery(document).ready(function(){		
			
			var msg = jQuery("#msg").val();
			if(msg.length>0){
				layer.alert(msg);
			}			
			
			//全选
			jQuery("#checkAll").on("click",function(){
				if(this.checked){
					jQuery("[name = selectItem]:checkbox").prop("checked",true);
				}else{
					jQuery("[name = selectItem]:checkbox").prop("checked",false);
				}
			});
			
		});
		
	</script>
	</body>
</html>