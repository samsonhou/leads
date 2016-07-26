<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
		 <title>合作商管理</title>
	</head>
	<body class="gray-bg">
	<form action="" method="post" name="form1" class="form-horizontal">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
            <div class="panel-heading">合作商查询</div>
                <div class="panel-body">
                    <div class="col-sm-12">
                    <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
                            <div class="form-group ">
                                <label class="col-sm-2 control-label">合作商名称</label>
                                <div class="col-sm-4">
                               		<input type="text" name="partnerName" value="${partnerName}" class="form-control"/>
                                </div>
                            </div>
                            
                            
                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                   <input type="button" onclick="search();" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                                   &nbsp; &nbsp; 
	                                   &nbsp; &nbsp; 
	                                   <input type="button" onclick="increased();" value="新 增 " class="btn btn-primary btn-sm zd-btn-pd1">
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
                                        <th style="width:50px;text-align: center;">序号</th>
                                        <th style="text-align: center;">合作商名称</th>
                                        <th style="text-align: center;">合作商编号</th>
                                        <th style="text-align: center;">联系人</th>
                                        <th style="text-align: center;">电话</th>
                                        <th style="text-align: center;">券类型</th>
                                        <th style="text-align: center;">服务内容</th>
                                        <th style="width: 50px; text-align: center;">操作</th>
                                    </tr>
                                </thead>
                                
                                
                                <tbody>
                                	<#list page.list as obj>
									<tr>
                                        <td style="text-align: center;">${obj_index+1}</td>
                                       	<td style="text-align: center;">${obj.partnerName}</td>
                                        <td style="text-align: left;">${obj.partnerCode}</td>
                                        <td style="text-align: left;">${obj.contact}</td>
                                        <td style="text-align: center;">${obj.tel}</td>
                                        <td style="text-align: center;">${obj.couponType}</td>
                                        <td style="text-align: center;">${obj.service}</td>
                                        <td style="text-align: center;" >
											<a class="btn btn-primary btn-xs" onclick="doEditorPartner('${obj.id}')">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;
											<a class="btn btn-primary btn-xs" onclick="addCoupon('${obj.id}')">添加券</a>&nbsp;&nbsp;&nbsp;&nbsp;
											<a class="btn btn-primary btn-xs" onclick="queryCoupons('${obj.id}')">删除券</a>
                                    	</td>
                                    </tr>  
                                    </#list>
                              </tbody>
                            </table>
     					   </div>
                  <@pages url="${contextPath}/coupon/partner/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
              </div>
	</form>
	
	
	
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document" style="width:1000px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新增合作商</h4>
      </div>
      <form name='saveform' id='saveform'  class="form-search" method="post" >
      <div class="modal-body" style="height:250px;"><!--高度根据表单高度相应调整-->
      <input type="hidden" name="id" id="id"/>
                   <div class="ibox-content ">
                   
                            <div class="form-group ziding-ibox-modal model_alert_1">
                                <label class="col-sm-2 control-label">合作商名称</label>
                                <div class="col-sm-4 ">
                                	<input type="text" name="partnerName" id="partnerName" datatype="*" nullmsg="请填写合作商名称！" class="form-control" />
                                </div>
                                <label class="col-sm-2 control-label">合作商编号</label>
                                <div class="col-sm-4 ">
                                	<input type="text" name="partnerCode" id="partnerCode" placeHolder="无需填写，自动生成" readOnly class="form-control"/>
                                </div>
                            </div>	
                            
                            <div class="form-group ziding-ibox-modal model_alert_1">
                            	<label class="col-sm-2 control-label">联系人</label>
                                <div class="col-sm-4 ">
                                	<input type="text" name="contact" id="contact" class="form-control" datatype="*" nullmsg="请填写联系人！" />
                                </div>
                                <label class="col-sm-2 control-label">电话</label>
                                <div class="col-sm-4 ">
                                	<input type="text" name="tel" id="tel" datatype="*" nullmsg="请填写电话！" class="form-control" />
                                </div>
                            </div>
                            	
                            <div class="form-group ziding-ibox-modal">
                                <label class="col-sm-2 control-label  required">所在省</label>
                                <div class="col-sm-2">
                                    <@select type="1" codeType='1049' fieldId='province' fieldName='province'  props=" class='form-control m-b' datatype='*' nullmsg='请选择所在省！' onchange='getCityList(this.value)' "  />
                                </div>
                                <div class="col-sm-2 ">
                                    <select id="city" name="city" class='form-control ' datatype="*" nullmsg="请选择所在市！">
										<option value="">请选择</option>
									</select>
									<input id="cityTmp" type="hidden"/>
                                </div>
                                <div class="col-sm-6 ">
                                    <input type="text" name="address" id="address" class="form-control" datatype="*" nullmsg="请填写具体地址！">
                                </div>
                            </div>				
                            <div class="form-group ziding-ibox-modal">
                              <label class="col-sm-2 control-label  required">服务内容</label>
                                <div class="col-sm-10">
                                 <textarea rows="3" id="service" name="service" class="form-control"  placeholder="服务内容" datatype="*" nullmsg="请填写服务内容！"></textarea>
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

<!-- Modal -->
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document" style="width:1000px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加合作商赠券</h4>
      </div>
      <form name='saveform1' id='saveform1'  class="form-search" method="post" >
      <div class="modal-body" style="height:250px;"><!--高度根据表单高度相应调整-->
      <input type="hidden" name="partnerId" id="partnerId"/>
                   <div class="ibox-content ">
                   
                            <div class="form-group ziding-ibox-modal model_alert_1">
                                <label class="col-sm-2 control-label">合作商名称</label>
                                <div class="col-sm-4 ">
                                	<input type="text" name="partnerName" id="partnerName" readOnly class="form-control" />
                                </div>
                                <label class="col-sm-2 control-label">合作商编号</label>
                                <div class="col-sm-4 ">
                                	<input type="text" name="partnerCode" id="partnerCode" readOnly class="form-control"/>
                                </div>
                            </div>	
                            
                            <div class="form-group ziding-ibox-modal model_alert_1">
                            	<label class="col-sm-2 control-label">赠券名称</label>
                                <div class="col-sm-4 ">
                                	<input type="text" name="couponName" id="couponName" class="form-control" datatype="*" nullmsg="请填写赠券名称！" />
                                </div>
                            	<input type="hidden" id="status" name="status" value="1">
                            </div>
                              																		
					</div>
      </div>
     </form>
    
      <div class="modal-footer">
      	<input type="hidden" data-seq='00' value="00" id="_defOrganId_">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="saveCoupon();">保存</button>
      </div>
    
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document" style="width:1000px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">删除合作商赠券</h4>
      </div>
      <form name='saveform2' id='saveform2'  class="form-search" method="post" >
      <div class="modal-body"><!--高度根据表单高度相应调整-->
      <input type="hidden" name="partnerId" id="partnerId"/>
                   
                           <div class="panel panel-default table-responsive ziding-td">
				 <table class="table table-condensed table-bordered table-striped " >
                                <thead>
                                    <tr>
                                        <th style="width:50px;text-align: center;">序号</th>
                                        <th style="text-align: center;">合作商名称</th>
                                        <th style="text-align: center;">券类型</th>
                                        <th style="width: 50px; text-align: center;">删除</th>
                                    </tr>
                                </thead>
                                
                                
                                <tbody id="tbody">
                              </tbody>
                            </table>
     					   </div>
                              																		
      </div>
     </form>
    
      <div class="modal-footer">
      	<input type="hidden" data-seq='00' value="00" id="_defOrganId_">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    
    </div>
  </div>
</div>

    <#include "/pub/footer_res_detail.ftl"/>
	
	<script type="text/javascript">
	// 级联读取城市
	function getCityList(id) {
		if(id == ""){
			$("#city").html("<option>请选择</option>");
			return;
		}
		var params = {
			id : id
		};
		$.ajax({
			url : "${contextPath}/coupon/partner/queryCity.do",
			data : params,
			async : false,
			dataType : 'json',
			success : function(data) {
				$("#city").html('');
				for (var i = 0; i < data.length; i++) {
					$("#city").append(
							"<option value='" + data[i].id + "'>"
									+ data[i].cityName + "</option>");
				}
			}
	
		});
	}
		var subForm=$("#saveform").Validform({
				showAllError:true
		});
		
		var couponForm=$("#saveform1").Validform({
				showAllError:true
		});
		
		
		
			
		function search(){
			form1.action = "${contextPath}/coupon/partner/queryList.do";
			form1.submit();
		}
		function save(){
			subForm.submitForm(false,"${contextPath}/coupon/partner/savePartner.do");
		}
		function saveCoupon(){
			couponForm.submitForm(false,"${contextPath}/coupon/partner/saveCouponType.do");
		}
		
		

		function increased() {  
            var saveform = $('#saveform');
             saveform.find('#id').val("");
             saveform.find('#partnerName').val("");
             saveform.find('#partnerCode').val("");
             saveform.find('#contact').val("");
             saveform.find('#tel').val("");
             saveform.find('#service').val("");
             saveform.find('#address').val("");
             saveform.find('#province').val("");
             saveform.find('#cityTmp').val("");
             saveform.find('#city').html("");
            var myModal = $('#myModal');
            myModal.find('.modal-title').text('新增合作商');
            myModal.modal('show');  //打开新增界面
            
            } 
            
      
	    function doEditorPartner(id) {  
	    	$.ajax({  
	            type: "POST",  
	            url: "${contextPath}/coupon/partner/editVO.do",  
	            data: "id=" + id,
	            success: function(response){
	             var saveform = $('#saveform');
	             saveform.find('#id').val(response.id);
	             saveform.find('#partnerName').val(response.partnerName);
	             saveform.find('#partnerCode').val(response.partnerCode);
	             saveform.find('#contact').val(response.contact);
	             saveform.find('#tel').val(response.tel);
	             saveform.find('#service').val(response.service);
	             saveform.find('#address').val(response.address);
	             saveform.find('#province').val(response.province);
	             saveform.find('#cityTmp').val(response.city);
	             cityListInit();
	             var myModal = $('#myModal');
	             myModal.find('.modal-title').text('编辑合作商');
	             myModal.modal('toggle');  //打开编辑界面
	            },  
	            error: function(e){  
	            alert('Error: ' + e);  
	            }  
	          });  
         }
         
	    function addCoupon(id) {  
	    	$.ajax({  
	            type: "POST",  
	            url: "${contextPath}/coupon/partner/editVO.do",  
	            data: "id=" + id,
	            success: function(response){
	             var saveform = $('#saveform1');
	             saveform.find('#partnerId').val(response.id);
	             saveform.find('#partnerName').val(response.partnerName);
	             saveform.find('#partnerCode').val(response.partnerCode);
	             cityListInit();
	             var myModal = $('#myModal1');
	             myModal.find('.modal-title').text('编辑合作商');
	             myModal.modal('toggle');  //打开编辑界面
	            },  
	            error: function(e){  
	            alert('Error: ' + e);  
	            }  
	          });  
         }
         
	    function queryCoupons(id) {  
		    var saveform = $('#saveform2');
		    saveform.find('#tbody').html("");
	    	$.ajax({  
	            type: "POST",  
	            url: "${contextPath}/coupon/partner/queryCoupons.do",  
	            data: "id=" + id,
	            success: function(response){
	            	
		             for(var i=0;i<response.length;i++){
		             	saveform.find('#tbody').append("<tr><td>"+(i+1)+"</td><td>"+response[i].partnerName+"</td><td>"+response[i].couponName+"</td><td>"
		             	+"<a class='btn btn-primary btn-xs' onclick='deleteCoupons("+response[i].id+")'>删除券</a>"+"</td></tr>")
		             	
		             }
	             var myModal = $('#myModal2');
	             myModal.find('.modal-title').text('删除赠券');
	             myModal.modal('toggle');  //打开编辑界面
	            },  
	            error: function(e){  
	            alert('Error: ' + e);  
	            }  
	          });  
         }

		jQuery(document).ready(function(){
			//cityListInit();
			jQuery("#pagination").page("form1");
		});
		
		function cityListInit() {
		var provinceId = $("#province").val();
			if (provinceId != '') {
				getCityList(provinceId);
				var citytmp = $('#cityTmp').val();
				$("#city").val(citytmp);
			}
		}
		
		
		function deleteCoupons(id){
			saveform2.action = "${contextPath}/coupon/partner/deleteCoupons.do?id="+id;
			saveform2.submit();
		}
		
	</script>
	</body>	
</html>