<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>留言板</title>
	</head>
	<body class="gray-bg">
	<form  method="post" action="${contextPath}/message/queryList.do" name="form1" id="form1" class="form-horizontal">

		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
				<div class="panel-heading">留言板</div>
				<div class="panel-body" style="padding-bottom: 0px;">
					<div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	 <div class="form-group ">
                                <label class="col-sm-2 control-label" style=" margin:3px 0 0 0;">留言标题</label>
                                <div class="col-sm-2">                                  
                                	<input type="text" name="msgTitle" id="msgTitle" value="${vo.msgTitle}" class="form-control" />
                                </div> 
                            </div> 
                           <div class="form-group ">
                              <div class="col-sm-4 col-sm-offset-6">
			  	 	 	      <input type="submit" id="but" value="查询" class="btn btn-primary btn-sm zd-btn-pd1">&nbsp;&nbsp;
                              <input type="button" onclick="showMsg();" id="export" value="我要提问" class="btn btn-primary btn-sm zd-btn-pd1">
                      	 	 	
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
		                 		 		<th style="text-align: center; width:50px;">序号</th>	
		                 		 		<th style="text-align: center;">标题</th>	
		                 		 		<th style="text-align: center;">时间</th>	
                                    </tr>
                                </thead>
                                <tbody id="span3">
                                	<#list page.list as obj>
									<tr>
                                        <td style="text-align: center;">${obj_index+1}</td>
                                        <td><a href="#" onclick="reply('${obj.id}')">${obj.msgTitle}</a></td>
                                        <td>${(obj.createdTime?string("yyyy-MM-dd HH:mm:ss"))!}</td>
                                                                                 
                                    </tr>  
                                    </#list>
                              </tbody>
               </table>
			</div>
					
		</div>
		<@pages url="${contextPath}/message/queryList.do" pageCount="${page.pages}" currentPage="${page.pageNum}" />
	</form>
	
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">提问</h4>
      </div>
      <form name='saveform' id='saveform'  class="form-search" method="post" >
      <div class="modal-body" style="height:150px;"><!--高度根据表单高度相应调整-->
      <input type="hidden" name="id" id="id"/>
                   <div class="ibox-content ">
                   
                            <div class="form-group ziding-ibox-modal model_alert_1">
                                <label class="col-sm-2 control-label">问题</label>
                                <div class="col-sm-8 ">
                                	<textarea rows="3"  name="msgTitle" id="msgTitle" datatype="*" placeholder="200字内！" nullmsg="请填写！" class="form-control"></textarea>
                                </div>
                            </div>	
                              																		
					</div>
      </div>
     </form>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" id="btn_sub" class="btn btn-primary" onclick="save()">保存</button>
      </div>
    
    </div>
  </div>
</div>
	<#include "/pub/footer_res_detail.ftl"/>

	<script type="text/javascript">
    	$(document).ready(function(){
			$("#pagination").page("form1");
		});
    	function showMsg(){
    		var mymodal = $("#myModal");
    		mymodal.find("#msgTitle").val("");
    		mymodal.modal("show");
    	}
    	var msgForm=$("#saveform").Validform({
				showAllError:true
		});
		
		function save(){
			msgForm.submitForm(false,"${contextPath}/message/addMessage.do");
		}
		
		function reply(id){
			openNewTab("${contextPath}/message/doReply.do?id="+id,"留言回复");
		}
   	 		 	
	</script>
	</body>
</html>