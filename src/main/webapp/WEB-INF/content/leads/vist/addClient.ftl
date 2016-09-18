<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <link href="${contextPath}/res/pub/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
		 <link href="${contextPath}/res/pub/css/demo.css" rel="stylesheet">
		 <title>追踪回复</title>

	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/vist/saveClientAndTrace.do" method="post" name="form1" id="form1" class="form-horizontal">
		<input type="hidden" id="comform" name="comform"  value="${comform}">
		<input type="hidden" id="urgeid" name="urgeid"  value="${urgeid}"> <!--催促ID -->		
		<div class="container-fluid">
		 <div class="panel panel-default" style="margin-top: 1px;">
		       <div class="panel-heading" id="topDiv">客户基本信息</div>
		       <div class="panel-body" style="padding-bottom: 0px;">
		       <div class="col-sm-12">
		            <div class="float-e-margins">
		                 <div class="ibox-content" style="padding:0 0 0 0">
		                 	
		                 	<div class="form-group ">
                                <label class="col-sm-2 control-label">姓名 <font color="#ff0000">*</font></label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="clientName" id="clientName" datatype="*" maxlength="10" nullmsg="请输入客户姓名" value="${clientVO.clientName!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label  required" >手机<font color="#ff0000">*</font></label>
                                <div class="col-sm-4">
                                    <input type="text" name="tel" id="tel" onblur="codeCheckTel(this)" datatype="m" errormsg="请正确输入手机号" maxlength="11" value="${clientVO.tel!''}" class="form-control" >
                                    <input	name="telPre" id="telPre" type="hidden" value="${clientVO.tel!''}"/>
                                </div>
                            </div>
                            
                             <div class="form-group ">
                                <label class="col-sm-2 control-label  required">来源 <font color="#ff0000">*</font></label>
                                <div class="col-sm-2">
                                    <@select type='0' codeType="1044" fieldId="fromtypeBig" fieldName="fromtypeBig" props=" datatype='*' nullmsg='请选择线索来源' class='form-control' " />
                                </div>
                                <div class="col-sm-2">
                                    <select style='display:none;'  class='form-control fromtype'"></select>
                                    <@select type='1' codeType="1022" fieldId="fromtype" fieldName="fromtype" paramName="pid" paramValue="0" props=" style='display:none;' class='form-control fromtype'" />
                                    <input id="channel" name="channel" placeholder="请填写" style='display:none;' class='form-control fromtype'/>
                                    <@select type='1' codeType="1046" fieldId="fromtype" fieldName="fromtype" paramName="pid" paramValue="0" props=" style='display:none;' class='form-control fromtype'" />
                                </div>
                                <label class="col-sm-2 control-label">手机1</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="tel1" id="tel1" maxlength="11"  value="${clientVO.tel1!''}" datatype="mOe" onblur="codeCheckTel(this)" class="form-control">
                                </div>                                
                            </div>
                            <div class="form-group ">
                                <label class="col-sm-2 control-label">QQ</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="qq" id="qq" maxlength="20" value="${clientVO.qq!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label  required" >微信</label>
                                <div class="col-sm-4">
                                    <input type="text" name="weixin" id="weixin"  maxlength="20" value="${clientVO.weixin!''}" class="form-control" >
                                </div>
                            </div>
                            <div class="form-group ">                                
                                <label class="col-sm-2 control-label  required" >座机电话</label>
                                <div class="col-sm-4">
                                    <input type="text" name="phone" id="phone" placeholder="0731-48458745 或 85623658"  maxlength="13" value="${clientVO.phone!''}" class="form-control" >
                                </div>
                                <label class="col-sm-2 control-label">身份证号</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="personid" id="personid" maxlength="18" value="${clientVO.personid!''}" class="form-control">
                                </div>
                            </div>
                            <div class="form-group ">
                                
                                <label class="col-sm-2 control-label  required">加急处理</label>
                                <div class="col-sm-4">
                                   <input name="ifurgent" value="1"  type="radio"> 是   <input name="ifurgent" value="0" checked type="radio"> 否
                                </div>
                                <label class="col-sm-2 control-label  required">小定金支付情况</label>
                                <div class="col-sm-4">
                                <input type="hidden" value="${clientVO.depositStatus!''}" />
                                   <@select type='0' codeType="1040" defValue="${clientVO.depositStatus!''}" fieldId="depositStatus" fieldName="depositStatus" props="datatype='*' nullmsg='请选择小定金支付情况' class='form-control' " />
                                </div>
                            </div>
                            
                            <div class="form-group">
                            	<label class="col-sm-2 control-label  required">到店时间</label>
                                <div class="col-sm-4">
                                 	<input type="text" readonly name="firstTimeComing" id="firstTimeComing" maxlength="30"  placeholder="到店时间" class="form-control layer-date" />
                                </div>
                                <label class="col-sm-2 control-label  required">大定金支付情况</label>
                                <div class="col-sm-4">
                                <input type="hidden" value="${clientVO.depositStatus!''}" />
                                   <@select type='0' codeType="1052" defValue="${clientVO.innDeposit!''}" fieldId="innDeposit" fieldName="innDeposit" props="datatype='*' nullmsg='请选择大定金支付情况' class='form-control' " />
                                </div>
                            	
                            </div>

                            <div class="form-group ">
                         	    <label class="col-sm-2 control-label">邮箱</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="email" id="email" maxlength="50"  value="${clientVO.email!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label  required">是否已结算</label>
                                <div class="col-sm-4">
                                <input type="hidden" value="${clientVO.depositStatus!''}" />
                                   <@select type='0' codeType="1000" defValue="${clientVO.isCharged!''}" fieldId="isCharged" fieldName="isCharged" props="class='form-control' " />
                                </div>
                            </div> 
                            
                            <div class="form-group">
                            	<label class="col-sm-2 control-label  required">租赁产品</label>
                            	<div class="col-sm-4">
                            	<@select type='0' codeType="1036"  fieldId="product" fieldName="product"  props=" class='form-control' " />
                            	</div>
                            	<label class="col-sm-2 control-label">外部订单号</label>
                                <div class="col-sm-4 ">
                                    <input type="text" name="orderNo" id="orderNo" maxlength="50"  value="${clientVO.orderNo!''}" class="form-control">
                                </div>
                            </div> 
                              	
		                 </div>
		            </div>
		       </div>
		       </div>
		 </div>
		 
		 <div class="panel panel-default" style="margin-top: 1px;">
	       <div class="panel-heading">咨询信息</div>
	       <div class="panel-body" style="padding-bottom: 0px;">
	       <div class="col-sm-12">
	            <div class="float-e-margins">
	                 <div class="ibox-content" style="padding:0 0 0 0">
	                 
	                 <div class="form-group">
                                <label class="col-sm-2 control-label">业务大类  <font color="#ff0000">*</font></label>
                                <div class="col-sm-4">
                                    <@select type='1' codeType="1021" defValue="${clientVO.bigPid!'-1'}" fieldId="bigPid" fieldName="bigPid"  paramName="pid" paramValue="0" props="datatype='*' nullmsg='请选择业务大类' class='form-control' " />
                                </div>
                                <label class="col-sm-2 control-label">业务小类  <font color="#ff0000">*</font></label>
                                   <div class="col-sm-4" id="samllPidDiv" data-defvalue="${clientVO.smallPid!-1}">
		    			  			<@select type='1' codeType="1021" fieldId="samllPid" fieldName="samllPid"  paramName="pid" paramValue="111" props="datatype='*' nullmsg='请选择业务小类' class='form-control' " />
                                   </div>   
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">咨询详情  <font color="#ff0000">*</font></label>
                                <div class="col-sm-10">
                                    <textarea placeholder="请录入咨询详情" class='form-control' name="title" datatype="*" nullmsg="请录入咨询详情" id="title">${clientVO.title!''}</textarea>
                                </div>
                                
                            </div>

	                 </div>
	            </div>
	       </div>
	       </div>
		 </div>
	     <div class="panel panel-default" style="margin-top: 1px;">
	       <div class="panel-heading">任务指派</div>
	       <div class="panel-body" style="padding-bottom: 0px;">
	       		<div class="form-group">
	       			<label class="col-sm-2 control-label">业务员</label>
	       			<div class="col-sm-3">
	       				<input id="sidName" name ="sidName" type="text" value="${sidNameFromye!''}" datatype="*" nullmsg="请选择销售人员" class='form-control' readonly />
                        <input id="sid" name ="sid" type="hidden"  value="${sidFromye!''}" />                                
                        <input id="rid" name ="rid" type="hidden"  value="${sidFromye!''}" />                                
                        <input id="companyid" name ="companyid" type="hidden"  value="${companyidFromye!''}"  />
                                  
	       			</div>
	       		</div>
	       </div>
	     </div>
		 
		 <VAR>
		 <div class="panel panel-default" style="margin-top: 1px;">
		       <div class="panel-heading">追踪录入</div>
		       <div class="panel-body" style="padding-bottom: 0px;">
		       <div class="col-sm-12">
		            <div class="float-e-margins">
		                 <div class="ibox-content" style="padding:0 0 0 0">
		                 	<div class="form-group">
                                <label class="col-sm-2 control-label">追踪记录  <font color="#ff0000">*</font></label>
                                <div class="col-sm-10">
                                    <textarea placeholder="请录入追踪记录" class='form-control' datatype="*" name="t_title" id="t_title"></textarea>
                                </div>                                
                            </div>
                            
                             <div class="form-group ">
                                <input type="hidden" name="rankbefore" id = "rankbefore">
                                <input type="hidden" id="init_rank" name="init_rank"> 
                                <label class="col-sm-2 control-label">等级 <font color="#ff0000">*</font></label>
                                <div class="col-sm-4 ">                                   
                                    <@select type='1' codeType="1053" fieldId="rank" fieldName="rank"  props=" class='form-control' " />                               
                                </div>
                                <div id="staid" class="col-sm-6" style="display:none;">
                                     <input name="status" id = "status" value="1"  type="radio"> 无人接听
                                     <input name="status" id = "status" value="2"  type="radio"> 无法接通
                                     <input name="status" id = "status" value="3"  type="radio"> 关机 
                                     <input name="status" id = "status" value="4"  type="radio"> 停机 
                                     <input name="status" id = "status" value="5"  type="radio"> 挂断 
                                </div>
                                <div id="cont" style="display:none;">
                                     <label class="col-sm-2 control-label">合同号 <font color="#ff0000">*</font></label>
                                     <input type="text" name="contractno" id ="contractno" onblur="codeCheck(this)" class='form-control' style="width: 300px;">
                                </div>
                            </div>
                            <div class="form-group ">
                                <div id="stait" class="col-xs-offset-2" style="display:none">                             
                                     <input name="reason" id = "reason1" value="1"  type="radio" onclick="getType('1')"> A 车型不匹配
                                     <input name="reason" id = "reason2" value="2"  type="radio" onclick="getType('2')"> B 金融方案不满意
                                     <input name="reason" id = "reason3" value="3"  type="radio" onclick="getType('3')"> C 风控原因  (审核未通过)
                                     <input name="reason" id = "reason4" value="4"  type="radio" onclick="getType('4')"> D 其他原因 
                                     <input type="text" name="reasonCont" id ="reasonCont"  style="width:500px">
                                </div>
                             </div>
 
                             <input name="id" type="hidden" value="${cMap.ID!''}">
                             
                              <div class="form-group">
                                <label class="col-sm-2 control-label">下次跟踪时间</label>
                                <div class="col-sm-4">
                                    <input type="text" readonly name="nextdate" id="nextdate" maxlength="30"  placeholder="日期" class="form-control layer-date" >
                                </div>                                
                            </div>
                            
                             
                            <input id="fromwhere" name="fromwhere" type="hidden" >
                             <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                  <input id="btn" name ="btn" type="submit" value="提 交" class="btn btn-sm btn-primary zd-btn-pd1"  data-toggle="modal" data-target="#myModal">
	                             	  &nbsp; &nbsp; 
	                            	  <input type="button" value="关 闭" id="closeBtn" onclick="closeTab();" class="btn btn-sm btn-primary zd-btn-pd1" >
	                             </div>
                            </div>
                            
		                 </div>
		            </div>
		       </div>
		       </div>
		 </div>
		 </VAR>
		<div>
	</form>
	<#include "/pub/message.ftl"/>
	<#include "/pub/footer_res_detail.ftl"/>
	<script type="text/javascript">
		var firsttime={
    		elem:"#firstTimeComing",
    		format:"YYYY-MM-DD",
    		max:"2099-06-16"
    	};
    	laydate(firsttime);
		
		var nextdate={
    		elem:"#nextdate",
    		format:"YYYY-MM-DD",
    		max:"2099-06-16"
    	};
    	laydate(nextdate);
	
		$(document).ready(function(){
			$("#fromtypeBig").on("change",function(){
				$("#fromtypeBig option").each(function(i,o){
					if($(this).prop("selected")||$(this).attr("selected")){
						$(".fromtype").hide();
						if(i!=0){
							$(".fromtype").eq(i).attr("datatype","*").attr("nullmsg","请选择线索类型");
							$(".fromtype").eq(i).removeAttr("disabled");
							$(".fromtype").eq(i).show();
						}
					}else{
						$(".fromtype").eq(i).removeAttr("datatype","*").removeAttr("nullmsg","请选择线索类型");
						$(".fromtype").eq(i).attr("disabled","true");
					}
				});
			});
			
			
			jQuery("#bigPid").on("change", function(){
				jQuery.ajax({
					type:"post",
					url:"${contextPath}/leads/client/querySub.do?bigPid="+jQuery("#bigPid").val(),
					data:"",
					async:true,
					dataType:"text",
					success:function(data){			
						var $samllPidDiv=jQuery("#samllPidDiv");
						var v=$samllPidDiv.attr("data-defvalue");
						if(v=='-1' || v=='0'){
							v='';
						}
						$samllPidDiv.empty().html(data).find("select").attr("datatype","*").addClass("form-control").val(v);
						$samllPidDiv.attr("data-defvalue","");	
					}
				});
			});
			
			$("#rank").on("change",function(){
				var rank = $("#rank").val();
				if(rank=="C") {
					$("#stait").show();
				}else{
					$("#stait").hide();
				}
				if(rank=="D") {
					$("#staid").show();
				}else{
					$("#staid").hide();
				}
				
			});
			
			
			
			var demo =$("#form1").Validform({tiptype : 1,
				ignoreHidden : false,
				dragonfly : false,
				tipSweep : true,
				showAllError : true,
				postonce : true,
				ajaxPost : true,
				datatype:{mOe:/^$|^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$|17[0-9]{9}$/},
				beforeCheck:function(curform){
					if(formValidate()){
					}else{
						return false;
					}
				},
				callback:function(data){
					layer.alert(data.info, function(index){
			  			closeTab();
			  			layer.close(index);
					}); 
				}
			});
			
				
			});
		
		function codeCheckTel(obj) {
				
				// 首先进行校验		
				$.ajax({
					type:"post",
					url:"${contextPath}/leads/client/checkTel.do",
					data:"tel="+ $("#tel").val(),
					dataType:"text",
					success:function(data){//alert(data);
						if(data.length > 0){
							layer.alert(data);
							
						}
					}
				});	
			}
			
	
	function  formValidate(){
	    	var saveform = $('#form1');
	       
	        var isgetcar = saveform.find('#isGetCar').val();
	        if(isgetcar==''){
	        	//swal({title:"",text:"请选择是否提车！"});
	        	layer.alert("请选择是否提车！");
	        	return false;
	        }
	        
	        var lv = saveform.find('#getCarDate').val();
	        if(lv=='' && isgetcar =='1'){
	        	//swal({title:"",text:"请输入提车时间！"});
	        	layer.alert("请输入提车时间！");
	        	return false;
	        }
	        
	        var lv = saveform.find('#carNo').val();
	        if(lv=='' && isgetcar =='1'){
	        	//swal({title:"",text:"请输入车牌号！"});
	        	layer.alert("请输入车牌号！");
	        	return false;
	        }
	        
	        var rk= saveform.find('#rank').val()
	        var isCheck = $("input[name='reason']").is(':checked')
	        var checkValue = $("input[name='reason']:checked").val();
	        if(isCheck==false&&rk=='C'){
	        	//swal({title:"",text:"请选择放弃原因!"});
	        	layer.alert("请选择放弃原因!");
	            return false;
	        }  
	        if(checkValue=='4'&&rk=='C'){
	           var lv= saveform.find('#reasonCont').val();
	           lv = $.trim(lv);
	           var reg = /^[\u4E00-\u9FA5]+$/; 
	           if(lv==''){
	              //swal({title:"",text:"请输入具体原因!"});
	              layer.alert("请输入具体原因!");
	              return false;
	           }
	           if(lv.length<5){
	              //swal({title:"",text:"具体原因不能少于5个汉字!"});
	              layer.alert("具体原因不能少于5个汉字!");
	              return false;
	           }
	           if (!reg.test(lv)){
	              //swal({title:"",text:"只能输入汉字!"}); 
	              layer.alert("只能输入汉字!");
	              return false ; 
	           } 
	        }
	        var contract = saveform.find('#contractno').val();
	        var rank =$("#rank").val();
	        contract = $.trim(contract);
	        if(contract== ''&&rank=='O'){
	           swal({title:"",text:"合同号为空!"});
	           //layer.alert("合同号为空!");
	           return false;
	        }
	        if(contract.length<5&&rank=='O'){
	          // swal({title:"",text:"合同号不足5位!"});
	          layer.alert("合同号不足5位!");
	           return false;
	        }   	               
	      	return  true ;  
	      }
		
	</script>

	</body>
</html>