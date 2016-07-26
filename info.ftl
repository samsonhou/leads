
<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		<title>详情模板</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/assign/query.do" method="post" name="form1" class="form-horizontal">
		<@token />
		<div class="container-fluid">
			
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">详情模板</div>
			  <div class="panel-body">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 	
			  	 	 	 	<!-- 添加一行 -->
			  	 	 	 	<div class="form-group">
                                <label class="col-sm-2 control-label">${client_index+1}-客户名称</label>
                                <input type="hidden" name="clientId"  value="${client.ID!'0'}">
                                <div class="col-sm-4 ">
                                    <input type="text" readonly value="${client.NAME!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label">当前处理人</label>
                                <div class="col-sm-4">
                                  <input type="text" readonly value="${client.REAL_NAME!''}" class="form-control" >
                                </div>
                            </div>
                            
                            
			  	 	 	 </div>
			  	 	 </div>
			  	 </div>
			  </div>
			</div><!-- end panel -->
			
		</div>
	</form>
	<#include "/pub/message.ftl"/>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		jQuery(document).ready(function(){
			
		});
	</script>
	</body>
</html>