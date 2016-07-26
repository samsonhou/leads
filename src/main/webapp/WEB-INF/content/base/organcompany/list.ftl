<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>机构管理</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/base/organcompany/queryList.do" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
            <div class="panel-heading">机构查询</div>
                <div class="panel-body">
                    <div class="col-sm-12">
                    <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
                            <div class="form-group ">
                                <label class="col-sm-2 control-label">机构</label>
								<!--
                                <input id="organCode" name="organCode" type="hidden" value="${(findObj.organCode)!''}">
                                <@organ showLevel='00' fieldId="organId" fieldName="organId" defValue="${(findObj.organId)!''}"   props=" class='form-control'  " /> 
                                -->
                                <div class="col-sm-4">
                                <input id="name" name ="name" type="text" value="${findObj.name!''}" class='form-control' readonly  placeholder="选择公司"  onclick="showMenu();" />
                                <input id="organId" name ="organId" type="hidden"  value="${findObj.organId!''}"  />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否有效</label>
                                <div class="col-sm-4">
                                	<@select  fieldId='status' codeType='1000' fieldName='status' defValue='${(findObj.status)!""}' props=" class='form-control m-b'  " />   
                            	</div>
                            </div>
                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                   <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                   &nbsp; &nbsp; 
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
                                        <th style="width:50px;text-align: center;">序号</th>
                                        <th style="text-align: center;">机构名称</th>
                                        <th style="text-align: center;">机构简称</th>
                                        <th style="text-align: center;">地址</th>
                                        <th style="width: 50px; text-align: center; ">是否有效</th>
                                        <th style="text-align: center;">创建时间</th>
                                        <th style="width: 50px; text-align: center;">操作</th>
                                    </tr>
                                </thead>
                                
                                
                                <tbody>
                                	<#list page.list as organCompany>
									<tr>
                                        <td style="text-align: center;">${organCompany_index+1}</td>
                                        <!--td style="text-align: center;"><code>${organCompany.organName}</code></td-->
                                        <td style="text-align: left;">${organCompany.organName} ${organCompany.name}</td>
                                        <td style="text-align: left;">${organCompany.abbrName}</td>
                                        <td style="text-align: center;">${organCompany.address}</td>
                                        <td style="text-align: center;">${(organCompany.status=="1")?string('是','否')}</td>
                                        <td style="text-align: center;">${organCompany.createDate?string('yyyy-MM-dd ')!""}</td>
                                        <td style="text-align: center;" >
                                    		
                                    		<#if organCompany.status=="0" >
                                    		<a href="#" onclick="doEditorStatusPost('${organCompany.organId}')">启用</a>&nbsp;&nbsp;
                                    		<#else>
											<a href="#" onclick="doEditorPost('${organCompany.organId}')">编辑</a>&nbsp;&nbsp;
                                    		</#if>
                                    	</td>
                                    </tr>  
                                    </#list>
                              </tbody>
                            </table>
     					   </div>
                  <@pages url="${contextPath}/base/organcompany/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
              </div>
	</form>
	
	
	
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新增机构</h4>
      </div>
      <form name='saveform' id='saveform'  class="form-search" method="post" >
      <div class="modal-body" style="height:250px;"><!--高度根据表单高度相应调整-->
      <input id="isnew" name="isnew" type="hidden" value="1">
      <input id="organCode" name="organCode" type="hidden" value="${(findObj.organCode)!''}" >
                   <div class="ibox-content ">
                            <div class="form-group ziding-ibox-modal model_alert_1">
                                <label class="col-sm-2 control-label">所属机构</label>
                                <!--
                                <div class="col-sm-10 ">
                                 <@organ showLevel='00' fieldId='parentId' fieldName='parentId' defValue='00' showAreaId='_organTag2_' props=" class='form-control'  " />
                                </div>
                                -->
                                <div class="col-sm-10 ">
                                <input id="name1" name ="name1" type="text" class='form-control' readonly  placeholder="选择公司"  onclick="showMenu1();" />
                                <input id="organId1" name ="organId" type="hidden"  value=""  />
                                <input id="parentId1" name ="parentId" type="hidden"  value=""  />
                                </div>
                            </div>	
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label  required">机构名称</label>
                                <div class="col-sm-4">
                                    <input type="text" name="name"  id="name" class="form-control" >
                                </div>
                                <label class="col-sm-2 control-label">机构简称</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="abbrName" id="abbrName" class="form-control">
                                </div>
                            </div>				
                            <div class="form-group ziding-ibox-modal">
                              <label class="col-sm-2 control-label  required">地址</label>
                                <div class="col-sm-10">
                                 <textarea rows="3" id="address" name="address" class="form-control"  placeholder="地址"  ></textarea>
                                </div>							
                            </div>	
                              <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">是否有效</label>
                                <div class="col-sm-10 ">
                                   <@select  fieldId='status' codeType='1000' fieldName='status'  defValue='1' haveHead="false" props=" class='form-control m-b'  "  />    
								</div>								
                            </div>																			
					</div>
      </div>
     </form>
     <div id="menuContent1" class="menuContent" style="display:none;  position: absolute;">
		<ul id="treeDemo1" class="ztree" style="margin-top:0; width:340px; height: 300px;"></ul>
    </div>
      <div class="modal-footer">
      	<input type="hidden" data-seq='00' value="00" id="_defOrganId_">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="save()">保存</button>
      </div>
    
    </div>
  </div>
</div>

<!-- 机构树 -->
	<div id="menuContent" class="menuContent" style="display:none;  position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:340px; height: 300px;"></ul>
    </div>
    <#include "/pub/footer_res_detail.ftl"/>
    <#include "/pub/organ_tree.ftl"/>
	
	<script type="text/javascript">
		function search(){
		 var lv=form1.organId.value.length;
		 if(lv==0){
		    swal({title:"",text:"请选择你查询的机构!"});
		 }else{
			form1.action="${contextPath}/base/organcompany/queryList.do";
			form1.submit();
		}
		}
		function save(){
			if(formValidate()){
			saveform.action="${contextPath}/base/organcompany/save.do";
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
            var saveform = $('#saveform');
            saveform.find('#isnew').val('1');   //1为新增 
            saveform.find('#name').val(''); //   清空
            saveform.find('#name1').val(''); //   清空
            saveform.find('#address').val(''); //   清空
            saveform.find('#abbrName').val(''); //   清空
            saveform.find('#status').find("option[value='1']").attr("selected",true);
            jQuery("#_defOrganId_").val('00').attr('data-seq','00');
            var myModal = $('#myModal');
            myModal.find('.modal-title').text('新增类型');
            myModal.modal('show');  //打开新增界面
            
            } 
            
            function doEditorStatusPost(id){
             $.ajax({  
	            type: "POST",  
	            url: "${contextPath}/base/organcompany/editorstatus.do",
	            data: "status=1&organId=" + id + "&organCode=${(findObj.organCode)!''}",
	            success: function(response){
	            search();
	            },  
	            error: function(e){  
	            alert('Error: ' + e);  
	            }  
	            });  
	            }
      
	    function doEditorPost(id) {  
	            $.ajax({  
	            type: "POST",  
	            url: "${contextPath}/base/organcompany/editor.do",  
	            //data: "organId=" + id + "&organCode=JZ",
	            data: "organId=" + id + "&organCode=${(findObj.organCode)!''}",
	            success: function(response){
	             var saveform = $('#saveform');
	             saveform.find('#isnew').val('0'); //表示当前为修改    1为新增 
	             saveform.find('#organId1').val(response.organId);
	             saveform.find('#organCode').val(response.organCode);
	             saveform.find('#parentId1').val(response.parentId);
	             saveform.find('#name1').val(response.name);
	             saveform.find('#name').val(response.name);
	             saveform.find('#abbrName').val(response.abbrName);
	             saveform.find('#address').val(response.address);
	             //saveform.find('#status').find("option[value='"+response.status+"']").attr("selected",true);
	             
	             //jQuery("#_defOrganId_").val(response.organId).attr('data-seq',response.sysOrganCode);
	             var myModal = $('#myModal');
	             myModal.find('.modal-title').text('编辑类型');
	             myModal.modal('toggle');  //打开编辑界面
	            },  
	            error: function(e){  
	            alert('Error: ' + e);  
	            }  
	            });  
	            }

		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
			
			//organ.initOrgan();
			organ.autoTrigger();
		});
	</script>
	</body>
</html>