<!DOCTYPE html>
<html lang="en">
<head>
	<#include "/pub/header_res.ftl"/>
	<title>花生好车（捷众普惠）线索管理平台</title>
	<link href="${contextPath}/res/pub/css/login.min.css" rel="stylesheet">

</head>
<body class="signin">
    <div class="signinpanel">
        <div class="row">
            <div class="col-sm-7">
                <div class="signin-info">
                    <div class="logopanel m-b">
                        <h1>花生好车（捷众普惠）租车</span></h1>
                    </div>
                    <div class="m-b"></div>
                    <h4>欢迎使用 <strong>花生好车（捷众普惠）线索管理系统</strong></h4>
                    <ul class="m-b">
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 线索管理</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 信息追踪</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 管理分配</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 灵活操作</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 建议使用google浏览器</li>
                    </ul>
                </div>
            </div>
            <div class="col-sm-5">
                <form role="form"  method="post" action="${contextPath}/j_spring_security_check" >                    
                <h4 class="no-margins">用户登录</h4>
                    <p class="m-t-md text-danger"  id="errorTip"></p>
                    <input type="text" class="form-control uname" style="color:#000000" placeholder="用户名"  name="j_username"  required/>
                    <input type="password" class="form-control pword m-b"  style="color:#000000" placeholder="密码"  name="j_password" required/>
                    <button  type="submit" class="btn btn-success btn-block">登录</button>
                </form>
            </div>
        </div>
        <div class="signup-footer">
            <div class="pull-left">
                &copy; 2015-${(.now?date)?string('yyyy')} 花生好车（捷众普惠）汽车租赁
            </div>
        </div>
    </div>
    
	<div class="modal fade" id="errorWindown" tabindex="-1" role="dialog">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title"><font color="#000">错误提示</h4>
	      </div>
	      <div class="modal-body" style="height: 250px;overflow-y:auto;">
	        <p id="errorInfo" style="color:#000"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-success" data-dismiss="modal">关 闭</button>
	      </div>
	    </div>
	  </div>
	</div>
	<textarea rows="1" cols="1" id="errMsg" style="display: none;">${(Session['SPRING_SECURITY_LAST_EXCEPTION'].message)!""}</textarea>
	<#include "/pub/footer_res.ftl"/>
	<script type="text/javascript">
		try{
			if(top!=this){
				window.top.location.href="index.jsp";
			}
		}catch(e){
		}
		var error=jQuery("#errMsg").val();
		jQuery(function(){
			if(error.length<10){
				jQuery("#errorTip").text(error);
			}else{
				jQuery("#errorInfo").text(error);
				jQuery('#errorWindown').modal("show");
			}
		});
	</script>
</body>
</html>