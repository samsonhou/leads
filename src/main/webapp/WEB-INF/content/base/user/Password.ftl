
<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		<title>密码修改</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/base/user/editorPassword.do" method="post" name="form1" class="form-horizontal">
		<@token />

        <input type="hidden"   id="userId"   name="userId"   value="${authorUser.userId!''}" >
        <input type="hidden"   id="ispass"   name="ispass"   value="${authorUser.isPass!''}" >
        <input type="hidden"   id="password" name="password" value="123456" >
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">密码修改</div>
			  <div class="panel-body">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 	<!-- 添加一行 -->
			  	 	 	 	<div class="form-group">
                                <label class="col-sm-2 control-label">机构</label>
                                <div class="col-sm-4 ">
                                    <input type="text" readonly value="${authorUser.organName!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label">组织</label>
                                <div class="col-sm-4">
                                  <input type="text" readonly value="${authorUser.organCompanyName!''}" class="form-control" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-4 ">
                                    <input type="text" readonly value="${authorUser.realName!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label">登录名</label>
                                <div class="col-sm-4">
                                  <input type="text" readonly value="${authorUser.userName!''}" class="form-control" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">电话</label>
                                <div class="col-sm-4 ">
                                    <input type="text" readonly value="${authorUser.tel!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label">邮箱</label>
                                <div class="col-sm-4">
                                  <input type="text" readonly value="${authorUser.email!''}" class="form-control" >
                                </div>
                            </div>
                            <!-- 添加一行 -->
			  	 	 	 	<div class="form-group">
                                <label class="col-sm-2 control-label">密码</label>
                                <input type="hidden" name="clientId"  value="${client.ID!'0'}">
                                <div class="col-sm-4 ">
                                    <input type="password" id="password1"  name="password1" value="" class="form-control" >
                                </div>
                                <label class="col-sm-2 control-label">确认密码</label>
                                <div class="col-sm-4">
                                    <input type="password" id="password2"  name="password2" value="" class="form-control" >
                                </div>
                            </div>
                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                   <input type="button" onclick="reset();" value="清空" class="btn btn-primary zd-btn-pd1">
	                                   &nbsp; &nbsp; 
	                                   &nbsp; &nbsp; 
	                                   <input type="button" onclick="save();" value="保存 " class="btn btn-primary zd-btn-pd1">
	                                   
	                             </div>
                            </div>
                            
			  	 	 	 </div>
			  	 	 </div>
			  	 </div>
			  </div>
			</div><!-- end panel -->
			<h5><font color="#FF0000" >密码要求:</font></h5>
			1、长度大于8<br/>
            2、密码必须是字母大写，字母小写，数字，特殊字符中任意三个组合。
		</div>
		
	</form>
	<#include "/pub/message.ftl"/>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
	function reset(){
			form1.password1.value="";
			form1.password2.value="";
			
		}
		
		function save(){
			if(formValidate()){
			   form1.password.value=form1.password1.value;
			   var ispassold =form1.ispass.value;
			   var ispass =form1.ispass.value;
			   if(ispass==0){
			   form1.ispass.value=1;
			   }
				$.ajax({  
            	type: "POST",  
            	url: "${contextPath}/base/user/editorPassword.do",  
            	data: "ispass="+form1.ispass.value+"&userId="+form1.userId.value+"&password=" + form1.password.value ,
            	success: function(response){
            	if(ispassold==0){
            	//window.navigate("");
            	window.location.href = "${contextPath}/login.do";
            	}else{
            	swal({title:"",text:"密码修改成功!"});
            	}
            	},  
            	error: function(e){  
            	alert('Error: ' + e);  
            	}  
            	});
            
            }
		}
		
		function formValidate(){
		var  password1= form1.password1.value;
		var  password2=	form1.password2.value;
		if(password1.length===0){
		swal({title:"",text:"请输入密码"});
		return false;
		}
		if(password1.length>15){
		swal({title:"",text:"输入密码长度不能大于15"});
		return false;
		}
		if(checkPass(password1)<3){
		swal({title:"",text:"密码复杂度不够，请重新设置"});
		return false;
		}
		if(password2.length===0){
		swal({title:"",text:"请输入确认密码"});
		return false;
		}
		if(password2.length>15){
		swal({title:"",text:"输入确认密码长度不能大于15"});
		return false;
		}
		if(password1!==password2){
		swal({title:"",text:"两次密码不一致!"});
		 return false;
		}
		return true; 
		}
		function checkPass(pass){ 
		if(pass.length < 8){  return 0; } 
		var ls = 0; 
		if(pass.match(/([a-z])+/)){  ls++; }  
		if(pass.match(/([0-9])+/)){  ls++; }  
		if(pass.match(/([A-Z])+/)){   ls++; }  
		if(pass.match(/[^a-zA-Z0-9]+/)){ ls++;}  
		return ls; 
		} 


	
		jQuery(document).ready(function(){
			
		});
	</script>
	</body>
</html>