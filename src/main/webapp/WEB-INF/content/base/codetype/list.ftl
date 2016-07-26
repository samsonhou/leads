<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>码表管理</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/base/codetype/queryList.do" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">码表查询</div>
                <div class="panel-body">
                    <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
                            <div class="form-group ">
                                <label class="col-sm-2 control-label">类型编码</label>
                                <div class="col-sm-4 ">
                                    <input type="text" id="codeType" name="codeType" class="form-control" value="${(findObj.codeType)!''}" >
                                </div>
                                <label class="col-sm-2 control-label  required">类型姓名</label>
                                <div class="col-sm-4">
                                    <input type="text" id="name" name="name" class="form-control" value="${(findObj.name)!''}" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否标准</label>
                                <div class="col-sm-4">
                                 	<@select  fieldId="type" codeType="999" fieldName="type"  defValue="${(findObj.type)!''}" props=" class='form-control m-b'  "  />
                                </div>
                                <label class="col-sm-2 control-label">是否有效</label>
                                <div class="col-sm-4">
                                	<@select  fieldId="status" codeType="1000" fieldName="status" defValue="${(findObj.status)!''}" props=" class='form-control m-b'  " />
                                </div>
                            </div>
                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                   <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                   &nbsp; &nbsp; 
	                                   <input type="button" onclick="increased();" value="新 增 " class="btn btn-primary btn-sm zd-btn-pd1">
	                                  
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
                                       <th style="width: 50px;text-align: center;">编号</th>
                                       <th style="text-align: center;">类型名称</th>
                                       <th style="text-align: center;">类型编码</th>
                                       <th style="width: 60px; text-align: center;">是否标准类型</th>
                                       <th style="width: 60px; text-align: center;">是否启用</th>
                                       <th style="width: 100px; text-align: center;">创建时间</th>
                                       <th style="width: 100px; text-align: center;">操作 </th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#list page.list as codeType >
									<tr>
                                        <td style="text-align: center;">${codeType_index+1}</td>
                                        <td style="text-align: left;">${codeType.name}</td>
                                        <td style="text-align: center;">${codeType.codeType}</td>
                                        <td style="text-align: center;" >${(codeType.type=="1")?string('是','否')}</td>
                                        <td style="text-align: center;">${(codeType.status=="1")?string('是','否')}</td>
                                        <td style="text-align: center;">${codeType.createDate?string('yyyy-MM-dd ')!""}</td>
                                     <td >
                                        <a href="#" onclick="doEditorPost('${codeType.codeType}')">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                        
                                        <#if codeType.type=="1">
       									<a href="#" onclick="pezhi('${codeType.codeType}','${codeType.name}')">配置</a>&nbsp;&nbsp;&nbsp;&nbsp;
  									    </#if>
                                     </td>
                                    </tr>  
                                    </#list>
                             </tbody>
                            </table>
                       </div>
                   <@pages url="${contextPath}/base/codetype/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
        </div>
	</form>
	
	
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新 增 码 表</h4>
      </div>
    
     <form name='saveform' id='saveform'  class="form-search" method="post" >
      <div class="modal-body" style="height:200px;"><!--高度根据表单高度相应调整-->
                   <div class="ibox-content ">
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">类型编码</label>
                                <div class="col-sm-4 ">
                                <input id="codeType" name="codeType" readonly="readonly" class="form-control" type="text" placeholder="类型编码" >
                                </div>
                                <label class="col-sm-2 control-label  required">类型名称</label>
                                <div class="col-sm-4">
                               <input id="name" name="name" type="text"  class="form-control" placeholder="类型名称" >
                                </div>
								
                            </div>			
                            
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">是否有效</label>
                                <div class="col-sm-4 ">

                                    <@select  fieldId='status' codeType='1000' fieldName='status' props=" class='form-control m-b'  " haveHead="false" /> 
								</div>		
                                <label class="col-sm-2 control-label">是否标准</label>
                                <div class="col-sm-4 ">
                                    <@select  fieldId='type' codeType='999' fieldName='type' props=" class='form-control m-b'  "  haveHead="false" /> 
								</div>																
                            </div>			
                            <div id="feibiaozhunsqlDiv"   class="hidden"  >
                                <label class="col-sm-2 control-label">非标准SQL</label>
                                <div class="col-sm-10 ">
                                    <textarea rows="3" id="txtSql" name="txtSql" class="form-control"  placeholder="非标准执行SQL： select  as name , as value form 表名  where  "  ></textarea>
								</div>								
                            </div> 																			
					</div>
      </div>               
     <input id="isnew" name="isnew" type="hidden" value="1">
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
			form1.action="${contextPath}/base/codetype/queryList.do";
			form1.submit();
		}
	    function pezhi(id,name) {
		    openNewTab("${contextPath}/base/codetype/pezhifind.do?name="+name+"&codeType="+id,name);
	    } 
		function save(){
			if(formValidate()){
			saveform.action="${contextPath}/base/codetype/save.do";
			saveform.submit();
			}
		}
		
		var selectOnchange = function () {  
			if($(this).val()==0)
			$("#feibiaozhunsqlDiv").removeClass().addClass('form-group ziding-ibox-modal');
			else
			$("#feibiaozhunsqlDiv").removeClass().addClass('hidden');	
		
		};  
		$("#saveform").find("#type").change("onchange", selectOnchange);
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
		});
        function doAjaxPost() {  
        var formdata=$("#saveform").serialize()
        $.ajax({  
        type: "POST",  
        url: "${contextPath}/base/codetype/save.do",  
        data: formdata,  
        success: function(response){  
         alert('成功 ');
        },  
        error: function(e){  
        alert('Error: ' + e);  
        }  
        });  
        }
        function doEditorPost(id) {  
            $.ajax({  
            type: "POST",  
            url: "${contextPath}/base/codetype/editor.do",  
            data: "codeType=" + id ,   
            success: function(response){
             var saveform = $('#saveform');
             saveform.find('#isnew').val('0'); //表示当前为修改    1为新增 
             saveform.find('#codeType').val(response.codeType);
             saveform.find('#name').val(response.name);
             saveform.find('#status').find("option[value='"+response.status+"']").attr("selected",true);
             saveform.find('#type').find("option[value='"+response.type+"']").attr("selected",true);
             saveform.find('#txtSql').val(response.txtSql);
             if(response.type==0){
            	 $("#feibiaozhunsqlDiv").removeClass().addClass('form-group ziding-ibox-modal');
             }
             else{
            	 $("#feibiaozhunsqlDiv").removeClass().addClass('hidden');
             }
             var myModal = $('#myModal');
             myModal.find('.modal-title').text('编辑类型');
             myModal.modal('show');  //打开编辑界面
            },  
            error: function(e){  
            alert('Error: ' + e);  
            }  
            });  
            }  
        function increased() {  
            $.ajax({  
            type: "POST",  
            url: "${contextPath}/base/codetype/increased.do",  
            data: "",   
            success: function(response){
             var saveform = $('#saveform');
             saveform.find('#isnew').val('1'); // 表示当前为修改    1为新增 
             saveform.find('#name').val(''); //   清空类型名称
             saveform.find('#codeType').val(response.codeType);
             saveform.find('#status').find("option[value='1']").attr("selected",true);
             saveform.find('#type').find("option[value='1']").attr("selected",true);
             saveform.find('#txtSql').val('');
             $("#feibiaozhunsqlDiv").removeClass().addClass('hidden');
             
             var myModal = $('#myModal');
             myModal.find('.modal-title').text('新增类型');
             myModal.modal('show');  //打开新增界面
            },  
            error: function(e){  
            alert('Error: ' + e);  
            }  
            });  
            } 
        
      function  formValidate(){
    	var saveform = $('#saveform');
        var lv= saveform.find('#name').val().length;
        if(lv==0){
        	swal({title:"",text:"类型名称不能为空!" });
           return false;
         }
         if(lv>30){
        	 swal({title:"",text:"类型名称长度不能大于 30!" });
            return  false;
         }
        lv=saveform.find('#txtSql').val().length;
        
        if(saveform.find("#type").val()==0&&lv==0){
          swal({title:"",text:"SQL不能为空 !" });
          return  false;
        }
        if(lv>800){
        	swal({title:"",text:"SQL长度 不能大于 800 !" });
            return  false;
        }
        if(!strValidate(saveform.find('#txtSql').val())){
        	return  false;
        }
       return  true ;  
      } 
      
    function  strValidate(str){
    str=str.toLowerCase();
    if(str.indexOf("insert")>=0){
      swal({title:"",text:"含有此字符串:insert" });
      return false;
    }
    if(str.indexOf("delete")>=0){
    	swal({title:"",text:"含有此字符串:delete" });
    	return false;	
    	} 
    if(str.indexOf("update")>=0){
    	swal({title:"",text:"含有此字符串:update" });
    	return false;	
     }
     if(str.indexOf("create")>=0){
      swal({title:"",text:"含有此字符串:create" });
      return false;	
     } 
     if(str.indexOf("drop")>=0){
    	swal({title:"",text:"含有此字符串:drop" });
      	return false;	
      } 
      return true;
    }    	  
</script> 
	</body>
</html>