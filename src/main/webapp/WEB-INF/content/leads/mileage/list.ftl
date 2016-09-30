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
		 <title>客户里程与满意度</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/mileage/query.do" method="post" name="searchForm" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">里程查询</div>
				<div class="panel-body" style="padding-bottom: 0px;">
			  		<div class="col-sm-12">
			  			<div class="float-e-margins">
			  	 	 		<div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 		<div class="form-group">
                                	<label class="col-sm-2 control-label">申请人姓名</label>
                                	<div class="col-sm-4 ">
                                    	<input type="text" name="customername" value="${queryVO.customername!''}" class="form-control">
                                	</div>
                                	<label class="col-sm-2 control-label  required">申请人电话</label>
                                	<div class="col-sm-4">
                                  		<input type="text" name="tel" value="${queryVO.tel!''}" class="form-control" >
                                	</div>
                            	</div>
                            	<div class="form-group">
                                	<label class="col-sm-2 control-label">车架号</label>
	                                <div class="col-sm-4">
	                                   <input type="text" name="carvin" value="${queryVO.carRentelVO.carvin!''}" class="form-control" >
	                                </div>
	                                <label class="col-sm-2 control-label">门店</label>
	                                <div class="col-sm-4">
	                                   <input type="text" name="store" value="${queryVO.store!''}" class="form-control" >
	                                </div>
                            	</div>
                            	<div class="form-group">
	                                <label class="col-sm-2 control-label  required">满足里程提醒</label>
                                	<div class="col-sm-4">
                                    	<@select type='0' codeType="1000" defValue="${queryVO.needremind!''}" fieldId="needremind" fieldName="needremind"  props=" class='form-control' " />
                                	</div>
	                                <div class="col-sm-6 control-label">
		                            	<input type="button" onclick="doSearch();" id="searchBtn" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
		                            	&nbsp;&nbsp;
		                            	<input type="button" onclick="updateRemind();" value="更改提醒状态" class="btn btn-primary btn-sm zd-btn-pd1">
		                            	&nbsp;&nbsp;
		                            	<input type="button" onclick="export2Excel();" value="里程导出" class="btn btn-primary btn-sm zd-btn-pd1"> 
		                            	&nbsp;&nbsp;
		                            	<input type="button" onclick="importExcel();" value="数据导入" class="btn btn-primary btn-sm zd-btn-pd1">
		                            	&nbsp;&nbsp;
		                            	<input type="button" onclick="downloadTemplate();" value="模板下载" class="btn btn-primary btn-sm zd-btn-pd1">
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
					 		<th style="text-align: center;">子公司</th>
					 		<th style="text-align: center;">门店</th>
                            <th style="text-align: center;">申请人</th>
                            <th style="text-align: center;">电话</th>
                            <th style="text-align: center;">车架号</th>
                            <th style="text-align: center;">品牌型号</th>
                            <th style="text-align: center;">行驶里程</th>
                            <th style="text-align: center;">交车日期</th>
                            <th style="text-align: center;">是否已提醒</th>
                            <th style="text-align: center;">客户满意度</th>
					 	</tr>
					 </thead>
					 <tbody>
					 	<#list page.list as vo>
							<tr>
								<td style="text-align: center;">${vo_index+1}</td>
								<td style="text-align: center;"><input type="checkbox" name="selectItem" value="${vo.carRentelVO.mileageVO.id}:${vo.carRentelVO.mileageVO.isremind}"></td>
								<td>${vo.subsidiary}</td>
                                <td>${vo.store}</td>
                                <td>${vo.customername}</td>
                                <td>${vo.tel}</td>
                                <td>${vo.carRentelVO.carvin}</td>
                                <td>${vo.carRentelVO.brandtype}</td>
                                <td>${vo.carRentelVO.mileageVO.mileage}</td>
                                <td>${(vo.carRentelVO.deliverdate?string("yyyy-MM-dd"))!}</td>
                                <td>${vo.carRentelVO.mileageVO.isremind}</td>
                                <td style="text-align: center;"><a href="#" onclick="viewDetail('${vo.id}');">查看</a></td>
                            </tr>  
                        </#list>
					 </tbody>
				</table>
			</div>	
			<@pages url="${contextPath}/leads/mileage/matchingQuery.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />	
		</div>
		<div id="menuContent" class="menuContent" style="display:none;  position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:340px; height: 300px;"></ul>
		</div>
		<input id="msg" type="hidden" value="${msg}">
	</form>
	
	<form name="uploadForm" action="${contextPath}/leads/mileage/mileageImport.do" enctype="multipart/form-data" method="POST">
		 <input id="uploadFile" type="file" name="uploadFile" onchange="doImport(this)" style="display:none" />
	</form>
	
	
	<#include "/pub/footer_res_detail.ftl"/>
	<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script type="text/javascript">
	
		//条件查询
		function doSearch(){
			searchForm.action="${contextPath}/leads/mileage/matchingQuery.do";
			searchForm.submit();
		}
		
		//更新提醒状态
		function updateRemind(){
			var items=getCheckedItems();
			if(items.length<1){
				layer.alert("请选择需要更改提醒状态的数据！");
			}else{
				layer.confirm('确定更改所选记录的提醒状态吗？', {icon: 3, title:'提示'}, function(index){
					var mids='';
					for(var i=0;i<items.length;i++){
						mids+=items[i];
						if(i!=(items.length-1)){
							mids+=",";
						}
					}
					
					var load = layer.load();
    				layer.close(index);
					$.ajax({  
		            	type: "POST",  
		            	url: "${contextPath}/leads/mileage/updateRemind.do?mids="+mids,  
		            	data: "",
			            success: function(resp){
			            	layer.close(load);
			            	layer.alert(resp.msg,function(index){
			            		$('#searchBtn').click();
			            	});
			            }
		            });
				});
			}
		}
		
		//数据导出
		function export2Excel(){
			layer.confirm('确定导出符合查询条件的数据吗？', {icon: 3, title:'提示'}, function(index){
				searchForm.action="${contextPath}/leads/mileage/mileageExport.do";
				searchForm.submit();
    			layer.close(index);
			});
		}
		
		//模板下载
		function downloadTemplate(){
			searchForm.action="${contextPath}/leads/mileage/downloadTemplate.do";
			searchForm.submit();
		}

		
		//数据导入
		function importExcel(){
			$("#uploadFile").click();  
		}
		function doImport(obj){
			var fileName = $("#uploadFile").val();
			if(fileName == ""){
				layer.alert('请选择要导入的Excel文件！');
				return;
			}else{
				var suffix = fileName.substr(fileName.lastIndexOf(".")); 
				if(".xls" != suffix && ".xlsx" != suffix){
					layer.alert('请勿导入非Excel类型的文件！');
					return;
				}
				uploadForm.submit();
			}
			layer.load();
		}
		
		
		//获取已选数据
		function getCheckedItems(){
			var items=[];
			jQuery("[name = selectItem]:checkbox").each(function(){
				if(this.checked){
					items.push(jQuery(this).val());
				}
			});
			return items;
		}
		
		
		jQuery(document).ready(function(){
		
			var msg = jQuery("#msg").val();
			if(msg.length>0){
				layer.alert(msg);
			}
			
			jQuery("#pagination").page("searchForm");
			
			//全选
			jQuery("#checkAll").on("click",function(){
				if(this.checked){
					jQuery("[name = selectItem]:checkbox").prop("checked",true);
				}else{
					jQuery("[name = selectItem]:checkbox").prop("checked",false);
				}
			});
			
		});
		
		
		//查看客户满意度
		function viewDetail(itemId){
			var parm = "id="+itemId;
			openNewTab("${contextPath}/leads/mileage/mileageDetail.do?"+parm,"满意度详情");
		}
		
	</script>
	</body>
</html>