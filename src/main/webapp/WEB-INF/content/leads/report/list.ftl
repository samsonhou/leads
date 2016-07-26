<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <link href="${contextPath}/res/pub/css/demo/ziding/jquery.onoff.css" rel="stylesheet">
		 
		 <title>报表导出</title>
	</head>
	<body class="gray-bg">
	<form  method="post" name="form1" id="form1" class="form-horizontal">

		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">报表导出</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					<div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	 <div class="form-group ">
			  	 	 	 	    
                                <label class="col-sm-1 control-label" style=" margin:5px;">选择日期</label>
                                <div id="date" class="col-sm-2 ">
                                   <input type="text" readonly name="stnextdate" id="stnextdate" value="${stnextdate!''}" maxlength="30"  placeholder="开始日期" class="form-control layer-date" >                                  
                                </div>
                                <div id="date1" class="col-sm-2 ">                            
                                   <input type="text" readonly name="stnextdate1" id="stnextdate1" value="${stnextdate1!''}" maxlength="30"  placeholder="结束日期" class="form-control layer-date" >
                                </div>
                                <label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">机构</label>
                                <div class="col-sm-4">                                  
                                    <@select type='1' codeType="1034" fieldId="organId" fieldName="organId" defValue="${organId!''}" haveHead="false" paramName="organId" paramValue="${ogId}" props=" class='form-control ' " /> 
                                </div> 
                                <!-- 
                                <div  class="col-sm-2 ">
			  	 	 	 	       <input type="checkbox" <#if status!='1'> checked </#if>  onchange="getType('1')"/> 
			  	 	 	 	       <input name="status" id="status" type="hidden" value="" />
			  	 	 	 	    </div>			  	 	 	 
                                -->  
                             
                            </div>     
                               
                               
                           <div class="form-group ">
                              <div class="col-sm-4 col-sm-offset-8"></div>
                              <div class="col-sm-4 col-sm-offset-8">
			  	 	 	      <input type="button" id="but" onclick="sub()" value="查询" class="btn btn-primary btn-sm zd-btn-pd1">&nbsp;&nbsp;
                                    <input type="button" id="but" onclick="excel()" value="导 出" class="btn btn-primary btn-sm zd-btn-pd1">
                      	 	 	
			  	 	 	      </div>
                           </div>
			  	 	  </div>
			  	 	 </div>
				</div>
			</div>
			
			<div class="panel panel-default table-responsive ziding-td">
				<table class="table table-condensed table-bordered table-striped table-hover"  id="table1">
                                <thead>
                                    <tr>
		                 		 		<th style="text-align: center;">城市</th>	
		                 		 		<th style="text-align: center;">客户经理</th>	
		                 		 		<th style="text-align: center;">线索量</th>	
		                 		 		<th style="text-align: center;">首次邀约量</th>	
		                 		 		<th style="text-align: center;">首次跟进率</th>	
		                 		 		<th style="text-align: center;">战败客户</th>	
		                 		 		<th style="text-align: center;">战败率</th>	
		                 		 		<th style="text-align: center;">到店客户量</th>	
		                 		 		<th style="text-align: center;">首次邀约到店量</th>	
		                 		 		<th style="text-align: center;">通过风控   客户量</th>	
		                 		 		<th style="text-align: center;">风控通过率</th>	
		                 		 		<th style="text-align: center;">成交客户量</th>	
		                 		 		<th style="text-align: center;">成交率</th>	
		                 		 		<th style="text-align: center;">战败成交量</th>	   
		                 		 		<th style="text-align: center;">进件量</th>	      
		                 		 		<th style="text-align: center;">进件率</th>	      
		                 		 		<th style="text-align: center;">签约量</th>	                    		 	 
		                 		 		<th style="text-align: center;">签约率</th>
                                    </tr>
                                </thead>
                                <tbody id="span3">
                                	<#list list as detail>
									<tr <#if detail.person=='城市合计'> style="font-weight:bold;" </#if> class="span3tr">
                                        <td>${detail.city}</td>
                                        <td>${detail.person}</td>
                                        <td>${detail.num1}</td> 
                                        <td>${detail.num7}</td>
                                        <td>${detail.num8}</td>
                                        <td>${detail.num5}</td>
                                        <td>${detail.num9}</td>
                                        <td>${detail.num2}</td>
                                        <td>${detail.num10}</td>
                                        <td>${detail.num3}</td>
                                        <td>${detail.num11}</td>
                                        <td>${detail.num4}</td>
                                        <td>${detail.num12}</td>
                                        <td>${detail.num6}</td>
                                        <td>${detail.num13}</td>
                                        <td>${detail.num15}</td>
                                        <td>${detail.num14}</td>
                                        <td>${detail.num16}</td>                                          
                                    </tr>  
                                    </#list>
                              </tbody>
               </table>
			</div>		
		</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
	<script src="${contextPath}/res/pub/js/demo/ziding/jquery.onoff.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="http://malsup.github.io/min/jquery.blockUI.min.js"></script>
	<script>
     
jQuery.fn.rowspan = function(colIdx) { 
    return this.each(function(){
    var that;
    $('tr', this).each(function(row) {
    $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
    if(that!=null && $(this).html() == $(that).html()) {
    rowspan = $(that).attr("rowSpan");
    if (rowspan == undefined) {
    $(that).attr("rowSpan",1);
    rowspan = $(that).attr("rowSpan"); }
    rowspan = Number(rowspan)+1;
    $(that).attr("rowSpan",rowspan);
    $(this).hide();
    }else{
      that = this;
     }
   });
  });
 });
}
$(function() {
  $("#table1").rowspan(0);
}); 
    </script>
	
	
	<script type="text/javascript">
		var end1={
    		elem:"#stnextdate",
    		format:"YYYY-MM-DD",
    		min:"1970-06-16",
    		max:"2099-06-16",
    		istime:true,istoday:false
    	};
    	laydate(end1);
    	
    	var end2={
    		elem:"#stnextdate1",
    		format:"YYYY-MM-DD",
    		min:"1970-06",
    		max:"2099-06",
    		istime:true,istoday:false
    	};
    	
    	laydate(end2);
    			
    	function excel(){
    		if(formValidate()){   		
    		form1.action="${contextPath}/leads/report/exportToClient.do";
    		form1.submit();
    		}
		}
		
		/*function tests(){
		    $.blockUI({message: '<h3><img src="${contextPath}/res/pub/imgT/ajax/loading.gif" align="absmiddle"/> 处理中...</h3>'});
		    setTimeout('sub()',5000);
		}*/
		
		function sub(){
    		if(formValidate()){	
    		form1.action="${contextPath}/leads/report/getExcelData.do";
    		form1.submit();
    		}
		}
    	function  formValidate(){	
    	     //var isCheck = $("input[type='checkbox']").is(':checked')
             //var status = isCheck==true?'2':'1';	
    	     //var status = $("input[name=status]:checked").attr("id");
    	     var lv;
    	     var saveform = $('#form1');
    	     //$("#status").attr("value",status);
    	     /*if(status=='1'){
    	        lv= saveform.find('#stnextdate').val().length;
    	        if(lv==0){
    	           swal({title:"",text:"日期不能为空!" });
    	           return false;
    	      }   	    
    	     }else{
    	        lv= saveform.find('#stnextdate1').val().length;
    	        if(lv==0){
    	           swal({title:"",text:"月份不能为空!" });
    	           return false;
    	        }
    	     }*/ 
    	      lv= saveform.find('#stnextdate').val().length;
    	      if(lv==0){
    	         swal({title:"",text:"开始日期不能为空!" });
    	         return false;
    	      }
    	      lv= saveform.find('#stnextdate1').val().length;
    	      if(lv==0){
    	         swal({title:"",text:"结束日期不能为空!" });
    	         return false;
    	      }   	 
    	     
    	    return true;
    	}
    	jQuery(document).ready(function(){
    	/*
    	     var status = '${status}';
    	     if(status=='1'){
    	        $("input[name='stnextdate1']").attr("disabled",true);
    	        $("input[name='stnextdate']").attr("disabled",false);
    	      
    	        $("#date").show();
    	        $("#date1").hide();
    	     }else{
    	        $("input[name='stnextdate1']").attr("disabled",false);
    	        $("input[name='stnextdate']").attr("disabled",true);
    	      
    	        $("#date").hide();
    	        $("#date1").show();
    	     }
             $('input[type="checkbox"]').onoff();
        */
    	});
   	 		 	
    	function getType(obj){
    	var isCheck = $("input[type='checkbox']").is(':checked');
        obj = isCheck==true?'2':'1';
    	    if(obj=='1'){
    	      $("input[name='stnextdate1']").attr("disabled",true);
    	      $("input[name='stnextdate']").attr("disabled",false);
    	      
    	      $("#date1").hide();
    	      $("#date").show();
    	      
    	    }else{
    	      $("input[name='stnextdate1']").attr("disabled",false);
    	      $("input[name='stnextdate']").attr("disabled",true);
    	      
    	      $("#date").hide();
    	      $("#date1").show();
    	    }
    	}   	
	</script>
	</body>
</html>