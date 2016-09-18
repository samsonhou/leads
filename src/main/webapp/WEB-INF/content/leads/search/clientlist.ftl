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
.form-group{padding-bottom: 0px;margin-bottom: 10px;}
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
	<form action="${contextPath}/leads/search/queryList.do" method="post" name="form1" id="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">客户线索查询   &nbsp;&nbsp;&nbsp;线索总数为:${count}</div>
				<div class="panel-body" style="padding-bottom: 15px;">
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
		    			  			<@select type='1' codeType="1021" defValue="${clientVO.samllPid!'-1'}" fieldId="samllPid" fieldName="samllPid"  paramName="pid" paramValue="111" props=" class='form-control' " />
                                </div>                           
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">起止日期</label>
                                <div class="col-sm-2">
                                    <input type="text" readonly name="stnextdate" id="stnextdate" value="${stnextdate!''}" maxlength="30"  placeholder="开始日期" class="form-control layer-date" >
                                </div>
                                <div class="col-sm-2" >
		    			  			<input type="text" readonly name="nextdate" id="nextdate" value="${nextdate!''}" maxlength="30"  placeholder="结束日期" class="form-control layer-date" >
                                </div>
                                <label class="col-sm-2 control-label">来源</label>
                                 <div class="col-sm-2">
                                    <@select type='0' codeType="1044" defValue="${clientVO.fromtypeBig}" fieldId="fromtypeBig" fieldName="fromtypeBig" props=" datatype='*' nullmsg='请选择线索来源' class='form-control' " />
                                </div>
                                <div class="col-sm-2">
                                    <select style='display:none;'  class='form-control fromtype'"></select>
                                    <div class="fromtype" style='display:none;'>
                                    <div id="magicsuggest_1022"></div>
                                	<input type="hidden" id="fromtype" name="fromtype" value="${clientVO.fromtype}" class="form-control">
                                	</div>
                                    <input id="channel" name="channel" value="${clientVO.channel}" placeholder="请填写" style='display:none;' class='form-control fromtype'/>
                                    <@select type='1' codeType="1046" defValue="${clientVO.fromtype}" fieldId="fromtype" fieldName="fromtype" paramName="pid" paramValue="0" props=" style='display:none;' class='form-control fromtype'" />
                                </div>      
                            </div>
                            
                            <div class="form-group">
                            	<label class="col-sm-2 control-label">是否进件</label>
                            	<div class="col-sm-4">
                            		<@select type='0' codeType="1000" defValue="${clientVO.isIncome!''}" fieldId="isIncome" fieldName="isIncome"  props=" class='form-control' " />
                                </div>
                                <label class="col-sm-2 control-label">是否成交</label>
                            	<div class="col-sm-4">
                            		<@select type='0' codeType="1000" defValue="${clientVO.isDeal!''}" fieldId="isDeal" fieldName="isDeal"  props=" class='form-control' " />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">销售人员</label>
                                <div class="col-sm-4">
                                   <input id="sidName" name ="sidName" type="text" value="${dealPerson!''}" class='form-control' readonly  placeholder="请选择销售人员"  onclick="showMenu();" />
                                   <input id="sid" name ="sid" type="hidden"  value="${clientVO.sid!''}" />                                
                                   <input id="companyid" name ="companyid" type="hidden"  value="${clientVO.companyid!''}"  />
                                </div>     
                                <label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">机构</label>
                                <div class="col-sm-4">                                  
                                    <@select type='1' codeType="1034" fieldId="organId" fieldName="organId" defValue="${organId!''}" haveHead="false" paramName="organId" paramValue="${ogId}" props=" class='form-control' " /> 
                                </div>                           
                            </div>
                            
                            <div class="form-group">
                            	<label class="col-sm-2 control-label">租赁产品</label>
                            	<div class="col-sm-4">
                            		<@select type='0' codeType="1036" defValue="${clientVO.product!''}" fieldId="product" fieldName="product"  props=" class='form-control' " />
                                </div>
                                <label class="col-sm-2 control-label">是否可回收</label>
                            	<div class="col-sm-4">
                            		<@select type='0' defValue="${clientVO.isRecycle!''}" codeType="1000"  fieldId="isRecycle" fieldName="isRecycle"  props=" class='form-control' " />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否提车</label>
                            	<div class="col-sm-4">
                            		<@select type='0' defValue="${clientVO.isGetCar!''}" codeType="1037"  fieldId="isGetCar" fieldName="isGetCar"  props=" class='form-control' " />
                                </div>
                                <label class="col-sm-2 control-label">提车时间</label>
                            	<div class="col-sm-4">
                            		<input type="text" readonly name="getCarDate" id="getCarDate" value="${(clientVO.getCarDate?string('yyyy-MM-dd'))!''}" maxlength="30"  placeholder="提车时间" class="form-control layer-date" >
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否到店</label>
                            	<div class="col-sm-4">
                            		<@select type='0' defValue="${clientVO.idd!''}" codeType="1000"  fieldId="idd" fieldName="idd"  props=" class='form-control' " />
                                </div>
                                <label class="col-sm-2 control-label">小定金支付情况</label>
                            	<div class="col-sm-4">
                            		<@select type='0' defValue="${clientVO.depositStatus!''}" codeType="1040"  fieldId="depositStatus" fieldName="depositStatus"  props=" class='form-control' " />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">礼品发放</label>
                            	<div class="col-sm-4 ">
                                   <input type="checkbox" name="gift" ${(gifts?index_of("1") != -1)?string("checked","")} value="1">到店礼&nbsp;&nbsp;&nbsp;&nbsp;
                                   <input type="checkbox" name="gift" value="2" ${(gifts?index_of("2") != -1)?string("checked","")}>订车礼&nbsp;&nbsp;&nbsp;&nbsp;
                                   <input value="3" name="gift" type="checkbox"${(gifts?index_of("3") != -1)?string("checked","")}>交车礼
                                </div>
                               
                            </div>
                                  
                           <div class="form-group ">
                                <div class="col-sm-4 col-sm-offset-8">
	                                <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm">
	                                &nbsp;&nbsp;
	                                <#if flag  = '0'> 
                                    <input type="button" onclick="excel()" value="导 出" class="btn btn-primary btn-sm"> 	
                                    </#if>                                    
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
                                        <th style="text-align: center;">客户姓名</th>
                                        <th style="text-align: center;">业务类别</th>
                                        <th style="text-align: center;">手机</th>
                                        <th style="text-align: center;">客服专员</th>
                                        <th style="text-align: center;">门店</th>
                                        <th style="text-align: center;">客户经理</th>
                                        <th style="text-align: center;">来源</th>
                                        <th style="text-align: center;">客服首次邀约时间</th>
                                        <th style="text-align: center;">客服首次邀约详情</th>
                                        <th style="text-align: center;">小定金支付情况</th>
                                        <th style="text-align: center;">是否已结算</th>
                                        <th style="text-align: center;">外部订单号</th>
                                        <th style="text-align: center;">客服末次邀约时间</th>
                                        
                                        
                                        <th style="text-align: center;">门店一次邀约时间</th>
                                        <th style="text-align: center;">门店一次邀约详情</th>
                                        <th style="text-align: center;">门店二次邀约时间</th>
                                        <th style="text-align: center;">门店二次邀约详情</th>
                                        <th style="text-align: center;">门店三次邀约时间</th>
                                        <th style="text-align: center;">门店三次邀约详情</th>
                                        <th style="text-align: center;">门店末次邀约时间</th>
                                        <th style="text-align: center;">门店末次邀约详情</th>
                                        <th style="text-align: center;">预判等级</th>
                                        <th style="text-align: center;">预判C级原因</th>
                                        <th style="text-align: center;">预判D级原因</th>
                                        <th style="text-align: center;">租赁产品</th>
                                        <th style="text-align: center;">到店时间</th>
                                        <th style="text-align: center;">大定金支付情况</th>
                                        
                                        <th style="text-align: center;">是否进件</th>
                                        <th style="text-align: center;">进件时间</th>
                                        <th style="text-align: center;">是否签约</th>
                                        <th style="text-align: center;">签约时间</th>
                                        <th style="text-align: center;">是否提车</th>
                                        <th style="text-align: center;">提车时间</th>
                                        <th style="text-align: center;">是否邀约</th>
                                        <th style="text-align: center;">是否到店</th>
                                        <th style="text-align: center;">风控审核状态</th>
                                        <th style="text-align: center;">花生合同号</th>
                                        <th style="text-align: center;">是否可退小定金</th>
                                        <th style="text-align: center;">是否可回收</th>
                                        <th style="text-align: center;">礼品发放</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#list page.list as client>
									<tr class="span3tr">
										<td style="text-align: center;">${client_index+1}</td>
										<td><a href="#" onclick="viewDetail('${client.ID}');">查看</a></td>
                                        <td>${client.NAME}</td>
                                        <td>${client.BIG_PID} - ${client.SMALL_PID}</td>
                                        <td>${client.TEL}</td>
                                        <td>${client.RID}</td>
                                        <td>${client.COMNAME}</td>
                                        <td>${client.SID}</td>
                                        <td>${client.FROMTYPE}</td>
                                        <td>${client.QDATE}</td>
                                        <td>${client.TITLE}</td>
                                        <td>${(client.DEPOSITSTATUS==1)?string('无定金模式（普通来源）',(client.DEPOSITSTATUS==2)?string('已支付',(client.DEPOSITSTATUS==3)?string('未支付',(client.DEPOSITSTATUS==4)?string('已退回',''))))}</td>
                                        <td>${client.ISCHARGED}</td>
                                        <td>${client.ORDERNO}</td>
                                        <td>${(client.ALLOTDATE?string('yyyy-MM-dd'))!''}</td>
                                        <td>${(client.FDATE?string('yyyy-MM-dd'))!''}</td>
                                        <td>${client.FDETAIL}</td>
                                        <td>${(client.SDATE?string('yyyy-MM-dd'))!''}</td>
                                        <td>${client.SDETAIL}</td>
                                        <td>${(client.TDATE?string('yyyy-MM-dd'))!''}</td>
                                        <td>${client.TDETAIL}</td>
                                        <td>${(client.LDATE?string('yyyy-MM-dd'))!''}</td>
                                        <td>${client.LDETAIL}</td>
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
                                        <td>${client.PRODUCT}</td>
                                       	<td>${(client.FIRSTTIMECOMING?string('yyyy-MM-dd'))!''}</td>
                                       	<td>${client.INNDEPOSIT}</td>
                                       	
                                       	<td>${(client.ISINCOME=='1')?string('是','否')}</td>
                                        <td>${(client.INCOMEDATE?string('yyyy-MM-dd'))!''}</td>
                                        <td>${(client.ISDEAL=='1')?string('是','否')}</td>
                                        <td>${(client.DEALDATE?string('yyyy-MM-dd'))!''}</td>
                                        <td>${client.ISGETCAR}</td>
                                        <td>${(client.GETCARDATE?string('yyyy-MM-dd'))!''}</td>
                                        <td>${(client.LMTNUM>0)?string('是',(client.LMTNUM==0)?string('否',''))}</td>
                                        <td>${(client.IDD==1)?string('是',(client.IDD==0)?string('否',''))}</td>
                                        <td>${client.CREDIT}-${client.CREDIT_STATUS}</td>
                                        <td>${client.CONTRACTNO}</td>
                                        <td>${client.ISCANCLE}</td>
                                        <td>${client.ISRECYCLE}</td>
                                        <td>
										<#if client.GIFT?index_of("1") != -1>到店礼,</#if>
										<#if client.GIFT?index_of("2") != -1>订车礼,</#if>
										<#if client.GIFT?index_of("3") != -1>交车礼</#if>
										</td>
                                    </tr>  
                                    </#list>
                              </tbody>
                            </table>
			</div>
			<@pages url="${contextPath}/leads/search/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
		</div>
		<div id="menuContent" class="menuContent" style="display:none;  position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:340px; height: 300px;"></ul>
		</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script type="text/javascript">
	var myData_1022=<@queryselect type="1" codeType="1022" />
 //读取下拉框的 值，健 
 		function getmagicSuggest_1022(){
 			ms1 = $('#magicsuggest_1022').magicSuggest({
 		        width: '80%',//宽度
 		        placeholder: '请选择',
 		        style:'float:left;width:100%;',
 		        allowFreeEntries: false,   //这个参数很重要，如果你不需要用户自已创建标签，则用这个
 		        data: myData_1022.data,
 		        selectionStacked: true ,
 		        maxSelectionRenderer: function(data){ return ""},
 		        noSuggestionText: '',
 		        maxSelection:1 //单选按照 0取值 
 		    });
 		    $(ms1).on('selectionchange', function(e, cb, s){
 		     var object =cb.getSelection()[0];  
 		    console.log(object);
 		    if(undefined==object){$("#fromtype").val("");}else{
 		     $("#fromtype").val(object.id);}
 		    });
 		    getStoredCallback_1022(ms1);
 		}
 		//获取查询条件回显
 	function getStoredCallback_1022(ms1){ 
 	  var bl = $('#fromtype').val();
 	  if(bl == ''||0==bl) return;
 	  var array = bl.split(","); 
 	  //设置延迟，否则取不到数据
 	  setTimeout(function (){
     	  ms1.setValue(array);
     	  }, 200);
 	}
		function search(){
			form1.action="${contextPath}/leads/search/queryList.do";
			form1.submit();
		}
		//查看
		function viewDetail(itemId){
			var parm = "id="+itemId;
			openNewTab("${contextPath}/leads/assign/addTrace.do?"+parm,"查看线索");
		}
		jQuery(document).ready(function(){
			getmagicSuggest_1022();
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
					//初始化 业务小类 bigPid
					if(jQuery("#bigPid").val().length>0){
						jQuery("#bigPid").trigger('change');
					}
				}
			});
		});
		
		$("#fromtypeBig option").each(function(i,o){
			if($(this).attr("selected")){
						$(".fromtype").hide();
						if(i!=0){
							$(".fromtype").eq(i).removeAttr("disabled");
							$(".fromtype").eq(i).show();
						}
					}else{
						if(i==1){
							$(".fromtype").eq(i).find("#fromtype").attr("disabled",true);
						}
						$(".fromtype").eq(i).attr("disabled",true);
					}
		});
		
		$("#fromtypeBig").on("change",function(){
				$("#fromtypeBig option").each(function(i,o){
					if($(this).prop("selected")){
						$(".fromtype").hide();
						if(i!=0){
							if(i==1){
								$(".fromtype").eq(i).find(".ms-sel-item").html("");
								$(".fromtype").eq(i).find("#fromtype").val("");
								$(".fromtype").eq(i).find("#fromtype").removeAttr("disabled");
							}
							$(".fromtype").eq(i).removeAttr("disabled");
							$(".fromtype").eq(i).show();
						}
					}else{
						if(i==1){
							$(".fromtype").eq(i).find("#fromtype").attr("disabled",true);
						}
						$(".fromtype").eq(i).attr("disabled",true);
					}
				});
			});
		
		$("#bigPid").on("change", function(){
			$.ajax({
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
		
		
			var end1={
	    		elem:"#stnextdate",
	    		//format:"YYYY/MM/DD hh:mm:ss",
	    		format:"YYYY-MM-DD",
	    		min:"1970-06-16 23:59:59",
	    		max:"2099-06-16 23:59:59",
	    		istime:false,istoday:false,
	    		choose:function(a){
	    			end2.min=a
	    		}
	    	};
	    	laydate(end1);
			var end2={
	    		elem:"#nextdate",
	    		//format:"YYYY/MM/DD hh:mm:ss",
	    		format:"YYYY-MM-DD",
	    		min:"1970-06-16 23:59:59",
	    		max:"2099-06-16 23:59:59",
	    		istime:false,istoday:false,
	    		choose:function(a){
	    			end1.max=a
	    		}
	    	};
	    	laydate(end2);
	    	
	    	var getCarDate={
    		elem:"#getCarDate",
    		format:"YYYY-MM-DD",
    		max:"2099-06-16",
    		istime:false,
    		istoday:false
	    	};
	    	laydate(getCarDate);
	    	
	    	function excel(){
	    	 if(formValidate()){
    		   form1.action="${contextPath}/leads/search/exportExcel.do";
    		   form1.submit();
    		  }	
		   }
		   
		   
		   function  formValidate(){
		   	 var fromType = $("#fromtype").val();
		   	 if(fromType != '391'){
    		 var saveform = $('#form1');
    		 var lv= saveform.find('#stnextdate').val().length;
    	     if(lv==0){
    	       swal({title:"",text:"开始日期不能为空!"});
    	       return false;
    	      }
    	    lv= saveform.find('#nextdate').val().length;
    	    if(lv==0){
    	       swal({title:"",text:"结束日期不能为空!"});
	           return false;
	         }
	        var m1 = saveform.find('#stnextdate').val();
	        var m2 = saveform.find('#nextdate').val();
	        
	        d1 = new Date(m1.replace(/-/g, "/"));
	        d2 = new Date(m2.replace(/-/g, "/"));
	        var days = d2.getTime() - d1.getTime();
	        var time = parseInt(days / (1000 * 60 * 60 * 24));
	        if(time > 30){
	           swal({title:"",text:"选择时间不能超过30天!"});
	           return false
	        }
	        }
    	    return true;
    	}
    	
	</script>
	</body>
</html>