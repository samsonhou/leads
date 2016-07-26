   <!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>制定计划</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/search/planlist.do" method="post" name="form1" id="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">制定计划</div>
			  <div class="panel-body" style="padding-bottom: 0px;">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">                 
                            <div class="form-group">
                                <label class="col-sm-2 control-label">选择月份</label>
                                <div class="col-sm-2 ">
                                   <input type="text" readonly name="month" id="month" value="${month!''}" maxlength="30"  placeholder="开始日期" class="form-control layer-date" >
                                </div>
                                <label class="col-sm-2 control-label">机构</label>
                                <div class="col-sm-6">
                                	<input type="hidden" id="queryOrganId" value='${organId!''}'>
                                    <@organ showLevel='00' fieldId="organId" fieldName="organId" defValue="${organId!''}"   props=" class='form-control'  " /> 
                                </div>
                            </div>
                            

                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                 <button type="button" class="btn btn-primary btn-sm" onclick="search();" id="searchBtn">查 询</button>   
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
					 		<th style="text-align: center;">姓名</th>
                            <th style="text-align: center;">员工编号</th>
                            <th style="text-align: center;">组织</th>
                            <th style="text-align: center;">任务量</th>
                            <th style="text-align: center;">计划月份</th>
                            <th style="text-align: center;">时间</th>
                            <th style="text-align: center;">操作</th>                          
					 	</tr>
					 </thead>
					 <tbody>
					 	 <#list page.list as data>
                              <tr>             
                                 <td style="text-align: center; width:10%">${data.USER_NAME}</td>
                                 <td style="text-align: center; width:20%">${data.CODE}</td>
                                 <td style="text-align: center; width:20%">${data.NAME}</td>
                                 <td style="text-align: center;padding:0px 1px 0px 1px;"><input class="form-control" id="count" name ="count"  value="${data.COUNT}" /></td>
                                 <td style="text-align: center; width:15%">${data.MONTH}</td>
                                 <td style="text-align: center; width:20%">${data.CREATEDATE}</td>
                                 <td style="text-align: center; width:5%;padding:0px 1px 0px 1px;"><button type="button" class="btn btn-primary btn-xs" onclick="saveOne('${data.ID}',this,'${data.MONTH}');" >保存</button></td>
                              </tr>
                         </#list>
					 </tbody>
				</table>
			</div>	
			<@pages url="${contextPath}/leads/search/queryPlan.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />	
		</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		function search(){
		  if(formValidate()){
		    $("#searchBtn").attr("disabled","disabled").html('<i class="fa fa-spinner fa-spin f-2"></i> 处理中');
			form1.action="${contextPath}/leads/search/queryPlan.do";
			form1.submit();
		  }
		}
        var end1={
    		elem:"#month",
    		format:"YYYY-MM",
    		min:"1970-06",
    		max:"2099-06",
    		istime:false,istoday:false
    	};
    	laydate(end1);
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
			organ.initOrgan("_organTag_");
			//begin
			if(jQuery("#queryOrganId").val().length>0){
    			jQuery("#companyid").val(jQuery("#queryOrganId").val());
    		}
    		//如果queryOrganId在最后一级里面 则不触发
    		$lastSelect=jQuery("#_organTag_").find("select").last();
    		var flag=true;
    		$lastSelect.find("option").each(function(){
    			if($(this).val()==jQuery("#queryOrganId").val()){
    				flag=false;
    				return false;
    			}
    		});
    		if(!flag){
    			$lastSelect.trigger("onchange");
    		}
    		//end
		});
		
		function formValidate(){
		   var saveform = $('#form1');
    	   var lv= saveform.find('#month').val().length;
    	   if(lv==0){
    	      swal({title:"",text:"请选择月份!"});
    	      return false;
    	   }
    	   return true;
		}
		
		function saveOne(id,obj,month){
		var num= $(obj).parents('tr').children('td').eq(3).find('input').val();
		if(num.trim()==''){
		   swal({title:"",text:"任务量不能为空!"});
		   return;
		}
		var ex = /^\d+$/;
        if (!ex.test(num)) {
           swal({title:"",text:"任务量只能为整数!"});
		   return;
        } 
		
			$.ajax({  
	            type: "POST",  
	            url: "${contextPath}/leads/search/modifyPlan.do?id="+id+"&num="+num+"&month="+month,  
	            data: "" ,   
	            dataType:"text",
	            success: function(response){
	              swal({title:"",text:response});
	            },  
	            error: function(e){  
	              alert('Error: ' + e);  
	           }  
	        });  
	     }
	</script>
	</body>
</html>