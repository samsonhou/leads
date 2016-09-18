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
	<form action="${contextPath}/leads/client/queryList.do" method="post" name="form1" id="form1" class="form-horizontal">
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
                                <label class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-4 ">
                                    <input type="text" id="cName" readonly value="${cMap.NAME!''} " class="form-control">
                                </div>
                                <label class="col-sm-2 control-label">手机</label>
                                <div class="col-sm-4">
                                     <input type="text" readonly value="${cMap.TEL!''}" class="form-control">
                                </div>
                            </div>
                            
                            <div class="form-group ">
                         	    <label class="col-sm-2 control-label">来源</label>
                                <div class="col-sm-2">
                                    <input type="text" readonly value="${(cMap.FROMTYPEBIG=='1')?string('互联网',(cMap.FROMTYPEBIG=='2')?string('渠道',(cMap.FROMTYPEBIG=='3')?string('直销','')))}" class="form-control">
                                </div>
                                <div class="col-sm-2">
                                    <input type="text" readonly value="${(cMap.FROMTYPEBIG=='1'||cMap.FROMTYPEBIG=='3')?string(cMap.FROMTYPE,cMap.CHANNEL)}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label">手机1</label>
                                <div class="col-sm-4 ">
                                   <input type="text" readonly value="${cMap.TEL1!''}" class="form-control">
                                </div>                                
                            </div> 
                            
                            <div class="form-group ">
                         	    <label class="col-sm-2 control-label">QQ</label>
                                <div class="col-sm-4">
                                    <input type="text" readonly value="${cMap.QQ!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label">微信</label>
                                <div class="col-sm-4 ">
                                   <input type="text" readonly value="${cMap.WEIXIN!''}" class="form-control">
                                </div>                                
                            </div>    
                            
                            <div class="form-group ">
                         	    <label class="col-sm-2 control-label">座机电话</label>
                                <div class="col-sm-4">
                                    <input type="text" readonly value="${cMap.PHONE!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label">身份证号</label>
                                <div class="col-sm-4 ">
                                   <input type="text" readonly value="${cMap.PERSONID!''}" class="form-control">
                                </div>                                
                            </div>  
 							
 							<div class="form-group ">
                         	    <label class="col-sm-2 control-label">是否加急</label>
                                <div class="col-sm-4">
                                    <input type="text" readonly value="${cMap.IFURGENT!''}" class="form-control">
                                </div>  
                                <label class="col-sm-2 control-label">邮箱</label>
                                <div class="col-sm-4 ">
                                   <input type="text" readonly value="${cMap.EMAIL!''}" class="form-control">
                                </div>                             
                            </div> 
                                  
                            <div class="form-group ">
                         	    <label class="col-sm-2 control-label">业务大类</label>
                                <div class="col-sm-4">
                                    <input type="text" readonly value="${cMap.BIG_PID!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label">业务小类</label>
                                <div class="col-sm-4 ">
                                   <input type="text" readonly value="${cMap.SMALL_PID!''}" class="form-control">
                                </div>                                
                            </div>   
                            
                            <div class="form-group ">
                         	    <label class="col-sm-2 control-label">小定金支付情况</label>
                                <div class="col-sm-4">
                                    <input type="text" readonly value="${cMap.DEPOSITSTATUS!''}" class="form-control">
                                </div>
                         	    <label class="col-sm-2 control-label">是否已结算</label>
                                <div class="col-sm-4">
                                    <input type="text" readonly value="${cMap.ISCHARGED!''}" class="form-control">
                                </div>
                            </div>   
                            
                            
                            <div class="form-group">
                            	<label class="col-sm-2 control-label  required">是否通过风控</label>
                                <div class="col-sm-4">
									<input type="text" readonly value="${cMap.IFK!''}" class="form-control">
                                </div>
                            	<label class="col-sm-2 control-label  required">外部订单号</label>
                                <div class="col-sm-4">
									<input type="text" readonly value="${cMap.ORDERNO!''}" class="form-control">
                                </div>
                            </div>
                            
                            
                            <div class="form-group">
                            	<label class="col-sm-2 control-label  required">进件时间</label>
                            	<div class="col-sm-4">
                            	<input type="text" readonly name="getCarDate" id="getCarDate" value="${(cMap.GETCARDATE?string('yyyy-MM-dd'))!''}" maxlength="30" class="form-control layer-date" >
                            	</div>
                            	<label class="col-sm-2 control-label  required">提车时间</label>
                            	<div class="col-sm-4">
                            	<input type="text" readonly name="getCarDate" id="getCarDate" value="${(cMap.GETCARDATE?string('yyyy-MM-dd'))!''}" maxlength="30" class="form-control layer-date" >
                            	</div>
                            </div>
                            <div class="form-group" >
                            	<label class="col-sm-2 control-label  required">花生合同号</label>
                            	<div class="col-sm-4">
                            	<input type="text" readonly  value="" class="form-control" >
                            	</div>
                            	<label class="col-sm-2 control-label  required">车牌号</label>
                            	<div class="col-sm-4">
                            	<input type="text" readonly value="${cMap.CARNO!''}" class="form-control" >
                            	</div>
                            </div>
                            
                            <div class="form-group">
                            	<label class="col-sm-2 control-label  required">租赁产品</label>
                            	<div class="col-sm-4">
                            	<@select type='0' defValue="${cMap.PRODUCT!''}" codeType="1036"  fieldId="product" fieldName="product"  props=" class='form-control' " />
                            	</div>
                            	<label class="col-sm-2 control-label  required">到店时间</label>
                            	<div class="col-sm-4">
                            		<input type="text" readonly name="firstTimeComing" id="firstTimeComing" maxlength="30" value="${(cMap.FIRSTTIMECOMING?string('yyyy-MM-dd'))!''}"  placeholder="到店时间" class="form-control layer-date" />
                            	</div>
                            </div> 
                            
                            <div class="form-group">
                            	<label class="col-sm-2 control-label  required">大定金支付情况</label>
                            	<div class="col-sm-4">
                            	<@select type='0' defValue="${cMap.INNDEPOSIT!''}" codeType="1052"  fieldId="innDeposit" fieldName="innDeposit"  props=" class='form-control' " />
                            	</div>
                            	<#if flag =='Y'>
                            	<label class="col-sm-2 control-label">${actConfig.activityName}</label>
                                <div class="col-sm-4 ">
                                   <input type="checkbox" name="gift" ${(cMap.GIFT?index_of("1") != -1)?string("checked onclick='return false'","")} value="1">到店礼&nbsp;&nbsp;&nbsp;&nbsp;
                                   <input type="checkbox" name="gift" value="2" ${(cMap.GIFT?index_of("2") != -1)?string("checked onclick='return false'","")}>订车礼&nbsp;&nbsp;&nbsp;&nbsp;
                                   <input value="3" name="gift" type="checkbox"${(cMap.GIFT?index_of("3") != -1)?string("checked onclick='return false'","")}>交车礼
                                </div>
                                </#if>
                            </div>
                             
                              	
		                 </div>
		            </div>
		       </div>
		       </div>
		 </div>
		 	 
		 <div class="panel panel-default table-responsive ziding-td">
		       <div class="panel-heading">追踪历史记录</div>
		                 	<table class="table table-condensed table-bordered table-striped table-hover newzdtr" style="table-layout:fixed;">
		                 		 <thead>
		                 		 	<tr class="info"> 
		                 		 		<th style="text-align: center;width: 50px;">序号</th>
		                 		 		<th style="text-align: center;width: 80px;">追踪人员</th>
		                 		 		<th style="text-align: center;width: 170px;">追踪时间</th>
		                 		 		<th style="text-align: center;">追踪详情</th>
		                 		 	</tr>
		                 		 </thead>
		                 		 <tbody>
		                 		 <tr class="info"> 
		                 		 	 <#list trace as de>
		                 		 	 <tr> 
		                 		 	 	<td style="text-align: center;">${de_index+1}</td>
		                 		 	 	<td style="text-align: center;">${de.UNAME!''}</td>
		                 		 	 	<td style="text-align: center;">${de.TDATE!''}</td>
		                 		 	 	<td style="width:100%; word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" title="${de.TITLE!''}"">${de.TITLE!''}</td>
		                 		 	 	</tr>
		                 		 	 </#list>
		                 		 </tbody>
		                 	</table>
		 </div>
		 
		 <div class="panel panel-default table-responsive ziding-td">
		       <div class="panel-heading">客服历史记录</div>
		                 	<table class="table table-condensed table-bordered table-striped table-hover newzdtr" style="table-layout:fixed;">
		                 		 <thead>
		                 		 	<tr class="info"> 
		                 		 		<th style="text-align: center;width: 50px;">序号</th>
		                 		 		<th style="text-align: center;width: 80px;">客服人员</th>
		                 		 		<th style="text-align: center;width: 170px;">操作时间</th>
		                 		 		<th style="text-align: center;">操作详情</th>
		                 		 	</tr>
		                 		 </thead>
		                 		 <tbody>
		                 		 <tr class="info"> 
		                 		 	 <#list serviceTrace as de>
		                 		 	 <tr> 
		                 		 	 	<td style="text-align: center;">${de_index+1}</td>
		                 		 	 	<td style="text-align: center;">${de.NAME!''}</td>
		                 		 	 	<td style="text-align: center;">${de.TIME!''}</td>
		                 		 	 	<td style="width:100%; word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" title="${de.CONTENT!''}"">${de.CONTENT!''}</td>
		                 		 	 	</tr>
		                 		 	 </#list>
		                 		 </tbody>
		                 	</table>
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
                                    <textarea placeholder="请录入追踪记录" class='form-control' name="t_title" id="t_title"></textarea>
                                </div>                                
                            </div>
                            
                             <div class="form-group ">
                                <input type="hidden" name="rankbefore" id = "rankbefore" value="${cMap.RANKID}">
                                <input type="hidden" id="init_rank" name="init_rank"  value="${cMap.INIT_RANK!''}"> 
                                <label class="col-sm-2 control-label">等级 <font color="#ff0000">*</font></label>
                                <div class="col-sm-4 ">                                   
                                    <@select type='1' codeType="1053" defValue="${cMap.RANKID!''}" fieldId="rank" fieldName="rank"  props=" class='form-control' onchange='changeRank(this);'" />                               
                                </div>
                                <div id="staid" class="col-sm-6">
                                     <input name="status" id = "status" value="1" <#if cMap.STATUSID = '1'> checked </#if> type="radio"> 无人接听
                                     <input name="status" id = "status" value="2" <#if cMap.STATUSID = '2'> checked </#if> type="radio"> 无法接通
                                     <input name="status" id = "status" value="3" <#if cMap.STATUSID = '3'> checked </#if> type="radio"> 关机 
                                     <input name="status" id = "status" value="4" <#if cMap.STATUSID = '4'> checked </#if> type="radio"> 停机 
                                     <input name="status" id = "status" value="5" <#if cMap.STATUSID = '5'> checked </#if> type="radio"> 挂断 
                                </div>
                                <div id="cont">
                                     <label class="col-sm-2 control-label">合同号 <font color="#ff0000">*</font></label>
                                     <input type="text" name="contractno" id ="contractno"  value="${cMap.CONTRACTNO}" onblur="codeCheck(this)" class='form-control' style="width: 300px;">
                                </div>
                            </div>
                            <div class="form-group ">
                                <div id="stait" class="col-xs-offset-2">                             
                                     <input name="reason" id = "reason1" value="1" <#if cMap.REASON = '1'> checked </#if> type="radio" onclick="getType('1')"> A 车型不匹配
                                     <input name="reason" id = "reason2" value="2" <#if cMap.REASON = '2'> checked </#if> type="radio" onclick="getType('2')"> B 金融方案不满意
                                     <input name="reason" id = "reason3" value="3" <#if cMap.REASON = '3'> checked </#if> type="radio" onclick="getType('3')"> C 风控原因  (审核未通过)
                                     <input name="reason" id = "reason4" value="4" <#if cMap.REASON = '4'> checked </#if> type="radio" onclick="getType('4')"> D 其他原因 
                                     <input type="text" name="reasonCont" id ="reasonCont"  value="${cMap.REASONCONT}" style="width:500px">
                                </div>
                             </div>
 
                             <input name="id" type="hidden" value="${cMap.ID!''}">
                             
                              <div class="form-group">
                                <label class="col-sm-2 control-label">下次跟踪时间</label>
                                <div class="col-sm-4">
                                    <input type="text" readonly name="nextdate" id="nextdate" maxlength="30"  placeholder="日期" class="form-control layer-date" >
                                </div>                                
                            </div>
                            
                             
                            <input id="fromwhere" name="fromwhere" type="hidden" value="${fromwhere!''}">
                             <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                  <input id="btn" name ="btn" type="button" value="提 交" onclick="save();" class="btn btn-sm btn-primary zd-btn-pd1"  data-toggle="modal" data-target="#myModal">
	                             	  &nbsp; &nbsp; 
	                            	  <input type="button" value="关 闭" id="closeBtn" onclick="closeWin();" class="btn btn-sm btn-primary zd-btn-pd1" >
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
		function save(){
			if($("#comform").val()=='1'){
				jQuery("#cName").focus();
			}
			if(formValidate()){
				form1.action="${contextPath}/leads/vist/saveTrace.do";
				$("#isGetCar").removeAttr('disabled');
				form1.submit();
			}
		}
		
		
		function codeCheck(obj) {
			     var saveform = $('#form1');
		         var contract = saveform.find('#contractno').val();
	             var rank =$("#rank").val();
	             contract = $.trim(contract);
				 if(contract != '' && rank=='O'){		
				 $.ajax({
					type:"post",
					url: "${contextPath}/leads/search/getExists.do?connum="+contract,  
					data:"",
					dataType:"text",
					success:function(data){
					  if(data!='0'){
					     swal({title:"",text:"合同号已存在!"});
					     $("#contractno").val('');	
					  }  
					}
			});	
			}
		}	
		function  formValidate(){
		    var connum='';
	    	var saveform = $('#form1');
	        var value = saveform.find('#t_title').val();
	        value = $.trim(value);
	        if(value== ''){
	        	swal({title:"",text:"追踪记录为空!"});
	           return false;
	        }         
	        if(value.length<5){
	        	swal({title:"",text:"追踪记录至少录入5个字符!"});
		        return false;
	        }
	        var lv= saveform.find('#status').val().length;
	        if(lv==0){
	        	swal({title:"",text:"请选状态!"});
	           return false;
	        }
	        var lv= saveform.find('#rank').val().length;
	        if(lv==0){
	        	swal({title:"",text:"请选择等级!"});
	           return false;
	        }
	        
	        //增加是否到店和是否风控必填
	        var idd= saveform.find('#idd').val();
	        if(idd==''){
	        	swal({title:"",text:"请选择是否到店!"});
	            return false;
	        }
	        var lv = saveform.find("#firstTimeComing").val();
	        if(idd=='1' && lv == ''){
	        	swal({title:"",text:"请填写到店时间!"});
	        	return false;
	        }
	        var lv= saveform.find('#ifk').val();
	        if(lv==''){
	        	swal({title:"",text:"请选择是否通过风控!"});
	            return false;
	        }
	        var lv = saveform.find('#product').val();
	        if(lv==''){
	        	swal({title:"",text:"请选择租赁产品！"});
	        	return false;
	        }
	        var lv = saveform.find('#isSubMaterial').val();
	        if(lv==''){
	        	swal({title:"",text:"请选择是否提交审核材料！"});
	        	return false;
	        }
	        var isgetcar = saveform.find('#isGetCar').val();
	        if(isgetcar==''){
	        	swal({title:"",text:"请选择是否提车！"});
	        	return false;
	        }
	        var lv = saveform.find('#will').val();
	        if(lv==''){
	        	swal({title:"",text:"请选择购车意向！"});
	        	return false;
	        }
	        
	        var lv = saveform.find('#getCarDate').val();
	        if(lv=='' && isgetcar =='1'){
	        	swal({title:"",text:"请输入提车时间！"});
	        	return false;
	        }
	        
	        var lv = saveform.find('#carNo').val();
	        if(lv=='' && isgetcar =='1'){
	        	swal({title:"",text:"请输入车牌号！"});
	        	return false;
	        }
	        
	        var rk= saveform.find('#rank').val()
	        var isCheck = $("input[name='reason']").is(':checked')
	        var checkValue = $("input[name='reason']:checked").val();
	        if(isCheck==false&&rk=='C'){
	        	swal({title:"",text:"请选择放弃原因!"});
	            return false;
	        }  
	        if(checkValue=='4'&&rk=='C'){
	           var lv= saveform.find('#reasonCont').val();
	           lv = $.trim(lv);
	           var reg = /^[\u4E00-\u9FA5]+$/; 
	           if(lv==''){
	              swal({title:"",text:"请输入具体原因!"});
	              return false;
	           }
	           if(lv.length<5){
	              swal({title:"",text:"具体原因不能少于5个汉字!"});
	              return false;
	           }
	           if (!reg.test(lv)){
	              swal({title:"",text:"只能输入汉字!"}); 
	              return false ; 
	           } 
	        }
	        var contract = saveform.find('#contractno').val();
	        var rank =$("#rank").val();
	        contract = $.trim(contract);
	        if(contract== ''&&rank=='O'){
	           swal({title:"",text:"合同号为空!"});
	           return false;
	        }
	        if(contract.length<5&&rank=='O'){
	           swal({title:"",text:"合同号不足5位!"});
	           return false;
	        }   	               
	      	return  true ;  
	      }
		jQuery(document).ready(function(){	
		
			$("#idd").on("change",function(){
				if($("#idd").val()=="1"){
					$("#comingTime").show();
					$("#firstTimeComing").attr("datatype","*");
				}else{
					$("#comingTime").hide();
					$("#firstTimeComing").removeAttr("datatype");
				}
				
			});
		  
		
			var message="${message!''}";
			if(message.length>0){
				jQuery('#messageModal').modal('show');
			}
			jQuery('#messageModal').on('hidden.bs.modal', function (e) {
				if(message.length>1){
					closeWin();
				}
			})
			
			var rank =$("#rank").val();
			//合同号
    		var isEmp = '${cMap.CONTRACTNO}'==''?true:false;
    		if(rank=='A' || rank=='B' || rank=='C'|| rank=='O' || rank==''){
    			$("input[name='status']").attr("disabled",true);
    			$("#staid").hide();
    			//$("input[name='status']").attr("disabled","disabled");
    			
    			if(!isEmp&&rank=='O'){
    			   $("input[name='contractno']").attr("disabled",true);
    			}
    		}
    		//add by cj
    		if(rank=='A' || rank=='B' || rank=='D'|| rank=='O' || rank==''){
    			$("input[name='reason']").attr("disabled",true);
    			$("#stait").hide();
    			//$("input[name='status']").attr("disabled","disabled");
    			
    			if(!isEmp&&rank=='O'){
    			   $("input[name='contractno']").attr("disabled",true);
    			}
    		}

    		if(rank=='A' || rank=='B' || rank=='D'|| rank=='C' || rank==''){
    			$("input[name='contractno']").attr("disabled",true);
    			$("#cont").hide();
    			//$("input[name='status']").attr("disabled","disabled");
    		}
    		var reason =$("#reason").val();
    		/*if(obj=='4'){
    	      $("input[name='reasonCont']").attr("disabled",false);
    	    }else{
    	      $("input[name='reasonCont']").attr("disabled",true);
    	    }*/
    		
    		//add by liangds 2016-01-19 隐藏关闭按钮
    		if($("#comform").val()=='1'){
    			$("#closeBtn").hide();
    		}
    		
    	    //ADD BY CJ 
    	    var rea = "${cMap.REASON!''}";
    	    var init_rank= "${cMap.INIT_RANK}";
    	    if(rea!='4'){
    	         $("input[name='reasonCont']").attr("value","");
    	         $("input[name='reasonCont']").attr("disabled",true);
    	    }
    	    //add by cj
    	    if((rank=='C'&&init_rank!='C')||rank=='O'){
    	        $("#btn").attr("disabled","disabled")
    	    }
    	    
    	    var getCarDate = "${cMap.GETCARDATE}";
    	    if(getCarDate != ''){
    	    	$("#carInfo").show();
    	    }else{
    	    	$("#carInfo").hide();
    	    }
    	  
		});
		
		var fromwhere=$("#fromwhere").val(); 
		if(fromwhere =="viewonly")
			$("VAR").html("");	
		
		 var end2={
    		elem:"#nextdate",
    		//format:"YYYY/MM/DD hh:mm:ss",
    		format:"YYYY-MM-DD",
    		min:laydate.now(0),
    		max:"2099-06-16 23:59:59",
    		istime:false,istoday:false,
    		choose:function(a){
    		
    		}
    	};
    	laydate(end2);
    	
    	
    	$("#rank").change("onchange", selectOnchange);
    	function selectOnchange(){
    		var rank =$("#rank").val();
    	    //合同号
    		var isEmp = '${cMap.CONTRACTNO}'==''?false:true;
    		if(rank=='A' || rank=='B' || rank==''){
    			$("input[name='status']").attr("disabled",true);
    			$("#staid").hide();
    			//ADD BY CJ 
    		    $("input[name='reason']").attr("disabled",true);
    			$("#stait").hide();
    			//$("input[name='status']").attr("disabled","disabled");
    			
    			$("input[name='contractno']").attr("disabled",true);
    			$("#cont").hide(); 	
    		} 
    		if(rank=='D'){
    			$("input[name='status']").attr("disabled",false);
    			$("#staid").show();
    			//$("input[name='status']").attr("disabled","");
    			//add by cj 
    			$("input[name='reason']").attr("disabled",true);
    			$("#stait").hide();
    			
    			$("input[name='contractno']").attr("disabled",true);
    			$("#cont").hide(); 		
    		}
    		if(rank=='C'){
    		    $("input[name='status']").attr("disabled",true);
    			$("#staid").hide();
    			//$("input[name='status']").attr("disabled","disabled"); 
    			//add by cj
    			$("input[name='reason']").attr("disabled",false);
    			$("#stait").show(); 	
    			
    			$("input[name='contractno']").attr("disabled",true);
    			$("#cont").hide(); 			
    		}		
    		if(rank=='O'){
    		    $("input[name='status']").attr("disabled",true);
    			$("#staid").hide();
    			//$("input[name='status']").attr("disabled","disabled"); 
    			//add by cj
    			$("input[name='reason']").attr("disabled",true);
    			$("#stait").hide(); 
    			
    			$("input[name='contractno']").attr("disabled",isEmp);
    			$("#cont").show();  
    		}	
    		
		}
		
    	//关闭窗口
    	function closeWin(){
    		if($("#comform").val()=='1'){
    			closeLayer();
    		}else{
    			closeTab();
    		}
    	}
    	
    	function getType(obj){
    	    if(obj=='4'){
    	      $("input[name='reasonCont']").attr("disabled",false);
    	    }else{
    	      $("input[name='reasonCont']").attr("disabled",true);
    	     
    	    }
    	}
    	
    	function showGetCarInfo(obj){
    		var isGetCar = $("#isGetCar").val();
    		if(isGetCar == '1'){
    			$("#carInfo").show();
    		}else{
    			$("#getCarDate").val('');
    			$("#carNo").val('');
    			$("#carInfo").hide();
    		}
    	}
     	
     	
     	function changeRank(obj){
     		var rank = obj.value;
     		if(rank =='O'){
     			$("#isGetCar").val('1');
     			$("#isGetCar").attr('disabled','false');
     			$("#carInfo").show();
     		}else{
     			$("#isGetCar").val('');
     			$("#isGetCar").removeAttr('disabled');
     			$("#carInfo").hide();
     		}
     	}
	</script>

	</body>
</html>