<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>赠券管理</title>
	</head>
	<body class="gray-bg">
	<form action="" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
            <div class="panel-heading">赠券查询</div>
                <div class="panel-body" style="padding-bottom: 0px;">
                    <div class="col-sm-12">
                    <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">合作商名称</label>
                                <div class="col-sm-2">
                               		<@select type="1" codeType='1050' fieldId='partnerId' fieldName='partnerId' defValue="${condition.partnerId}"  props=" class='form-control m-b' onchange='getCouponType(this.value)' "  />
                                </div>
                                <label class="col-sm-2 control-label">赠券类型</label>
                                <div class="col-sm-2">
                               		<select id="couponTypeId" name="couponTypeId" class="form-control"><option value="">请选择</option></select>
                               		<input id="couponTypeIdTmp" type ="hidden" value="${condition.couponTypeId}" />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">赠券状态</label>
                                <div class="col-sm-2">
                               		<@select type="0" codeType='1051' fieldId='status' fieldName='status' defValue="${condition.status}"   props=" class='form-control m-b'"  />
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
                                        <th style="text-align: center;">合作商名称</th>
                                        <th style="text-align: center;">合作商编号</th>
                                        <th style="text-align: center;">赠券类型</th>
                                        <th style="text-align: center;">面值</th>
                                        <th style="text-align: center;">数量</th>
                                        <th style="text-align: center;">状态</th>
                                    </tr>
                                </thead>
                                
                                
                                <tbody>
                                	<#list page.list as obj>
									<tr>
                                        <td style="text-align: center;">${obj_index+1}</td>
                                       	<td style="text-align: center;">${obj.partnerName}</td>
                                       	<td style="text-align: center;">${obj.partnerCode}</td>
                                        <td style="text-align: left;">${obj.couponName}</td>
                                        <td style="text-align: left;">${obj.value}</td>
                                        <td style="text-align: center;">${obj.count}</td>
                                        <td style="text-align: center;">${(obj.status=='1')?string('未发放',(obj.status=='2')?string('未使用',(obj.status=='3')?string('已使用','')))}</td>
                                    </tr>  
                                    </#list>
                              </tbody>
                            </table>
     					   </div>
                  <@pages url="${contextPath}/coupon/gen/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
              </div>
	</form>
	
	
	
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document" style="width:1000px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新增赠券</h4>
      </div>
      <form name='saveform' id='saveform'  class="form-search" method="post" >
      <div class="modal-body" style="height:250px;"><!--高度根据表单高度相应调整-->
      <input type="hidden" name="id" id="id"/>
                   <div class="ibox-content ">
                   
                            <div class="form-group ziding-ibox-modal model_alert_1">
                                <label class="col-sm-2 control-label">合作商名称</label>
                                <div class="col-sm-4 ">
                                	<@select type="1" codeType='1050' fieldId='partnerId' fieldName='partnerId'  props=" datatype='*' nullmsg='请选择合作商！' class='form-control m-b' onchange='getCouponType1(this.value)' "  />
                                </div>
                                <label class="col-sm-2 control-label">赠券类型</label>
                                <div class="col-sm-4 ">
                                	<select id="couponTypeId" name="couponTypeId" class="form-control" datatype="*" nullmsg="请选择赠券类型！"><option>请选择</option></select>
                                </div>
                            </div>	
                            
                            <div class="form-group ziding-ibox-modal model_alert_1">
                            	<label class="col-sm-2 control-label">数量</label>
                                <div class="col-sm-4 ">
                                	<input type="text" name="count" id="count" class="form-control" datatype="*" nullmsg="请填写数量！" />
                                </div>
                            	<label class="col-sm-2 control-label">面值</label>
                                <div class="col-sm-4 ">
                                	<input type="text" name="value" id="value" class="form-control" datatype="*" nullmsg="请填写面值！" />
                                </div>
                            </div>
                            	
                              																		
					</div>
      </div>
     </form>
      <div class="modal-footer">
      	<input type="hidden" data-seq='00' value="00" id="_defOrganId_">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" id="btn_sub" class="btn btn-primary" onclick="save()">保存</button>
      </div>
    
    </div>
  </div>
</div>


    <#include "/pub/footer_res_detail.ftl"/>
	
	<script type="text/javascript">
	// 级联赠券类型
	function getCouponType(id) {
		if(id == ""){
			$("#couponTypeId").html("<option value=''>请选择</option>");
			return;
		}
		var params = {
			id : id
		};
		$.ajax({
			url : "${contextPath}/coupon/gen/queryCouponType.do",
			data : params,
			async : false,
			dataType : 'json',
			success : function(data) {
				$("#couponTypeId").html("<option value=''>请选择</option>");
				for (var i = 0; i < data.length; i++) {
					$("#couponTypeId").append(
							"<option value='" + data[i].id + "'>"
									+ data[i].couponName + "</option>");
				}
			}
	
		});
	}
	function getCouponType1(id) {
		if(id == ""){
			$("#myModal").find("#couponTypeId").html("<option>请选择</option>");
			return;
		}
		var params = {
			id : id
		};
		$.ajax({
			url : "${contextPath}/coupon/gen/queryCouponType.do",
			data : params,
			async : false,
			dataType : 'json',
			success : function(data) {
				$("#myModal").find("#couponTypeId").html("");
				for (var i = 0; i < data.length; i++) {
					$("#myModal").find("#couponTypeId").append(
							"<option value='" + data[i].id + "'>"
									+ data[i].couponName + "</option>");
				}
			}
	
		});
	}
		var subForm=$("#saveform").Validform({
				showAllError:true
		});
		
		
			
		function search(){
			form1.action = "${contextPath}/coupon/gen/queryList.do";
			form1.submit();
		}
		function save(){
			subForm.submitForm(false,"${contextPath}/coupon/gen/save.do");
		}
		

		function increased() {  
            var saveform = $('#saveform');
             saveform.find('#partnerId').val("");
             saveform.find('#couponTypeId').val("");
             saveform.find('#count').val("");
             saveform.find('#value').val("");
            var myModal = $('#myModal');
            myModal.modal('show');  //打开新增界面
            
            } 
            
      

		jQuery(document).ready(function(){
			CouponTypeListInit();
			jQuery("#pagination").page("form1");
		});
		
		function CouponTypeListInit() {
		var partnerId = $("#partnerId").val();
			if (partnerId != '') {
				getCouponType(partnerId);
				var couponTypeIdTmp = $('#couponTypeIdTmp').val();
				$("#couponTypeId").val(couponTypeIdTmp);
			}
		}
		
		
		
	</script>
	</body>	
</html>