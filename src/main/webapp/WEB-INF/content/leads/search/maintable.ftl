<form method="post" name="form1" id="form1" class="form-horizontal">
		<div class="container-fluid">
		    <!-- 个人信息 -->
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">个人信息</div>
			  <div class="panel-body" style="padding-bottom: 0px;">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0"> 	 	
                            <div class="form-group">   
                               <div><lable>当前积分:</lable>
                                  &nbsp;&nbsp;&nbsp;<font size=5>${sum!'0'}</font>&nbsp;&nbsp;&nbsp;分
                               </div>
                               <div><lable>本月战败转成交加分:</lable>&nbsp;&nbsp;&nbsp;<font size=5 color="green">${add}</font>&nbsp;&nbsp;&nbsp;分</div>
                               <div><lable>本月超时线索减分:</lable>&nbsp;&nbsp;&nbsp;<font size=5 color="red">${subtract}</font>&nbsp;&nbsp;&nbsp;分</div>
                            </div>
			  	 	 	 </div>
			  	 	 </div>
			  	 </div>
			  </div>
			</div>
			<!-- 重要提醒客户 -->		
			<div class="panel-heading">重要提醒客户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">您目前有</font>&nbsp;<font size=4 color="red">${t_count!'0'}</font>&nbsp;<font color="red">条需要处理的线索</font></div>
			<div class="panel panel-default table-responsive ziding-td" style="height:260px; overflow-y: scroll;">
				<table class="table table-condensed table-bordered table-striped table-hover" >		 				
					 <thead>
					 	<tr>
					 		<th style="text-align: center;width:10%">操作</th>
                            <th style="text-align: center;width:20%">联系人</th>
                            <th style="text-align: center;width:40%">业务</th>
                            <th style="text-align: center;width:15%">手机号</th>  
                            <th style="text-align: center;width:15%">超时类型</th>                             
					 	</tr>
					 </thead>
					 <tbody>
					    <#list impClient as imp>
                        <tr>             
                            <td style="text-align: center; width:10%"><a href="#" onclick="addTrace('${imp.CLIENT_ID}','0')">跟踪回复</a></td>
                            <td style="text-align: center; width:20%">${imp.CLIENT_NAME}</td>
                            <td style="text-align: center; width:40%">${imp.BUSINESS_TYPE}</td>
                            <td style="text-align: center; width:15%">${imp.TEL}</td>  
                            <#if imp.OVERTIME_TYPE  = '1'> 
                            <td style="text-align: center; width:15%">超时24小时</td>     
                            </#if>  
                            <#if imp.OVERTIME_TYPE  = '2'> 
                            <td style="text-align: center; width:15%">超时96小时</td>     
                            </#if>  
                             <#if imp.OVERTIME_TYPE  = '3'> 
                            <td style="text-align: center; width:15%">超时192小时</td>     
                            </#if>  
                            <#if imp.OVERTIME_TYPE  = ''> 
                            <td style="text-align: center; width:15%"></td>     
                            </#if>    
                        </tr>
                        </#list>
					 </tbody>
				</table>
			</div>	
		    <!-- 催促提醒客户 -->
		    <div class="panel-heading">催促提醒客户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">您目前有</font>&nbsp;<font size=4 color="red">${u_count!'0'}</font>&nbsp;<font color="red">条催促的线索</font></div>	
			<div class="panel panel-default table-responsive ziding-td" style="height:260px; overflow-y: scroll;">			     
				 <table class="table table-condensed table-bordered table-striped table-hover" >
					  <thead>
					 	<tr>
					 		<th style="text-align: center;">操作</th>
                            <th style="text-align: center;">联系人</th>
                            <th style="text-align: center;">业务</th>
                            <th style="text-align: center;">手机号</th>  
                            <th style="text-align: center;">催促次数</th>
                            <th style="text-align: center;">催促时间</th>                                 
					 	</tr>
					 </thead>
					 <tbody>
					    <#list urgeClient as client>
                        <tr>             
                            <td style="text-align: center; width:10%"><a href="#" onclick="addTrace('${client.URGE_AFFAIR_ID}','${client.ID}')">跟踪回复</a></td>
                            <td style="text-align: center; width:10%">${client.URGE_AFFAIR_CONTENT}</td>
                            <td style="text-align: center; width:20%">${client.BUSINESS_TYPE}</td>
                            <td style="text-align: center; width:15%">${client.TEL}</td>  
                            <td style="text-align: center; width:15%">${client.URGE_NUM}</td>  
                            <td style="text-align: center; width:15%">${client.URGE_CREATE_DATE?string('yyyy-MM-dd')!""}</td>            
                        </tr>
                        </#list>
					 </tbody>
				</table>
			</div>	
			<!-- 任务提醒 -->
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">任务提醒</div>
			  <div class="panel-body" style="padding-bottom: 0px;">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0"> 	 	
                            <div class="form-group">                              
                               <div><lable>本月任务量:&nbsp;&nbsp;<font size=5>${plan_num}</font></lable>&nbsp;&nbsp;已完成数量:&nbsp;&nbsp;<font size=5 color="green">${perform!"0"}</font>&nbsp;&nbsp;
                                                                                             未完成数量:&nbsp;&nbsp;<font size=5 color="red">${num!"0"}</font>
                               </div>                             
                            </div>
			  	 	 	 </div>
			  	 	 </div>
			  	 </div>
			  </div>
			</div>		
		</div>
	</form>
	
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
	   function addTrace(id,cid){
			openNewTab("${contextPath}/leads/vist/addTrace.do?id="+id+"&urgeid="+cid,"线索催促回复");
	   }
	
		jQuery(document).ready(function(){	
		});
   	</script>