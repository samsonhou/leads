<!DOCTYPE html>
<html lang="en">
<head>
    <!--[if lt IE 8]>
    <script>
        alert('不支持IE6-8，请使用谷歌、火狐等浏览器\n或360、QQ等国产浏览器的极速模式浏览本页面！');
    </script>
    <![endif]-->
    <#include "/pub/header_res.ftl"/>
    <title>花生好车（捷众普惠）线索管理平台</title>
	<style>
	.zhezhao{ width:100%; height:100%; display:none;overflow-x:hidden; overflow-y:visible; position:absolute; z-index:10001; left:0; top:0; ;background:rgba(0,0,0,0.5); text-align:center}
	::-webkit-scrollbar{width:0;}
	</style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav onmouseleave="hideLayer();" class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i></div>
            <div class="sidebar-collapse"">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                                <span class="text-muted text-xs block">时间：${.now?date}</span>
                                <span class="text-muted text-xs block">机构：${_organName_!""}</span>
                                <span class="text-muted text-xs block">姓名：${_userName_!""}</span>
                                <span class="text-muted text-xs block  zddt">个人资料&nbsp;<b class="caret"></b></span>
                                </span>
                            </a>
                            <input type="hidden" name="_userName_" id="_userName_" value="${_userName_!""}">
                            <input type="hidden" name="_userId_" id="_userId_" value="${_userId_!"0"}">
                            <input type="hidden" id="isLock" value="${_isLock_!"0"}">
                            <ul id='otherInfo' class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a onclick="javascript:hideLayer();" class="J_menuItem" href="${contextPath}/Password.do">修改密码</a></li>
                            </ul>
                        </div>
                        <div class="logo-element">花生好车（捷众普惠）</div>
                    </li>
                    
                    <#list groupList as groupBean >
	                <li>
	                    <a href="#" id="group_${groupBean.groupId}"><i class="fa fa-bars"></i><span class="nav-label">${groupBean.groupName}</span><span class="fa arrow"></span></a>
	                    <ul class="nav nav-second-level">
	                        <#list groupBean.list as modelBean >
	                        <li><a class="J_menuItem" href="${contextPath}${modelBean.moduleUrl}" data-index="${modelBean.moduleId}">
	                        <#if modelBean.moduleName == "时效跟踪">
	                        	<span style="color:red;">${modelBean.moduleName}</span>
	                        <#else>${modelBean.moduleName}
	                        </#if>
	                        
	                        </a></li>
	                        </#list>
	                    </ul>
	                </li>
					</#list>
	
					<li style="display: none;">
						<a id="openTab" class="J_menuItem" href="#" data-index="99000"></a>
					</li>
					
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1"  style="overflow-x:hidden" >
            <div class="row content-tabs" style="position:relative">
			<div class="navbar-header" style="position:absolute; z-index:1000;"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary "  style="margin:0 ; padding:9px 13px 11px 12px;background-color:#fff; border:#f2f2f2; color:#999;border-radius:0; border-right:1px solid #eee; " href="#"><i class="fa fa-angle-double-left fa-lg"></i></a></div>
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="${contextPath}/indexPage.do">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <button class="roll-nav roll-right dropdown J_tabClose"><span class="dropdown-toggle" data-toggle="dropdown">关闭操作<span class="caret"></span></span>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
                    </ul>
                </button>
                <a href="${contextPath}/j_spring_security_logout" id="logoutLink" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main" >
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${contextPath}/indexPage.do" frameborder="0" data-id="${contextPath}/indexPage.do" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right"><a href="javascript:void(0);">&copy; 2015-${(.now?date)?string('yyyy')} 花生好车（捷众普惠）汽车租赁 </a></div>
				<div class="pull-left"><a href="javascript:void(0);"><span id="leftMsg">&nbsp;</span></a></div>
            </div>
        </div>
        <!--右侧部分结束-->

    </div>

	<div class="zhezhao">
		<iframe id="hastenTask" width="100%" height="800px" src="" frameborder="0" ></iframe>
	</div>



   <div class="modal fade" id="confirmMessageModal">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">提示信息</h4>
	      </div>
	      <div class="modal-body text-center">
	        <p id="showAlertInfo"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button"  class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal">确&nbsp;&nbsp;认</button>
	      </div>
	    </div>
	  </div>
	</div>
    <#include "/pub/footer_res.ftl"/>
	<!--<script src="${contextPath}/res/pub/js/demo/ziding/jquery.js" type="text/javascript"></script>-->
	
    <script type="text/javascript">
    var msg = '${message}';
    if(msg==''){
    }else{
       $("#showAlertInfo").text(msg);
	   $('#confirmMessageModal').modal('show');
    }
   
   
    //打开tab 标签
    function openTab(_href,_tabName){
    	if(_tabName==undefined){
    		_tabName="新Tab";
    	}
    	if(_tabName.length>6){
    		_tabName=_tabName.substr(0,6);
    	}
    	var $openTab=jQuery("#openTab");
    	//已存在
    	if($openTab.data(_href)){
    		$openTab.attr("href",_href).text(_tabName);
    		$openTab.trigger("click");
    	}
    	//不存在
    	else{
    		//href index tabName
    		$openTab.data(_href,_href);
    		$openTab.attr("href",_href).attr("data-index",(Number($openTab.attr("data-index"))+1)).text(_tabName);
    		$openTab.trigger("click");
    	}
    }
    //关闭当前活动页
    function closeTab(){
    	jQuery(".page-tabs-content").find("a").each(function(){
    		$this=jQuery(this);
    		if($this.hasClass("active")){
    			$this.find("i").trigger("click");
    		}
    	});
    }
    function closeLayer(){
    	jQuery(".zhezhao").hide();
    }
    //初始化
    jQuery(function(){
    	jQuery(".navbar-header").find("a").first().on("click",function(){
    		if(jQuery(this).html()=='<i class="fa fa-angle-double-left fa-lg"></i>'){
    			jQuery(this).html('<i class="fa fa-angle-double-right fa-lg"></i>');
    		}else{
    			jQuery(this).html('<i class="fa fa-angle-double-left fa-lg"></i>');
    		}
    	});
    });
    function hideLayer(){
    	jQuery(".nav-header").trigger("click");
    }
    
    function getTask(taskId){
    	if($(".zhezhao").is(":hidden")){
    		$("#hastenTask").attr("src","${contextPath}/leads/vist/addTrace.do?id="+taskId+"&comform=1");
        	$(".zhezhao").css({display:"block",height:$(window).height()});
    	}
    }
    //add by liangds 2016-01-20
    /**
    if(typeof(EventSource)!=="undefined" && !!window.EventSource && "${_isRole2_!'0'}"=='1'){
		var eventSource = new EventSource("${contextPath}/exchange/pushMsg.do");
		eventSource.onmessage = function(event) {
			var obj=jQuery.parseJSON(event.data);
			if(obj.userId>0 && obj.taskId>0 && obj.userId==$("#_userId_").val()){
				getTask(obj.taskId);
			}
		}
		eventSource.onopen = function(){
			//$("#leftMsg").text("推送已打开");
		}
	}
    */
       //弹出账户锁定提示
    if($("#isLock").val()==1){
	layer.open({
	    title: '提 示',
	    shade:0.8,
	    closeBtn: 0,
	    scrollbar:false,
	    content: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的账号已被锁定,请联系管理员解锁 !  ',
	    yes:function(index,layero){
	    	$("#logoutLink").find("i").trigger("click");
			layer.close(index);
		}
	}); 
	}
    </script>
	
</body>
</html>