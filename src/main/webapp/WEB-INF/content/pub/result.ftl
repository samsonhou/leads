
<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		<title>提交跳转</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/assign/query.do" method="post" name="form1" class="form-horizontal">
		<input type="hidden" id="comform" name="comform"  value="${comform}">
	</form>
	<#include "/pub/message.ftl"/>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		jQuery(document).ready(function(){
			var message="${message!''}";
			if(message.length>0){
				jQuery('#messageModal').modal('show');
			}
			jQuery('#messageModal').on('hidden.bs.modal', function (e) {
				if(message.length>1){
					closeWin();
				}
			})
		});
		//关闭窗口
    	function closeWin(){
    		if($("#comform").val()=='1'){
    			closeLayer();
    		}else{
    			closeTab();
    		}
    	}
	</script>
	</body>
</html>