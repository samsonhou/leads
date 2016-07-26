<!DOCTYPE html>
<html lang="en">
<head>
<#include "/pub/header_wx.ftl"/>
<title>线索详情-${Session.WX_USER_KEY_USERNAME!"未知用户"}</title>
<style type="text/css">
body, html {
	height: 100%;
	-webkit-tap-highlight-color: transparent;
	background-color: #FBF9FE;
}
.weui_label{width: 4em;}
</style>
</head>
<body ontouchstart>
	<div class="weui_tab">
		<div class="weui_navbar" style="z-index: 999999">
			<div id="tab1" class="weui_navbar_item weui_bar_item_on">客户信息</div>
			<div id="tab2" class="weui_navbar_item">追踪记录</div>
			<#if agentType??>
				<#if agentType!='3'>
			 <div id="tab3" class="weui_navbar_item">追踪回复</div> 
			    </#if>
			</#if>
		</div>
		<div class="weui_tab_bd">
			<div id="tab1_item">
				<div class="weui_cells" style="margin-top: 1px;">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>客户姓名</p>
						</div>
						<div class="weui_cell_ft">${cMap.NAME!''}</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>客户电话</p>
						</div>
						<div class="weui_cell_ft">${cMap.TEL!''}</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>线索来源</p>
						</div>
						<div class="weui_cell_ft">${cMap.FROMTYPE!''}</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>业务大类</p>
						</div>
						<div class="weui_cell_ft">${cMap.BIG_PID!''}</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>业务小类</p>
						</div>
						<div class="weui_cell_ft">${cMap.SMALL_PID!''}</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>是否加急</p>
						</div>
						<div class="weui_cell_ft">${cMap.IFURGENT!''}</div>
					</div>
				</div>
				<div class="weui_cells_title">咨询详情</div>
				<div class="weui_cells weui_cells_form">
		            <div class="weui_cell">
		                <div class="weui_cell_bd weui_cell_primary">
		                    <textarea class="weui_textarea" readonly style="color: #888;font-size: 14px;" readonly="readonly" rows="2">${cMap.TITLE!''}</textarea>
		                </div>
		            </div>
		        </div>
				<div class="weui_btn_area">
					<#if agentType??>
						<#if agentType=='1'>
							<a class="weui_btn  weui_btn_default" href="${contextPath}/exchange/wx/urge/queryList.do">返 回</a>
						</#if>
						<#if agentType=='2'>
							<a class="weui_btn  weui_btn_default" href="${contextPath}/exchange/wx/vist/queryList.do">返 回</a>
						</#if>
						<#if agentType=='4'>
							<a class="weui_btn  weui_btn_default" href="${contextPath}/exchange/wx/sign/index.do">返 回</a>
						</#if>
					</#if>
				</div>
			</div>
			<div id="tab2_item" style="display: none;">
		        <div class="weui_panel weui_panel_access" style="margin-top: 1px;">
		            <div class="weui_panel_bd">
		            <#list trace as de>
		                <div class="weui_media_box weui_media_text">
		                    <h5 class="weui_media_title">${de.UNAME!''} ${de.TDATE!''}</h5>
		                    <p class="weui_media_desc">${de.TITLE!''}</p>
		                </div>
		             </#list>
		            </div>
	        	</div>
			</div>
			<div id="tab3_item" style="display: none;">
				<form action="${contextPath}/exchange/wx/urge/queryList.do" method="post" name="form1">
				<input type="hidden" name="agentType" id="agentType" value="${agentType}">
				<div class="weui_cells weui_cells_form" style="margin-top: 1px;">
		            <div class="weui_cell">
		                <div class="weui_cell_hd"><label class="weui_label">下次时间</label></div>
		                <div class="weui_cell_bd weui_cell_primary">
		                    <input class="weui_input" name="nextdate" id="nextdate"  style="margin-left: 15px;" type="date">
		                </div>
		                <div class="weui_cell_ft">
		                    <i class="weui_icon_waiting_circle"></i>
		                </div>
		            </div>
		            <div class="weui_cell weui_cell_select weui_select_after">
						<div class="weui_cell_hd">是否到店</div>
						<div class="weui_cell_bd weui_cell_primary">
						    <#if cMap.IDD = '1'>
                            	<@select type='0' defValue="${cMap.IDD!''}" codeType="1000"  fieldId="idd" fieldName="idd"  props=" disabled='disabled' class='weui_select' " />
                            </#if>
                            <#if cMap.IDD = '0'>
                               <@select type='0' defValue="${cMap.IDD!''}" codeType="1000"  fieldId="idd" fieldName="idd"  props=" class='weui_select' " />
                            </#if>
                            <#if cMap.IDD = ''>
                               <@select type='0' defValue="${cMap.IDD!''}" codeType="1000"  fieldId="idd" fieldName="idd"  props=" class='weui_select' " />
                            </#if> 
						</div>
					</div>
					<div class="weui_cell weui_cell_select weui_select_after">
						<div class="weui_cell_hd">通过风控</div>
						<div class="weui_cell_bd weui_cell_primary">
						    <@select type='0' defValue="${cMap.IFK!''}" codeType="1000"  fieldId="ifk" fieldName="ifk"  props=" class='weui_select' " /> 
						</div>
					</div>
					<div class="weui_cell weui_cell_select weui_select_after">
		            <input type="hidden" name="rankbefore" id = "rankbefore" value="${cMap.RANKID}">
						<div class="weui_cell_hd">线索等级</div>
						<div class="weui_cell_bd weui_cell_primary">
						    <@select type='0' codeType="1026" defValue="${cMap.RANKID!''}" fieldId="rank" fieldName="rank"  props=" class='weui_select' " /> 
						</div>
					</div>
					<div id="staid" class="weui_cell weui_cell_select weui_select_after">
						<div class="weui_cell_hd">电话原因</div>
						<div class="weui_cell_bd weui_cell_primary">
							<select name="status" id = "status" class="weui_select">
								<option value="1" <#if cMap.STATUSID = '1'> selected </#if> >无人接听</option>
								<option value="2" <#if cMap.STATUSID = '2'> selected </#if> >无法接通</option>
								<option value="3" <#if cMap.STATUSID = '3'> selected </#if> >关机</option>
								<option value="4" <#if cMap.STATUSID = '4'> selected </#if> >停机 </option>
								<option value="5" <#if cMap.STATUSID = '5'> selected </#if> >挂断</option>
							</select>
						</div>
					</div>
					<div id="cont" class="weui_cell">
		                <div class="weui_cell_hd"><label class="weui_label">合同编号</label></div>
		                <div class="weui_cell_bd weui_cell_primary">
		                    <input class="weui_input" name="contractno" id ="contractno"  value="${cMap.CONTRACTNO}" onblur="codeCheck(this)"  style="margin-left: 15px;" type="text" placeholder="成单时需录入合同编号">
		                </div>
		            </div>
		            <div  id="stait" class="weui_cell weui_cell_select weui_select_after">
						<div class="weui_cell_hd">放弃原因</div>
						<div class="weui_cell_bd weui_cell_primary">
							<select name="reason" id = "reason" class="weui_select">
								<option value="1" <#if cMap.REASON = '1'> selected </#if> >A车型不匹配</option>
								<option value="2" <#if cMap.REASON = '2'> selected </#if> >B金融方案不满意</option>
								<option value="3" <#if cMap.REASON = '3'> selected </#if> >C风控原因 </option>
								<option value="4" <#if cMap.REASON = '4'> selected </#if> >其他原因 </option>
							</select>
						</div>
					</div>
					
					<input name="id" type="hidden" value="${cMap.ID!''}">
					<input type="hidden" id="comform" name="comform"  value="${comform}">
					<input type="hidden" id="urgeid" name="urgeid"  value="${urgeid}"> <!--催促ID -->
					
					<div id="stait1" class="weui_cell">
		                <div class="weui_cell_hd"><label class="weui_label">其它原因</label></div>
		                <div class="weui_cell_bd weui_cell_primary">
		                    <input class="weui_input" name="reasonCont" id ="reasonCont"  value="${cMap.REASONCONT}" style="margin-left: 15px;" type="text" placeholder="其它原因">
		                </div>
		            </div>
		        </div>
		        <div class="weui_cells_title">追踪记录 </div>
		        <div class="weui_cells weui_cells_form">
		            <div class="weui_cell">
		                <div class="weui_cell_bd weui_cell_primary">
		                    <textarea name="t_title" id="t_title" class="weui_textarea" placeholder="请录入追踪记录" rows="3"></textarea>
		                </div>
		            </div>
		        </div>
		        <div class="weui_btn_area" style="margin-top: 10px;">
		            <a class="weui_btn weui_btn_primary" id="submitInfo" href="javascript:;">提 交</a>
		            <#if agentType??>
						<#if agentType=='1'>
							<a class="weui_btn  weui_btn_default" href="${contextPath}/exchange/wx/urge/queryList.do">返 回</a>
						</#if>
						<#if agentType=='2'>
							<a class="weui_btn  weui_btn_default" href="${contextPath}/exchange/wx/vist/queryList.do">返 回</a>
						</#if>
						<#if agentType=='4'>
							<a class="weui_btn  weui_btn_default" href="${contextPath}/exchange/wx/sign/index.do">返 回</a>
						</#if>
					</#if>
		        </div>
		        </form>
			</div>
		</div>
	</div>
		<div id="loadingToast" class="weui_loading_toast"
		style="display: none;">
		<div class="weui_mask_transparent"></div>
		<div class="weui_toast">
			<div class="weui_loading">
				<div class="weui_loading_leaf weui_loading_leaf_0"></div>
				<div class="weui_loading_leaf weui_loading_leaf_1"></div>
				<div class="weui_loading_leaf weui_loading_leaf_2"></div>
				<div class="weui_loading_leaf weui_loading_leaf_3"></div>
				<div class="weui_loading_leaf weui_loading_leaf_4"></div>
				<div class="weui_loading_leaf weui_loading_leaf_5"></div>
				<div class="weui_loading_leaf weui_loading_leaf_6"></div>
				<div class="weui_loading_leaf weui_loading_leaf_7"></div>
				<div class="weui_loading_leaf weui_loading_leaf_8"></div>
				<div class="weui_loading_leaf weui_loading_leaf_9"></div>
				<div class="weui_loading_leaf weui_loading_leaf_10"></div>
				<div class="weui_loading_leaf weui_loading_leaf_11"></div>
			</div>
			<p class="weui_toast_content">数据提交中</p>
		</div>
	</div>
	<div class="weui_dialog_alert" id="dialog" style="display: none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd"><strong class="weui_dialog_title">提示信息</strong></div>
            <div class="weui_dialog_bd" id="dialogMsg"></div>
            <div class="weui_dialog_ft">
                <a href="javascript:closeDialog();" class="weui_btn_dialog primary">确定</a>
            </div>
        </div>
    </div>
	
	<#include "/pub/footer_wx.ftl"/>
	<script type="text/javascript">
		Zepto(function($) {
			Zepto(".weui_navbar_item").on("click", function() {
				Zepto(".weui_navbar_item").removeClass("weui_bar_item_on");
				Zepto(this).addClass("weui_bar_item_on");
				Zepto(".weui_tab_bd").children().hide();
				var tid = Zepto(this).attr("id");
				Zepto("#" + tid + "_item").show();
			});
			Zepto("#submitInfo").on("click",function(){
				//Zepto(this).addClass("weui_btn_disabled");
				//Zepto("#loadingToast").show();
				//setTimeout("submitInfo()", 300);
				submitInfo();
			});
			
			var message="${message!''}";
			if(message.length>0){
				openDialog(message);
			}
			
			
			var rank =$("#rank").val();
			if(rank=='C'||rank=='O'){
    	        //$("#submitInfo").attr("disabled","disabled")
				$("#submitInfo").hide(); 
    	    }
			//合同号
    		var isEmp = '${cMap.CONTRACTNO}'==''?true:false;
    		if(rank=='A' || rank=='B' || rank=='C'|| rank=='O' || rank==''){
    			$("input[name='status']").prop("disabled",true);
    			$("#staid").hide();
    			//$("input[name='status']").attr("disabled","disabled");
    			
    			if(!isEmp&&rank=='O'){
    			   $("input[name='contractno']").prop("disabled",true);
    			}
    		}
    		//add by cj
    		if(rank=='A' || rank=='B' || rank=='D'|| rank=='O' || rank==''){
    			$("input[name='reason']").prop("disabled",true);
    			$("#stait").hide();
    			$("#stait1").hide();
    			//$("input[name='status']").attr("disabled","disabled");
    			
    			if(!isEmp&&rank=='O'){
    			   $("input[name='contractno']").prop("disabled",true);
    			}
    		}

    		if(rank=='A' || rank=='B' || rank=='D'|| rank=='C' || rank==''){
    			$("input[name='contractno']").prop("disabled",true);
    			$("#cont").hide();
    			//$("input[name='status']").attr("disabled","disabled");
    		}
    		var reason =$("#reason").val();

    		
    		//add by liangds 2016-01-19 隐藏关闭按钮
    		if($("#comform").val()=='1'){
    			$("#closeBtn").hide();
    		}
    		
    	    //ADD BY CJ 
    	    var rea = "${cMap.REASON!''}";
    	    if(rea!='4'){
    	         $("input[name='reasonCont']").prop("value","");
    	         $("input[name='reasonCont']").prop("disabled",true);
    	    }
		})
		
		function submitInfo(){
			if(formValidate()){
				form1.action="${contextPath}/exchange/wx/urge/submitInfo.do";
				form1.submit();
			}
		}
		function  formValidate(){
	        var value = $('#t_title').val();
	        value = $.trim(value);
	        if(value== ''){
	        	return openDialog("追踪记录为空!");
	        }         
	        if(value.length<5){
	        	return openDialog("追踪记录至少录入5个字符!");
	        }
	        var lv= $('#status').val().length;
	        if(lv==0){
	        	return openDialog("请选状态!");
	        }
	        var lv= $('#rank').val().length;
	        if(lv==0){
	        	return openDialog("请选择等级!");
	        }
	        
	        var contract = $('#contractno').val();
	        var rank =$("#rank").val();
	        contract = $.trim(contract);
	        if(contract== ''&&rank=='O'){
	        	return openDialog("合同号为空!");
	        } 
	        if(contract.length<5&&rank=='O'){
	        	return openDialog("合同号不足5位!");
	        }           
	      	return  true ;  
	     }
		
		$("#rank").on("change", selectOnchange);
    	function selectOnchange(){
    		var rank =Zepto("#rank").val();
    	    //合同号
    		var isEmp = '${cMap.CONTRACTNO}'==''?false:true;
    		if(rank=='A' || rank=='B' || rank==''){
    			$("input[name='status']").prop("disabled",true);
    			$("#staid").hide();
    			//ADD BY CJ 
    		    $("input[name='reason']").prop("disabled",true);
    			$("#stait").hide();
    			$("#stait1").hide();
    			//$("input[name='status']").attr("disabled","disabled");
    			
    			
    			$("input[name='contractno']").prop("disabled",true);
    			$("#cont").hide();
    		} 
    		if(rank=='D'){
    			$("input[name='status']").prop("disabled",false);
    			$("#staid").show();
    			//$("input[name='status']").attr("disabled","");
    			//add by cj 
    			$("input[name='reason']").prop("disabled",true);
    			$("#stait").hide();
    			$("#stait1").hide();
    			
    			$("input[name='contractno']").prop("disabled",true);
    			$("#cont").hide();
    		}
    		if(rank=='C'){
    		    $("input[name='status']").prop("disabled",true);
    			$("#staid").hide();
    			//$("input[name='status']").attr("disabled","disabled"); 
    			//add by cj
    			$("input[name='reason']").prop("disabled",false);
    			$("#stait").show(); 
    			$("#stait1").show();
    			
    			$("input[name='contractno']").prop("disabled",true);
    			$("#cont").hide();
    		}		
    		if(rank=='O'){
    		    $("input[name='status']").prop("disabled",true);
    			$("#staid").hide();
    			//$("input[name='status']").attr("disabled","disabled"); 
    			//add by cj
    			$("input[name='reason']").prop("disabled",true);
    			$("#stait").hide(); 
    			$("#stait1").hide();
    			
    			$("input[name='contractno']").prop("disabled",isEmp);
    			$("#cont").show();
    		}	
		}
    	
    	$("#reason").on("change", getType);
    	function getType(){
    		var obj =Zepto("#reason").val();
    	    if(obj=='4'){
    	      $("input[name='reasonCont']").prop("disabled",false);
    	    }else{
    	      $("input[name='reasonCont']").prop("disabled",true);    	     
    	    }
    	}
    	
    	function openDialog(msg){
			Zepto("#dialogMsg").text(msg);
			Zepto("#dialog").show();
			return false;
		}
		function closeDialog(){
			Zepto("#dialog").hide();
		}

		function codeCheck(obj) {
	         var contract = $('#contractno').val();
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
				     openDialog("合同号已存在!");
				     $("#contractno").val('');	
				  }  
				}
		});	
		}
	}
	</script>
</body>
</html>