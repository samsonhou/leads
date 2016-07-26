<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>码表配置子项</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/base/codetype/pezhifind.do" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">码表配置子项查询</div>
                <div class="panel-body">
                    <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
                            <div class="form-group ">
                                <label class="col-sm-2 control-label">类型编码</label>
                                <div class="col-sm-4 ">
                                 <input id="codeType" name="codeType" readonly="readonly" type="text" value="${codeType}" class="form-control" >
                                </div>
                                <label class="col-sm-2 control-label  required">类型名称</label>
                                <div class="col-sm-4">
                                <input id="codeTypeName" name="codeTypeName"  readonly="readonly" type="text" value="${codeTypeName}" class="form-control">
                                </div>
                            </div>
                           
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">对象名称</label>
                                <div class="col-sm-4">
                                    <input type="text" name="name"  id="name" class="form-control"  value="${(findObj.name)!''}" >
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
	                                    <!-- Button trigger modal -->
	                                    <input type="button" onclick="add();" value="新 增 " class="btn btn-primary btn-sm zd-btn-pd1">
	                                   &nbsp; &nbsp; 
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
                                      <th style="text-align: center;" >编号</th>
                                        <th style="text-align: center;" >类型编码</th>
                                        <th style="text-align: center;" >类型名称</th>
                                        <th style="text-align: center;" >对象名称 </th>
                                        <th style="text-align: center;">对象值</th>
                                        <th style="text-align: center;">序号</th>
                                        <th style="text-align: center;">是否启用</th>
                                        <th style="text-align: center;" >创建时间</th>
                                        <th style="text-align: center;" >操作 </th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#list page.list as codeType >
									<tr>
                                        <td style="text-align: center;">${codeType_index+1}</td>
                                        <td style="text-align: center;" >${codeType.codeType}</td>
                                        <td style="text-align: center;" >${codeType.codeTypeName}</td>
                                        <td style="text-align: center;" >${codeType.name}</td>
                                        <td style="text-align: center;" >${codeType.value}</td>
                                        <td style="text-align: center;" >${codeType.seq}</td>
                                        <td style="text-align: center;" >${(codeType.status=="1")?string('是','否')}</td>
                                        <td style="text-align: center;" >${codeType.createDate?string('yyyy-MM-dd ')!""}</td>
                                        <td>
                                          <a href="#" onclick="doEditorPost('${codeType.codeItemId}')">编辑</a>&nbsp;&nbsp; 
                                          <a href="#" onclick="del('${codeType.codeItemId}')">删除</a>&nbsp;&nbsp;
                                        </td>
                                    </tr>  
                                    </#list>
                             </tbody>
                            </table>
                        </div>
                <@pages url="${contextPath}/base/codetype/pezhifind.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
        </div>
        <input id="peizhipage" name="peizhipage" type="hidden" value="${peizhipage}">
     </form>
	
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新 增  子 类 型 </h4>
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
                               <input id="codeTypeName" name="codeTypeName" readonly="readonly" type="text"  class="form-control" placeholder="类型名称" >
                                </div>
                            </div>
                            	
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">对象名称</label>
                                <div class="col-sm-4 ">
                                <input id="name" name="name" type="text"  class="form-control" placeholder="对象名称" >
                                </div>
                                <label class="col-sm-2 control-label  required">对象值</label>
                                <div class="col-sm-4">
                               <input id="value" name="value" type="text"  class="form-control" placeholder="对象值" >
                                </div>
                            </div>		
                            
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">序号</label>
                                <div class="col-sm-4 ">
                                <input id="seq" name="seq" type="text"  class="form-control" placeholder="序号" >
                                </div>	
                                <label class="col-sm-2 control-label">是否启用</label>
                                <div class="col-sm-4 ">
                                   <@select  fieldId='status' codeType='1000' fieldName='status' props=" class='form-control m-b'  " /> 
								</div>																
                            </div>		
                            																			
					</div>
      </div>               
     <input id="isnew" name="isnew" type="hidden" value="1">
     <input id="codeItemId" name="codeItemId" type="hidden" value="0">
     
     </form>
     
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="save()">保存</button> 
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="confirmMessageModal">
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
	        <button type="button" class="btn btn-primary" data-dismiss="modal">取&nbsp;&nbsp;消</button>
	        <button id="confirmokBut" type="button" class="btn btn-primary" data-dismiss="modal" onclick="confirm()">确&nbsp;&nbsp;定</button>
	      </div>
	    </div>
	  </div>
	</div>
<form name='delform' id='delform'  method="post" >
 <input id="codeType" name="codeType" type="hidden" value="">
<input id="codeItemId" name="codeItemId" type="hidden" value="">
</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		function search(){
			form1.action="${contextPath}/base/codetype/pezhifind.do";
			form1.submit();
		}
		function confirm(){  
			delform.action="${contextPath}/base/codetype/delpezhi.do";
			delform.submit();
		}
		function del(id){
			 $("#showAlertInfo").text('你确认要删除吗？');
		     $('#confirmMessageModal').modal('show');
		     $("form#delform input[name='codeItemId']").val(id);
			 var tye=$("form[name=form1] input[name=codeType]").val();
			 $("form#delform input[name='codeType']").val(tye);	
		}
		function add(){
			var form1 = $('#form1');
			var tye=$("form[name=form1] input[name=codeType]").val();
			var codeTypeName=$("form[name=form1] input[name=codeTypeName]").val();
			$("form#saveform input[name='codeType']").val(tye);
			$("form#saveform input[name='codeTypeName']").val(codeTypeName);
			$("form#saveform input[name='codeType']").val(tye);
			$('#saveform').find('#status').find("option[value='1']").attr("selected",true);
			$('#saveform').find('#isnew').val('1'); //1为新增 
			 var myModal = $('#myModal');
             myModal.find('.modal-title').text('配置:'+codeTypeName+'类型的子项');
             myModal.modal('show');  //打开配置界面
		}
		function save(){
			if(formValidate()){
			saveform.action="${contextPath}/base/codetype/pezhisave.do";
			saveform.submit();
			}
		}
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
		});
		function  formValidate(){
	    	var saveform = $('#saveform');
	        var lv= saveform.find('#name').val().length;
	        if(lv==0){
	        	swal({title:"",text:"对象名称不能为空!"});
	           return false;
	         }
	         if(lv>10){
	        	swal({title:"",text:"对象名称长度不能大于 10!" });
	            return  false;
	         }
	        var lv= saveform.find('#value').val().length;
		    if(lv==0){
		        swal({title:"",text:"对象值不能为空!" });
		        return false;
		        }
		        if(lv>10){
		        swal({title:"",text:"对象值长度不能大于 10!" });
		        return  false;
		      }
		      var lv= saveform.find('#seq').val().length;
			  if(lv==0){
			   swal({title:" ",text:"序号不能为空!" });
			   return false;
			   }
			   if(lv>3){
			   swal({title:" ",text:"序号长度不能大于 3!" });
			   return  false;
			   }
	       return  true ;  
	      } 
	</script>
	<script type="text/javascript">  
     
        function doEditorPost(id) {
            $.ajax({  
            type: "POST",  
            url: "${contextPath}/base/codetype/editorpeizhi.do",  
            data: "codeItemId=" + id ,   
            success: function(response){
             var saveform = $('#saveform');
             var codeTypeName=$("form[name=form1] input[name=codeTypeName]").val();
             saveform.find('#isnew').val('0'); //表示当前为修改    1为新增 
             saveform.find('#codeItemId').val(response.codeItemId);
             saveform.find('#codeType').val(response.codeType);
             saveform.find('#codeTypeName').val(codeTypeName); 
             saveform.find('#name').val(response.name);
             saveform.find('#value').val(response.value);
             saveform.find('#seq').val(response.seq);
             saveform.find('#status').find("option[value='"+response.status+"']").attr("selected",true);
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
</script> 
	</body>
</html>