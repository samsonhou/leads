<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <style type="text/css">
		 	.textInput{width:100%;border:1px solid#ccddff;height:18px;}
		 </style>
		 <title>菜单管理</title>
	</head>
	<body class="gray-bg">
	<div class="container-fluid">
	<div class="panel panel-default" style="margin-top: 1px;">
			<div class="panel-heading">菜单管理</div>
            <div class="panel-body" style="padding: 0px;">
            <div class="col-sm-4" style="padding: 0px;">
                <div class="float-e-margins">
                 <div class="title">
                     <h5>菜单URL</h5>
                 </div>
                 <div class="ibox-content" style="padding: 15px 1px 1px;">
                       <div class="panel panel-default table-responsive ziding-td">
                            <table class="table table-condensed table-bordered table-striped table-hover" >
                                <thead>
                                    <tr>
                                      <th style="text-align: center;width:40px; " >编号</th>
                                      <th style="text-align: center;width:40px; " >选择</th>
                                      <th style="text-align: center;">菜单名称</th>
                                      <th style="text-align: center;">备注信息</th>
                                      <th style="text-align: center;width:40px; " >
                                      	 <button type="button" class="btn btn-danger btn-xs" onclick="increased();" >添加</button>
                                      </th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#list page.list as codeType >
									<tr id='${codeType.moduleId}'>
                                        <td style="text-align: center;">${codeType_index+1}</td>
                                        <td style="text-align: center;"><input type="checkbox" name="checkboxmodule" id="checkboxmodule${codeType.moduleId}" onclick="selcheckbox(this)"  data-id="${codeType.moduleId}" data-name="${codeType.moduleName}" ></td>
                                        <td>${codeType.moduleName}</td>
                                        <td> ${codeType.remark} </td>
                                        <td><button type="button" class="btn btn-primary btn-xs" onclick="doEditorModurlePos('${codeType.moduleId}');" >编辑</button></td>
                                    </tr>  
                                    </#list>
                             </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 用户组菜单URL 关系保存 -->
            <form action="${contextPath}/base/modurleurl/queryList.do" method="post" name="form1" class="form-horizontal">
             <div class="col-sm-4" style="padding: 0px;">
                <div class="float-e-margins">
                 <div class="title">
                     <h5>组菜单</h5>
                 </div>
                 <div class="ibox-content" style="padding: 15px 1px 1px;">
                  <div class="panel panel-default table-responsive ziding-td">
                  <input id="group_module_id" name="group_module_id" type="hidden" value="0">
                  <input id="module_group_id" name="module_group_id" type="hidden" value="0">
                  <input id="moduleAndgroupName" name="moduleAndgroupName" type="hidden" value="">
                 <table id="group_modur"  class="table table-condensed table-bordered table-striped table-hover" >
                                <thead>
                                    <tr>
                                        <th style="text-align: center;width:40px; ">编号</th>
                                        <th style="text-align: center;">菜单名称</th>
                                        <th style="text-align: center;width:45px; ">排序</th >
                                        <th style="text-align: center;width:40px; " >
                                        	<button type="button" class="btn btn-danger btn-xs" onclick="savemodurlegroup();" >保存</button>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                             	</tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
           
            <!--用户组  -->
             <div class="col-sm-4" style="padding: 0px;">
                <div class="float-e-margins">
                 <div class="title">
                     <h5>菜单组名称</h5>
                 </div>
                 <div class="ibox-content" style="padding: 15px 1px 1px;">
                      <div class="panel panel-default table-responsive ziding-td">
                            <table class="table table-condensed table-bordered table-striped table-hover" >
                                <thead>
                                    <tr>
                                      <th style="text-align: center; width:40px " >选择</th>
                                      <th style="text-align: center;" >名称</th>
                                      <th style="text-align: center; width:40px " >排序</th>
                                      <th style="text-align: center;" >描述</th>
                                      <th style="text-align: center; width:40px " >
                                      	<button type="button" class="btn btn-danger btn-xs" onclick="groupIncreased();"  >添加</button>
                                      </th>
                                    </tr>
                                </thead>
                                <tbody>
                              
                                	<#list groupPage as group >
									<tr id='${group.groupId}'>
                                        <td style="text-align: center;"><input type="checkbox" name="checkboxgroup" id="checkboxgroup${group.groupId}"  onclick="groupPeizhieditor('${group.groupId}','${group.groupName}')"  data-id="${group.groupId}" data-name="${group.groupName}" ></td>
                                        <td><a href="#" onclick="groupPeizhieditor('${group.groupId}','${group.groupName}')" >
                                          ${group.groupName} </a>
                                        </td>
                                        <td> ${group.groupOrder} </td>
                                        <td> ${group.remark} </td>
                                        <td><button type="button" class="btn btn-primary btn-xs" onclick="doEditorGroupPos('${group.groupId}');" >编辑</button></td>
                                    </tr>  
                                    </#list>
                                   
                             </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
	 </form>
	</div>  
  </div>			
</div>
	
		<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">菜单新增</h4>
      </div>
       <form name='saveform' id='saveform'  class="form-search" method="post" >
      <div class="modal-body" style="height:230px;"><!--高度根据表单高度相应调整-->
     <input id="moduleId" name="moduleId" type="hidden" value="0">
                   <div class="ibox-content ">
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">菜单名称</label>
                                <div class="col-sm-10 ">
                                <input id="moduleName" name="moduleName"  class="form-control" type="text" placeholder="输入菜单名称" >
                                </div>
                            </div>			
                               <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">菜单URL</label>
                                <div class="col-sm-10 ">
                                    <textarea rows="2" id="moduleUrl" name="moduleUrl" class="form-control"  placeholder="输入备注信息"  ></textarea>
								</div>																	
                            </div>
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">备注信息</label>
                                <div class="col-sm-10 ">
                                    <textarea rows="3" id="remark" name="remark" class="form-control"  placeholder="输入备注信息"  ></textarea>
								</div>																	
                            </div>			
                            																		
			</div>
      </div>               
     
     </form>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="save()">保存</button>
      </div>
    </div>
  </div>
</div>
	
	<!-- Modal 用户组管理  -->
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">用户组管理</h4>
      </div>
      <form name='savegroupform' id='savegroupform'  class="form-search" method="post" >
      <div class="modal-body" style="height:230px;"><!--高度根据表单高度相应调整-->
        <input id="groupId" name="groupId" type="hidden" value="0">
                   <div class="ibox-content ">
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">组名称</label>
                                <div class="col-sm-10 ">
                                <input id="groupName" name="groupName"  class="form-control" type="text" placeholder="类型编码" >
                                </div>
                            </div>
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">序号</label>
                                <div class="col-sm-10 ">
                                <input id="groupOrder" name="groupOrder"  class="form-control" type="text" placeholder="类型编码" >
                                </div>
                            </div>			
                           
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">描述</label>
                                <div class="col-sm-10 ">
                                    <textarea rows="3" id="remark" name="remark" class="form-control"  placeholder="用户组描述"  ></textarea>
								</div>																		
                            </div>			
                            																		
					</div>
      </div>       
     </form>
       <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="savegroup()">保存</button>
      </div>
    </div>
  </div>
</div>
	
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
	function increased() {  
        var myModal = $('#myModal');
        myModal.find('.modal-title').text('新增菜单');
        myModal.find('#moduleId').val('0');
        myModal.find('#moduleName').val("");
        myModal.find('#moduleUrl').val("");
        myModal.find('#remark').val("");
        myModal.modal('show');  //打开新增界面 
        } 
	function groupIncreased() {  
        var myModal = $('#myModal2');
        myModal.find('.modal-title').text('新增用户组');
        myModal.find('#groupId').val('0');
        myModal.find('#groupName').val("");
        myModal.find('#groupOrder').val("");;
        myModal.find('#remark').val("");
        myModal.modal('show');  //打开新增界面 
        } 
		function search(){
			form1.action="${contextPath}/base/modurleurl/queryList.do";
			form1.submit();
		}
		function savemodurlegroup(){
			form1.action="${contextPath}/base/modurleurl/savemodurlegroup.do";
			form1.submit();
		}
		
		
		function groupPeizhieditor(id,name){
			
			$("input[name='checkboxgroup']").each(function(){
				   $(this).prop("checked",false);
				   $(this).prop("disabled",false);
			});  
			var objcheck= $("input[id=checkboxgroup"+id+"]");
			objcheck.prop("checked",true);
			objcheck.prop("disabled",true);
			$('#group_module_id').val(id);
			$('#moduleAndgroupName').val(name);
			$('#module_group_id').val("0");
			 $.ajax({  
		            type: "POST",  
		            url: "${contextPath}/base/modurleurl/groupPeizhi.do",  
		            data: "id=" + id ,     // 当前组ID  
		            success: function(response){
		            	remove_Tbody();  //删除 Tbody里面的元素 
	        			$("input[name='checkboxmodule']").each(function(){
		     				   $(this).prop("checked",false);
		     			});
		            	for(var i=0;i<response.length;i++){
		            		var obj=response[i];
		            		obj.id=obj.moduleId; // 删除是使用 
		            	    $("input[id=checkboxmodule"+obj.moduleId+"]").prop("checked",true)
		            		addRow(obj);
		            	}


		            },  
		            error: function(e){  
		            alert('Error: ' + e);  
		            }  
		            }); 
			
		}
		function modulePeizhieditor(id,name){
			$("input[name='checkboxmodule']").each(function(){
				   $(this).prop("checked",false);
				   $(this).prop("disabled",false);
			});  
			var objcheck=$("input[id=checkboxmodule"+id+"]");
			 objcheck.prop("checked",true);
			 objcheck.prop("disabled",true);
			$('#module_group_id').val(id);
			$('#moduleAndgroupName').val(name);
			$('#group_module_id').val("0");
			 $.ajax({  
		            type: "POST",  
		            url: "${contextPath}/base/modurleurl/modulePeizhi.do",  
		            data: "id=" + id ,     // 当前菜单ID  
		            success: function(response){
		            	remove_Tbody();  //删除 Tbody里面的元素 
		            	$("input[name='checkboxgroup']").each(function(){
		     				   $(this).prop("checked",false);
		     			}); 
		            	for(var i=0;i<response.length;i++){
		            		var obj=response[i]; 
		            		$("input[id=checkboxgroup"+obj.groupId+"]").prop("checked",true);
		            		obj.id=obj.groupId; // 删除是使用 
		            		addRow(obj);	
		            	}


		            },  
		            error: function(e){  
		            alert('Error: ' + e);  
		            }  
		            }); 
			
		}
	
		
		function save(){
			if(formValidate()){
				saveform.action="${contextPath}/base/modurleurl/save.do";
				saveform.submit();
			}
			
		}
		function  formValidate(){
	    	var saveform = $('#saveform');
	        var lv= saveform.find('#moduleName').val().length;
	        if(lv==0){
	        	swal({title:"",text:"菜单名称不能为空!"});
	           return false;
	         }
	         if(lv>30){
	        	 swal({title:"",text:"菜单名称长度不能大于 30!"});
	            return  false;
	         }
	         lv= saveform.find('#moduleUrl').val().length;
		        if(lv==0){
		        	swal({title:"",text:"菜单URL不能为空!"});
		           return false;
		         }
		         if(lv>100){
		        	 swal({title:"",text:"菜单URL长度不能大于 100!"});
		            return  false;
		         }
		         lv= saveform.find('#remark').val().length;
			     if(lv>100){
			        swal({title:"",text:"备注信息长度不能大于 100!"});
			        return  false;
			     }
	       return  true ;  
	      }
		
		function savegroup(){
		   if(formgroupValidate()){
			savegroupform.action="${contextPath}/base/modurleurl/savegroup.do";
			savegroupform.submit();
		   }
		}
		function  formgroupValidate(){
	    	var saveform = $('#savegroupform');
	        var lv= saveform.find('#groupName').val().length;
	        if(lv==0){
	        	swal({title:"",text:"组名称不能为空!"});
	           return false;
	         }
	         if(lv>30){
	        	 swal({title:"",text:"组名称长度不能大于 30!"});
	            return  false;
	         }
	         lv= saveform.find('#groupOrder').val().length;
		        if(lv==0){
		        	swal({title:"",text:"序号不能为空!"});
		           return false;
		         }
		         if(isNaN(saveform.find('#groupOrder').val())){
		        	 swal({title:"",text:"序号必须为数字!"});
		            return  false;
		         }
		        lv= saveform.find('#remark').val().length;
			     if(lv>100){
			        swal({title:"",text:"描述长度不能大于 100!"});
			        return  false;
			     } 
	       return  true ;  
	      }
		function doEditorModurlePos(id){
			$.ajax({  
	            type: "POST",  
	            url: "${contextPath}/base/modurleurl/editormodurle.do",  
	            data: "id=" + id ,   
	            success: function(response){
	             var myModal = $('#myModal');
	             myModal.find('#moduleId').val(response.moduleId);
	             myModal.find('#moduleName').val(response.moduleName);
	             myModal.find('#moduleUrl').val(response.moduleUrl);
	             myModal.find('#remark').val(response.remark);
	             myModal.find('.modal-title').text('编辑菜单');
	             myModal.modal('show');  //打开编辑界面
	            },  
	            error: function(e){  
	            alert('Error: ' + e);  
	            }  
	            });  
	            }
		function doEditorGroupPos(id){
			 $.ajax({  
		            type: "POST",  
		            url: "${contextPath}/base/modurleurl/editorgroup.do",  
		            data: "id=" + id ,   
		            success: function(response){
		             var myModal = $('#myModal2');
		             myModal.find('.modal-title').text('编辑组');
		             myModal.find('#groupId').val(response.groupId);
		             myModal.find('#groupName').val(response.groupName);
		             myModal.find('#groupOrder').val(response.groupOrder);
		             myModal.find('#remark').val(response.remark);
		             myModal.modal('show');  //打开编辑界面
		            },  
		            error: function(e){  
		            alert('Error: ' + e);  
		            }  
		            });  
		            }  
		function selcheckbox(obj){
			var $obj=jQuery(obj);
			var checkname=$obj.attr("name");
			var date={};
			var name=$('#moduleAndgroupName').val();
			var moduleid=$('#module_group_id').val();
			var groupid=$('#group_module_id').val();
			if(name.length<=0){
				swal({title:"",text:"你没选择你的维护维度！"});
				$obj.prop('checked',false)
				return ;
			}
			if(moduleid.length>0&&"0"===groupid&&checkname.indexOf("group")>0){  //基于菜单进行维护组 
				date.moduleName=name;
				date.moduleId=moduleid;
				date.groupId=$obj.attr("data-id");
				date.groupName=$obj.attr("data-name");
				date.id=date.groupId;   // 删除进行 取消选择使用
				date.seq=0;
				if($obj.prop('checked')){
					addRow(date);
				}else{
				   remove_line(date.id);
				}
				
			}else if("0"===moduleid&&groupid.length>0&&checkname.indexOf("module")>0){ // 基于用户组进行维护菜单
				date.moduleId=$obj.attr("data-id");
				date.moduleName=$obj.attr("data-name");
				date.groupName=name;
				date.groupId=groupid;
				date.id=date.moduleId;  // 删除进行 取消选择使用
				date.seq=0;
				if($obj.prop('checked')){
					addRow(date);
				}else{
					 remove_line(date.id);
				}
			}else{
				if(checkname.indexOf("module")>0){
					$obj.prop('checked',false)
				    swal({title:"",text:"你选择的维护维度为菜单维护，只能选择你用户组!"});
				}
				if(checkname.indexOf("group")>0){
					swal({title:"",text:"你选择的维护维度为用户组维护，只能选择你菜单!"});
					$obj.prop('checked',false)
				}
			}

			
		}
	   /**
	            表格操作 
	   **/
		function addRow(obj) {
			var tabObj = $('#group_modur');
			var tabbody=tabObj.find('tbody');
	        max_line_num = tabbody.find("tr:last-child").children("td").html();
	        if (max_line_num == null) {
	            max_line_num = 1;
	        }
	        else {
	            max_line_num = parseInt(max_line_num);
	            max_line_num += 1;
	        }
	        var newtr=" <tr id='line" + obj.id + "'>" +
            "<td style='text-align: center;' >" + max_line_num + "</td>" +
            "<td style='text-align:center'><input id='moduleId' name='moduleId' type='hidden' value='"+obj.moduleId+"'><input id='groupId'  name='groupId'  type='hidden' value='"+obj.groupId+"'>"+obj.moduleName+"</td>" +
            "<td><input id='seq' name='seq' type='text' class='textInput'  value='" + obj.seq + "' size=4></td>" +
            "<td style='text-align:center'>" +
                "<button type='button' class='btn btn-primary btn-xs' data-id='" + obj.id + "' onclick='delRow(this) '>删除 </button> " +
            "</td>" +
        "</tr>";
	       
        tabbody.append(newtr);
	    }
	   
		function remove_line(index){
			var tr_id = $("#group_modur>tbody>tr[id=line"+index+"]").attr("id");
			$('#'+tr_id).remove();
			//序号重新排序
			  var _rowLength = group_modur.rows.length;
			  var _j = 1;
			  for ( var i = 1; i < _rowLength; i++) {
				  group_modur.rows[i].cells[0].innerHTML = _j;
			     _j++;
			    }	
		}
	   
		function delRow(obj) {
			var $obj=jQuery(obj);
			var id=$obj.attr("data-id");
			var moduleid=$('#module_group_id').val();
			var groupid=$('#group_module_id').val();
			if(moduleid.length>0&&"0"===groupid){       //基于菜单进行维护组 
			$("input[id=checkboxgroup"+id+"]").prop("checked",false);
			}else if("0"===moduleid&&groupid.length>0){ // 基于用户组进行维护菜单
			$("input[id=checkboxmodule"+id+"]").prop("checked",false);
			}
			$(obj).parent().parent().remove();
			
		  var _rowLength = group_modur.rows.length;
		  var _j = 1;
		  for ( var i = 1; i < _rowLength; i++) {
			  group_modur.rows[i].cells[0].innerHTML = _j;
		     _j++;
		    }
		}
	
		function remove_Tbody() {
			var tabObj = $('#group_modur');
			var tabbody=tabObj.find('tbody');
			tabbody.html("");
		}
		jQuery(document).ready(function(){
			remove_Tbody();
		});
	</script>
	</body>
</html>