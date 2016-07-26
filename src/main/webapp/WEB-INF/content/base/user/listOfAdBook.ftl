<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>通讯录</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/base/user/queryListOfAdBook.do" method="post" name="form1" class="form-horizontal">
		<input type="hidden" id="assginItems" name="assginItems">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">通讯录</div>
			  <div class="panel-body">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 <input type="hidden" name="roleId" id="roleId" value="${roleId!""}">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 <div class="form-group "> 	      
                                <label class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-4 ">
                                  <input id="realName" name="realName" class="form-control" type="text" value="${(findObj.realName)!''}">
                                </div>
                                <label class="col-sm-1">&nbsp;</label>
                                <div class="col-sm-4">
	                                 <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">                                 
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
                                        <th style="width: 60px;text-align: center; where:50px; ">序号</th>
                                       
                                        <th style="text-align: center;">姓名</th>
                                        <th style="text-align: center;">分公司名称</th>
                                        <th style="text-align: center;">电话</th>
                                        <th style="text-align: center;">邮箱</th>
                                    </tr>
                                </thead>
                                
                                
                                <tbody>
                                	<#list page.list as userinfo >
                                	
									<tr>
                                        <td style="text-align: center;">${userinfo_index+1}</td>
                                        <td style="text-align: left;">${userinfo.realName}</td>
                                        <td style="text-align: left;">${userinfo.organCompanyName}</td>
                                        <td style="text-align: center;">${userinfo.tel}</td>
                                        <td style="text-align: center;">${userinfo.email}</td>
                                    </tr>  
                                    </#list>
					 </tbody>
				</table>
			</div>	
			<@pages url="${contextPath}/base/user/queryListOfAdBook.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />	
		</div>
	</form>
	
	
	
	
	
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		function search(){
			form1.action="${contextPath}/base/user/queryListOfAdBook.do";
			form1.submit();
		}
		
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
			jQuery("#myModal").on('show.bs.modal',function(){
				jQuery("#_organTag_").find("select").val('');
				jQuery("#_organTag_").find("select").each(function(){
					if(jQuery(this).attr('data-parent') == '1'){
					}else{
						jQuery(this).remove();
					}
				});
				var $defOrganId=jQuery("#_defOrganId_");
				jQuery("#organId").val($defOrganId.val()).attr('data-seq',$defOrganId.attr('data-seq'));
				organ.initOrgan();
			});
		});
		
	</script>
	</body>
</html>