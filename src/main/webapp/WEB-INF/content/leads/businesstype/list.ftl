<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>业务类型管理</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/businesstype/query.do" method="post" name="form1" class="form-horizontal">
		<input type="hidden" id="assginItems" name="assginItems">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">业务类型管理</div>
			  <div class="panel-body" style="padding-bottom: 0px;">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	<div class="form-group">
			  	 	 	 	
                                
                                <label class="col-sm-2 control-label">大类名称</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="title" id="title" value="${(findObj.title)!''}" class="form-control">
                                    <input type="hidden" id="pid" name="pid" value='0'>
                                </div>
                            </div>

                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                 <input type="button" onclick="search()" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                 &nbsp;&nbsp;<input type="button" onclick="clearForm(this.form)" value="清空" class="btn btn-primary btn-sm zd-btn-pd1">
	                                 &nbsp;&nbsp;<input type="button" onclick="increased();" value="新增大类 " class="btn btn-primary btn-sm zd-btn-pd1">
	                             </div>
                            </div>

			  	 	 	 </div>
			  	 	 </div>
			  	 </div>
			  </div>
			</div>
			
			<div class="panel panel-default table-responsive ziding-td">
				 <table class="table table-condensed table-bordered table-striped" >
					 <thead>
					 	<tr>
					 		<th style="text-align: center;width: 50px;">序号</th>
					 		<th style="text-align: center;width: 40px;">&nbsp;</th>
					 		<th style="text-align: center;">业务大类</th>
                            <th style="text-align: center;width: 150px; ">操作</th>
					 	</tr>
					 </thead>
					 <tbody>
					 	<#list page.list as client>
							<tr>
								<td style="text-align: center;">${client_index+1}</td>
								<td style="text-align: center;"><a onclick="changeIcon(this);" data-trid="index${client_index+1}"><i class="fa fa-plus-square-o"></i></a></td>
								<td>${client.title}</td>
                                <td>&nbsp;&nbsp;<input type="button" onclick="increased_PId(${client.id});" value="新增小类 " class="btn btn-primary btn-xs">
                                &nbsp;&nbsp;<input type="button" onclick="doEditorPost(${client.id});" value="编辑 " class="btn btn-primary btn-xs"></td>
                            </tr>
                            <#if (client.childrenCount>0) >
                            <tr  class="collapse" id="index${client_index+1}" >
                            
                            	<td colspan="2">业务小类</td>
                            	<td colspan="2"  style="padding:0;border:0px;">
									<table class="table" style="margin-bottom:0;border-top: 0px;"  border="0" >
                            			<#list client.childrenList as client>
                            			<tr>
											<td style="text-align:left;padding-left: 30px;">${client.title}</td>
                            			    <td style="border-left:1px solid #e7eaec;width: 150px;text-align:right;padding-right: 25px;" >
                            			    	<input type="button" onclick="doEditorPost(${client.id});"  value="编辑 " class="btn btn-primary btn-xs">
                            			    </td>
                            			</tr>
                            			 </#list>
                            		</table>
                            	</td>

                            </tr>
                            </#if>
                        </#list>
					 </tbody>
				</table>
			</div>	
			<@pages url="${contextPath}/leads/businesstype/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />	
		</div>
	</form>
	
	
	
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新增用户</h4>
      </div>
      <form name='saveform' id='saveform'  class="form-search" method="post" >
      <div class="modal-body" style="height:150px;"><!--高度根据表单高度相应调整-->
               <input id="id" name="id" type="hidden" value="0">
               <input id="pid" name="pid" type="hidden" value="0">
                   <div class="ibox-content " >
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label model_left_z">类型名称</label>
                                <div class="col-sm-10 ">
                                 <input type="text" name="title"  id="title" class="form-control" placeholder="类型名称" >
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
	
	
	
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		function search(){
			form1.action="${contextPath}/leads/businesstype/queryList.do";
			form1.submit();
		}
		function increased(){
		        var saveform = $('#saveform');
                saveform.find('#id').val("0");
                saveform.find('#pid').val("0");
                saveform.find('#title').val("");
                var myModal = $('#myModal');
                myModal.find('.modal-title').text('新增业务大类');
                myModal.modal('show');  //打开新增界面	
		}
		// 新增他的下级 
		function increased_PId(pid){
		        var saveform = $('#saveform');
                saveform.find('#id').val("0");
                saveform.find('#pid').val(pid);
                saveform.find('#title').val("");
                var myModal = $('#myModal');
                myModal.find('.modal-title').text('新增业务小类');
                myModal.modal('show');  //打开新增界面	
		}
		function save(){
			if(formValidate()){
			saveform.action="${contextPath}/leads/businesstype/save.do";
			saveform.submit();
			}
		}
		function formValidate(){
			var saveform = $('#saveform');
	        var lv= saveform.find('#title').val().length;
	        if(lv==0){
	        	swal({title:"",text:"类型名称不能为空!"});
	           return false;
	         }
	         if(lv>20){
	        	 swal({title:"",text:"类型名称长度不能大于 20!"});
	            return  false;
	         }
	        
	        return  true;
	    }
		//编辑 
		function doEditorPost(id) {  
	            $.ajax({  
	            type: "POST",  
	            url: "${contextPath}/leads/businesstype/editor.do",  
	            data: "id=" + id,
	            success: function(response){
	             var saveform = $('#saveform');
	             saveform.find('#id').val(response.id);
                 saveform.find('#pid').val(response.pid);
                 saveform.find('#title').val(response.title);
	             var myModal = $('#myModal');
	             myModal.find('.modal-title').text('编辑类型名称');
	             myModal.modal('show');  //打开编辑界面
	            },  
	            error: function(e){  
	            alert('Error: ' + e);  
	            }  
	            });  
	     }
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
		});
		function changeIcon(obj){
			$this=jQuery(obj);
			jQuery("#"+$this.attr("data-trid")).collapse('toggle');
			if($this.html()=='<i class="fa fa-plus-square-o"></i>'){
				$this.html('<i class="fa fa-minus-square-o"></i>');
    		}else{
    			$this.html('<i class="fa fa-plus-square-o"></i>');
    		}
		}
	</script>
	</body>
</html>