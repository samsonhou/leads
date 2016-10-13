<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>客户线索</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/client/queryList.do" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">客户线索录入</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					<div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label">客户姓名</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="clientName" value="${clientVO.clientName!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label  required">手机</label>
                                <div class="col-sm-4">
                                    <input type="text" name="tel" maxlength="11" value="${clientVO.tel!''}" class="form-control" >
                                </div>
                            </div>
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label">客户经理</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="customerManager" value="${clientVO.customerManager!''}" class="form-control">
                                </div>
                            </div>
                            
                            
                             <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                   <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                   &nbsp; &nbsp; 
	                                   <input type="button" value="新 增" class="btn btn-primary btn-sm zd-btn-pd1"  data-toggle="modal" data-target="#myModal">
	                                   &nbsp; &nbsp; 
	                                   <input type="button" value="导出" onclick="exp();" class="btn btn-primary btn-sm zd-btn-pd1">

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
                                        <th style="text-align: center;">客户姓名</th>
                                        <th style="text-align: center;">身份证</th>
                                        <th style="text-align: center;">手机</th>
                                        <th style="text-align: center;">城市</th>
                                        <th style="text-align: center;">门店</th>
                                        <th style="text-align: center;">客户经理</th>
                                        <th style="text-align: center;">填写时间</th>
                                        <th style="text-align: center;">状态</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#list page.list as client>
									<tr>
										<td style="text-align: center;">${client_index+1}</td>
                                        <td>${client.clientName}</td>
                                        <td>${client.idNo}</td>
                                        <td>${client.tel}</td>
                                        <td>${client.city}</td>
                                        <td>${client.organ}</td>
                                        <td>${client.customerManager}</td>
                                        <td>${(client.createdTime?string("yyyy-MM-dd HH:mm:ss"))!}</td>
                                        <td>${(client.status='1')?string('已分配','未分配')}</td>
                                        
                                    </tr>  
                                    </#list>
                              </tbody>
                            </table>
			</div>
			<@pages url="${contextPath}/leads/jy/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
		</div>
	</form>
	
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document" style="width:1000px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">新增线索</h4>
	      </div>
	      <form name='saveform' id='saveform'  class="form-search" method="post" >
	      <div class="modal-body" style="height:250px;"><!--高度根据表单高度相应调整-->
	      <input type="hidden" name="id" id="id"/>
	                   <div class="ibox-content ">
	                   
	                            <div class="form-group ziding-ibox-modal model_alert_1">
	                                <label class="col-sm-2 control-label">客户姓名</label>
	                                <div class="col-sm-4 ">
	                                	<input type="text" name="clientName" id="clientName" datatype="*" nullmsg="请填写客户姓名！" class="form-control" />
	                                </div>
	                                <label class="col-sm-2 control-label">身份证</label>
	                                <div class="col-sm-4 ">
	                                	<input type="text" name="idNo" id="card_no" datatype="idcard" errormsg="请正确填写客户身份证号！" class="form-control" />
	                                </div>
	                                
	                            </div>	
	                            
	                            <div class="form-group ziding-ibox-modal model_alert_1">
	                            	<label class="col-sm-2 control-label">电话</label>
	                                <div class="col-sm-4 ">
	                                	<input type="text" ajaxurl="${contextPath}/leads/jy/checkTel.do" name="tel" id="tel" maxlength="11" datatype="m" nullmsg="请填写客户电话！" errormsg="请填写正确的电话号码！" class="form-control"/>
	                                </div>
	                            	<label class="col-sm-2 control-label">城市</label>
	                                <div class="col-sm-4 ">
	                                	<input type="text" name="city" id="city" class="form-control" datatype="*" nullmsg="请填写城市！" />
	                                </div>
	                            </div>
	                            
	                            <div class="form-group ziding-ibox-modal model_alert_1">
	                            	<label class="col-sm-2 control-label">客户经理</label>
	                                <div class="col-sm-4 ">
	                                	<input type="text" name="customerManager" maxlength="10" id="customerManager" datatype="*" nullmsg="请填写客户经理！" class="form-control"/>
	                                </div>
	                            </div>
	                              																		
						</div>
	      </div>
	     </form>
	      <div class="modal-footer">
	      	<input type="hidden" data-seq='00' value="00" id="_defOrganId_">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" id="btn_sub" class="btn btn-primary" onclick="save()">保存</button>
	      </div>
	    
	    </div>
	  </div>
	</div>
	<#include "/pub/message.ftl"/>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		function search(){
			form1.action="${contextPath}/leads/jy/queryList.do";
			form1.submit();
		}
		
		$(document).ready(function(){
			jQuery("#pagination").page("form1");
		});
		
		var saveForm=$("#saveform").Validform({
				showAllError:true,
				postonce:true,
				datatype:{
					"idcard":function(gets,obj,curform,datatype){
						//该方法由佚名网友提供;
					
						var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子;
						var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值，10代表X;
					
						if (gets.length == 15) {   
							return isValidityBrithBy15IdCard(gets);   
						}else if (gets.length == 18){   
							var a_idCard = gets.split("");// 得到身份证数组   
							if (isValidityBrithBy18IdCard(gets)&&isTrueValidateCodeBy18IdCard(a_idCard)) {   
								return true;   
							}   
							return false;
						}
						return false;
						
						function isTrueValidateCodeBy18IdCard(a_idCard) {   
							var sum = 0; // 声明加权求和变量   
							if (a_idCard[17].toLowerCase() == 'x') {   
								a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作   
							}   
							for ( var i = 0; i < 17; i++) {   
								sum += Wi[i] * a_idCard[i];// 加权求和   
							}   
							valCodePosition = sum % 11;// 得到验证码所位置   
							if (a_idCard[17] == ValideCode[valCodePosition]) {   
								return true;   
							}
							return false;   
						}
						
						function isValidityBrithBy18IdCard(idCard18){   
							var year = idCard18.substring(6,10);   
							var month = idCard18.substring(10,12);   
							var day = idCard18.substring(12,14);   
							var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
							// 这里用getFullYear()获取年份，避免千年虫问题   
							if(temp_date.getFullYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
								return false;   
							}
							return true;   
						}
						
						function isValidityBrithBy15IdCard(idCard15){   
							var year =  idCard15.substring(6,8);   
							var month = idCard15.substring(8,10);   
							var day = idCard15.substring(10,12);
							var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
							// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
							if(temp_date.getYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
								return false;   
							}
							return true;
						}
						
					}
					
				}
		});
		
		function save(){
			saveForm.submitForm(false,"${contextPath}/leads/jy/addClient.do");
		}
		
		function openModal(){
		 var mymodal = $("#myModal");
		 mymodal.modal("show");
		}
		
		function exp(){
			form1.action = "${contextPath}/leads/jy/exp.do";
			form1.submit();
		}
		
	</script>
	</body>
</html>