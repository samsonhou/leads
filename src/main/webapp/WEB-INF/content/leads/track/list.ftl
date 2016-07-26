<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>客户线索</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/agingtrack/queryList.do" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">跟踪查询</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					 <div class="col-sm-12">
					 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
                            <div class="form-group">
                                <label class="col-sm-2 control-label">请选择跟踪类型</label>
                                <div class="col-sm-4">
                                    <@select type='0' codeType="1033" defValue="${agingTrackType!'-1'}" fieldId="agingTrackType" fieldName="agingTrackType"   props=" class='form-control' " />
                                </div>
                            </div>
                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                   <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                             </div>
                            </div>
                            
			  	 	 	 </div>
			  	 	 	 </div>
					 </div>
				</div>
			</div>
			
			<div class="panel panel-default table-responsive ziding-td">
				<table class="table table-condensed table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                    	<th style="text-align: center;width: 50px;">序号</th>
                                    	<th style="text-align: center;">操作</th>
                                        <th style="text-align: center;">联系人</th>
                                        <th style="text-align: center;">业务类别</th>
                                        <th style="text-align: center;">手机</th>
                                        <th style="text-align: center;">填写人</th>
                                        <th style="text-align: center;">销售人员</th>
                                        <th style="text-align: center;">来源</th>
                                        <th style="text-align: center;">等级</th>
                                        <th style="text-align: center;">电话具体原因</th>
                                        <th style="text-align: center;">下次跟踪时间</th>
                                        <th style="text-align: center;">填写时间</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#list page.list as client>
									<tr>
										<td style="text-align: center;">${client_index+1}</td>
										<td><a href="#" onclick="newhuifu('${client.ID}');">跟踪回复</a></td>
                                        <td <#if client.IFURGENT=="是"> style="color:#FF0000" </#if>>${client.NAME}</td>
                                        <td>${client.BIG_PID} - ${client.SMALL_PID}</td>
                                        <td>${client.TEL}</td>
                                        <td>${client.RID}</td>
                                        <td>${client.SID}</td>
                                        <td>${client.FROMTYPE}</td>
                                        <td>${client.RANK}</td>
                                        <td>${client.STATUS}</td>
                                        <td>${client.NEXTDATE}</td>
                                        <td>${client.QDATE}</td>
                                        
                                        
                                    </tr>  
                                    </#list>
                              </tbody>
                            </table>
			</div>
			<@pages url="${contextPath}/leads/agingtrack/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
		</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script src="${contextPath}/res/pub/js/plugins/layer/layer.min.js"></script>
	<script src="${contextPath}/res/pub/js/demo/layer-demo.min.js"></script>
	<script type="text/javascript">
		function search(){
			if($("#agingTrackType").val() == ""){
				layer.alert("请先选择！");
				return;
			}
			form1.action="${contextPath}/leads/agingtrack/queryList.do";
			form1.submit();
		}
		
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
			
		});
		
		function newhuifu(obj){
			var parm = "id="+obj;
			openNewTab("${contextPath}/leads/vist/addTrace.do?"+parm,"新增跟踪");
		}
	</script>
	</body>
</html>