<!DOCTYPE html>
<html lang="en">
<head>
<#include "/pub/header_res.ftl"/>
<#include "/pub/footer_res_detail.ftl"/>
<meta charset="utf-8">
<title>错误</title>
<style type="text/css">
body {
padding: 50px;
}
</style>
</head>
<body>
<div class="alert alert-warning">
   <a href="#" class="close" data-dismiss="alert">
      &times;
   </a>
   <h2>${title}</h2>
     ${msg}
</div>
</body>
</html>