<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>在线用户</title>
	</head>
	<body class="gray-bg">
	<form action="" method="post" name="form1" class="form-horizontal">
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="row">
            <div class="col-sm-12">
                <div class="float-e-margins">
                    <div class="ibox-content" style="padding: 0px;">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover" style="margin-bottom: 0px;">
                                <thead>
                                    <tr>
                                        <th style="width: 50px;text-align: center;">序号</th>
                                        <th style="text-align: center;">用户名称</th>
                                        <th style="text-align: center;">用户编码</th>
                                        <th style="text-align: center;">构机代码</th>
                                        <th style="text-align: center;">是否失效</th>
                                        <th style="text-align: center;">登录时间</th>
                                        <th style="text-align: center;">SessionId</th>
                                        <th style="text-align: center;">登录IP</th>
                                        <th style="text-align: center;">操 作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#list userList as user>
									<tr>
                                        <td style="text-align: center;">${user_index+1}</td>
                                        <td>${user.realName}</td>
                                        <td>${user.userCode}</td>
                                        <td>${user.organId}</td>
                                        <td>${user.expired?string('是', '否')}</td>
                                        <td>${user.lastRequest}</td>
                                        <td>${user.sessionId}</td>
                                        <td>${user.loginIp}</td>
                                        <td style="text-align: center;"><input type="button" class="btn btn-primary btn-xs" value="踢 出" onclick="reject('${user.userCode}','${user.sessionId}');"></td>
                                    </tr>  
                                    </#list>
                              </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
	function reject(userCode,sessionId){
		form1.action="${contextPath}/base/user/online/reject.do?userCode="+userCode+"&sessionId="+sessionId;
		form1.submit();
	}
	</script>
	</body>
</html>