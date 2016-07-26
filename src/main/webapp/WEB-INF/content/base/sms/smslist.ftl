<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <link href="${contextPath}/res/pub/css/demo/ziding/jquery.onoff.css" rel="stylesheet">
		 <title>短信发送</title>
		 <style type="text/css">
			input.form-control-size {width: 112px;}
		</style>
	</head>
	<body class="gray-bg">
	<form  method="post" action="${contextPath}/base/sms/queryList.do" name="form1" id="form1" class="form-horizontal">

		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">短信发送</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					<div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">客户姓名</label>
                                <div class="col-sm-2">                                  
                                    <input type="text" class="form-control" name="clientName" value="${clientName}" /> 
                                </div> 
                                <label class="col-sm-2 control-label" style=" margin:5px;">客户电话</label>
                                <div id="date" class="col-sm-2 ">
                                   	<input type="text" class="form-control" name="clientTel" value="${clientTel}" />                                  
                                </div>
                            </div>
                            
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">所属机构</label>
                                <div class="col-sm-2">                                  
                                    <@select type='1' codeType="1034" fieldId="organId" fieldName="organId" defValue="${organId!''}" haveHead="false" paramName="organId" paramValue="${orgId}" props=" class='form-control ' " /> 
                                </div> 
                                <label class="col-sm-2 control-label" style=" margin:5px;">发送状态</label>
                                <div id="date" class="col-sm-2 ">
                                   <@select type='0' codeType="1042" fieldId="sendStatus" fieldName="sendStatus" defValue="${sendStatus!''}"  props=" class='form-control ' " />                                  
                                </div>
                            </div> 
                                
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">录入时间</label>
                                <div id="date" class="col-sm-4 ">
                                   <input type="text" readonly name="startDate" datatype="*" nullmsg="请选择开始日期" id="startDate" value="${startDate!''}" placeholder="开始日期" class="form-control layer-date form-control-size">                                  
                                  	至 <input type="text" readonly name="endDate" datatype="*" nullmsg="请选择结束日期" id="endDate" value="${endDate!''}" placeholder="结束日期" class="form-control layer-date form-control-size" >
                                </div>
                            </div>     
                               
                               
                           <div class="form-group ">
                              <div class="col-sm-4 col-sm-offset-8">
                              <input type="hidden" id="isQuery" name="isQuery">
			  	 	 	      <input type="submit" id="but" value="查询" class="btn btn-primary btn-sm zd-btn-pd1">&nbsp;&nbsp;
			  	 	 	      </div>
                           </div>
			  	 	  </div>
			  	 	 </div>
				</div>
			</div>
			</div>
			
			<div class="panel panel-default table-responsive ziding-td">
				<table class="table table-condensed table-bordered table-striped table-hover" id="table1">
                                <thead>
                                    <tr>
                                    
		                 		 		<th style="text-align: center;width:5px;">序号</th>	
		                 		 		<th style="text-align: center;">客户姓名</th>	
		                 		 		<th style="text-align: center;">客户电话</th>	
		                 		 		<th style="text-align: center;">客户经理</th>	
		                 		 		<th style="text-align: center;">所属机构</th>	
		                 		 		<th style="text-align: center;">录入时间</th>	
		                 		 		<th style="text-align: center;">发送状态</th>	
		                 		 		<th style="text-align: center;">操作</th>	
                                    </tr>
                                </thead>
                                <tbody id="span3">
                                	<#list page.list as obj>
									<tr>
                                        <td style="width:5px;">${obj_index+1}</td>
                                        <td>${obj.CLIENTNAME}</td>
                                        <td>${obj.CLIENTTEL}</td>
                                        <td>${obj.SALSEMAN}</td>
                                        <td>${obj.ORGAN}</td>
                                        <td>${obj.QDATE}</td>
                                        <td>${obj.SENDSTATUS}</td>
                                        <td style="text-align: center;"><button type="button" class="btn btn-primary btn-xs" onclick="sendMsg('${obj.ID}');">短信发送</button></td>
                                                                                 
                                    </tr>  
                                    </#list>
                              </tbody>
               </table>
			</div>		
		</div>
		<@pages url="${contextPath}/base/sms/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
	</form>
	<#include "/pub/footer_res_detail.ftl"/>

	<script type="text/javascript">
		var start={
    		elem:"#startDate",
    		format:"YYYY-MM-DD",
    		min:"1970-06-16",
    		max:"2099-06-16",
    		choose: function(dates){ 
				end.min = dates;
				end.start = dates;
        	}
    		
    	};
    	laydate(start);
    	
    	var end={
    		elem:"#endDate",
    		format:"YYYY-MM-DD",
    		choose: function(dates){
    		 start.max = dates;
    		}
    	};
    	
    	laydate(end);
    	
		$(document).ready(function(){
			$("#pagination").page("form1");
		});
    	
    	function sendMsg(id){
    		layer.confirm('是否发送短信?', {icon: 3, title:'提示'}, function(index){
    			layer.close(index);
    			var ind = layer.load();
    			$.ajax({  
	            type: "POST",  
	            url: "${contextPath}/base/sms/sendMsg.do?id="+id,  
	            data: "",
	            success: function(resp){
	            	layer.close(ind);
	            	layer.alert(resp.msg);
	            }
	            }); 
	            
			});
    	}
				
   	 		 	
	</script>
	</body>
</html>