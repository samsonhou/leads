<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>500-服务出错</title>
    <link href="/leads/res/pub/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="/leads/res/pub/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="/leads/res/pub/css/animate.min.css" rel="stylesheet">
    <link href="/leads/res/pub/css/style.min.css?v=3.0.0" rel="stylesheet">
    <link href="/leads/res/pub/css/ziding.css?v=3.0.0" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper-content  animated fadeInRight blog">
        <div class="row">
            <div class="col-lg-4" style="width:100%">
                <div class="zd-er-t1">
                        <div class="zd-er-t2">500</div>
                        <p>
                          <br><span class="text-muted" style="color:#ff0000">错误描述:${errorMsg!" "}</span>
                        </p>
						<p>错误信息:${stackTrace!" "}
                        </p>
                </div>
            </div>
        </div>
    </div>
    <script src="/leads/res/pub/js/jquery-2.1.1.min.js"></script>
    <script src="/leads/res/pub/js/bootstrap.min.js?v=3.4.0"></script>
    <script src="/leads/res/pub/js/content.min.js?v=1.0.0"></script>
</body>

</html>

