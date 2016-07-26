<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>追踪率统计</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/businesstype/query.do" method="post" name="form1" class="form-horizontal">
		<input type="hidden" id="assginItems" name="assginItems">
		<@token />
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">追踪率统计</div>
			  <div class="panel-body" style="padding-bottom: 0px;">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	<div class="form-group">
			  	 	 	 	
                                <label class="col-sm-1 control-label" style=" margin:5px;">起止日期</label>
                                <div class="col-sm-2 ">
                                   <input type="text" readonly name="stnextdate" id="stnextdate" value="${stnextdate!''}" maxlength="30"  placeholder="开始日期" class="form-control layer-date" >
                                </div>
                                <div class="col-sm-2">
                                    <input type="text" readonly name="nextdate" id="nextdate" value="${nextdate!''}" maxlength="30"  placeholder="结束日期" class="form-control layer-date" >
                                </div>
                                
                                <label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">机构</label>
                                <div class="col-sm-4">                                  
                                    <@select type='1' codeType="1034" fieldId="organId" fieldName="organId" defValue="${organId!''}" haveHead="false" paramName="organId" paramValue="${ogId}" props=" class='form-control ' " />
                                </div>
                                
                            </div>
                            <div class="form-group">
                                 <div class="col-sm-4 col-sm-offset-10">
	                                 <button type="button" class="btn btn-primary btn-sm" onclick="search();" id="searchBtn">查 询</button>&nbsp;&nbsp;
	                                 <button type="button" class="btn btn-primary btn-sm" onclick="exportReport();" id="expBtn">导出</button>                              
	                             </div>
                                
                            </div>
			  	 	 	 </div>
			  	 	 </div>
			  	 </div>
			  </div>
			</div>
			
			<div class="panel panel-default table-responsive ziding-td">
				 <table class="table table-condensed table-bordered table-striped" >
					 <thead>
					 	<tr>
					 		<th style="text-align: center;width: 50px;">序号</th>
					 		<th style="text-align: center;width: 20px;">&nbsp;</th>
					 		<th style="text-align: center;width:200px;">名称</th>
                            <th style="text-align: center;">时间</th>
                            <th style="text-align: center;width:100px;">总量</th>
                            <th style="text-align: center;width:100px;">回复量</th>
                            <th style="text-align: center;width:100px;">未回复量</th>
                            <th style="text-align: center;width:100px;">追踪率</th>
					 	</tr>
					 </thead>
					 <tbody>
					 	<#list data as client>
							<tr>
								<td style="text-align: center;">${client_index+1}</td>
								<td style="text-align: center;"><a onclick="changeIcon(this);" data-trid="index${client_index+1}"><i class="fa fa-plus-square-o"></i></a></td>
								<td>${client.NAME}</td>                         
                                <td style="text-align: center;">${time}</td>
                                <td style="text-align: center;">${client.TOTAL}</td>
                                <td style="text-align: center;">${client.RE_TOTAL}</td>
                                <td style="text-align: center;">${client.UN_RE_TOTAL}</td>   
                                <td style="text-align: right;">${client.PERCENT}</td>               
                            </tr>
                            <#if  (client.itemList?size>0) >  
                            
                            <tr class="collapse" id="index${client_index+1}" > 
                            	<td colspan="2" style="text-align: center;width:70px;">销售人员</td>
                            	<td colspan="6"  style="padding:0;border:0px;">                         	
									<table class="table table-hover table-condensed" style="margin-bottom:0;border-top: 0px;"  border="0">
									<#list client.itemList as client1>  
                            			<tr>
										   <td style="width:200px;padding-left: 50px;">${client1.REAL_NAME}</td>
                                           <td style="text-align: center;">${time}</td>  
                                           <td style="width:100px;text-align: center;border-left:1px solid #e7eaec;">${client1.TOTAL}</td> 
                                           <td style="width:100px;text-align: center;border-left:1px solid #e7eaec;">${client1.RE_TOTAL}</td>
                                           <td style="width:100px;text-align: center;border-left:1px solid #e7eaec;">${client1.UN_RE_TOTAL}</td>   
                                           <td style="width:100px;text-align: right;border-left:1px solid #e7eaec;">${client1.PERCENT}</td> 
                            			</tr>	
                            		</#list>   
                            		</table>
                            	 </td> 
                            </tr>                                  
                            </#if>
                        </#list>
					 </tbody>
				</table>
			</div>	
		</div>
	</form>
	
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
	
		function search(){
			$("#searchBtn").attr("disabled","disabled").html('<i class="fa fa-spinner fa-spin f-2"></i>  统计中');
			form1.action="${contextPath}/leads/search/replyList.do";
			form1.submit();
		}
		
		function exportReport(){
			form1.action ="${contextPath}/leads/search/exportReport.do";
			form1.submit();
		}
		
		jQuery(document).ready(function(){
			organ.initOrgan("_organTag_");
		});
		function changeIcon(obj){
			$this=jQuery(obj);
			jQuery("#"+$this.attr("data-trid")).collapse('toggle');
			if($this.html()=='<i class="fa fa-plus-square-o"></i>'){
				$this.html('<i class="fa fa-minus-square-o"></i>');
    		}else{
    			$this.html('<i class="fa fa-plus-square-o"></i>');
    		}
		}
		
		 var end1={
    		elem:"#stnextdate",
    		format:"YYYY-MM-DD hh:mm",
    		//format:"YYYY-MM-DD",
    		min:"1970-06-16 23:59:59",
    		max:"2099-06-16 23:59:59",
    		istime:true,istoday:false,
    		choose:function(a){
    			end2.min=a
    		}
    	};
    	laydate(end1);
		var end2={
    		elem:"#nextdate",
    		format:"YYYY-MM-DD hh:mm",
    		//format:"YYYY-MM-DD",
    		min:"1970-06-16 23:59:59",
    		max:"2099-06-16 23:59:59",
    		istime:true,istoday:false,
    		choose:function(a){
    			end1.max=a
    		}
    	};
    	laydate(end2);
	</script>
	</body>
</html>