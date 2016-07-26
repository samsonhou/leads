<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>滴滴合作数据报表</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/report/ddindex.do" method="post" name="form1" id="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">报表查询 </div>
				<div class="panel-body" style="padding-bottom: 0px;">
					<div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label" style=" margin:5px;">起止日期</label>
                                <div class="col-sm-2 ">
                                   <input type="text" readonly name="stnextdate" id="stnextdate" value="${stnextdate!''}" maxlength="30"  placeholder="开始日期" class="form-control layer-date" >
                                </div>
                                <div class="col-sm-2">
                                    <input type="text" readonly name="nextdate" id="nextdate" value="${nextdate!''}" maxlength="30"  placeholder="结束日期" class="form-control layer-date" >
                                </div>
                                <label class="col-sm-2 control-label" style=" margin:5px;">定金支付情况</label>
                                <div class="col-sm-2">
                                	<@select type='1' codeType="1041" fieldId="depositStatus" fieldName="depositStatus" defValue="${depositStatus!''}" props=" class='form-control' " />
                                </div>
								
                            </div>
                            
                            <div class="form-group">
                            	<label class="col-sm-2 control-label" style=" margin:5px;">来源</label>
                                <div class="col-sm-2">                                  
                                    <@select type='1' codeType="1043" fieldId="fromType" fieldName="fromType" defValue="${fromType!''}" haveHead="false" props=" class='form-control ' " /> 
                                </div>
                            </div>	 
                            
                            <div class="form-group">
                            	<div class="col-sm-4 col-sm-offset-10">
								<input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                   &nbsp;&nbsp;&nbsp;<input type="button" onclick="excel()" value="导 出" class="btn btn-primary btn-sm zd-btn-pd1">
								</div>
                            </div>
                             	 	 	 	
			  	 	 	 </div>
			  	 	 </div>
			  	 	 </div>
				</div>
			</div>
			
			<div class="panel panel-default table-responsive ziding-td">
				<table class="table table-condensed table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th style="text-align: center;">分公司</th>
                                        <th style="text-align: center;">线索数量</th>
                                        <th style="text-align: center;">邀约数量</th>
                                        <th style="text-align: center;">到店数量</th>
                                        <th style="text-align: center;">风控进件数量</th>
                                        <th style="text-align: center;">风控批复数量</th>
                                        <th style="text-align: center;">客户提车数量</th>
                                    </tr>
                                </thead>
                                <tbody >
                                	<#list detail as detail>
									<tr >
                                        <td>${detail.ABBR_NAME}</td>
                                        <td>${detail.TOTNUM}</td>
                                        <td>${detail.YYNUM}</td> 
                                        <td>${detail.DDNUM}</td>
                                        <td>${detail.JJNUM}</td>
                                        <td>${detail.TGNUM}</td>
                                        <td>${detail.TCNUM}</td>                           
                                    </tr>  
                                    </#list>
									<#if detail?size gt 0 >
									<tr >
                                        <td>${de.ABBR_NAME}</td>
                                        <td>${de.TOTNUM}</td>
                                        <td>${de.YYNUM}</td> 
                                        <td>${de.DDNUM}</td>
                                        <td>${de.JJNUM}</td>
                                        <td>${de.TGNUM}</td>
                                        <td>${de.TCNUM}</td>                           
                                    </tr> 
                                   </#if> 
                              </tbody>
                            </table>
			</div>
			
		</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>

	<script type="text/javascript">
	
		function search(){
			if(formValidate()){
			form1.action="${contextPath}/leads/report/ddindex.do";
			form1.submit();
			}
		}
		
		var end1={
    		elem:"#stnextdate",
    		//format:"YYYY/MM/DD hh:mm:ss",
    		format:"YYYY-MM-DD",
    		min:"1970-06-16",
    		max:"2099-06-16",
    		istime:false,istoday:true,
    		choose:function(a){
    			end2.min=a
    		}
    	};
    	laydate(end1);
		var end2={
    		elem:"#nextdate",
    		//format:"YYYY/MM/DD hh:mm:ss",
    		format:"YYYY-MM-DD",
    		min:"1970-06-16",
    		max:"2099-06-16",
    		istime:false,istoday:true,
    		choose:function(a){
    			end1.max=a
    		}
    	};
    	laydate(end2);
    	function excel(){
    		if(formValidate()){
    			stnextdate = $("#stnextdate").val();	// 开始时间
    			nextdate = $("#nextdate").val();		// 结束时间
    			
    			form1.action = "${contextPath}/leads/report/excelList.do";
    			form1.submit();
    			//location.href = "${contextPath}/leads/report/excelList.do?stnextdate=" + stnextdate + "&nextdate=" + nextdate+"&depositStatus="+$("#depositStatus").val();
    		}			
		}
    	function  formValidate(){
    		 var saveform = $('#form1');
    		 var lv= saveform.find('#stnextdate').val().length;
    	     if(lv==0){
    	       swal({title:"",text:"开始日期不能为空!" });
    	       return false;
    	      }
    	    lv= saveform.find('#nextdate').val().length;
    	    if(lv==0){
	        	swal({title:"",text:"结束日期不能为空!" });
	           return false;
	         }
    	    return true;
    	}
	</script>
	</body>
</html>