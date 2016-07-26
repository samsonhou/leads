<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		<link href="${contextPath}/res/pub/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <style>
div.content_wrap {width: 600px;height:380px;}
div.content_wrap div.left{float: left;width: 250px;}
div.content_wrap div.right{float: right;width: 340px;}
div.zTreeDemoBackground {width:250px;height:362px;text-align:left;}

ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:360px;overflow-y:scroll;overflow-x:auto;}
ul.log {border: 1px solid #617775;background: #f0f6e4;width:300px;height:170px;overflow: hidden;}
ul.log.small {height:45px;}
ul.log li {color: #666666;list-style: none;padding-left: 10px;}
ul.log li.dark {background-color: #E3E3E3;}

/* ruler */
div.ruler {height:20px; width:220px; background-color:#f0f6e4;border: 1px solid #333; margin-bottom: 5px; cursor: pointer}
div.ruler div.cursor {height:20px; width:30px; background-color:#3C6E31; color:white; text-align: right; padding-right: 5px; cursor: pointer}		 
		 </style>
		 <title>线索分配</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/assign/query.do" method="post" name="form1" class="form-horizontal">
		<input type="hidden" id="assginItems" name="assginItems">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">分配查询</div>
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
                            
                            <div class="form-group">
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
		    			  			<@select type='1' codeType="1021" defValue="${clientVO.samllPid!-1}" fieldId="samllPid" fieldName="samllPid"  paramName="pid" paramValue="111" props=" class='form-control' " />
                                </div>
                      
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">销售人员</label>
                                <div class="col-sm-4">
                                   <input id="sidName" name ="sidName" type="text" value="${dealPerson!''}" class='form-control' readonly  placeholder="请选择销售人员"  onclick="showMenu();" />
                                   <input id="sid" name ="sid" type="hidden"  value="${clientVO.sid!''}" />                                
                                   <input id="companyid" name ="companyid" type="hidden"  value="${clientVO.companyid!''}"  />
                                </div>
                                 <label class="col-sm-2 control-label"></label>
                                <div class="col-sm-4">
	                                 <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                 &nbsp;&nbsp;
	                                 <input type="button" onclick="assignMult();" value="多选分配" class="btn btn-primary btn-sm zd-btn-pd1">
	                                 &nbsp;&nbsp;
	                                 <#if "${flag}"=="0"> 
	                                 <input type="button" onclick="delDataMult('${flag}');" value="批量删除" class="btn btn-primary btn-sm zd-btn-pd1">
                                     </#if>     
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
					 		<th style="text-align: center;width: 50px;"><input type="checkbox" id="checkAll"></th>
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
										<td style="text-align: center;"><input type="checkbox" name="assginItem" value="${client.ID}"></td>
										<td><a href="javascript:void(0);" onclick="assignSign('${client.ID}');">分配</a>&nbsp;
                                        	<a href="#" onclick="viewDetail('${client.ID}');">查看</a>&nbsp;
                                        <#if "${flag}"=="0"> 
                                        	<a href="#" onclick="delData('${flag}','${client.ID}');">删除</a></td>
                                        </#if>   
                                        </td>
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
			<@pages url="${contextPath}/leads/assign/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />	
		</div>
		<div id="menuContent" class="menuContent" style="display:none;  position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:340px; height: 300px;"></ul>
		</div>
		<input id="ids" name="ids" type="hidden" value="-1">
	</form>
	
	<!-- 弹出框 -->
	<div class="modal fade" id="messageModal">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">提示信息</h4>
	      </div>
	      <div class="modal-body text-center">
	        <p id="showAlertInfo"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal">确&nbsp;&nbsp;定</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="confirmMessageModal">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">提示信息</h4>
	      </div>
	      <div class="modal-body text-center">
	        <p id="showAlertInfo1"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal">取&nbsp;&nbsp;消</button>
	        <button id="confirmokBut" type="button" class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal" onclick="confirm()">确&nbsp;&nbsp;定</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<#include "/pub/footer_res_detail.ftl"/>
	<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script type="text/javascript">
		function search(){
			form1.action="${contextPath}/leads/assign/queryList.do";
			form1.submit();
		}
		//分选分配
		function assignMult(){
			var items=getCheckItem();
			if(items.length<1){
				$("#showAlertInfo").text('请选择需要分配的任务');
	    		$('#messageModal').modal('show');
			}else{
				var params='';
				for(var i=0;i<items.length;i++){
					params+=items[i];
					if(i!=(items.length-1)){
						params+=",";
					}
				}
				jQuery("#assginItems").val(params);
				openNewTab("${contextPath}/leads/assign/toAssgin.do?assginItems="+params,"人员分配");
			}
		}
		//单个分配
		function assignSign(itemId){
			jQuery("#assginItems").val(itemId);
			openNewTab("${contextPath}/leads/assign/toAssgin.do?assginItems="+itemId,"人员分配");
		}
		//查看
		function viewDetail(itemId){
			var parm = "id="+itemId;
			openNewTab("${contextPath}/leads/assign/addTrace.do?"+parm,"查看线索");
		}
		
		
		function confirm(){  
			form1.action="${contextPath}/leads/assign/delData.do";
			form1.submit();
		}
		
		//单个删除  flag:0=管理员
		function delData(flag,id){
		    if(flag!='0'){
		        $("#showAlertInfo").text('只有管理员才能删除.');
	    		$('#messageModal').modal('show');
	    		return;
		    }
		     $("#ids").val(id);
		     $("#showAlertInfo1").text('你确认要删除吗？');
		     $('#confirmMessageModal').modal('show');
		}
		
		
		//批量删除 flag:0=管理员
		function delDataMult(flag,id,page){
		       if(flag!='0'){
		         $("#showAlertInfo").text('只有管理员才能删除哦.');
	    		 $('#messageModal').modal('show');
	    		 return;
		       }
		       var items=getCheckItem();
			   if(items.length<1){
				  $("#showAlertInfo").text('请选择需要删除的线索');
	    		  $('#messageModal').modal('show');
			    }else{
				var params='';
				for(var i=0;i<items.length;i++){
					params+=items[i];
					if(i!=(items.length-1)){
						params+=",";
					}
				}
				$("#ids").val(params);
		        $("#showAlertInfo1").text('你确认要删除吗？');
		        $('#confirmMessageModal').modal('show');
			 }

		}
		function getCheckItem(){
			var items=[];
			jQuery("[name = assginItem]:checkbox").each(function(){
				if(this.checked){
					items.push(jQuery(this).val());
				}
			});
			return items;
		}
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
			//全选或取消全选
			jQuery("#checkAll").on("click",function(){
				if(this.checked){//全选中
					jQuery("[name = assginItem]:checkbox").prop("checked",true);
				}else{//取消全选中
					jQuery("[name = assginItem]:checkbox").prop("checked",false);
				}
			});
			
			var zNodes;
			jQuery.ajax({
				type : "post",
				url : "${contextPath}/leads/client/queryOrgPerson.do?from=forAssign",
				data : "",
				async:true,
				dataType : "text",
				success : function(data) {
					zNodes = eval(data);
					jQuery.fn.zTree.init(jQuery("#treeDemo"), setting, zNodes);
					//初始化 业务小类 bigPid
					if(jQuery("#bigPid").val().length>0){
						jQuery("#bigPid").trigger('change');
					}
				}
			});
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
		
		function onClick(e, treeId, treeNode) {
			var zTree = jQuery.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck(e, treeId, treeNode) {
			var zTree = jQuery.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			jQuery("#sidName").val(v);
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].id.substring(2,nodes[i].id.length) + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			jQuery("#sid").val(v);

			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].pId+ ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			jQuery("#companyid").val(v);
		}

		function showMenu() {
			var isnew = jQuery("#isnew").val();
			if(isnew=="1"){
				return;
			}
			var cityObj = jQuery("#sidName");
			var cityOffset = jQuery("#sidName").offset();
			jQuery("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			
			jQuery("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			jQuery("#menuContent").fadeOut("fast");
			jQuery("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "sidName" || event.target.id == "menuContent" || jQuery(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
		
		var setting = {
				check: {
					enable: true,
					chkStyle: "radio",
					radioType: "all"
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick,
					onCheck: onCheck
				}
			};
	</script>
	</body>
</html>