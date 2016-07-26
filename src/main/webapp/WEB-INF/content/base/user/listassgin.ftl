<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>菜单组与用户分配管理</title>
	</head>
	<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
			<div class="row">
            <div class="col-sm-5">
            <div class="float-e-margins">
            <div class="ibox-title">
                  <h5>菜单组</h5>
            </div>
            <div class="ibox-content">   
            <div class="table-responsive">
				<table class="table table-condensed table-bordered table-striped table-hover ">
                                <thead>
                                    <tr>
                                      <th style="text-align: center; width:50px; " >编号</th>
                                      <th style="text-align: center; width:50px;  " >选择</th>
                                      <th style="text-align: center; " >组名称</th>
                                      <th style="text-align: center; width:50px "> 序号</th>
                                      <th style="text-align: center;" >描述</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <#list groupList as group >
									<tr id='${group.groupId}'>
                                        <td style="text-align: center;">${group_index+1}  </td>
                                        <#if group.temp  gt 0 >
                                        <td style="text-align: center;"><input type="checkbox" checked="checked"  name="checkboxmodule" id="checkboxmodule${group.groupId}" onclick="selcheckbox(this)"  data-id="${group.groupId}" data-name="${group.groupName}" ></td>
                                        <#else>
                                        <td style="text-align: center;"><input type="checkbox"  name="checkboxmodule" id="checkboxmodule${group.groupId}" onclick="selcheckbox(this)"  data-id="${group.groupId}" data-name="${group.groupName}" ></td>
                                        </#if>
                                        <td>${group.groupName}</td>
                                        <td>${group.groupOrder}</td>
                                        <td>${group.remark}</td>
                                    </tr>  
                                    </#list>
                             </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 用户组菜单URL 关系保存 -->
            <form action="" method="post" name="form1" class="form-horizontal">
             <div class="col-sm-7">
                <div class="float-e-margins">
                 <div class="ibox-title">
                     <h5>正在给用户：${uservo.realName}  分配菜单组</h5>
                 </div>
                 <div class="ibox-content">
			      <div class="panel panel-default table-responsive ziding-td">
                  <input id="module_userId_id" name="module_userId_id" type="hidden" value="${uservo.userId}">
                  <input id="module_userId_name" name="module_userId_name" type="hidden" value="${uservo.realName}">
                 
                            <table id="group_modur" class="table table-condensed table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                      <th style="text-align: center;width:10%; " >编号</th>
                                      <th style="text-align: center;"  >组名称</th>
                                      <th style="text-align: center;width:10%; " >
                                      	<button type="button" class="btn btn-danger btn-xs" onclick="savemodurlegroup();" >保存</button>
                                      </th>
                                    </tr>
                                </thead>
                                <tbody>
                                <#list selectgrouplist as group > 
									 <tr id="line${group.groupId}" data-id="${group.groupId}">
                                        <td style="text-align: center;">${group_index+1}</td>
                                        <td style="text-align:left ;" >
                                        <input id='userId' name='userId' type='hidden' value='${uservo.userId}'>
                                        <input id='groupId' name='groupId' type='hidden' value='${group.groupId}'>
                                        ${group.groupName}</td>
                                        <td style="text-align: center;" ><button type='button' class='btn btn-info btn-xs' data-id='${group.groupId}' onclick='delRow(this) '>删除 </button></td>
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
	
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		function savemodurlegroup(){
			form1.action="${contextPath}/base/user/saveUsergroup.do";
		    form1.submit();
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
		
		
		/***
		点击 左边的 选择框的操作
		***/
		function selcheckbox(obj){
			var $obj=jQuery(obj);
			var checkname=$obj.attr("name");
			var date={};
			var name=$('#module_userId_name').val();
			var moduleuserId=$('#module_userId_id').val();
			if(name.length<=0){
				swal({title:"",text:"你没选择你的维护维度！"});
				$obj.prop('checked',false)
				return ;
			}
			date.groupId=$obj.attr("data-id");
			date.groupName=$obj.attr("data-name");
			date.userName=name;
			date.userId=moduleuserId;
			date.id=date.groupId;  // 删除进行 取消选择使用
				
				if($obj.prop('checked')){
					addRow(date);
				}else{
				   remove_line(date.id);
				}
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
            "<td style='text-align:left'><input id='userId' name='userId' type='hidden' value='"+obj.userId+"'><input id='groupId'  name='groupId'  type='hidden' value='"+obj.groupId+"'>"+obj.groupName+"</td>" +

            "<td style='text-align:center'>" +
                "<button type='button' class='btn btn-primary btn-xs' data-id='" + obj.id + "' onclick='delRow(this) '>删除 </button> " +
            "</td>" +
        "</tr>";
	       
        tabbody.append(newtr);
	    }
		function delRow(obj) {
			var $obj=jQuery(obj);
			var id=$obj.attr("data-id");
			var moduleuserId=$('#module_userId_id').val();
			var groupid=$('#group_module_id').val();
			$("input[id=checkboxmodule"+id+"]").prop("checked",false);
			$(obj).parent().parent().remove();
		    var _rowLength = group_modur.rows.length;
		    var _j = 1;
		    for ( var i = 1; i < _rowLength; i++) {
			  group_modur.rows[i].cells[0].innerHTML = _j;
		     _j++;
		    }
		}
		
	   /***
	        根据当前用户已经选择了的菜单，去选择左边的菜单组 的操作  在页面加载完成后调用 
	   ***/
		function selcetCheckBox() {
			var tabObj = $('#group_modur');
			var tabbody=tabObj.find('tbody');
			var trs=tabbody.find('tr');
			trs.each(function(){
				var trid=$(this).attr('data-id');
				$("input[name='checkboxmodule']").each(function(){
					var id=$(this).attr('data-id');
					if(id===trid){
					 $(this).prop("checked",true);
					}
				}); 
			}); 
		}
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
			selcetCheckBox();
		});
	</script>
	</body>
</html>