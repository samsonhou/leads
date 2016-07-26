<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>用户管理</title>
	</head>
	
	<body class="gray-bg">
	<form action="${contextPath}/base/user/query.do" method="post" name="form1" class="form-horizontal">
		<input type="hidden" id="assginItems" name="assginItems">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">用户查询</div>
			  <div class="panel-body">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 <div class="form-group ">
			  	 	 	       <label class="col-sm-2 control-label">登录名</label>
                                <div class="col-sm-4 ">
                                  <input id="userCode" name="userCode" class="form-control" type="text" value="${(findObj.userCode)!''}">
                                </div>
                                <label class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-4 ">                              
                                  <input id="realName" name="realName" class="form-control" type="text" value="${(findObj.realName)!''}">
                                </div>
                          </div>
                           <div class="form-group ">
			  	 	 	       <label class="col-sm-2 control-label">员工编码</label>
                                <div class="col-sm-4 ">
                                  <input id="jzCode" name="jzCode"  class="form-control" type="text" value="${(findObj.jzCode)!''}" placeholder="员工编码" >
                                </div>
                                 <label class="col-sm-2 control-label">是否有效</label>
                                <div class="col-sm-4">
                                <@select  fieldId='status' codeType='1000' fieldName='status' defValue='${(findObj.status)!""}' props=" class='form-control '  "/>   
                              </div>
                          </div>
                          <div class="form-group">    
                              <label class="col-sm-2 control-label" >机构</label>
                              <div class="col-sm-4">                                  
                                 <@select type='1' codeType="1034" fieldId="organId1" fieldName="organId1" defValue="${findObj.organId!''}" paramName="organId" paramValue="${organId1!''}" haveHead="false"  props=" class='form-control' " /> 
                              </div>
                               <label class="col-sm-2 control-label" >角色</label>
                              <div class="col-sm-4">                                  
                                 <@select type='1' codeType="1039" fieldId="roleId" fieldName="roleId" defValue="${roleId!''}" paramName="roleId" paramValue="00" props=" class='form-control' " /> 
                              </div>
                            </div>
                          
                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                 <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                 &nbsp;&nbsp;<input type="button" onclick="clearForm(this.form)" value="清空" class="btn btn-primary btn-sm zd-btn-pd1">
	                                 &nbsp;&nbsp;<input type="button" onclick="increased();" value="新 增 " class="btn btn-primary btn-sm zd-btn-pd1">
	                                 
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
                                        <th style="width: 60px;text-align: center; where:50px; ">序号</th>
                                        <th style="text-align: center;">登录名</th>
                                        <th style="text-align: center;">姓名</th>
                                        <th style="text-align: center;">员工编码</th>
                                        <th style="text-align: center;">机构</th>
                                        <th style="text-align: center;">组织</th>
                                        <th style="text-align: center;">角色</th>
                                        <th style="text-align: center; where:50px;">是否有效</th>
                                        <th style="text-align: center;">创建时间</th>
                                        <th style="text-align: center;">操作</th>
                                    </tr>
                                </thead>
                                
                                
                                <tbody>
                                	<#list page.list as userinfo >
                                	
									<tr>
                                        <td style="text-align: center;">${userinfo_index+1}</td>
                                        <td style="text-align: left;">${userinfo.userCode}</td>
                                        <td style="text-align: left;">${userinfo.realName}</td>
                                        <td style="text-align: center;">${userinfo.jzCode}</td>
                                        <td style="text-align: left;">${userinfo.organName}</td>
                                        <td style="text-align: left;">${userinfo.organCompanyName}</td>
                                        <td style="text-align: left;">${userinfo.roleName}</td>
                                        <td style="text-align: center;">${(userinfo.status=="1")?string('是','否')}</td>
                                        <td style="text-align: center;">${userinfo.createDate?string('yyyy-MM-dd ')!""}</td>
                                        <td style="text-align: center;">
                                        <a href="#" onclick="doEditorPost('${userinfo.userId}')">编辑</a>&nbsp;&nbsp; 
                                        <a href="#" onclick="doEditorPasswordPost('${userinfo.userId}')">密码重置</a>&nbsp;&nbsp; 
                                        <a href="#" onclick="assignSign('${userinfo.userId}')">菜单组</a>&nbsp;&nbsp; </td>
                                    </tr>  
                                    </#list>
					 </tbody>
				</table>
			</div>	
			<@pages url="${contextPath}/base/user/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />	
		</div>
	</form>
	
	<!-- 弹出框 -->
	<div class="modal fade" id="messageModal">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">提示信息</h4>
	      </div>
	      <div class="modal-body text-center">
	        <p id="showAlertInfo"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">确&nbsp;&nbsp;定</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新增用户</h4>
      </div>
      <form name='saveform' id='saveform'  class="form-search" method="post" >
      <div class="modal-body" style="height:350px;"><!--高度根据表单高度相应调整-->
      <input id="userId" name="userId" type="hidden" value="0">
                   <div class="ibox-content " >
                    <div id="divzhuzhi" class="form-group ziding-ibox-modal model_alert_1">
                                <label class="col-sm-2 control-label model_left_z">组织</label>
                                <div class="col-sm-10" style=" margin-bottom:0" >
                                  <@select type='1' codeType="1034" fieldId="organId" fieldName="organId" defValue="${findObj.organId!''}" paramName="organId" paramValue="${organId1!''}" haveHead="false"  props=" class='form-control' " /> 
                                </div>
                    </div>
                            	<div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label model_left_z">登录名</label>
                                <div class="col-sm-10 ">
                                 <input type="text" name="userCode"  id="userCode" class="form-control" placeholder="登录名"  onblur="isCheckUserOnly()">
                                </div>
                                
                            </div>
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label model_left_z">姓名</label>
                                <div class="col-sm-4 ">
                                 <input type="text" name="realName"  id="realName" class="form-control" >
                                </div>
                                <label class="col-sm-2 control-label model_left_z">员工编码</label>
                                <div class="col-sm-4 ">
                                  <input id="jzCode" name="jzCode" readonly="readonly" type="text" value="${(findObj.organCode)!''}" class="form-control" >
                                </div>
                            </div>	
						
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label model_left_z">电话</label>
                                <div class="col-sm-10 ">
                                 <input type="text" name="tel"  id="tel" class="form-control" placeholder="电话:01088888888,010-88888888,0955-7777777,13844448888 " >
                                </div>
                                
                            </div>	
                             <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label model_left_z">邮箱</label>
                                <div class="col-sm-10 ">
                                 <input type="text" name="email"  id="email" class="form-control" placeholder="邮箱:XXXX@126.com" >
                                </div>
                                
                            </div>                               
    
    
                             <div class="form-group ziding-ibox-modal">
                             
                                <label class="col-sm-2 control-label model_left_z">有效否</label>
                                <div class="col-sm-10 ">
                                  <@select  fieldId="status" codeType="1000" haveHead="false"  fieldName="status" defValue="${(findObj.status)!''}" props=" class='form-control m-b'  " />
                                </div>
                            </div>
                            <div class="form-group ziding-ibox-modal">
                             
                                <label class="col-sm-2 control-label model_left_z">初始密码</label>
                                <div class="col-sm-10 ">
                                  <input type="text" name="password"  id="password" class="form-control" placeholder="密码，不输入为不修改 " >
                               </div>
                            </div>
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label model_left_z">角色</label>
                                <div  id="userRoles" class="col-sm-10 ">
                                </div>
                            </div>
                           
                          																		
					</div>
					<br/>
      </div>
     </form>
      <div class="modal-footer">
      	<input type="hidden" data-seq='00' value="00" id="_defOrganId_">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" id="savebut" class="btn btn-primary" onclick="save()">保存</button>
      </div>
    </div>
  </div>
</div>
	
	
	
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
	    var isboot=true;
		function search(){
			form1.action="${contextPath}/base/user/queryList.do";
			form1.submit();
		}
		//分选分配
		function assignMult(){
			var items=getCheckItem();
			if(items.length<1){
				$("#showAlertInfo").text('请选择需要分配的任务');
	    		$('#messageModal').modal('show');
			}else{
				var params='';
				for(var i=0;i<items.length;i++){
					params+=items[i];
					if(i!=(items.length-1)){
						params+=",";
					}
				}
				jQuery("#assginItems").val(params);
				openNewTab("${contextPath}/base/user/toAssgin.do?assginItems="+params,"人员菜单组分配");
			}
		}
		//单个分配
		function assignSign(id){
			openNewTab("${contextPath}/base/user/toAssgin.do?userId="+id,"人员菜单组分配");
		}
		function save(){
			if(formValidate()){
			saveform.action="${contextPath}/base/user/save.do";
			saveform.submit();
			}
		}
		function formValidate(){
			var saveform = $('#saveform');
	        var lv= saveform.find('#realName').val().length;
	        if(lv==0){
	        	swal({title:"",text:"用户名称不能为空!"});
	           return false;
	         }
	         if(lv>20){
	        	 swal({title:"",text:"用户名称长度不能大于 20!"});
	            return  false;
	         }
	       
            lv=saveform.find('#userCode').val().length;
	        if(lv==0){
	          swal({title:"",text:"登录名不能为空 !"});
	          return  false;
	        }
	        if(lv>20){
	        	swal({title:"",text:"登录名长度不能大于20 !"});
	            return  false;
	        }
	        lv=saveform.find('#userCode').val().length;
	        if(lv==0){
	          swal({title:"",text:"地址不能为空 !"});
	          return  false;
	        }
	        if(lv>100){
	        	swal({title:"",text:"地址简称长度不能大于100 !"});
	            return  false;
	        }
	         lv=saveform.find('#tel').val();
	        if(lv.length==0){
	          swal({title:"",text:"电话不能为空 !"});
	          return  false;
	        }
	        if(!checkPhone(lv)){
	        	swal({title:"",text:"电话格式不正确 !"});
	            return  false;
	        }
	         lv=saveform.find('#email').val()
	        if(lv.length==0){
	          swal({title:"",text:"邮箱不能为空 !"});
	          return  false;
	        }
	        if(!isEmail(lv)){
	        	swal({title:"",text:"邮箱格式不正确!"});
	            return  false;
	        }
	        
	       return  true ;  
		}
		
		function checkPhone(str){
           var reg = /^0\d{2,3}-?\d{7,8}$/;
           var re = /^1\d{10}$/;
           if(re.test(str)||reg.test(str)){
        		return true
    		}else{
        		return false
    	 	}
		}
		function isEmail(str){ 
		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
		return reg.test(str); 
		} 
		// 新增
		function increased() {  
			$.ajax({  
            type: "POST",  
            url: "${contextPath}/base/user/increased.do",  
            data: "",   
            success: function(response){
            	var listRole=response.temproles
	            var roles='';
	            for(var i=0;i<listRole.length;i++){
	              var obj=listRole[i];
	              roles+='<label><input id="'+obj['id']+'" name="roles" type="checkbox" value="'+obj['id']+'" />'+obj['roleName']+' </label> &nbsp;&nbsp;';	
	           }
            	var saveform = $('#saveform');
                saveform.find('#userId').val("0");
                /*
                if(null!=response.organName&&response.organName.length>2){
                 saveform.find('#organId').val(response.organId);
                 saveform.find('#jzCode').val(response.jzCode);
                  var html='<label class="col-sm-2 control-label model_left_z">组织</label><div class="col-sm-10" style=" margin-bottom:0" ><div class="col-sm-10 ">'
                	  +response.organName+'&nbsp;&nbsp;&nbsp;&nbsp;'+response.organCompanyName+
                  '</div><input type="hidden" name="organId"  id="organId" value="'+response.organId+'"></div>';
                 $('#divzhuzhi').html(html);
                 isboot=false;
                }else{
                  //saveform.find('#jzCode').val("");
                  //jQuery("#_defOrganId_").val('00').attr('data-seq','00');
                } */
                //saveform.find('#organId').val(response.organId);
                saveform.find('#jzCode').val("");
                saveform.find('#realName').val("");
                saveform.find('#userCode').val("");
                saveform.find('#tel').val("");
	            saveform.find('#email').val("");
                saveform.find('#Password').val("");
                saveform.find('#status').find("option[value='1']").attr("selected",true);
                saveform.find('#userRoles').html(roles);  
               
                var myModal = $('#myModal');
                myModal.find('.modal-title').text('新增用户 ');
                myModal.modal('show');  //打开新增界面	

            },  
            error: function(e){  
            alert('Error: ' + e);  
            }  
            }); 
 
            } 
		
		function doEditorPasswordPost(id){
			$.ajax({  
	            type: "POST",  
	            url: "${contextPath}/base/user/editorPassword.do",  
	            data: "userId=" + id+"&password=jZ@32145",
	            success: function(response){
	            $("#showAlertInfo").text('密码重置成功， 为jZ@32145');
		    	$('#messageModal').modal('show');
	            },  
	            error: function(e){  
	            //alert('Error: ' + e);  
	            }  
	            });  
		}

		//编辑 
		function doEditorPost(id) {  
	            $.ajax({  
	            type: "POST",  
	            url: "${contextPath}/base/user/editor.do",  
	            data: "userId=" + id,
	            success: function(response){
	             var listRole=response.temproles
	             var roles='';
	              for(var i=0;i<listRole.length;i++){
	            		var obj=listRole[i];
	            		if(obj['status']==="99"){
	            			roles+='<label><input id="'+obj['id']+'" name="roles" type="checkbox" checked="checked"  value="'+obj['id']+'" />'+obj['roleName']+' </label>&nbsp;&nbsp;';		
	            		}else{
	            			roles+='<label><input id="'+obj['id']+'" name="roles" type="checkbox"   value="'+obj['id']+'" />'+obj['roleName']+' </label>&nbsp;&nbsp;';		
	            		}
	            		
	            	}
	             
	             var saveform = $('#saveform');
	             saveform.find('#userId').val(response.userId);
	             /*
	             if(null!=response.organName&&response.organName.length>2){
	                 saveform.find('#organId').val(response.organId);
	                 saveform.find('#jzCode').val(response.jzCode);
	                  var html='<label class="col-sm-2 control-label model_left_z">组织</label><div class="col-sm-10" style=" margin-bottom:0" ><div class="col-sm-10 ">'
	                	  +response.organName+'&nbsp;&nbsp;&nbsp;&nbsp;'+response.organCompanyName+
	                  '</div></div>';
	                 $('#divzhuzhi').html(html);
	                 isboot=false;
	                }else{
	                  saveform.find('#jzCode').val(response.jzCode);
	                  jQuery("#_defOrganId_").val(response.organId).attr('data-seq',response.sysOrganCode);
	                }*/
				 saveform.find('#jzCode').val(response.jzCode);
				 saveform.find('#organId').val(response.organId);
	             saveform.find('#realName').val(response.realName);
	             saveform.find('#userCode').val(response.userCode);
	             saveform.find('#tel').val(response.tel);
	             saveform.find('#email').val(response.email);
	             saveform.find('#status').find("option[value='"+response.status+"']").attr("selected",true);
	             saveform.find('#userRoles').html(roles); 
	             
	             var myModal = $('#myModal');
	             myModal.find('.modal-title').text('编辑用户');
	             myModal.modal('show');  //打开编辑界面
	             $('#savebut').removeAttr("disabled");
	            },  
	            error: function(e){  
	            alert('Error: ' + e);  
	            }  
	            });  
	     }
		function getCheckItem(){
			var items=[];
			jQuery("[name = assginItem]:checkbox").each(function(){
				if(this.checked){
					items.push(jQuery(this).val());
				}
			});
			return items;
		}
		function isCheckUserOnly(){ 
			var userCode=$.trim($('#saveform').find('#userCode').val());
			var userId=$('#saveform').find('#userId').val();
			$.ajax({  
	            type: "POST",  
	            url: "${contextPath}/base/user/checkuseronly.do",  
	            data: {userId:userId,userCode:userCode},
	            success: function(response){
	            if(null!=response.ischeck&&response.ischeck==='NO'){
			    	swal({title:"",text:"登录用户名已存在,请重新输入!"});
			    	$('#savebut').attr('disabled',"true")
	            }else if(null!=response.ischeck&&response.ischeck==='OK'){
	            	$('#savebut').removeAttr("disabled");
	            }
	            },  
	            error: function(e){  
	           
	            }  
	            });  
			
		}
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
			jQuery("#myModal").on('show.bs.modal',function(){
				jQuery("#_organTag_").find("select").val('');
				jQuery("#_organTag_").find("select").each(function(){
					if(jQuery(this).attr('data-parent') == '1'){
					}else{
						jQuery(this).remove();
					}
				});
				if(isboot){
				var $defOrganId=jQuery("#_defOrganId_");
				//jQuery("#organId").val($defOrganId.val()).attr('data-seq',$defOrganId.attr('data-seq'));
				//organ.initOrgan();
			    }
			});
		});
		
	</script>
	</body>
</html>