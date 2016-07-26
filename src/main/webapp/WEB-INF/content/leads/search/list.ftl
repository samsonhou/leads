<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>客服录入统计</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/search/queryListByUser.do" method="post" name="form1" id="form1" class="form-horizontal">
		<input type="hidden" id="assginItems" name="assginItems">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">客服录入统计</div>
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
                                
                                <label class="col-sm-2 control-label">客服名称</label>
                                <div class="col-sm-4 ">
                                    <@select type='1' codeType="1028" defValue="${userId!''}" fieldId="userId" fieldName="userId" paramName="tablename,field" paramValue="lm_client,rank" props=" class='form-control' " />
                                </div>
                            </div>
                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                 <input type="button" onclick="search()" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
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
                                    	<th style="text-align: center;width: 50px;">序号</th>
                                        <th style="text-align: center;">客服名称</th>
                                        <th style="text-align: center;">开始日期</th>
                                        <th style="text-align: center;">截止日期</th>
                                        <th style="text-align: center;width: 120px;">录入数量</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	 <#list data as data>
                                       <tr>
                                       	<td style="text-align: center;">${data_index+1}</td>
                                        <td>${data.NAME}</td>
                                        <td>${stnextdate}</td>
                                        <td>${nextdate}</td>
                                        <td>${data.NUM}</td>
                                        </tr>
                                    </#list>
                              </tbody>
                            </table>
			</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">

		var end1={
    		elem:"#stnextdate",
    		format:"YYYY-MM-DD",
    		min:"1970-06-16 23:59:59",
    		max:"2099-06-16 23:59:59",
    		istime:false,istoday:false,
    		choose:function(a){
    			end2.min=a
    		}
    	};
    	laydate(end1);
		var end2={
    		elem:"#nextdate",
    		format:"YYYY-MM-DD",
    		min:"1970-06-16 23:59:59",
    		max:"2099-06-16 23:59:59",
    		istime:false,istoday:false,
    		choose:function(a){
    			end1.max=a
    		}
    	};
    	laydate(end2);
	
		function search(){	
		   if(formValidate()){
		      form1.action="${contextPath}/leads/search/queryListByUser.do";
		      form1.submit();
		   }	
		}
		
	    function  formValidate(){    
	        var saveform = $('#form1');
    		  	
    	     lv= saveform.find('#stnextdate').val().length;
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