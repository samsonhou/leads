<#-- SELECT 标签 type 0:标准型 1:自定义型 -->
<#macro select codeType="" type="0" fieldId="" fieldName="" defValue=""  whereCase="" paramName="" paramValue="" haveHead="true" headName="请选择"  props="" >
	<#if type=='0'>
		<select id="${fieldId!''}" name="${fieldName!''}"  ${props!' '}>
			${htmlUtil('select','${type}','${codeType}','${defValue}','${whereCase}','${haveHead}','${headName}')}
		</select>
	<#elseif type=='1'>
		<select id="${fieldId!''}" name="${fieldName!''}"  ${props!' '}>
			${htmlUtil('select','${type}','${codeType}','${defValue}','${paramName}','${paramValue}','${haveHead}','${headName}')}
		</select>
	</#if>
</#macro>

<#-- 分页 标签 -->
<#macro pages url="" pageCount="0" currentPage="1" >
	<input type="hidden" id="currenPage" name="currenPage" value="${currentPage}">
	<div id="pagination" class="text-center" url="${url}" pageCount="${pageCount}" currentPage="${currentPage}" ></div>
</#macro>

<#-- 机构选择 标签 -->
<#macro organ showLevel=""  defValue="" fieldId="" fieldName="" showAreaId='_organTag_'  props="">
	${htmlUtil('organ','${showLevel}','${defValue}','${fieldId}','${fieldName}','${showAreaId}','${props}')}
</#macro>
<#-- 来源级联下拉列表 标签 -->
<#macro fromtype showLevel=""  defValue="" fieldId="" fieldName="" showAreaId='_organTag_'  props="">
	${htmlUtil('fromtype','${showLevel}','${defValue}','${fieldId}','${fieldName}','${showAreaId}','${props}')}
</#macro>
<#macro queryselect codeType="" type="0" defValue="" whereCase="" paramName="" paramValue=""  >${htmlUtil('queryselect','${type}','${codeType}','${defValue}','${whereCase}','${paramName}','${paramValue}')}</#macro>
<#-- token -->
<#macro token >
	<input type="hidden"  name="_token_"  value="${_token_!''}">
</#macro>