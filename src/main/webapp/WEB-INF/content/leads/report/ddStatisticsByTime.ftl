<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <link href="${contextPath}/res/pub/css/demo/ziding/jquery.onoff.css" rel="stylesheet">
		 
		 <title>滴滴来源考评</title>
	</head>
	<body class="gray-bg">
	<form  method="post" name="form1" id="form1" class="form-horizontal">

		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">滴滴来源考评</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					<div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">机构</label>
                                <div class="col-sm-2">                                  
                                    <@select type='1' codeType="1034" fieldId="organId" fieldName="organId" defValue="${organId!''}" haveHead="false" paramName="organId" paramValue="${orgId}" props=" class='form-control ' " /> 
                                </div> 
                                <label class="col-sm-2 control-label" style=" margin:5px;">选择日期</label>
                                <div id="date" class="col-sm-2 ">
                                   <input type="text" readonly name="startDate" datatype="*" nullmsg="请选择开始日期" id="startDate" value="${startDate!''}" maxlength="30"  placeholder="开始日期" class="form-control layer-date" >                                  
                                </div>
                                <div id="date1" class="col-sm-2 ">                            
                                   <input type="text" readonly name="endDate" datatype="*" nullmsg="请选择结束日期" id="endDate" value="${endDate!''}" maxlength="30"  placeholder="结束日期" class="form-control layer-date" >
                                </div>
                            </div>
                                 
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">来源</label>
                                <div class="col-sm-2">                                  
                                    <@select type='1' codeType="1043" fieldId="fromType" fieldName="fromType" defValue="${fromType!''}" haveHead="false" props=" class='form-control ' " /> 
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
		                 		 		<th style="text-align: center;">分类</th>	
		                 		 		<th style="text-align: center;">机构</th>	
		                 		 		<th style="text-align: center;">总线索</th>	
		                 		 		<th style="text-align: center;">邀约平均时长(天)</th>	
		                 		 		<th style="text-align: center;">到店平均时长(天)</th>	
		                 		 		<th style="text-align: center;">进件平均时长(天)</th>	
		                 		 		<th style="text-align: center;">审核通过平均时长(天)</th>	
		                 		 		<th style="text-align: center;">交车平均时长(天)</th>	
		                 		 		<th style="text-align: center;">投诉平均时长(天)</th>	
		                 		 		<th style="text-align: center;">备注</th>	
                                    </tr>
                                </thead>
                                <tbody id="span3">
                                	<#list list as obj>
									<tr>
										<td>${obj.CATALOG}</td>
                                        <td>${obj.NAME}</td>
                                        <td>${obj.TOTNUM}</td>
                                        <td>${obj.YYAT}</td>
                                        <td>${obj.DDAT}</td>
                                        <td>${obj.JJAT}</td>
                                        <td>${obj.TGAT}</td>
                                        <td>${obj.TCAT}</td>
                                        <td>${obj.TSAT}</td>
                                        <td></td>
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
    			$("#form1").attr("action","${contextPath}/leads/report/ddStatisticsByTime.do");
    			$("#isQuery").val("Y");
    		}
    	});
    	
    	function excel(){
    		demo.submitForm(false,"${contextPath}/leads/report/ddStatisticsByTimeExport.do");
    	}
    	
    	jQuery.fn.rowspan = function(colIdx) { 
		    return this.each(function(){
		    var that;
		    $('tr', this).each(function(row) {
		    $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
		    if(that!=null && $(this).html() == $(that).html()) {
		    rowspan = $(that).attr("rowSpan");
		    if (rowspan == undefined) {
		    $(that).attr("rowSpan",1);
		    rowspan = $(that).attr("rowSpan"); }
		    rowspan = Number(rowspan)+1;
		    $(that).attr("rowSpan",rowspan);
		    $(this).hide();
		    }else{
		      that = this;
		     }
		   });
		  });
		 });
		}
		$(function() {
		  $("#table1").rowspan(0);
		}); 
   	 		 	
	</script>
	</body>
</html>