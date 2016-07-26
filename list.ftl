<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>查询模板</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/assign/query.do" method="post" name="form1" class="form-horizontal">
		<input type="hidden" id="assginItems" name="assginItems">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">查询模板</div>
			  <div class="panel-body" style="padding-bottom: 0px;">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	<div class="form-group">
                                <label class="col-sm-2 control-label">类姓名</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="name" value="" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label">电话</label>
                                <div class="col-sm-4">
                                  <input type="text" name="tel" value="" class="form-control" >
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">等级</label>
                                <div class="col-sm-4 ">
                                    <@select type='1' codeType="1020" defValue="${clientVO.rank!''}" fieldId="rank" fieldName="rank" paramName="tablename,field" paramValue="lm_client,rank" props=" class='form-control' " />
                                </div>
                                <label class="col-sm-2 control-label">状态</label>
                                <div class="col-sm-4">
                                    <@select type='1' codeType="1020" defValue="${clientVO.status!''}" fieldId="status" fieldName="status"   paramName="tablename,field" paramValue="lm_client,status" props=" class='form-control' " />
                                </div>
                            </div>
                            

                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                 <input type="button" onclick="" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
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
					 		<th style="text-align: center;">联系人</th>
                            <th style="text-align: center;">业务类别</th>
                            <th style="text-align: center;">电话</th>
                            <th style="text-align: center;">填写人</th>
                            <th style="text-align: center;">销售人员</th>
					 	</tr>
					 </thead>
					 <tbody>
					 	<#list page.list as client>
							<tr>
								<td style="text-align: center;">${client_index+1}</td>
                                <td>${client.NAME}</td>
                                <td>${client.BIG_PID} - ${client.SMALL_PID}</td>
                                <td>${client.TEL}</td>
                                <td>${client.RID}</td>
                                <td>${client.SID}</td>
                                <td><a href="javascript:void(0);" >分配</a>&nbsp;&nbsp;<a href="javascript:void(0);" >查看</a></td>
                            </tr>  
                        </#list>
					 </tbody>
				</table>
			</div>	
			<@pages url="${contextPath}/leads/assign/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />	
		</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		function search(){
			form1.action="${contextPath}/leads/assign/queryList.do";
			form1.submit();
		}

		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
		});
	</script>
	</body>
</html>