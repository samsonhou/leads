<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		<link href="${contextPath}/res/pub/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
		<link href="${contextPath}/res/pub/css/demo.css" rel="stylesheet">
		<title>客户满意度详情</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/assign/query.do" method="post" name="form1" class="form-horizontal">
		<@token />
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">客户及行驶里程信息</div>
			  	<div class="panel-body">
			  		<div class="col-sm-12">
			  	 		<div class="float-e-margins">
			  	 	 		<div class="ibox-content" style="padding:0 0 0 0">
				  	 	 	 	<div class="form-group">
	                                <label class="col-sm-2 control-label">申请人</label>
	                                <div class="col-sm-4 ">
	                                    <input type="text" readonly value="${customerVO.customername!''}" class="form-control">
	                                </div>
	                                <label class="col-sm-2 control-label ">电话</label>
	                                <div class="col-sm-4">
	                                  <input type="text" readonly value="${customerVO.tel!''}" class="form-control" >
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">子公司</label>
	                                <div class="col-sm-4 ">
	                                    <input type="text" readonly value="${customerVO.subsidiary!''}" class="form-control">
	                                </div>
	                                <label class="col-sm-2 control-label ">门店</label>
	                                <div class="col-sm-4">
	                                  <input type="text" readonly value="${customerVO.store!''}" class="form-control" >
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">品牌型号</label>
	                                <div class="col-sm-4 ">
	                                    <input type="text" readonly value="${customerVO.carRentelVO.brandtype!''}" class="form-control">
	                                </div>
	                                <label class="col-sm-2 control-label ">车架号</label>
	                                <div class="col-sm-4">
	                                  <input type="text" readonly value="${customerVO.carRentelVO.carvin!''}" class="form-control" >
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">行驶里程</label>
	                                <div class="col-sm-4 ">
	                                    <input type="text" readonly value="${customerVO.carRentelVO.mileageVO.mileage!''}" class="form-control">
	                                </div>
	                                <label class="col-sm-2 control-label ">上次记录行驶里程</label>
	                                <div class="col-sm-4">
	                                  <input type="text" readonly value="${customerVO.carRentelVO.mileageVO.lastmileage!''}" class="form-control" >
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">是否已提醒</label>
	                                <div class="col-sm-4 ">
	                                	<@select type='0' codeType="1000" defValue="${customerVO.carRentelVO.mileageVO.isremind!''}" fieldId="isremind" fieldName="isremind"  props="disabled class='form-control' " />
	                                </div>
	                                <label class="col-sm-2 control-label ">交车日期</label>
	                                <div class="col-sm-4">
	                                  <input type="text" readonly value="${(customerVO.carRentelVO.deliverdate?string("yyyy-MM-dd"))!}" class="form-control" >
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">调研人</label>
	                                <div class="col-sm-4 ">
	                                    <input type="text" readonly value="${customerVO.investigator!''}" class="form-control">
	                                </div>
	                                <label class="col-sm-2 control-label ">性别</label>
	                                <div class="col-sm-4">
	                                	<@select type='0' codeType="1054" defValue="${customerVO.investigatorsex!''}" fieldId="investigatorsex" fieldName="investigatorsex"  props="disabled class='form-control' " />
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">调研日期</label>
	                                <div class="col-sm-4 ">
	                                    <input type="text" readonly value="${(customerVO.investigationdate?string("yyyy-MM-dd"))!}" class="form-control">
	                                </div>
	                                <label class="col-sm-2 control-label ">是否接通</label>
	                                <div class="col-sm-4">
	                                  <input type="text" readonly value="${customerVO.connectstatus!''}" class="form-control" >
	                                </div>
	                            </div>
			  	 	 	 	</div>
			  	 		</div>
			  		</div>
			 	</div>
			 	
			 	<div class="panel-heading">客户满意度信息</div>
					<table style="align:center;" width="75%">
						<tbody>
						 	<#list questionList as questionVO>
								<tr>
									<th colspan="6" height="35" style="vertical-align: middle; padding-left: 20px; font-weight: bold;">
										${questionVO.question}
									</th>
								</tr>
								<#if questionVO.id<10>
								<tr>
									<td width="10%" height="30"></td>
									<td width="15%" align="left" style="vertical-align: middle">
										<#if questionVO.answer='A'>
											<div style="color:red; font-weight:bold;">${questionVO.optiona}</div>
										<#else>
											${questionVO.optiona}
										</#if>
									</td>
	                                <td width="15%" align="left" style="vertical-align: middle">
										<#if questionVO.answer='B'>
											<div style="color:red; font-weight:bold;">${questionVO.optionb}</div>
										<#else>
											${questionVO.optionb}
										</#if>
									</td>
	                                <td width="15%" align="left" style="vertical-align: middle">
										<#if questionVO.answer='C'>
											<div style="color:red; font-weight:bold;">${questionVO.optionc}</div>
										<#else>
											${questionVO.optionc}
										</#if>
									</td>
	                                <td width="15%" align="left" style="vertical-align: middle">
										<#if questionVO.answer='D'>
											<div style="color:red; font-weight:bold;">${questionVO.optiond}</div>
										<#else>
											${questionVO.optiond}
										</#if>
									</td>
	                                <td align="left" style="vertical-align: middle">
										<#if questionVO.answer='E'>
											<div style="color:red; font-weight:bold;">${questionVO.optione}</div>
										<#else>
											${questionVO.optione}
										</#if>
									</td>
	                            </tr>  
	                            </#if>
	                            <#if questionVO.id=1>
								<tr>
									<td width="10%" height="35"></td>
									<td colspan="5" align="left" style="vertical-align: middle">
										<div style="float: left;">其它途径：</div>
										<div style="font-weight:bold;">${questionVO.detailanswer}</div>
									</td>
	                            </tr> 
								</#if>
								<#if questionVO.id=6>
								<tr>
									<td width="10%" height="35"></td>
									<td colspan="5" align="left" style="vertical-align: middle">
										<div style="float: left;">具体收费情况：</div>
										<div style="color:red; font-weight:bold;">${questionVO.detailanswer}</div>
									</td>
	                            </tr> 
								</#if>
								<#if questionVO.id=7>
								<tr>
									<td width="10%" height="35"></td>
									<td colspan="5" align="left" style="vertical-align: middle">
										<div style="float: left;">具体哪里不满意（时限/态度/车辆/其它）：</div>
										<div style="color:red; font-weight:bold;">${questionVO.detailanswer}</div>
									</td>
	                            </tr> 
								</#if>
								<#if questionVO.id=10>
								<tr>
									<td width="10%" height="35"></td>
									<td colspan="5" align="left" style="vertical-align: middle">
										<div style="float: left;">您的意见或建议：</div>
										<div style="color:red; font-weight:bold; float: left;">${questionVO.answer}</div>
									</td>
	                            </tr> 
								</#if>
	                        </#list>
						</tbody>
					</table>
				</div>
				
			</div>
		</div>
			
			
	</form>
	<div id="menuContent" class="menuContent" style="display:none;  position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:340px; height: 300px;"></ul>
	</div>
	<#include "/pub/message.ftl"/>
	<#include "/pub/footer_res_detail.ftl"/>
	<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		function showMenu() {
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
		
	</script>
	</body>
</html>