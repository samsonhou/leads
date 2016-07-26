<!DOCTYPE html>
<html lang="en">
	<head>
		<title></title>
	<#include "/pub/header_res.ftl"/>
	<#include "/pub/footer_res_detail.ftl"/>
	 <link href="${contextPath}/res/pub/css/demo/ziding/jquery-filestyle.min.css" rel="stylesheet">
	 <link rel="stylesheet" type="text/css" href="http://libs.useso.com/js/font-awesome/4.2.0/css/font-awesome.min.css">
	 
	 <link href="${contextPath}/res/pub/css/demo/ziding/zoom.css" rel="stylesheet">
	
	 <style type="text/css">
		div.layui-layer-btn {background-color: green;}
	 </style>
	 </head>
	
	<body class="gray-bg">
		<form name='importform' id='importform'  class="form-search" method="post" enctype="multipart/form-data">
	            <div class="row">
	                <div class="col-sm-3"></div>
	                <div class="col-sm-6">               
	                    <input type="file" id="file" name="file" class="jfilestyle" data-buttonText="<span class='fa fa-folder-open-o'></span> Add">
	                    <br />
	                </d iv>
	            </div>
	            <div class="col-sm-4 ">            
	              <button type="button" class="btn btn-primary btn-sm zd-btn-pd1" onclick="doImport()" id="searchBtn">导入</button>                              
	            </div>
                <div class="col-sm-4 ">     
                  <img src="${contextPath}/res/pub/imgT/${name!'def.jpg'}" width="20%;" height="20%;" data-action="zoom" class="img-rounded"/>                      
	            </div>
	    </div>
		</form>
	    <script src="${contextPath}/res/pub/js/demo/ziding/jquery-filestyle.min.js" type="text/javascript"></script>
	    <script src="${contextPath}/res/pub/js/demo/ziding/zoom.js" type="text/javascript"></script>
		<script type="text/javascript">
			function doImport(){
			    $("#searchBtn").attr("disabled","disabled").html('<i class="fa fa-spinner fa-spin f-2"></i> 上传中');
				importform.action = "${contextPath}/leads/search/impFile.do";
				importform.submit();
			}
		</script>
	</body>
</html>
