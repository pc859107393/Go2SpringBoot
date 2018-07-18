<#assign base=req.contextPath />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>出错啦</title>
    <!-- Bootstrap core CSS -->
    <link href="${base}/static/error/css/bootstrap.css" rel="stylesheet">
    <!-- FONT AWESOME CSS -->
    <link href="${base}/static/error/css/font-awesome.min.css" rel="stylesheet"/>
    <!--GOOGLE FONT -->
    <link href='http://fonts.googleapis.com/css?family=Nova+Flat' rel='stylesheet' type='text/css'>
    <!-- custom CSS here -->
    <link href="${base}/static/error/css/style.css" rel="stylesheet"/>
</head>
<body>


<div class="container">

    <div class="row pad-top text-center">
        <div class="col-md-6 col-md-offset-3 text-center">
            <h1> 发生了什么？ </h1>
            <h5> 好像出了点错！</h5>
            <h5><#if path?exists>
                ${path}
            </#if></h5>
            <h5><#if msg?exists>
                ${msg}
            </#if></h5>
        </div>
    </div>

    <div class="row text-center">
        <div class="col-md-8 col-md-offset-2">

            <h3><i class="fa fa-lightbulb-o fa-5x"></i></h3>
            <a href="javascript:history.back()" class="btn btn-primary">返回上一页</a>
        </div>
    </div>

</div>
<!-- /.container -->


<!--Core JavaScript file  -->
<script src="${base}/static/error/js/jquery-1.10.2.js"></script>
<!--bootstrap JavaScript file  -->
<script src="${base}/static/error/js/bootstrap.js"></script>
<!--Count Number JavaScript file  -->
<script src="${base}/static/error/js/countUp.js"></script>
</body>
</html>
