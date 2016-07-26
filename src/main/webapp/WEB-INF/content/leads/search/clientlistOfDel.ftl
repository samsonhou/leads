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
		 <title>客户线索</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/search/queryList.do" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">回收站查询</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					<div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-4 ">
                                    <input type="text"  name="clientName" value="${clientVO.clientName!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label  required">手机</label>
                                <div class="col-sm-4">
                                    <input type="text" name="tel" maxlength="11" value="${clientVO.tel!''}" class="form-control" >
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
									<tr class="span3tr">
										<td style="text-align: center;">${client_index+1}</td>
										<td>
                                    		<a href="#" onclick="viewDetail('${client.ID}');">查看</a>
                                          	<a href="#" onclick="renewData('${flag}','${client.ID}');">恢复</a>
                                         </td>
                                        <td>${client.NAME}</td>
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
			<@pages url="${contextPath}/leads/search/queryListOfCol.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
		</div>
		<div id="menuContent" class="menuContent" style="display:none;  position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:340px; height: 300px;"></ul>
		</div>
		<input id="ids" name="ids" type="hidden" value="-1">
	</form>
	
	<div class="modal fade" id="confirmMessageModal">
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
	        <button type="button"  class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal">取&nbsp;&nbsp;消</button>
	        <button id="confirmokBut" type="button" class="btn btn-primary btn-sm zd-btn-pd1"  data-dismiss="modal" onclick="confirm()">确&nbsp;&nbsp;定</button>
	      </div>
	    </div>
	  </div>
	</div>
	<#include "/pub/footer_res_detail.ftl"/>
   <script>

    </script>
	<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script type="text/javascript">
		function search(){
			form1.action="${contextPath}/leads/search/queryListOfCol.do";
			form1.submit();
		}
		//查看
		function viewDetail(itemId){
			var parm = "id="+itemId;
			openNewTab("${contextPath}/leads/assign/addTrace.do?"+parm,"查看线索");
		}
	     function confirm(){  
			form1.action="${contextPath}/leads/search/renewData.do";
			form1.submit();
		}
	
	    //add by cj 回收站已删除的线索恢复 flag:0=管理员
		function renewData(flag,id){
		    if(flag!='0'){
		        $("#showAlertInfo").text('只有管理员才能删除.');
	    		$('#messageModal').modal('show');
	    		return;
		    }
		     $("#ids").val(id);
		     $("#showAlertInfo").text('你确认要恢复吗？');
		     $('#confirmMessageModal').modal('show');
		}
		
		    
		
		
		jQuery(document).ready(function(){
			jQuery("#pagination").page("form1");
			
			var zNodes;
			jQuery.ajax({
				type : "post",
				url : "${contextPath}/leads/client/queryOrgPerson.do",
				data : "",
				async:true,
				dataType : "text",
				success : function(data) {
					zNodes = eval(data);
					jQuery.fn.zTree.init(jQuery("#treeDemo"), setting, zNodes);
					
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