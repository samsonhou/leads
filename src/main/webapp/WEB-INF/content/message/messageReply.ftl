<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		 <title>留言回复</title>
	</head>
	<body class="gray-bg">
	<div class="panel panel-primary">
       <div class="panel-heading">问题</div>
       	  <div class="panel-body">
       	  <div class="row">
       	  	<div class="col-sm-4">${list[0].msgUserName}</div>
       	  	<div class="col-sm-6">${list[0].msgTitle}</div>
       	  	<div class="col-sm-2">${(list[0].createdTime?string("yyyy-MM-dd HH:mm:ss"))!}</div>
       	  </div>
       	  </div>
    </div>
    <form id="form1" name="form1" method="post" class="form-horizontal" action="">
	<div class="panel panel-primary">
       <div class="panel-heading">回复记录</div>
       	  <div class="panel-body">
       	  	
	       	  <table class="table">
	       	  	<tbody>
	       	  		<#list list[1].list as obj>
	       	  			<tr class="${(((obj_index+1) % 2)=='0')?string('active','success')}">
	       	  				<td>${obj.answerUserName}</td>
	       	  				<td>${obj.answer}</td>
	       	  				<td>${(obj.createdTime?string("yyyy-MM-dd HH:mm:ss"))!}</td>
	       	  			</tr>
	       	  		</#list>
	       	  	</tbody>
	       	  </table>
       	  </div>
       	  <@pages url="${contextPath}/message/doReply.do?id=${list[0].id}" pageCount="${list[1].pages}" currentPage="${list[1].pageNum}" />
    </div>
	<div class="panel panel-primary">
       <div class="panel-heading">我要回复</div>
       	  <div class="panel-body">
       	  
       	  <input type="hidden" name="msgId" value="${list[0].id}">
       	  	<div class="row">
       	  		<div class="col-sm-4"></div>
       	  		<div class="col-sm-4">
       	  			<textarea rows="6"  name="answer" id="answer" placeholder="200字内！" class="form-control"></textarea>
       	  		</div>
       	  	</div>
       	  	<div class="row">
       	  		<div class="col-sm-8"></div>
       	  		<div class="col-sm-2">
       	  			<button type="button" id="save" onclick="saveAnswer();" class="btn btn-primary">发言</button>
       	  		</div>
       	  	</div>
       	 
       	  </div>
    </div>
     </form>
	<#include "/pub/footer_res_detail.ftl"/>

	<script type="text/javascript">
		$(document).ready(function(){
			$("#pagination").page("form1");
		});
		
		function saveAnswer(){
			var answer = $("#answer").val();
			if(answer == null || answer == ''){
				layer.alert("请填写回复内容！");
				return;
			}
			
			form1.action="${contextPath}/message/addAnswer.do";
			form1.submit();
		}
		
    	
   	 		 	
	</script>
	</body>
</html>