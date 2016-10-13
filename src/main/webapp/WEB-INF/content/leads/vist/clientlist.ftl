<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>客户线索</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/vist/queryList.do" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">跟踪查询</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					 <div class="col-sm-12">
					 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	<div class="form-group">
                                <label class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="clientName" value="${clientVO.clientName!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label  required">手机</label>
                                <div class="col-sm-4">
                                  <input type="text" name="tel" maxlength="11" value="${clientVO.tel!''}" class="form-control" >
                                </div>
                            </div>
                            
                            <div class="form-group ">
                                <label class="col-sm-2 control-label">等级</label>
                                <div class="col-sm-4 ">
                                    <@select type='0' codeType="1026" defValue="${clientVO.rank!''}" fieldId="rank" fieldName="rank"  props=" class='form-control' " />
                                </div>
                                <label class="col-sm-2 control-label  required">是否紧急</label>
                                <div class="col-sm-4">
                                    <@select type='0' codeType="1000" defValue="${clientVO.ifurgent!''}" fieldId="ifurgent" fieldName="ifurgent"  props=" class='form-control' " />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">业务大类</label>
                                <div class="col-sm-4">
                                    <@select type='1' codeType="1021" defValue="${clientVO.bigPid!'-1'}" fieldId="bigPid" fieldName="bigPid"  paramName="pid" paramValue="0" props=" class='form-control' " />
                                </div>
                                <label class="col-sm-2 control-label">业务小类</label>
                                <div class="col-sm-4" id="samllPidDiv" data-defvalue="${clientVO.smallPid!-1}">
		    			  			<@select type='1' codeType="1021" defValue="${clientVO.smallPid!-1}" fieldId="samllPid" fieldName="samllPid"  paramName="pid" paramValue="111" props=" class='form-control' " />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">分配时间</label>
                                <div class="col-sm-2">
                                	<input type="text" value="${clientVO.assignStartDate}" readonly placeholder="开始时间" id="assignStartDate" name="assignStartDate" class="form-control layer-date">
                                </div>
                                <div class="col-sm-2">
                                	<input type="text" value="${clientVO.assignEndDate}" readonly placeholder="结束时间" id="assignEndDate" name="assignEndDate" class="form-control layer-date">
                                </div>
                                
                            </div>
                            
                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                   <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                   &nbsp; &nbsp; 
	                                   <input type="button" value="新 增" onclick="newclient();" class="btn btn-primary btn-sm zd-btn-pd1"  data-toggle="modal" data-target="#myModal">
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
                                        <th style="text-align: center;">放弃原因</th>
                                        <th style="text-align: center;">电话具体原因</th>
                                        <th style="text-align: center;">下次跟踪时间</th>
                                        <th style="text-align: center;">填写时间</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#list page.list as client>
									<tr>
										<td style="text-align: center;">${client_index+1}</td>
										<td><a href="#" onclick="newhuifu('${client.ID}');">  <#if client.RANK[0..0]=='C'||client.RANK[0..0]=='O'>查看<#else>跟踪回复</#if> </a></td>
                                        <td <#if client.IFURGENT=="是"> style="color:#FF0000" </#if>>${client.NAME}</td>
                                        <td>${client.BIG_PID} - ${client.SMALL_PID}</td>
                                        <td>${client.TEL}</td>
                                        <td>${client.RID}</td>
                                        <td>${client.SID}</td>
                                        <td>${client.FROMTYPE}</td>
                                        <td>${client.RANK}</td>
                                        <td>
                                        <#if client.RANK[0..0]=='C'> 
                                            <#if client.REASON=='1'>A 车型不匹配      
                                            <#elseif client.REASON=='2'>B 金融方案不满意
                                            <#elseif client.REASON=='3'>C 风控原因  (审核未通过)
                                            <#elseif client.REASON=='4'>D (${client.REASONCONT})
                                            <#else> 
                                            </#if>   
                                        </#if>
                                        </td>
                                        <td>${client.STATUS}</td>
                                        <td>${client.NEXTDATE}</td>
                                        <td>${client.QDATE}</td>
                                        
                                        
                                    </tr>  
                                    </#list>
                              </tbody>
                            </table>
			</div>
			<@pages url="${contextPath}/leads/vist/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
		</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		var dateStart = {
			elem:"#assignStartDate",
			format:"YYYY-MM-DD"
		};
		laydate(dateStart);
		var dateEnd = {
			elem:"#assignEndDate",
			format:"YYYY-MM-DD"
		};
		laydate(dateEnd);
		function search(){
			form1.action="${contextPath}/leads/vist/queryList.do";
			form1.submit();
		}
		function newclient(){
			openNewTab("${contextPath}/leads/vist/addClient.do","新增客户");
		}
		function newhuifu(obj){
			var parm = "id="+obj;
			openNewTab("${contextPath}/leads/vist/addTrace.do?"+parm,"新增跟踪");
		}
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
			
			//初始化 业务小类 bigPid
			if(jQuery("#bigPid").val().length>0){
				jQuery("#bigPid").trigger('change');
			}
		});
		
		jQuery("#bigPid").on("change", function(){
			jQuery.ajax({
				type:"post",
				url:"${contextPath}/leads/client/querySub.do?bigPid="+jQuery("#bigPid").val(),
				data:"",
				dataType:"text",
				success:function(data){			
					var $samllPidDiv=jQuery("#samllPidDiv");
					var v=$samllPidDiv.attr("data-defvalue");
					if(v=='-1' || v=='0'){
						v='';
					}
					$samllPidDiv.empty().html(data).find("select").addClass("form-control").val(v);
					$samllPidDiv.attr("data-defvalue","");
				}
			});	
		});
	</script>
	</body>
</html>