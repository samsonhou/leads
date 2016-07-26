<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>详细页面</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/base/user/queryList.do" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">自由编辑表格</div>
			  <div class="panel-body">
			  	 1111
			  </div>
			</div>
			
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">多条编辑表格 <span class="pull-right" style="margin-right: 6px;"><input type="button" class="btn btn-primary btn-xs" value="添 加" ></span></div>
			  	 <table class="table table-bordered table-striped">
					 <thead>
					 	<tr>
					 		<th style="width: 50px;" class="text-center">序号</th>
					 		<th class="text-center">标题</th>
					 		<th class="text-center">标题</th>
					 		<th class="text-center">标题</th>
					 		<th class="text-center">标题</th>
					 		<th style="width: 80px;" class="text-center">操作</th>
					 	</tr>
					 </thead>
					 <tbody>
					 	<tr>
					 		<td class="text-center">1</td>
					 		<td>
					 			<input class="form-control input-sm" type="text" placeholder=".input-sm">
					 		</td>
					 		<td><input class="form-control input-sm" type="text" placeholder=".input-sm"></td>
					 		<td><input class="form-control input-sm" type="text" placeholder=".input-sm"></td>
					 		<td><select class="form-control input-sm"><option>11111</option><option>22222</option></select></td>
					 		<td class="text-center"><input type="button" class="btn btn-primary btn-xs" value="删 除" ></td>
					 	</tr>
					 </tbody>
				</table>
			</div>

			
		</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
	</script>
	</body>
</html>