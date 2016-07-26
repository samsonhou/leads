<!DOCTYPE html>
<html lang="en">
	<head>
		<title>节假日维护</title>
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
					<div class="panel-heading">节假日管理</div>
					<div class="panel-body" style="padding-bottom: 0px;">
						<div class="col-sm-12">
							<div class="float-e-margins">
								<div class="ibox-content" style="padding:0 0 0 0">
									<div class="form-group">
										<label class="col-sm-2 control-label">起止日期</label>
										<div class="col-sm-2 ">
                                   			<input type="text" readonly name="startDate" id="startDate" value="${startDate!''}" maxlength="30"  placeholder="开始日期" class="form-control layer-date" >
                                		</div>
										<div class="col-sm-2 ">
                                   			<input type="text" readonly name="endDate" id="endDate" value="${endDate!''}" maxlength="30"  placeholder="结束日期" class="form-control layer-date" >
                                		</div>
										<div class="col-sm-2">
											<a href="#" id="down" onclick="downTemplate();">Excel模板下载</a>
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-4 col-sm-offset-8">
											<input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">&nbsp;&nbsp;
											<input type="button" onclick="openEdit(null);" value="新增" class="btn btn-primary btn-sm zd-btn-pd1">&nbsp;&nbsp;
											<input type="button" onclick="importExcel();" value="Excel导入" class="btn btn-primary btn-sm zd-btn-pd1">
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
								<th style="text-align: center;">日期</th>
								<th style="text-align: center;">类型</th>
								<th style="text-align: center;">操作人</th>
								<th style="text-align: center;">操作时间</th>
								<th style="text-align: center;">操作</th>
							</tr>
						</thead>
						<tbody>
							<#list page.list as object>
								<tr>
									<td style="text-align: center;">${object_index+1}</td>
									<td style="text-align: center;" id="${object.id}">${object.holiday?string('yyyy-MM-dd')}</td>
									<td style="text-align: center;">
										<#if object.holidayType = "1">假日
											<#elseif object.holidayType = "2">节日
											<#elseif object.holidayType = "3">自定义
										</#if>
										<input type="hidden" value="${object.holidayType}" id="holidayType${object.id}">
									</td>
									<td style="text-align: center;">${object.modifyUserName}</td>
									<td style="text-align: center;">${(object.modifyDate?string('yyyy-MM-dd HH:mm:ss'))!""}</td>
									<td style="text-align: center;">
										<input type="button" class="btn btn-primary btn-xs" value="修改" onclick="openEdit('${object.id}');">&nbsp;&nbsp;
										<input type="button" class="btn btn-primary btn-xs" value="删除" onclick="deleteHoliday('${object.id}');">
									</td>
								</tr>
							</#list>
						</tbody>
					</table>
				</div>
				<@pages url="${contextPath}/base/holiday/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
			</div>
		</form>

		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">修改节假日</h4>
					</div>
					<form name='saveform' id='saveform'  class="form-search" method="post" >
						<input type="hidden" id="id" name="id">
						<input type="hidden" id="mStartDate" name="startDate">
						<input type="hidden" id="mEndDate" name="endDate">
						<input type="hidden" id="duplicatFlag" value="${duplicatFlag}">
						<div class="modal-body" style="height:230px;">
							<!--高度根据表单高度相应调整-->
							<div class="ibox-content ">

								<div class="form-group ziding-ibox-modal">
									<label class="col-sm-2 control-label">日期</label>
									<div class="col-sm-10 ">
										<input type="text" readonly name="holiday" id="holiday"  maxlength="30"  placeholder="选择日期" class="form-control layer-date">
									</div>
								</div>
								<div class="form-group ziding-ibox-modal">
									<label class="col-sm-2 control-label">类型</label>
									<div class="col-sm-4 ">
										<@select type='0' codeType="1032" fieldId="holidayType" fieldName="holidayType"  props=" class='form-control' " />
									</div>
								</div>
							</div>
						</div>
					</form>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="saveEdit()">保存</button>
					</div>
				</div>
			</div>
		</div>

		<!-- export Modal -->
		<div class="modal fade" id="importModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="importModalLabel">节假日导入</h4>
					</div>
					<form name='importform' id='importform'  class="form-search" method="post" enctype="multipart/form-data">
						<input type="hidden" id="mStartDate" name="startDate">
						<input type="hidden" id="mEndDate" name="endDate">
						<input type="hidden" id="error" name="error" value="${error}">
						<div class="modal-body" style="height:150px;">
							<!--高度根据表单高度相应调整-->
							<div class="ibox-content ">

								<div class="form-group ziding-ibox-modal">
									<label class="col-sm-2 control-label">文件</label>
									<div class="col-sm-10 ">
										<input type="file" readonly name="holidayExcel" id="holidayExcel" class="form-control layer-date">
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
				elem:"#startDate",
				format:"YYYY-MM-DD",
				min:"2000-01-01",
				max:"2099-01-01",
				istime:false,
				istoday:false
			};
			laydate(startDate);
			
			var endDate={
				elem:"#endDate",
				format:"YYYY-MM-DD",
				min:"2000-01-01",
				max:"2099-01-01",
				istime:false,
				istoday:false
			};
			laydate(endDate);

			jQuery(document).ready(function(){
				if($("#duplicatFlag").val()>0){
					layer.alert("修改日期重复！", {closeBtn: 1});
				}
				if($("#error").val() != ""){
					layer.alert($("#error").val());
				}
				//加载分页
				jQuery("#pagination").page("form1");
				organ.initOrgan("_organTag_");

			});

			//加载日期控件
			var mholiday={
				elem:"#holiday",
				format:"YYYY-MM-DD",
				min:"2000-01-01",
				max:"2099-01-01",
				istime:false,
				istoday:false
			};
			laydate(mholiday);

			//下载模板
			function downTemplate(){
				form1.action = "${contextPath}/base/holiday/downTemplate.do";
				form1.submit();
			}

			//查询
			function search(){
				//$("#year").val($("#years option:selected").val());
				if($("#startDate").val()==""){
					layer.alert("请选择起始日期！");
					return;
				}
				if($("#endDate").val()==""){
					layer.alert("请选择结束日期！");
					return;
				}
				if($("#endDate").val() < $("#startDate").val()){
					layer.alert("结束日期不能小于起始日期！");
					return;
				}
				form1.action="${contextPath}/base/holiday/queryList.do";
				form1.submit();
			}

			//打开修改窗口
			function openEdit(id){
				var myModal = $('#myModal');
				if(id == null) {
					myModal.find('#holiday').val('');
					myModal.find('select').find('option:first').attr('selected',true);
					myModal.find('.modal-title').text('新增节假日期');
				}else{

					myModal.find('#holiday').val($("#"+id).text());

					myModal.find('select').find('option').each(function(){
						if($(this).val() == $("#holidayType"+id).val()){
							$(this).attr('selected',true);
						}
					}); //带出所选日期初始类型
					myModal.find('#id').val(id);
					myModal.find('.modal-title').text('节假日期修改');
				}
				myModal.find('#mStartDate').val($('#startDate').val());
				myModal.find('#mEndDate').val($('#endDate').val());
				myModal.modal('show');  //打开编辑界面

			}

			//保存修改
			function saveEdit(){
				var myModal = $("#myModal");
				if(myModal.find("#holidayType").val() == ""){
					layer.alert("请选择节假日类型");
					return;
				}
				if(myModal.find("#holiday").val() == ""){
					layer.alert("请选择节假日期");
					return;
				}
				saveform.action = "${contextPath}/base/holiday/editHoliday.do?currenPage="+$("#pagination").attr("currentpage");
				saveform.submit();

			}

			//删除操作
			function deleteHoliday(id){
				layer.confirm('确认删除?', {icon: 3, title:'提示'}, function(index){
					form1.action = "${contextPath}/base/holiday/deleteHoliday.do?selectID="+id+"&currenPage="+$("#pagination").attr("currentpage");
					form1.submit();
					layer.close(index);
				});
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
				importform.action = "${contextPath}/base/holiday/importExcel.do";
				importform.submit();
			}
		</script>
		<script src="${contextPath}/res/pub/js/plugins/layer/layer.min.js"></script>
		<script src="${contextPath}/res/pub/js/demo/layer-demo.min.js"></script>
	</body>
</html>
