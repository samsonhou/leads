//来源选择
function fromtypeSelect() {
	var callBack = $.Callbacks("unique");
	var param = '';// 参数
	this.change = organChange;
	// this.getNextItem=getNextItem;
	// this.createNextItem=createNextItem;
	// this.setSelVal=setSelVal;
	// this.initOrgan=initOrgan;
	// this.autoTrigger=autoTrigger;//自动触发下一级
	// this.addCallBack=addCallBack;
	//	
	function organChange(obj) {
		var $obj = $(obj);
		var level = $obj.attr("level");
		if($(obj).val() != ''){
		$.ajax({
			url : BASE_PATH + '/leads/base/fromtype/queryFromtype.do' + "?pid=" + $obj.val()+"&level="+level,
			type : 'POST',
			async : true,
			contentType : "application/json",
			dataType : "json",
			success : function(result) {
				//alert(result.select);
				str = (result.select);
				if (str.length > 0) {
					//$obj.parent().parent().find("select:gt("+(Number(level)-1)+")").remove();
					//$obj.parent().parent().find("div[level='"+(Number(level)+1)+"']").append(str);
					$obj.parent().parent().find("select:gt("+(Number(level)-1)+")").remove();
					$obj.parent().append(str);
					$obj.parent().parent().find("input[name='code']").val($obj.val());
				}else{
					$obj.parent().parent().find("select:gt("+(Number(level)-1)+")").remove();
					$obj.parent().find("input[name='code']").val($obj.val());
				}
			},
			error : function() {
				alert("ajax调用出错了");
			}
		});
		}else{
			$obj.parent().parent().find("select:gt("+(Number(level)-1)+")").remove();
			$obj.parent().parent().find("input[name='code']").val($obj.val());
		}
		
	}

	// 创建下一元素
	function createNextItem(url, pid) {
		$.ajax({
			url : url + "?pid=" + pid,
			type : 'POST',
			async : true,
			contentType : "application/json",
			dataType : "json",
			success : function(result) {
				alert(result.select);
				str = (result.select);
				if (str.length > 0) {

				}
			},
			error : function() {
				alert("ajax调用出错了");
			}
		});
	}

}
var fromtype = new fromtypeSelect();
