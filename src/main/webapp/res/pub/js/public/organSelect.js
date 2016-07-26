
//机构选择
function OrganSelect(){
	var callBack=$.Callbacks("unique");
	var param='';//参数
	this.change=organChange;
	this.getNextItem=getNextItem;
	this.createNextItem=createNextItem;
	this.setSelVal=setSelVal;
	this.initOrgan=initOrgan;
	this.autoTrigger=autoTrigger;//自动触发下一级
	this.addCallBack=addCallBack;
	
	function organChange(obj, dv) {
		if (undefined == dv) {
			dv = '';
		}
		var $obj = jQuery(obj);
		var items = getNextItem($obj);// data-parent
		// 清除
		if ($obj.val() == '') {
			for (var i = items.length - 1; i >= 0; i--) {
				var item = items[i];
				if (item.attr('data-parent') == '1') {
					item.val('');
				} else {
					item.remove();
				}
			}
			setSelVal($obj.val(), $obj.parent().attr("id"));
		} else {
			setSelVal($obj.val(), $obj.parent().attr("id"));
			// 有没有左边 有则判断是否有选中
			if ($obj.attr('data-first') == '1'
					|| ($obj.prev() && $obj.prev().html().length > 0 && $obj.prev()
							.val().length > 0)) {
				//
			} else {
				$obj.val('');
				setSelVal($obj.val(), $obj.parent().attr("id"));
				return;
			}
			if (items.length == 0) {
				// 创建下一元素
				createNextItem('/leads/base/user/ajax.do', $obj.val(), dv, $obj
						.parent().attr("id"));
			} else {
				for (var i = 0; i < items.length; i++) {
					var item = items[i];
					if (item.attr('data-parent') == '1') {
						// 已取出
					} else {
						// 重新取值
						item.remove();
						createNextItem('/leads/base/user/ajax.do', $obj.val(),'', $obj.parent().attr("id"));
					}
				}
			}
		}
	}
	// 获取所有下在的兄弟元素
	function getNextItem($obj) {
		var items = [];
		$obj.nextAll().each(function() {
			items.push(jQuery(this));
		});
		return items;
	}
	// 创建下一元素
	function createNextItem(url, _organId, dv, showAreaId) {
		$.ajax({
			url : url + "?organId=" + _organId + "&defVal=" + dv,
			type : 'POST',
			async : true,
			contentType : "application/json",
			dataType : "json",
			success : function(result) {
				str = (result.select);
				if (str.length > 0) {
					var $sai=jQuery("#" + showAreaId);
					$sai.append(str);
					// 添加格式 取第一个的class或style 放到这里来 
					var $first=$sai.find("select").first();
					var $last=$sai.find("select").last();
					$last.attr("class",$first.attr("class")).attr("style",$first.attr("style"))
				}
			},
			error : function() {
				alert("ajax调用出错了");
			}
		});
	}
	// set select val to hidden
	function setSelVal(value, showAreaId) {
		//取到最后一个选中的值
		if(value==''){
			var sel = [];
			jQuery("#" + showAreaId).find("select").each(function() {
				sel.push($(this));
			});
			//从后往前找
			sel.reverse();
			for(var i=0;i<sel.length;i++){
				if(sel[i].val()!='' && sel[i].val().length>0){
					jQuery("#" + showAreaId).find("input").first().val(sel[i].val());
					break;
				}
				if(i==(sel.length-1)){
					jQuery("#" + showAreaId).find("input").first().val('');//一个都没有选中
				}
			}
		}else{
			jQuery("#" + showAreaId).find("input").first().val(value);
		}
		callBack.fire(param);
	}
	// 初始化
	function initOrgan(showAreaId) {
		if (showAreaId == undefined || showAreaId == '') {
			showAreaId = '_organTag_';
		}
		var val = jQuery("#" + showAreaId).find("input").first().attr("data-seq");
		if (val!='' && val.length > 0) {
			var arr = val.split("-");
			var sel = [];
			jQuery("#" + showAreaId).find("select").each(function() {
				sel.push($(this));
			});
			//data-first 第一个
			for (var i = 0; i < arr.length; i++) {
				if (i < sel.length) {
					sel[i].val(arr[i]);
				} else {
					organChange((sel[i - 1]).get(0), arr[i]);
				}
			}
		}
	}
	//自动触发最下一级
	function autoTrigger(showAreaId){
		if (showAreaId == undefined || showAreaId == '') {
			showAreaId = '_organTag_';
		}
		var $last=jQuery("#"+showAreaId).find("select").last();
		organChange($last,$last.val());
	}
	
	//执行自定义函数
	function addCallBack(_callback,_param){
		callBack.add(_callback);
		if(_param!=undefined){
			param=_param;
		}
	}
}
var organ=new OrganSelect();
//回调使用法
/**
 * 1.定义js方法
 * function test(abc){alert(abc);}
 * 2.把方法添加到进去
 * organ.addCallBack(test,'123');
 */