<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>组织管理</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/base/organ/queryList.do" method="post" name="form1" class="form-horizontal">
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="row">
            <div class="col-sm-12">
                <div class="float-e-margins">
                    <div class="ibox-title">
                        <h5>组织管理</h5>
                    </div>
                    <div class="ibox-content ziding-ibox">
                            <div class="form-group ">
                                <label class="col-sm-2 control-label">组织名称</label>
                                <div class="col-sm-4 ">
                                   <@organ   fieldId="parentId" fieldName="parentId" defValue="1" /> 
                                  <input id="organCode" name="organCode" type="hidden" value="${(findObj.organCode)!''}">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否有效</label>
                                <div class="col-sm-4">
                                <@select  fieldId='status' codeType='1000' fieldName='status' defValue='${(findObj.status)!""}' props=" class='form-control m-b'  "/>   
                            </div>
                            </div>
                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                   <input type="button" onclick="search();" value="查 询" class="btn btn-primary zd-btn-pd1">
	                                   &nbsp; &nbsp; 
	                                   &nbsp; &nbsp; 
	                                   <input type="button" onclick="increased();" value="新 增 " class="btn btn-primary zd-btn-pd1">
	                                   
	                             </div>
                            </div>
							
                    </div>
                </div>
            </div>
            <div class="col-sm-12">
                <div class="float-e-margins">
                    <div class="ibox-content">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th style="width: 60px;text-align: center;">序号</th>
                                        <th style="text-align: center;">组织名称</th>
                                        <th style="text-align: center;">组织编码</th>
                                        <th style="text-align: center;">创建时间</th>
                                        <th style="text-align: center;">操作</th>
                                    </tr>
                                </thead>
                                
                                
                                <tbody>
                                	<#list page.list as organ>
									<tr>
                                        <td style="text-align: center;">${organ_index+1}</td>
                                        <td style="text-align: center;"><code>${organ.organName}</code></td>
                                        <td style="text-align: center;"><code>${organ.organCode}</code></td>
                                        <td style="text-align: center;"><code>${organ.createDate?string('yyyy-MM-dd ')!""}</code></td>
                                        <td style="text-align: center;">
                                     <a href="#" onclick="doEditorPost('${organ.id}')">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp; </td>
                                    </tr>  
                                    </#list>
                              </tbody>
                            </table>
                        	<@pages url="${contextPath}/base/organ/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
	</form>
	
	
	
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新增组织</h4>
      </div>
      <form name='saveform' id='saveform'  class="form-search" method="post" >
      <div class="modal-body" style="height:200px;"><!--高度根据表单高度相应调整-->
      <input id="isnew" name="isnew" type="hidden" value="1">
      <input id="id" name="id" type="hidden" value="">
                   <div class="ibox-content ">
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">组织名称</label>
                                <div class="col-sm-4 ">
                                 <input type="text" name="organName"  id="organName" class="form-control" >
                                </div>
                                <label class="col-sm-2 control-label">组织编码</label>
                                <div class="col-sm-4 ">
                                  <input id="organCode" name="organCode" type="text" value="${(findObj.organCode)!''}">
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
			form1.action="${contextPath}/base/organ/queryList.do";
			form1.submit();
		}
		function save(){
			if(formValidate()){
			saveform.action="${contextPath}/base/organ/save.do";
			saveform.submit();
			}
		}
		function formValidate(){
			var saveform = $('#saveform');
	        var lv= saveform.find('#name').val().length;
	        if(lv==0){
	        	swal({title:"",text:"机构名称不能为空!"});
	           return false;
	         }
	         if(lv>20){
	        	 swal({title:"",text:"机构名称长度不能大于 20!"});
	            return  false;
	         }
	        lv=saveform.find('#abbrName').val().length;
	        
	        if(lv==0){
	          swal({title:"",text:"机构简称不能为空 !"});
	          return  false;
	        }
	        if(lv>20){
	        	swal({title:"",text:"机构简称长度 不能大于20 !"});
	            return  false;
	        }
            lv=saveform.find('#address').val().length;
	        if(lv==0){
	          swal({title:"",text:"地址不能为空 !"});
	          return  false;
	        }
	        if(lv>100){
	        	swal({title:"",text:"地址简称长度不能大于100 !"});
	            return  false;
	        }
	        
	       return  true ;  
		}
		function rootIncreased() {
		  openNewTab("${contextPath}/base/organ/index.do?",'根组织新增');
		}
		function increased() {  
            /**
			$.ajax({  
            type: "POST",  
            url: "${contextPath}/base/codetype/increased.do",  
            data: "",   
            success: function(response){
            },  
            error: function(e){  
            alert('Error: ' + e);  
            }  
            }); 
            **/
            var saveform = $('#saveform');
            saveform.find('#isnew').val('1');   //1为新增 
            saveform.find('#id').val("");
            saveform.find('#organCode').val("");
            saveform.find('#organName').val("");
            saveform.find('#status').find("option[value='1']").attr("selected",true);
            var myModal = $('#myModal');
            myModal.find('.modal-title').text('新增类型');
            myModal.modal('show');  //打开新增界面
            
            } 
	    function doEditorPost(id) {  
	            $.ajax({  
	            type: "POST",  
	            url: "${contextPath}/base/organ/editor.do",  
	            data: "id=" + id,
	            success: function(response){
	            	
	             var saveform = $('#saveform');
	             saveform.find('#isnew').val('0'); //表示当前为修改    1为新增 
	             saveform.find('#id').val(response.id);
	             saveform.find('#organCode').val(response.organCode);
	             saveform.find('#organName').val(response.organName);
	            
	             //saveform.find('#type').find("option[value='"+response.type+"']").attr("selected",true);
	             //saveform.find('#txtSql').val(response.txtSql);
	             if(response.type==0)
	     		    $("#feibiaozhunsqlDiv").removeClass().addClass('form-group ziding-ibox-modal');
	             else
	            	$("#feibiaozhunsqlDiv").removeClass().addClass('hidden');
	             var myModal = $('#myModal');
	             myModal.find('.modal-title').text('编辑类型');
	             myModal.modal('show');  //打开编辑界面
	            },  
	            error: function(e){  
	            alert('Error: ' + e);  
	            }  
	            });  
	            }

		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
			initOrgan();
		});
	</script>
	<script src="${contextPath}/res/pub/js/plugins/layer/layer.min.js"></script>
    <script src="${contextPath}/res/pub/js/demo/layer-demo.min.js"></script>
<script>
$('#add-layer').on('click', function(){
    layer.open({
        type: 2,
        title: 'layer弹窗',
        maxmin: true,
        shadeClose: true,
        area : ['800px' , '520px'],
        content: 'test-iframe.html'
    });
});
</script>
	</body>
</html>