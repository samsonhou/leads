<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>来源管理</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">来源查询</div>
                <div class="panel-body">
                    <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
                            <div class="form-group ">
                                <label class="col-sm-2 control-label">来源</label>
                                <div class="select_org">
                                <@fromtype defValue="${vo.code}" props=" class='form-control' "/>
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否有效</label>
                                <div class="col-sm-4">
                                	<@select  fieldId="status" codeType="1000" fieldName="status" defValue="${(vo.status)!''}" props=" class='form-control m-b'  " />
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
                                       <th style="width: 50px;text-align: center;">序号</th>
                                       <th style="text-align: center;">来源名称</th>
                                       <th style="text-align: center;">来源编码</th>
                                       <th style="text-align: center;">是否有效</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#list page.list as obj >
									<tr>
                                        <td style="text-align: center;">${obj_index+1}</td>
                                        <td>${obj.name}</td>
                                        <td>${obj.code}</td>
                                        <td>${(obj.status=="1")?string('是','否')}</td>
                                    </tr>  
                                    </#list>
                             </tbody>
                            </table>
                       </div>
                   <@pages url="${contextPath}/leads/base/fromtype/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
        </div>
	</form>
	
	
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="width:700px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新增来源</h4>
      </div>
    
     <form name='saveform' id='saveform'  class="form-search" method="post" >
      <div class="modal-body" style="height:200px;"><!--高度根据表单高度相应调整-->
                   <div class="ibox-content ">
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">来源所属</label>
                                <div class="select_org">
                                <@fromtype props=" class='form-control' "/>
								</div>
                            </div>
                            <div class="form-group ziding-ibox-modal">
                            <label class="col-sm-2 control-label  required">来源名称</label>
                                <div class="col-sm-10">
                               <input id="name" name="name" type="text"  class="form-control">
                                </div>
                            </div>			
                            
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">是否有效</label>
                                <div class="col-sm-10 ">
                                    <@select  fieldId='status' codeType='1000' defValue='1' fieldName='status' props=" class='form-control'  " haveHead="false" /> 
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
		$(document).ready(function(){
			jQuery("#pagination").page("form1");
		});
		function search(){
			form1.action="${contextPath}/leads/base/fromtype/queryList.do";
			form1.submit();
		}
	    function pezhi(id,name) {
		    openNewTab("${contextPath}/base/codetype/pezhifind.do?name="+name+"&codeType="+id,name);
	    } 
		function save(){
			if($("#myModal").find("input[name='code']").val() == ""){
				layer.alert("请选择来源所属！");
				return;
			}
			if($("#name").val()==""){
				layer.alert("请填写来源名称！",function(ind){
					$("#name").focus();
					layer.close(ind);
				});
				return;
			}
			
			saveform.action="${contextPath}/leads/base/fromtype/save.do";
			saveform.submit();
			
		}
        function increased() { 
        	 var myModal = $('#myModal');
             myModal.find('.modal-title').text('新增来源');
             myModal.modal('show');  //打开新增界面
            } 
            
</script> 
	</body>
</html>