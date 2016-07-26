<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
        <#include "/pub/footer_res.ftl"/>
		 <title>cj test</title>
	</head>
<script type="text/javascript">
FusionCharts.ready(function () {
    var myChart = new FusionCharts({
        type: 'Pareto3D',
        renderAt: 'chart-container',
        dataFormat: 'json',
        "width": "95%",
        "height": "70%",
        dataSource: {
    "chart": {
        "caption": "Employee late arrivals by reported cause",
        "subCaption": "Last month",
        "paletteColors": "#0075c2",
        "lineColor": "#1aaf5d",
        "xAxisName": "Reported Cause",
        "pYAxisName": "No. of Occurrence",
        "sYAxisname": "Cumulative Percentage",
        "bgColor": "#ffffff",
        "borderAlpha": "20",
        "showCanvasBorder": "0",
        "usePlotGradientColor": "0",
        "plotBorderAlpha": "10",
        "showHoverEffect": "1",
        "showValues": "0",
        "showXAxisLine": "1",
        "xAxisLineColor": "#999999",
        "divlineColor": "#999999",
        "showAlternateHGridColor": "0",
        "subcaptionFontBold": "0",
        "subcaptionFontSize": "14"
    },
    "data": [
        {
            "label": "Traffic",
            "value": "5680"
        },
        {
            "label": "Family Engagement",
            "value": "1036"
        },
        {
            "label": "Public Transport",
            "value": "950"
        },
        {
            "label": "Weather",
            "value": "500"
        },
        {
            "label": "Emergency",
            "value": "140"
        },
        {
            "label": "Others",
            "value": "68"
        }
    ]
}
    });
    myChart.render();
});
</script>
	<body>
	<form action="${contextPath}/leads/search/queryListByUser.do" method="post" name="form1" id="form1" class="form-horizontal">
		<input type="hidden" id="assginItems" name="assginItems">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">客服录入统计</div>
			  <div class="panel-body" style="padding-bottom: 0px;">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
                         
                            <div class="form-group">
                                <label class="col-sm-1 control-label" style=" margin:5px;">起止日期</label>
                                <div class="col-sm-2 ">
                                   <input type="text" readonly name="stnextdate" id="stnextdate" value="${stnextdate!''}" maxlength="30"  placeholder="开始日期" class="form-control layer-date" >
                                </div>
                                <div class="col-sm-2">
                                    <input type="text" readonly name="nextdate" id="nextdate" value="${nextdate!''}" maxlength="30"  placeholder="结束日期" class="form-control layer-date" >
                                </div>
                                
                                <label class="col-sm-2 control-label">客服名称</label>
                                <div class="col-sm-4 ">
                                    <@select type='1' codeType="1028" defValue="${userId!''}" fieldId="userId" fieldName="userId" paramName="tablename,field" paramValue="lm_client,rank" props=" class='form-control' " />
                                </div>
                            </div>
                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-8">
	                                 <input type="button" onclick="search()" value="查 询" class="btn btn-primary btn-sm zd-btn-pd1">
	                             </div>
                            </div>

			  	 	 	 </div>
			  	 	 </div>
			  	 </div>
			  </div>
			</div>	
			<div class="panel panel-default table-responsive ziding-td">
				<table class="table table-condensed table-bordered table-striped table-hover">
                 <tbody>
                    <div class="form-group">
	                  <div id="chart-container">FusionCharts will load here...</div>
                    </div>
                 </tbody>
               </table>
			</div>
	</form>
	
</body>
</html>