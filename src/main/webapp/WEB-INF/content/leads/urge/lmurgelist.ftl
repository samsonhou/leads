<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>催促管理查询</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/urge/query.do" method="post" name="form1" class="form-horizontal">
		<input type="hidden" id="urgeToPersonId" name="urgeToPersonId" Value="${findObj.urgeToPersonId!''}">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">催促管理</div>
			  <div class="panel-body" style="padding-bottom: 0px;">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
                            <div class="form-group">
                                 <label class="col-sm-2 control-label">催促人</label>
                                <div class="col-sm-4 ">
                                    <@select type='1' codeType="1028" defValue="${findObj.urgePersonId!''}" fieldId="urgePersonId" fieldName="urgePersonId" paramName="" paramValue="" props=" class='form-control' " />
                                </div>
                                
                                <label class="col-sm-2 control-label">办理状态</label>
                                <div class="col-sm-4">
                                    <@select  fieldId='urgeStatus' codeType='1000' fieldName='urgeStatus' defValue='${(findObj.urgeStatus)!""}' props=" class='form-control' "  />   
                                </div>
                            </div>
                            

                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                 <input type="button" onclick="search()" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
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
					 		<th style="text-align: center;width: 50px;">序号</th>
					 		<th style="text-align: center;">催促事件编码</th>
                            <th style="text-align: center;">催促事件内容</th>
                            <th style="text-align: center;">催促发起人</th>
                            <th style="text-align: center;">催促人</th>
                            <th style="text-align: center;">催促接受人</th>
                            <th style="text-align: center;">催促次数</th>
                            <th style="text-align: center;">办理状态</th>
                            <th style="text-align: center;">创建时间</th>
                            <th style="text-align: center;">处理时间</th>
                            <th style="text-align: center;">操作</th>
					 	</tr>
					 </thead>
					 <tbody>
					 	<#list page.list as object>
							<tr>
								<td style="text-align: center;">${object_index+1}</td>
                                <td style="text-align: center;">${object.urgeAffairId}</td>
                                <td>${object.urgeAffairContent}</td>
                                <td>${object.urgeFirstPersonName}</td>
                                <td>${object.urgePersonName}</td>
                                <td>${object.urgeToPersonName}</td>
                                <td>${object.urgeNum}</td>
                                <td style="text-align: center;">${(object.urgeStatus=="1")?string('是','否')}</td>
                                <td style="text-align: center;">${object.urgeCreateDate?string('yyyy-MM-dd ')!""}</td>
                                <td>
                                    <#if (object.urgeEndDate)??>
                                       ${object.urgeEndDate?string('yyyy-MM-dd ')!""}
                                    </#if>
                                </td>
                                <td>
                               <#if object.urgeStatus=="0" >
                                <a href="javascript:void(0);"  onclick="addTrace('${object.urgeAffairId}','${object.id}')" >处理</a>&nbsp;&nbsp;</td>
                               </#if>
                            </tr>  
                        </#list>
					 </tbody>
				</table>
			</div>	
			<@pages url="${contextPath}/leads/urge/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />	
		</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		function search(){
			form1.action="${contextPath}/leads/urge/queryList.do";
			form1.submit();
		}
		
		function addTrace(id,cid){
			openNewTab("${contextPath}/leads/vist/addTrace.do?id="+id+"&urgeid="+cid,"线索催促回复");
			}
		  
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
		});
	</script>
	</body>
</html>