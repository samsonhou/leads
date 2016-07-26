<!DOCTYPE html>
<html lang="en">
	<head>
		<title>赠券核销</title>
	<#include "/pub/header_res.ftl"/>
	<#include "/pub/footer_res_detail.ftl"/>
	</head>
	<body class="gray-bg">
		<form action="" method="post" name="form1" class="form-horizontal">
			<input type="hidden" id="urgeToPersonId" name="urgeToPersonId" Value="${findObj.urgeToPersonId!''}">
			<div class="container-fluid">
				<div class="panel panel-default" style="margin-top: 1px;">
					<div class="panel-heading">赠券核销</div>
					<div class="panel-body" style="padding-bottom: 0px;">
						<div class="col-sm-12">
							<div class="float-e-margins">
								<div class="ibox-content" style="padding:0 0 0 0">
								
									<div class="form-group">
										<label class="col-sm-2 control-label">赠券码</label>
										<div class="col-sm-2">
											<input type="text" name="couponCode" id="couponCode" class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-4 col-sm-offset-2">
											<input type="button" onclick="destory();" value="确认" class="btn btn-primary btn-sm zd-btn-pd1">
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>

				
			</div>
		</form>


		<script type="text/javascript">
		function destory(){
			var couponCode = $("#couponCode").val();
			if(couponCode == ""){
				layer.alert("请输入赠券码！");
				return;
			}
			var ind = layer.load();
			$.ajax({  
            type: "POST",  
            url: "${contextPath}/exchange/coupon/destory/doDestory.do?couponCode="+couponCode,  
            data: "",
            success: function(resp){
            	layer.close(ind);
            	layer.alert(resp.msg);
            }
            }); 
		}
		</script>
	</body>
</html>
