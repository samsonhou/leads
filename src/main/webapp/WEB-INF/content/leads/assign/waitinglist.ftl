<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>线索分配</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/assign/query.do" method="post" name="form1" class="form-horizontal">
		<input type="hidden" id="assginItems" name="assginItems">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">分配查询</div>
			  <div class="panel-body" style="padding-bottom: 0px;">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	<div class="form-group">
                                <label class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="clientName" value="${vo.clientName!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label  required">手机</label>
                                <div class="col-sm-2">
                                  <input type="text" name="tel" maxlength="11" value="${vo.tel!''}" class="form-control" >
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-2 control-label">来源</label>
                                 <div class="select_org col-sm-4">
                                <@fromtype defValue="${vo.fromType}" props=" class='form-control'"/>
								</div> 
                            	<label class="col-sm-2 control-label">是否分配</label>
                            	<div class="col-sm-2">
                                <@select type='0' codeType="1000" defValue="${vo.status!''}" fieldId="status" fieldName="status"  props=" class='form-control' " />
								</div> 
                            </div>
                            
                            <div class="form-group">
                                 <label class="col-sm-8 control-label"></label>
                                <div class="col-sm-4">
	                                 <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                 &nbsp;&nbsp;
	                                 <input type="button" onclick="assignMult();" value="多选分配" class="btn btn-primary btn-sm zd-btn-pd1">
	                                 &nbsp;&nbsp;
	                                 <input type="button" onclick="clueImport();" value="线索导入" class="btn btn-primary btn-sm zd-btn-pd1">
	                                 &nbsp;&nbsp;
	                                 <input type="button" onclick="downTemplate();" value="导入模板下载" class="btn btn-primary btn-sm zd-btn-pd1">
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
					 		<th style="text-align: center;width: 50px;">序号</th>
					 		<th style="text-align: center;width: 50px;"><input type="checkbox" id="checkAll"></th>
					 		<th style="text-align: center;width: 100px;">操作</th>
					 		<th style="text-align: center;">客户姓名</th>
                            <th style="text-align: center;">手机</th>
                            <th style="text-align: center;">城市</th>
                            <th style="text-align: center;">来源</th>
                            <th style="text-align: center;">状态</th>
					 	</tr>
					 </thead>
					 <tbody>
					 	<#list page.list as client>
									<tr>
										<td style="text-align: center;">${client_index+1}</td>
										<td style="text-align: center;"><input type="checkbox" name="assginItem" value="${client.id}"></td>
										<td style="text-align: center;"><a href="javascript:void(0);" onclick="assignSign('${client.id}');">分配</a>
                                        </td>
                                        <td>${client.clientName}</td>
                                        <td>${client.tel}</td>
                                        <td>${client.city}</td>
                                        <td>${client.fromTypeDesc}</td>
                                        <td>${(client.status='1')?string('已分配','未分配')}</td>
                                    </tr>  
                                    </#list>
					 </tbody>
				</table>
			</div>	
			<@pages url="${contextPath}/leads/assign/wait/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />	
		</div>
		<div id="menuContent" class="menuContent" style="display:none;  position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:340px; height: 300px;"></ul>
		</div>
		<input id="ids" name="ids" type="hidden" value="-1">
		<input id="msg" type="hidden" value="${msg}">
	</form>
	
	
	<form name="uploadForm" action="${contextPath}/leads/assign/wait/clueImport.do" enctype="multipart/form-data" method="POST">
		 <input id="uploadFile" type="file" name="uploadFile" onchange="doImport(this)" style="display:none" />
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
	        <button type="button" class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal">确&nbsp;&nbsp;定</button>
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
	        <p id="showAlertInfo1"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal">取&nbsp;&nbsp;消</button>
	        <button id="confirmokBut" type="button" class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal" onclick="confirm()">确&nbsp;&nbsp;定</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<#include "/pub/footer_res_detail.ftl"/>
	<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script type="text/javascript">
	
	var myData_1022=<@queryselect type="1" codeType="1022" />
 		//读取下拉框的 值，健 
 		function getmagicSuggest_1022(){
 			ms1 = $('#magicsuggest_1022').magicSuggest({
 		        width: '80%',//宽度
 		        placeholder: '请选择',
 		        style:'float:left;width:100%;',
 		        allowFreeEntries: false,   //这个参数很重要，如果你不需要用户自已创建标签，则用这个
 		        data: myData_1022.data,
 		        selectionStacked: true ,
 		        maxSelectionRenderer: function(data){ return ""},
 		        noSuggestionText: '',
 		        maxSelection:1 //单选按照 0取值 
 		    });
 		    $(ms1).on('selectionchange', function(e, cb, s){
 		     var object =cb.getSelection()[0];  
 		    console.log(object);
 		    if(undefined==object){$("#fromtype").val("");}else{
 		     $("#fromtype").val(object.id);}
 		    });
 		    getStoredCallback_1022(ms1);
 		}
 		//获取查询条件回显
 	function getStoredCallback_1022(ms1){ 
 	  var bl = $('#fromtype').val();
 	  if(bl == ''||0==bl) return;
 	  var array = bl.split(","); 
 	  //设置延迟，否则取不到数据
 	  setTimeout(function (){
     	  ms1.setValue(array);
     	  }, 200);
 	}
	
		function search(){
			form1.action="${contextPath}/leads/assign/wait/queryList.do";
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
				openNewTab("${contextPath}/leads/assign/wait/toAssgin.do?assginItems="+params,"人员分配");
			}
		}
		//单个分配
		function assignSign(itemId){
			jQuery("#assginItems").val(itemId);
			form1.action = "${contextPath}/leads/assign/wait/toAssgin.do?assginItems="+itemId;
			form1.submit();
			//openNewTab("${contextPath}/leads/assign/wait/toAssgin.do?assginItems="+itemId,"人员分配");
		}
		//查看
		function viewDetail(itemId){
			var parm = "id="+itemId;
			openNewTab("${contextPath}/leads/assign/addTrace.do?"+parm,"查看线索");
		}
		
		
		function confirm(){  
			form1.action="${contextPath}/leads/assign/delData.do";
			form1.submit();
		}
		
		//单个删除  flag:0=管理员
		function delData(flag,id){
		    if(flag!='0'){
		        $("#showAlertInfo").text('只有管理员才能删除.');
	    		$('#messageModal').modal('show');
	    		return;
		    }
		     $("#ids").val(id);
		     $("#showAlertInfo1").text('你确认要删除吗？');
		     $('#confirmMessageModal').modal('show');
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
		jQuery(document).ready(function(){
			//getmagicSuggest_1022();
			var msg = jQuery("#msg").val();
			if(msg.length>0){
				layer.alert(msg);
			}
		
			jQuery("#pagination").page("form1");
			//全选或取消全选
			jQuery("#checkAll").on("click",function(){
				if(this.checked){//全选中
					jQuery("[name = assginItem]:checkbox").prop("checked",true);
				}else{//取消全选中
					jQuery("[name = assginItem]:checkbox").prop("checked",false);
				}
			});
			
			$("#fromtypeBig option").each(function(i,o){
			if($(this).attr("selected")){
						$(".fromtype").hide();
						if(i!=0){
							$(".fromtype").eq(i).removeAttr("disabled");
							$(".fromtype").eq(i).show();
						}
					}else{
						if(i==1){
							$(".fromtype").eq(i).find("#fromtype").attr("disabled",true);
						}
						$(".fromtype").eq(i).attr("disabled",true);
					}
			});
			
			$("#fromtypeBig").on("change",function(){
				$("#fromtypeBig option").each(function(i,o){
					if($(this).prop("selected")){
						$(".fromtype").hide();
						if(i!=0){
							if(i==1){
								$(".fromtype").eq(i).find(".ms-sel-item").html("");
								$(".fromtype").eq(i).find("#fromtype").val("");
								$(".fromtype").eq(i).find("#fromtype").removeAttr("disabled");
							}
							$(".fromtype").eq(i).removeAttr("disabled");
							$(".fromtype").eq(i).show();
						}
					}else{
						if(i==1){
							$(".fromtype").eq(i).find("#fromtype").attr("disabled",true);
						}
						$(".fromtype").eq(i).attr("disabled",true);
					}
				});
			});
		
		
		});
		
		
		
		//线索导入
		function clueImport(){
			$("#uploadFile").click();  
		}
		function doImport(obj){
			var fileName = $("#uploadFile").val();
			if(fileName == ""){
				layer.alert('请选择要导入的Excel文件！');
				return;
			}else{
				var suffix = fileName.substr(fileName.lastIndexOf(".")); 
				if(".xls" != suffix && ".xlsx" != suffix){
					layer.alert('请勿导入非Excel类型的文件！');
					return;
				}
				uploadForm.submit();
			}
		}
		
		
		//模板下载
		function downTemplate(){
			form1.action="${contextPath}/leads/assign/wait/downTemplate.do";
			form1.submit();
		}
	</script>
	</body>
</html>