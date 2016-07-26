
<!DOCTYPE html>
<html lang="en">
<head>
<#include "/pub/message.ftl"/>
<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
<style>
div.content_wrap {width: 600px;height:380px;}
div.content_wrap div.left{float: left;width: 250px;}
div.content_wrap div.right{float: right;width: 340px;}
div.zTreeDemoBackground {width:250px;height:362px;text-align:left;}

ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:360px;overflow-y:scroll;overflow-x:auto;}
ul.log {border: 1px solid #617775;background: #f0f6e4;width:300px;height:170px;overflow: hidden;}
ul.log.small {height:45px;}
ul.log li {color: #666666;list-style: none;padding-left: 10px;}
ul.log li.dark {background-color: #E3E3E3;}

		 </style>
<title>产品管理</title>
<style>
.jstree-open>.jstree-anchor>.fa-folder:before {
	content: "\f07c";
}

.jstree-default .jstree-icon {
	color: #F90;
}
</style>
</head>
<body class="gray-bg">
	<form action="#" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">

			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">产品列表</div>
				<div class="panel-body">
					<div class="col-sm-12">
						<div class="float-e-margins">
							<div class="ibox-content" style="padding: 0 0 0 0">

								<div class="row">
									<div class="col-sm-4" style="border-right:1px solid #ccc">
										<div class="ibox float-e-margins">
											<div class="ibox-content">
												<!-- <ul id="treeDemo" class="ztree" style="margin-top:0; width:240px; height: 300px;"></ul> -->
<ul id="treeDemo" class="ztree"  
    style="margin: 0px; background-color: #ffffff;height: 460px;border: 0px;padding:0px; width: 100%"></ul>
											</div>
										</div>
									</div>
									<div class="col-sm-8">
										<div class="ibox float-e-margins">
											<div class="ibox-content">
												<div class="ibox ">
													<div id="c_content">
													<div class="modal-content">
      <#if ifCManager = 'true'>
      <div class="modal-header">
        <button type="button" id="addbutton" disabled="disabled" class="btn btn-danger btn-xs" onclick="increased();">添加</button>&nbsp;&nbsp;
		<button type="button" id="debutton"  disabled="disabled" class="btn btn-danger btn-xs" onclick="deletep();">删除</button>
      </div>
      </#if>
      <form name='saveform' id='saveform'  class="form-search" method="post" >
      <div class="modal-body" style="height:380px;"><!--高度根据表单高度相应调整-->
                   <div class="ibox-content " >
                    
                            	<div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label model_left_z">产品简称   <font color="#ff0000">*</font></label>
                                <div class="col-sm-10 ">
                                 <input type="text" name="abname"  id="abname"  maxlength="15" class="form-control" >
                                 <input id="id" name ="id" type="hidden" />
                                 <input id="pid" name ="pid" type="hidden" />  
                                 <input id="addtype" name ="addtype" type="hidden" />  <!-- 1 新增。2修改 -->
                                </div>
                                
                            </div>
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label model_left_z">产品名称   <font color="#ff0000">*</font></label>
                                <div class="col-sm-10 ">
                                 <input type="text" name="pname"  id="pname" maxlength="33" class="form-control" >
                                </div>
                            </div>	
						
                            <div id="hide1" class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label model_left_z">产品介绍</label>
                                <div class="col-sm-10 ">
                                 <textarea rows="2" id="pdescribe" name="pdescribe" maxlength="333"  class="form-control" ></textarea>
                                </div>
                                
                            </div>	
                             <div id="hide2" class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label model_left_z">话术</label>
                                <div class="col-sm-10 ">
                                 <textarea rows="8" id="pdetail" name="pdetail" maxlength="1000" class="form-control" ></textarea>
                                </div>                                
                            </div>                               																
					</div>
					<br/>
      </div>
     </form>
     <#if ifCManager = 'true'>
      <div class="modal-footer">
        <!-- <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>-->
        <button type="button" id="savebut" disabled="disabled" class="btn btn-primary" onclick="save()">保存</button>
      </div>
      </#if>
    </div>

													</div>
												</div>
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		function increased(){
			if($("#pid").val() !=''){
				$("#abname").val('');
				$("#pname").val('');
				$("#pdescribe").val('');
				$("#pdetail").val('');
				$("#pid").val($("#id").val());
				$("#id").val('');
				$("#addtype").val('1');
				$("#savebut").html('新增保存');
				$('#debutton').attr('disabled',"true");
				$('#addbutton').attr('disabled',"true");
				$('#hide2').show();
			}		
		}

		function confirm(){  
			form1.action="${contextPath}/leads/product/delete.do?id="+$("#id").val();
			form1.submit();
		}
		
		//单个删除
		function deletep(id){
		    if($("#pid").val() =='null'){
				swal({title:"",text:"一级产品内容不能删除！"});
				return;
			}
		    $("#showAlertInfo1").text('你确认要删除此产品内容及所有子级别的产品内容吗？');
		    $('#confirmMessageModal').modal('show');
		}
		
		function save(){
			if($.trim($("#abname").val()) == ''){
				swal({title:"",text:"请输入产品简称！"});
				return;
			}
			if($.trim($("#pname").val()) == ''){
				swal({title:"",text:"请输入产品名称！"});
				return;
			}
			var addtype =$("#addtype").val();
			if(addtype == '1')
				form1.action="${contextPath}/leads/product/add.do";
			else if(addtype == '2')
				form1.action="${contextPath}/leads/product/modify.do";
			form1.submit();
		}
	
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
	
		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			$("#abname").val(v);
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].id+ ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			$("#id").val(v);
	
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].pId+ ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			$("#pid").val(v);
			
			if($("#pid").val() ==''){
				$('#addbutton').attr('disabled',"true");
				$('#debutton').attr('disabled',"true");
				$("#addtype").val('');
				$("#pname").val('');
				$("#pdescribe").val('');
				$("#pdetail").val('');
				$('#savebut').attr('disabled',"true");
				$('#hide2').show();
				//alert('1111');
			}else{
				$('#addbutton').removeAttr("disabled"); 
				$('#debutton').removeAttr("disabled"); 
				$("#addtype").val('2');				
				$("#savebut").html('修改保存');
				$('#savebut').removeAttr("disabled");
				//alert('222222');
				$.ajax({
					type : "post",
					url : "${contextPath}/leads/product/queryRecord.do",
					data: "id=" + $("#id").val() ,  
					async:true,
					success : function(data) {
						$("#pname").val(data.pname);
						$("#pdescribe").val(data.pdescribe);
						$("#pdetail").val(data.pdetail);
						if($("#pid").val()=='null'){
							$('#hide2').hide();
						}else{
							$('#hide2').show();
						}
					}
				});
			}
		}
		
		var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick,
				onCheck: onCheck
			}
		};
		
		$(document).ready(function(){
			//$("#pagination").page("form1");
			
			var zNodes;
			$.ajax({
				type : "post",
				url : "${contextPath}/leads/product/queryProducts.do",
				data : "",
				async:true,
				dataType : "text",
				success : function(data) {
					zNodes = eval(data);				
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				}
			});
			
			if($("#savebut").length>0){
				//alert('31');
			}else{
				$('input[name=abname]').attr("readonly","readonly")
				$('input[name=pname]').attr("readonly","readonly")
				$('textarea[name=pdescribe]').attr("readonly","readonly")
				$('textarea[name=pdetail]').attr("readonly","readonly")
			}
			
			var message="${message!''}";
			if(message.length>0){
				jQuery('#messageModal').modal('show');
			}
		});
	</script>
</body>
</html>