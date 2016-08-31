<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <link href="${contextPath}/res/pub/css/demo/ziding/jquery.onoff.css" rel="stylesheet">
		 
		 <title>礼品发放统计</title>
	</head>
	<body class="gray-bg">
	<form  method="post" name="form1" id="form1" class="form-horizontal">

		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">礼品发放统计</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					<div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">门店</label>
                                <div class="col-sm-2">                                  
                                    <@select type='1' codeType="1034" fieldId="organId" fieldName="organId" defValue="${organId!''}" haveHead="false" paramName="organId" paramValue="${orgId}" props=" class='form-control ' " /> 
                                </div> 
                                <label class="col-sm-2 control-label" style=" margin:5px;">录入日期</label>
                                <div id="date" class="col-sm-2 ">
                                   <input type="text" readonly name="startDate" datatype="*" nullmsg="请选择开始日期" id="startDate" value="${startDate!''}" maxlength="30"  placeholder="开始日期" class="form-control layer-date" >                                  
                                </div>
                                <div id="date1" class="col-sm-2 ">                            
                                   <input type="text" readonly name="endDate" datatype="*" nullmsg="请选择结束日期" id="endDate" value="${endDate!''}" maxlength="30"  placeholder="结束日期" class="form-control layer-date" >
                                </div>
                            </div> 
                           <div class="form-group ">
                              <div class="col-sm-4 col-sm-offset-10">
                              <input type="hidden" id="isQuery" name="isQuery">
			  	 	 	      <input type="submit" id="but" value="查询" class="btn btn-primary btn-sm zd-btn-pd1">&nbsp;&nbsp;
                              <input type="button" onclick="excel();" id="export" value="导 出" class="btn btn-primary btn-sm zd-btn-pd1">
                      	 	 	
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
		                 		 		<th style="text-align: center;">门店</th>	
		                 		 		<th style="text-align: center;">到店礼</th>	
		                 		 		<th style="text-align: center;">订车礼</th>	
		                 		 		<th style="text-align: center;">交车礼</th>	
                                    </tr>
                                </thead>
                                <tbody id="span3">
                                	<#list list as obj>
									<tr>
                                        <td style="text-align: center;">${obj.ORGNAME}</td>
                                        <td>${obj.SHOPGIFT}</td>
                                        <td>${obj.ORDERGIFT}</td>
                                        <td>${obj.CARGIFT}</td>
                                                                                 
                                    </tr>  
                                    </#list>
                              </tbody>
               </table>
			</div>		
		</div>
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
    	
    	var demo = $("#form1").Validform({
    		beforeCheck:function(curform){
    			$("#form1").attr("action","${contextPath}/leads/report/gift/queryList.do");
    			$("#isQuery").val("Y");
    		}
    	});
    	
    	function excel(){
    		demo.submitForm(false,"${contextPath}/leads/report/gift/giftExport.do");
    	}
    	
   	 		 	
	</script>
	</body>
</html>