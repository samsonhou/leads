<form method="POST" name="assignForm" id="assignForm" class="form-horizontal">
	<div class="container-fluid">
		<div class="panel-heading" style="height: 45px; margin-top: 15px;">
	    	<div style="margin: auto; float: left; font-weight: bold; font-size: 15px;">待分配线索提醒</div>
	    	<div style="margin: auto; float: left; margin-left: 50px; font-size: 14px;">您目前有&nbsp;<font color="red"><b>${size}</b></font>&nbsp;条需要分配的线索</div>
	    </div>
	    <!--	
		<div class="panel panel-default">
			 <table class="table table-condensed table-bordered table-striped table-hover" style="width: 100%" >
				  <thead>
				 	<tr>
				 		<th style="text-align: center;">操作</th>
                        <th style="text-align: center;">客户姓名</th>
                        <th style="text-align: center;">手机号</th>  
                        <th style="text-align: center;">来源</th>                                 
				 	</tr>
				 </thead>
				 <tbody>
				    <#list customerList as vo>
                    <tr>             
                        <td style="text-align: center; width:10%"><a href="#" onclick="toassign('${vo.ID}')">分配</a></td>
                        <td style="text-align: center; width:10%">${vo.CLIENTNAME}</td>
                        <td style="text-align: center; width:20%">${vo.TEL}</td>
                        <td style="text-align: center; width:15%">${vo.COMEFROM}</td>  
                    </tr>
                    </#list>
				 </tbody>
			</table>
		</div>	
		-->
	</div>
</form>
	
<#include "/pub/footer_res_detail.ftl"/>
<script type="text/javascript">
   function toassign(id){
		assignForm.action = "${contextPath}/leads/assign/wait/toAssgin.do?assginItems="+id+"&from=index";
		assignForm.submit();
   }
</script>