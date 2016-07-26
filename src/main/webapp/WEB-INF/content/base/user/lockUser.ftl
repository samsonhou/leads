<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
			<link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
			<title>锁定用户</title>
	</head>
	<body class="gray-bg">
		<form action="" method="post" name="form1" class="form-horizontal">
			<input type="hidden" id="id">
			<div class="container-fluid">
				<div class="panel panel-default" style="margin-top: 1px;">
					<div class="panel-heading">锁定用户</div>
					<div class="panel-body" style="padding-bottom: 0px;">
						<div class="col-sm-12">
							<div class="float-e-margins">
								<div class="ibox-content" style="padding:0 0 0 0">
									<div class="form-group">
										<label class="col-sm-2 control-label">用户名称</label>
										<div class="col-sm-2 ">
                                   			<input type="text" name="userName" id="userName" value="${userVo.userName!''}" class="form-control">
                                		</div>
                                		<label class="col-sm-2 control-label">组织</label>
                                		<div class="col-sm-2 ">
										<@select type='1' codeType="1034" fieldId="organId" fieldName="organId" defValue="${(userVo.organId)!''}" paramName="organId" paramValue="${(orgId)!''}" haveHead="false"  props=" class='form-control' " /> 
                                		</div>
									</div>
									<div class="form-group">
										<div class="col-sm-4 col-sm-offset-8">
											<input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">&nbsp;&nbsp;
											<input type="button" onclick="unlockBatchSubmit();" value="批量解锁" class="btn btn-primary btn-sm zd-btn-pd1">
											<input type="hidden" id="ids" name="ids">
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
								<th style="width: 40px;text-align: center;"><input type="checkbox" id="checkAll"></th>
								<th style="width: 50px;text-align: center;">序号</th>
								<th style="text-align: center;">操 作</th>
								<th style="text-align: center;">用户名称</th>
								<th style="text-align: center;">用户编码</th>
								<th style="text-align: center;">组织</th>
								<th style="text-align: center;">是否失效</th>
								<th style="text-align: center;">最新锁定时间</th>
								<th style="text-align: center;">锁定次数</th>
							</tr>
						</thead>
						<tbody>
								<#list page.list as user>
									<tr>
										<td style="text-align: center;"><input type="checkbox" name="pickUnlock" value="${user.ID}"></td>
										<td style="text-align: center;">${user_index+1}</td>
										<td style="text-align: center;">
										<a href="#" onclick="unlockConfirm('${user.ID}');">解锁</a>
										</td>
										
										<td>${user.USERNAME}</td>
										<td>${user.JZCODE}</td>
										<td>${user.ORGANID}</td>
										<td>${user.STATUS}</td>
										<td>${(user.LOCKTIME?string('yyyy-MM-dd HH:mm:ss'))!""}</td>
										<td>${user.LOCKTIMES}</td>
									</tr>
								</#list>
								
							</tbody>
						</table>
					</div>
					<@pages url="${contextPath}/base/user/lock/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
					
				</form>
				<div class="modal fade" id="confirmMessageModal">
					<div class="modal-dialog modal-sm">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title">提示信息</h4>
							</div>
							<div class="modal-body text-center">
								<p id="showAlertInfo1"></p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal">取&nbsp;&nbsp;消</button>
								<button id="confirmokBut" type="button" class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal" onclick="unlockSubmit()">确&nbsp;&nbsp;定</button>
							</div>
						</div>
					</div>
				</div>
				<#include "/pub/footer_res_detail.ftl"/>
		<script type="text/javascript">
			$(document).ready(function(){
				//加载分页
				$("#pagination").page("form1");
				//全选或取消全选
				$("#checkAll").on("click",function(){
				if(this.checked){//全选中
					$("input[name = 'pickUnlock']:checkbox").prop("checked",true);
				}else{//取消全选中
					$("input[name = 'pickUnlock']:checkbox").prop("checked",false);
				}
				});
				
				organ.initOrgan();
				organ.autoTrigger();
			});
			
			function search(){
			if($("#organId").val()==""){
			layer.alert("请选择机构！")
			return;
			}
				form1.action = "${contextPath}/base/user/lock/queryList.do";
				form1.submit();
			}
			
			function unlockConfirm(id){
				$("#id").val(id);
				$("#showAlertInfo1").text('是否解锁？');
				$('#confirmMessageModal').modal('show');
			}
			function unlockSubmit(){
				form1.action="${contextPath}/base/user/lock/unlock.do?id="+$("#id").val()+"&currentPage="+$("#pagination").attr("currentpage");
				form1.submit();
			}
			
			function unlockBatchSubmit(){
			var picks = $("input[name='pickUnlock']:checked");
			if(picks.length == 0){
				layer.alert("请勾选要解锁的用户！");
				return;
			}
			var ids = "";	
			picks.each(function(){
			   ids += $(this).val() +",";
			});
			$("#ids").val(ids.substr(0,ids.length-1));
			layer.confirm('确认批量解锁?', {icon: 3, title:'提示'}, function(index){
				form1.action = "${contextPath}/base/user/lock/unlockBatch.do?currentPage="+$("#pagination").attr("currentpage");
				form1.submit();
			});
			}

		</script>
		</body>
</html>
