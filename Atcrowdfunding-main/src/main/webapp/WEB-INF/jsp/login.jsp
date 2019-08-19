<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/login.css">
	
	<style>

	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>

    <div class="container">


      <form id="loginForm" class="form-signin" action="doLogin.do" method="POST" role="form">
      ${exception.message}
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="floginacct" name="loginacct" value="superadmin" placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" id="fuserpswd" name="userpswd" value="123" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<select id="ftype" class="form-control" name="type" >
                <option value="member">会员</option>
                <option value="user">管理</option>
            </select>
		  </div>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> 记住我
          </label>
          <br>
          <label>
            忘记密码
          </label>
          <label style="float:right">
            <a href="reg.html">我要注册</a>
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()"> 登录</a>
      </form>
    </div>
    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
  
    <script>
    function dologin() {
    	
    	//通过获取id的值来形成json数据
    	var floginacct = $("#floginacct");
    	var fuserpswd = $("#fuserpswd");
    	var ftype = $("#ftype");
    	
    	//注意，表单比较空串不能用null，只能用“”
    	if($.trim(floginacct.val()) == "") {
    		//alert("账号不能为空");下面是使用layer组件的方法，5是哭脸，6是抖动的意思
    		layer.msg("账号不能为空", {time:1000, icon:5, shift:6}, function(){
    			floginacct.val("");
        		floginacct.focus();//时账号栏获取焦点
        	
    		});
    		
        	return false;
    	}
    	var loadingIndex = -1;
    	//这个时异步请求登录的方法：用ajax
    	$.ajax({
    		type : "POST",
    		data : {
    			"loginacct" :floginacct.val(),
    			 "userpswd" : fuserpswd.val(),
    			 "type" : ftype.val(),
    		},
    		url : "${APP_PATH}/doLogin.do",
    		beforeSend : function() {
    			loadingIndex = layer.msg('处理中', {icon: 16});
    			return true;
    			//只有返回true ,请求才会发送给服务器，一般做表单数据校验
    		},
    		success : function(result) {//如梭请求成功了执行什么函数   {success:false,message:"登录失败"}或{success:true}
			layer.close(loadingIndex);		
    		if(result.success) {
							//跳转页面
							window.location.href="${APP_PATH}/main.htm";
						}else {
							layer.msg(result.message, {time:1000, icon:5, shift:6});
						}

    		
    		},
    		error : function() {
    			layer.msg("登录失败", {time:1000, icon:5, shift:6});
    		}
    	})
    	
    	
    	
    	
    //	$("#loginForm").submit(); 	这个时同步请求，直接用JQeury的submit方法
        /* var type = $(":selected").val();
        if ( type == "user" ) {
            window.location.href = "main.html";
        } else {
            window.location.href = "index.html";
        } */
    }
    </script>
  </body>
</html>