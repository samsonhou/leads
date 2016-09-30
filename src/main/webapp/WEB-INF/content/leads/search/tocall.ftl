<form method="POST" name="callForm" id="callForm" class="form-horizontal">
	<div class="container-fluid">
		<div class="panel-heading" style="height: 45px; margin-top: 15px;">
	    	<div style="margin: auto; float: left; font-weight: bold; font-size: 15px;">外呼客户提醒</div>
	    	<div style="margin: auto; float: left; margin-left: 50px; font-size: 14px;">您目前有&nbsp;<font color="red"><b>${size}</b></font>&nbsp;位需要外呼的客户</div>
	    </div>	
		<div class="panel panel-default">
			 <table class="table table-condensed table-bordered table-striped table-hover" style="width: 100%" >
				  <thead>
				 	<tr>
				 		<th style="text-align: center;">操作</th>
                        <th style="text-align: center;">客户姓名</th>
                        <th style="text-align: center;">手机号</th>  
                        <th style="text-align: center;">未超时限</th>                                 
				 	</tr>
				 </thead>
				 <tbody>
				    <#list customerList as vo>
                    <tr>             
                        <td style="text-align: center; width:10%"><a href="#" onclick="tocall('${vo.ID}')">外呼</a></td>
                        <td style="text-align: center; width:10%">${vo.CLIENTNAME}</td>
                        <td style="text-align: center; width:20%">${vo.TEL}</td>
                        <td style="text-align: center; width:15%">${vo.TIMETYPE}</td>  
                    </tr>
                    </#list>
				 </tbody>
			</table>
		</div>	
	</div>
</form>
	
<#include "/pub/footer_res_detail.ftl"/>
<script type="text/javascript">
   function tocall(id){
		var parm = "id="+id;
		openNewTab("${contextPath}/leads/client/updateClient.do?"+parm,"外呼");
   }
</script>