<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>客户线索</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/client/queryList.do" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">客户线索查询</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					<div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="clientName" value="${clientVO.clientName!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label  required">手机</label>
                                <div class="col-sm-4">
                                    <input type="text" name="tel" maxlength="11" value="${clientVO.tel!''}" class="form-control" >
                                </div>
                            </div>
                            
                             <div class="form-group ">
                                <label class="col-sm-2 control-label">等级</label>
                                <div class="col-sm-4 ">
                                    <@select type='0' codeType="1026" defValue="${clientVO.rank!''}" fieldId="rank" fieldName="rank"  props=" class='form-control' " />
                                </div>
                                <label class="col-sm-2 control-label  required">是否紧急</label>
                                <div class="col-sm-4">
                                    <@select type='0' codeType="1000" defValue="${clientVO.ifurgent!''}" fieldId="ifurgent" fieldName="ifurgent"  props=" class='form-control' " />
                                </div>
                            </div>
                            
                             <div class="form-group">
                                <label class="col-sm-2 control-label">业务大类</label>
                                <div class="col-sm-4">
                                    <@select type='1' codeType="1021" defValue="${clientVO.bigPid!'-1'}" fieldId="bigPid" fieldName="bigPid"  paramName="pid" paramValue="0" props=" class='form-control' " />
                                </div>
                                <label class="col-sm-2 control-label">业务小类</label>
                                <div class="col-sm-4" id="samllPidDiv" data-defvalue="${clientVO.smallPid!-1}">
		    			  			<@select type='1' codeType="1021" defValue="${clientVO.samllPid!'-1'}" fieldId="samllPid" fieldName="samllPid"  paramName="pid" paramValue="111" props=" class='form-control' " />
                                </div>
                            </div>
                            
                             <div class="form-group">
                                <label class="col-sm-2 control-label">是否未分配</label>
                                <div class="col-sm-4">
                                    <@select type='0' codeType="1000" defValue="${clientVO.isAssign!''}" fieldId="isAssign" fieldName="isAssign"  props=" class='form-control' " />
                                </div>
                            </div>
                            
                             <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                   <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                   &nbsp; &nbsp; 
	                                   <input type="button" value="新 增" onclick="newclient();" class="btn btn-primary btn-sm zd-btn-pd1"  data-toggle="modal" data-target="#myModal">

	                             </div>
                            </div>
			  	 	 	 	
			  	 	 	 </div>
			  	 	 </div>
			  	 	 </div>
				</div>
			</div>
			
			<div class="panel panel-default table-responsive ziding-td">
				<table class="table table-condensed table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                    	<th style="text-align: center;width: 50px;">序号</th>
                                    	<th style="text-align: center;">操作</th>
                                        <th style="text-align: center;">客户姓名</th>
                                        <th style="text-align: center;">业务类别</th>
                                        <th style="text-align: center;">手机</th>
                                        <th style="text-align: center;">客服</th>
                                        <th style="text-align: center;">客户经理</th>
                                        <th style="text-align: center;">来源</th>
                                        <th style="text-align: center;">等级</th>
                                        <th style="text-align: center;">电话具体原因</th>
                                        <th style="text-align: center;">下次跟踪时间</th>
                                        <th style="text-align: center;">填写时间</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#list page.list as client>
									<tr>
										<td style="text-align: center;">${client_index+1}</td>
                                        <td style="text-align: center;">
                                        	<a href="#" onclick="viewClient('${client.ID}');">查看</a>
                                        	&nbsp;
                                        	<a href="#" onclick="updateClient('${client.ID}');">修改</a>
                                        	&nbsp;
                                        	<a href="#" onclick="hastenTask('${client.ID}');">催促</a>
                                        	&nbsp;
                                        	<a href="#" onclick="showMsgModal('${client.ID}','${client.TEL}')">发送短信</a>
                                        </td>
                                        <td>${client.NAME}</td>
                                        <td>${client.BIG_PID} - ${client.SMALL_PID}</td>
                                        <td>${client.TEL}</td>
                                        <td>${client.RID}</td>
                                        <td>${client.SID}</td>
                                        <td>${client.FROMTYPE}</td>
                                        <td>${client.RANK}</td>
                                        <td>${client.STATUS}</td>
                                        <td>${client.NEXTDATE}</td>
                                        <td>${client.QDATE}</td>
                                        
                                    </tr>  
                                    </#list>
                              </tbody>
                            </table>
			</div>
			<@pages url="${contextPath}/leads/client/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
		</div>
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
	<#include "/pub/message.ftl"/>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		function search(){
			form1.action="${contextPath}/leads/client/queryList.do";
			form1.submit();
		}
		function newclient(){
			openNewTab("${contextPath}/leads/client/addClient.do","新增客户");
		}
		function updateClient(obj){
			var parm = "id="+obj;
			openNewTab("${contextPath}/leads/client/updateClient.do?"+parm,"修改客户");
		}
		function viewClient(obj){
			var parm = "id="+obj;
			openNewTab("${contextPath}/leads/assign/addTrace.do?"+parm,"查看线索");
		}
		//催促回访
		function hastenTask(taskId){
			var index = layer.load(0,{shade: [0.5,'#000']});
			$.ajax({
				type:"post",
				url:"${contextPath}/leads/client/hasten.do?taskId="+taskId,
				data:"",
				dataType:"text",
				success:function(data){
					layer.close(index);
					jQuery('#showAlertInfo').text(data);
					jQuery('#messageModal').modal('show');
				}
			});	
		}
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
			//初始化 业务小类 bigPid
			if(jQuery("#bigPid").val().length>0){
				jQuery("#bigPid").trigger('change');
			}
		});
		
		$("#bigPid").on("change", function(){
			$.ajax({
				type:"post",
				url:"${contextPath}/leads/client/querySub.do?bigPid="+jQuery("#bigPid").val(),
				data:"",
				dataType:"text",
				success:function(data){			
					var $samllPidDiv=jQuery("#samllPidDiv");
					var v=$samllPidDiv.attr("data-defvalue");
					if(v=='-1' || v=='0'){
						v='';
					}
					$samllPidDiv.empty().html(data).find("select").addClass("form-control").val(v);
					$samllPidDiv.attr("data-defvalue","");	    
				}
			});	
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
		            		layer.close(index);
		            	});
		            }
	            }); 
			});
		}
	</script>
	</body>
</html>