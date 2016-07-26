<!DOCTYPE html>
<html lang="en">
	<head>
		<title>客户导入</title>
	<#include "/pub/header_res.ftl"/>
	<#include "/pub/footer_res_detail.ftl"/>
	<style type="text/css">
		div.layui-layer-btn {background-color: green;}
	</style>
	</head>
	<body class="gray-bg">
		<form action="${contextPath}/base/holiday/queryList.do" method="post" name="form1" class="form-horizontal">
			<input type="hidden" id="urgeToPersonId" name="urgeToPersonId" Value="${findObj.urgeToPersonId!''}">
			<div class="container-fluid">
				<div class="panel panel-default" style="margin-top: 1px;">
					<div class="panel-heading">客户导入</div>
					<div class="panel-body" style="padding-bottom: 0px;">
						<div class="col-sm-12">
							<div class="float-e-margins">
								<div class="ibox-content" style="padding:0 0 0 0">
								
									<div class="form-group">
										<label class="col-sm-2 control-label">客户姓名</label>
										<div class="col-sm-2">
											<input type="text" name="clientName" id="clientName" class="form-control" value="${condition.clientName}" />
										</div>
										<label class="col-sm-2 control-label">客户电话</label>
										<div class="col-sm-2">
											<input type="text" name="clientTel" id="clientTel" class="form-control" value="${condition.clientTel}" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">导入日期</label>
										<div class="col-sm-1 ">
                                   			<input type="text" readonly name="stDate" id="stDate" value="${condition.stDate!''}" maxlength="30"  placeholder="开始日期" class="form-control layer-date" >
                                		</div>
										<div class="col-sm-1 ">
                                   			<input type="text" readonly name="edDate" id="edDate" value="${condition.edDate!''}" maxlength="30"  placeholder="结束日期" class="form-control layer-date" >
                                		</div>
                                		<label class="col-sm-2 control-label">状态</label>
										<div class="col-sm-2">
											<@select type="0" codeType='1051' fieldId='status' fieldName='status' defValue="${condition.status}"   props=" class='form-control m-b'"  />
										</div>
										<div class="col-sm-2">
											<a href="#" id="down" onclick="downTemplate();">Excel模板下载</a>
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-4 col-sm-offset-8">
											<input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">&nbsp;&nbsp;
											<input type="button" onclick="importExcel();" value="导入客户" class="btn btn-primary btn-sm zd-btn-pd1">&nbsp;&nbsp;
											<input type="button" onclick="sendMsg();" value="发送短信" class="btn btn-primary btn-sm zd-btn-pd1">
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
								<th style="width:50px;text-align: center;">序号</th>
								<th style="text-align: center;">客户姓名</th>
								<th style="text-align: center;">手机号</th>
								<th style="text-align: center;">合作商</th>
								<th style="text-align: center;">券类型</th>
								<th style="text-align: center;">赠券码</th>
								<th style="text-align: center;">面值</th>
								<th style="text-align: center;">发放时间</th>
								<th style="text-align: center;">状态</th>
								<th style="text-align: center;">使用时间</th>
							</tr>
						</thead>
						<tbody>
							<#list page.list as obj>
								<tr>
									<td style="text-align: center;"><input type="checkbox" name="pickClient" value="${obj.id}"></td>
									<td style="text-align: center;">${obj_index+1}</td>
									<td style="text-align: center;">${obj.clientName}</td>
									<td style="text-align: center;">${obj.clientTel}</td>
									<td style="text-align: center;">${obj.partnerName}</td>
									<td style="text-align: center;">${obj.couponName}</td>
									<td style="text-align: center;">${obj.couponCode}</td>
									<td style="text-align: center;">${obj.value}</td>
									<td style="text-align: center;">${(obj.sendDate?string('yyyy-MM-dd'))!""}</td>
									<td style="text-align: center;">${(obj.status=='1')?string('未发放',(obj.status=='2')?string('未使用',(obj.status=='3')?string('已使用','')))}</td>
									<td style="text-align: center;">${(obj.useDate?string('yyyy-MM-dd'))!""}</td>
								</tr>
							</#list>
						</tbody>
					</table>
				</div>
				<@pages url="${contextPath}/coupon/client/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
			</div>
		</form>

		<!-- export Modal -->
		<div class="modal fade" id="importModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="importModalLabel">客户导入</h4>
					</div>
					<form name='importform' id='importform'  class="form-search" method="post" enctype="multipart/form-data">
						<input type="hidden" id="ids" name="ids">
						<input type="hidden" id="error" name="error" value="${error}">
						<div class="modal-body" style="height:150px;">
							<!--高度根据表单高度相应调整-->
							<div class="ibox-content ">

								<div class="form-group ziding-ibox-modal">
									<label class="col-sm-2 control-label">文件</label>
									<div class="col-sm-10 ">
										<input type="file" readonly name="clientExcel" id="clientExcel" class="form-control layer-date">
									</div>
								</div>
							</div>
						</div>
					</form>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="doImport()">导入</button>
					</div>
				</div>
			</div>
		</div>

		<script type="text/javascript">
			//加载日期控件
			var startDate={
				elem:"#stDate",
				format:"YYYY-MM-DD",
				min:"2000-01-01",
				max:"2099-01-01",
			};
			laydate(startDate);
			
			var endDate={
				elem:"#edDate",
				format:"YYYY-MM-DD",
				min:"2000-01-01",
				max:"2099-01-01",
				istime:false
			};
			laydate(endDate);

			jQuery(document).ready(function(){
				if($("#error").val() != ""){
					layer.alert($("#error").val());
				}
				//加载分页
				jQuery("#pagination").page("form1");
				
				$("#checkAll").on("click",function(){
				if(this.checked){//全选中
					$("input[name = 'pickClient']:checkbox").prop("checked",true);
				}else{//取消全选中
					$("input[name = 'pickClient']:checkbox").prop("checked",false);
				}
				});
				
			});

			//下载模板
			function downTemplate(){
				form1.action = "${contextPath}/coupon/client/downTemplate.do";
				form1.submit();
			}

			//查询
			function search(){
				form1.action="${contextPath}/coupon/client/queryList.do";
				form1.submit();
			}

			//打开导入窗口
			function importExcel(){
				var myModal = $("#importModal");
				myModal.find('#mStartDate').val($('#startDate').val());
				myModal.find('#mEndDate').val($('#endDate').val());
				$("#importModal").modal('show');
			}
			//导入操作
			function doImport(){
			if($("#clientExcel").val() == null || $("#clientExcel").val() == ""){
				layer.alert("请选择导入文件!");
				return;
			}
			
				importform.action = "${contextPath}/coupon/client/importExcel.do";
				importform.submit();
			}
			
			function sendMsg(){
			var picks = $("input[name='pickClient']:checked");
			if(picks.length == 0){
				layer.alert("请勾选客户！");
				return;
			}
			var ids = "";	
			picks.each(function(){
			   ids += $(this).val() +",";
			});
			$("#ids").val(ids.substr(0,ids.length-1));
    		layer.confirm('是否发送短信?', {icon: 3, title:'提示'}, function(index){
    			layer.close(index);
    			var ind = layer.load();
    			$.ajax({  
	            type: "POST",  
	            url: "${contextPath}/coupon/client/sendMsg.do?ids="+ids.substr(0,ids.length-1),  
	            data: "",
	            success: function(resp){
	            	layer.close(ind);
	            	layer.alert(resp.msg);
	            }
	            }); 
	            
			});
    	}
		</script>
	</body>
</html>
