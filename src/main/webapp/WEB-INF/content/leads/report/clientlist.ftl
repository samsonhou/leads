<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>来源报表</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/report/queryList.do" method="post" name="form1" id="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">来源报表查询  &nbsp;&nbsp;&nbsp;线索量&nbsp;${count!'0'}</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					<div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-1 control-label" style=" margin:5px;">起止日期</label>
                                <div class="col-sm-2 ">
                                   <input type="text" readonly name="stnextdate" id="stnextdate" value="${stnextdate!''}" maxlength="30"  placeholder="开始日期" class="form-control layer-date" >
                                </div>
                                <div class="col-sm-2">
                                    <input type="text" readonly name="nextdate" id="nextdate" value="${nextdate!''}" maxlength="30"  placeholder="结束日期" class="form-control layer-date" >
                                </div>
                                <div class="col-sm-2"></div>
								<div class="col-sm-3">
								<input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                   &nbsp;&nbsp;&nbsp;<input type="button" onclick="excel()" value="导 出" class="btn btn-primary btn-sm zd-btn-pd1">
								</div>
                            </div>
                            
                            <div class="form-group ">
                                <label class="col-sm-1 control-label" style=" margin:3px 0 0 0;">机构</label>
                                <div class="col-sm-6">                                  
                                    <@select type='1' codeType="1034" fieldId="organId" fieldName="organId" defValue="${organId!''}" haveHead="false" paramName="organId" paramValue="${ogId}" props=" class='form-control' " />
                                </div>
								<label class="col-sm-1 control-label" style=" margin:3px 0 0 0;">业务类型</label>
                                <div class="col-sm-3">
                                    <@select type='1' codeType="1021" defValue="${samllPid!'-1'}" fieldId="samllPid" fieldName="samllPid"  paramName="pid" paramValue="1" props=" class='form-control' " />
                                </div>
                            </div>	  	 	 	 	
			  	 	 	 </div>
			  	 	 </div>
			  	 	 </div>
				</div>
			</div>
			
			<div class="panel panel-default table-responsive ziding-td">
				<table class="table table-condensed table-bordered table-striped table-hover" id="table1">
                                <thead>
                                    <tr>
                                    <#list head as head>
		                 		 		<th style="text-align: center;">${head.NAME!''}</th>
		                 		 	 </#list>		                 		 	 
                                    </tr>
                                </thead>
                                <tbody id="span3">
                                	<#list detail as detail>
									<tr class="span3tr">
                                        <td>${detail.NAME}</td>
                                        <td>${detail.TITLE}</td>
                                        <td>${detail.MADD}</td> 
                                        <td>${detail.M1}</td>
                                        <td>${detail.M2}</td>
                                        <td>${detail.M3}</td>
                                        <td>${detail.M4}</td>
                                        <td>${detail.M5}</td>
                                        <td>${detail.M6}</td>
                                        <td>${detail.M7}</td>
                                        <td>${detail.M8}</td>
                                        <td>${detail.M9}</td>
                                        <td>${detail.M10}</td>
                                        <td>${detail.M11}</td>
                                        <td>${detail.M12}</td>
                                        <td>${detail.M13}</td>
                                        <td>${detail.M14}</td>
                                        <td>${detail.M15}</td>
                                        <td>${detail.M16}</td>
                                        <td>${detail.M17}</td>
                                        <td>${detail.M18}</td>
                                        <td>${detail.M19}</td> 
                                        <td>${detail.M20}</td> 
                                        <td>${detail.M21}</td> 
                                        <td>${detail.M22}</td>
                                        <td>${detail.M23}</td>
                                        <td>${detail.M24}</td>
                                        <td>${detail.M25}</td>
                                        <td>${detail.M26}</td> 
                                        <td>${detail.M27}</td>
                                        <td>${detail.M28}</td>  
                                        <td>${detail.M29}</td>                                     
                                        <td>${detail.M30}</td>                                     
                                        <td>${detail.M31}</td>                                     
                                        <td>${detail.M32}</td>                                     
                                        <td>${detail.M33}</td>                                     
                                        <td>${detail.M34}</td>                                     
                                        <td>${detail.M35}</td>                                     
                                        <td>${detail.M36}</td>                                     
                                        <td>${detail.M37}</td>                                     
                                        <td>${detail.M38}</td>                                     
                                        <td>${detail.M39}</td>                                     
                                        <td>${detail.M40}</td>                                     
                                        <td>${detail.M41}</td>                                     
                                    </tr>  
                                    </#list>
                              </tbody>
                            </table>
			</div>
			
		</div>
	</form>
	<#include "/pub/footer_res_detail.ftl"/>
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
	
	
		function search(){
			if(formValidate()){
			form1.action="${contextPath}/leads/report/queryList.do";
			form1.submit();
			}
		}
		
		var end1={
    		elem:"#stnextdate",
    		//format:"YYYY/MM/DD hh:mm:ss",
    		format:"YYYY-MM-DD hh:mm:ss",
    		min:"1970-06-16 23:59:59",
    		max:"2099-06-16 23:59:59",
    		istime:true,istoday:false,
    		choose:function(a){
    			end2.min=a
    		}
    	};
    	laydate(end1);
		var end2={
    		elem:"#nextdate",
    		//format:"YYYY/MM/DD hh:mm:ss",
    		format:"YYYY-MM-DD hh:mm:ss",
    		min:"1970-06-16 23:59:59",
    		max:"2099-06-16 23:59:59",
    		istime:true,istoday:false,
    		choose:function(a){
    			end1.max=a
    		}
    	};
    	laydate(end2);
    	function excel(){
    		if(formValidate()){
    		form1.action="${contextPath}/leads/report/excel/xlsx.do";
    		form1.submit();
    		}
			
		}
    	function  formValidate(){
    		 var saveform = $('#form1');
    		 var lv= saveform.find('#stnextdate').val().length;
    	     if(lv==0){
    	       swal({title:"",text:"开始日期不能为空!" });
    	       return false;
    	      }
    	    lv= saveform.find('#nextdate').val().length;
    	    if(lv==0){
	        	swal({title:"",text:"结束日期不能为空!" });
	           return false;
	         }
    	    var _organId=$('#organId').val();
    	    if(_organId=='' || _organId.length<1){
    	    	swal({title:"",text:"请选择机构!" });
 	           return false;
    	    }
    	    return true;
    	}
    	jQuery(document).ready(function(){
    		organ.initOrgan("_organTag_");
    	});
	</script>
	</body>
</html>