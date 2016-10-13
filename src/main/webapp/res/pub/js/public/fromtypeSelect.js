//来源选择
function fromtypeSelect() {
	this.change = organChange;
		
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
				str = (result.select);
				if (str.length > 0) {
					$obj.parent().find("select:gt("+(Number(level)-1)+")").remove();
					$obj.parent().append(str);
					$obj.parent().find("input[name='code']").val($obj.val());
				}else{
					$obj.parent().find("select:gt("+(Number(level)-1)+")").remove();
					$obj.parent().find("input[name='code']").val($obj.val());
				}
			},
			error : function() {
				alert("ajax调用出错了");
			}
		});
		}else{
			$obj.parent().find("select:gt("+(Number(level)-1)+")").remove();
			$obj.parent().find("input[name='code']").val($obj.val());
		}
		
	}

}
var fromtype = new fromtypeSelect();
