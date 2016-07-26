/**
 * 公共js方法类
 * add by 2015-12-07
 */

/**
 * 在找开的页面中 打开新的tab
 */
function openNewTab(_href,_tabName){
	try{
		window.parent.openTab(_href,_tabName);
	}catch(e){
		console.log("openNewTab>>打开新的Tab出错了..."+"[href:"+_href+"]"); 
	}
}

/**
 * 关闭tab 当前tab
 */
function closeTab(){
	try{
		window.parent.closeTab();
	}catch(e){
		console.log("closeTab error"); 
	}
}

/**
 * 关闭弹出层
 */
function closeLayer(){
	try{
		window.parent.closeLayer();
	}catch(e){
		console.log("closeTab error"); 
	}
}
/**
 * //objE为form表单     
 * 用法  onclick="clearForm(this.form)" 
 * 例子：<input type="button" onclick="clearForm(this.form)" value="清空" class="btn btn-primary btn-sm zd-btn-pd1">
 * @param objE
 */
function clearForm(objE){
    $(objE).find(':input').each(  
        function(){  
            switch(this.type){  
                case 'passsword':  
                case 'select-multiple':  
                case 'select-one':  
                case 'text':  
                $(this).val('');  
                    break;  
                case 'textarea':  
                    $(this).val('');  
                    break;  
                case 'checkbox':  
                case 'radio':  
                    this.checked = false;  
            }  
        }     
    );  
} 