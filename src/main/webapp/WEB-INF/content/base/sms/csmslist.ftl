<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <link href="${contextPath}/res/pub/css/demo/ziding/jquery.onoff.css" rel="stylesheet">
		 <title>自定义短信发送</title>
		 <style type="text/css">
			input.form-control-size {width: 112px;}
		</style>
	</head>
	<body class="gray-bg">
	<form  method="post" action="${contextPath}/base/csms/queryList.do" name="form1" id="form1" class="form-horizontal">

	<div class="container-fluid">
		<div class="panel panel-default" style="margin-top: 1px;">
			<div class="panel-heading">自定义短信发送</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					<div class="col-sm-12">
						<div class="float-e-margins">
			  	 	 		<div class="ibox-content" style="padding:0 0 0 0">
			  	 	 			<div class="form-group ">
                                	<label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">客户姓名</label>
                                	<div class="col-sm-2">                                  
                                    	<input type="text" class="form-control" name="clientName" value="${clientName}" /> 
                                	</div> 
                                	<label class="col-sm-2 control-label" style=" margin:5px;">客户电话</label>
	                                <div id="date" class="col-sm-2 ">
	                                   	<input type="text" class="form-control" name="clientTel" value="${clientTel}" />                                  
	                                </div>
                            	</div>
				  	 	 	 	<div class="form-group ">
	                                <label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">所属机构</label>
	                                <div class="col-sm-2">                                  
	                                    <@select type='1' codeType="1034" fieldId="organId" fieldName="organId" defValue="${organId!''}" haveHead="false" paramName="organId" paramValue="${orgId}" props=" class='form-control ' " /> 
	                                </div> 
	                                <label class="col-sm-2 control-label" style=" margin:5px;">发送状态</label>
	                                <div id="date" class="col-sm-2 ">
	                                   <@select type='0' codeType="1042" fieldId="sendStatus" fieldName="sendStatus" defValue="${sendStatus!''}"  props=" class='form-control ' " />                                  
	                                </div>
	                            </div>                                                               
                               
	                           <div class="form-group ">
	                              <div class="col-sm-4 col-sm-offset-8">
	                              <input type="hidden" id="isQuery" name="isQuery">
				  	 	 	      <input type="submit" id="but" value="查询" class="btn btn-primary btn-sm zd-btn-pd1">&nbsp;&nbsp;
				  	 	 	      </div>
	                           </div>
							</div>
			  			</div>
					</div>
				</div>
			</div>
			
			<div class="panel panel-default table-responsive ziding-td">
				<table class="table table-condensed table-bordered table-striped table-hover" id="table1">
                	<thead>
                    	<tr>
             		 		<th style="text-align: center;width:5px;">序号</th>	
             		 		<th style="text-align: center;">客户姓名</th>	
             		 		<th style="text-align: center;">客户电话</th>	
             		 		<th style="text-align: center;">发送内容</th>		
             		 		<th style="text-align: center;">发送状态</th>	
             		 		<th style="text-align: center;">操作</th>	
                        </tr>
                    </thead>
                    <tbody id="span3">
                    	<#list page.list as obj>
						<tr>
                            <td style="width:5px;">${obj_index+1}</td>
                            <td>${obj.CLIENTNAME}</td>
                            <td>${obj.CLIENTTEL}</td>
                            <td>${obj.MSG}</td>
                            <td>${obj.SENDSTATUS}</td>
                            <td style="text-align: center;">
                            	<button type="button" class="btn btn-primary btn-xs" onclick="showMsgModal('${obj.ID}','${obj.CLIENTTEL}')">发送短信</button>
                            </td>
                                                                     
                        </tr>  
                        </#list>
					</tbody>
               </table>
			</div>		
		</div>
		<@pages url="${contextPath}/base/csms/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
	</form>
	
	<div class="modal fade" id="msgModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						编辑短信
					</h4>
				</div>
				<form name="sendForm" id="sendForm" class="form-search">
					<input type="hidden" id="clientId" name="clientId" value="" />
					<div class="modal-body" style="height:270px;">
						<div class="ibox-content" style="padding:0 0 0 0">
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label">接收号码</label>
                                <div class="col-sm-10">
                                 <input type="text" name="receiveTel" id="receiveTel" readonly value="${receiveTel}" class="form-control" >
                                </div>
                            </div>	
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label" required>短信内容</label>
                                <div class="col-sm-10">
                                 <textarea rows="7" id="msgContent" name="msgContent" class="form-control" value="${msgContent}" placeholder="编辑短信内容" datatype="*" nullmsg="请填写短信内容！"></textarea>
                                </div>
                            </div>	
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" onclick="sendMsg()">发送</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<#include "/pub/footer_res_detail.ftl"/>

	<script type="text/javascript">
    	
		$(document).ready(function(){
			$("#pagination").page("form1");
		});
		
		//短信编辑模态窗口弹出
		function showMsgModal(id,tel){
			var msgModal = $('#msgModal');
			$('#receiveTel').val(tel);
			$('#clientId').val(id);
			msgModal.modal('show');
		}
		
		//发送短信
		function sendMsg(){
			var id = $('#clientId').val();
			var tel = $('#receiveTel').val();
			var msg = $('#msgContent').val();
			if(msg==''){
				layer.alert("请填写短信内容！");
				return;
			}
			layer.confirm('确定发送短信?', {icon: 3, title:'提示'}, function(index){
				$('#msgModal').modal('hide');
				var load = layer.load();
    			layer.close(index);
    			
    			$.ajax({  
	            	type: "POST",  
	            	url: "${contextPath}/base/csms/sendMsg.do",  
	            	data: {
	            		id:id,
	            		tel:tel, 
	            		msg:msg
	            	},
		            success: function(resp){
		            	layer.close(load);
		            	layer.alert(resp.msg,function(index){
		            		$('#but').click();
		            	});
		            }
	            }); 
			});
		}
    	
					 		 	
	</script>
	</body>
</html>