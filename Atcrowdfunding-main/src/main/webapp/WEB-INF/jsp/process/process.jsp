<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/main.css">
	
	<link rel="stylesheet" href="${APP_PATH }/css/pagination.css" />
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 流程管理</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li style="padding-top:8px;">
				<jsp:include page="/WEB-INF/jsp/common/userinfo.jsp"></jsp:include>
			
			</li>
            <li style="margin-left:10px;padding-top:8px;">
				<button type="button" class="btn btn-default btn-danger">
				  <span class="glyphicon glyphicon-question-sign"></span> 帮助
				</button>
			</li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
			<div class="tree">
				<jsp:include page="/WEB-INF/jsp/common/menu.jsp"></jsp:include>
			
			</div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 流程列表</h3>
			  </div>
			  <div class="panel-body">
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>

<button id="uploadDefBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-upload"></i> 上传流程定义文件</button>
<br>
<form id="uploadForm" action="" method="post" enctype="multipart/form-data">
	<input style="display: none" name="processDefFile" type="file" id="processDefFile">
</form>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
                  <th>流程定义名称</th>
                  <th>流程定义版本</th>
                  <th>流程定义Key</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
                
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
					
					 </td>
				 </tr>

			  </tfoot>
            </table>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>

    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
        <script src="${APP_PATH }/jquery/pagination/jquery.pagination.js"></script>
        
         <script src="${APP_PATH }/jquery/jquery-form/jquery-form.min.js"></script><!-- 导入js -->
	<script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
     
	
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
			    
			    queryProcess(0); 
            });
            
            
            var jsonObj = {
        			"pageno" : 1,
        			"pagesize" : 10 
        		};
            
            
            var loadingIndex = -1 ;
            function queryProcess(pageIndex){
            	jsonObj.pageno = pageIndex+1 ;
            	$.ajax({
            		type : "POST",
            		data : jsonObj,
            		url : "${APP_PATH}/process/doIndex.do",
            		beforeSend : function(){
            			loadingIndex = layer.load(2, {time: 10*1000});
            			return true ;
            		},
            		success : function(result){
            			layer.close(loadingIndex);
            			if(result.success){
            				var page = result.page ;
            				var data = page.datas ;
            				
            				var content = '';
            				
            				$.each(data,function(i,n){
            					content+=' <tr>';				
            					content+=' <td>'+(i+1)+'</td>';
            					content+=' <td>'+n.name+'</td>';
            					content+=' <td>'+n.version+'</td>';
            					content+=' <td>'+n.key+'</td>';
            					 
            					content+=' <td>';
            					content+='   <button type="button" onclick="window.location.href=\'${APP_PATH}/process/toShow.htm?id='+n.id+'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-eye-open"></i></button>';
             				   content+=' <button type="button" onclick="deleteProDef('+n.id+','+n.name+')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
             				//   content+=' <button type="button" onclick="deleteProDef(\''+n.id+'\',\''+n.name+'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
             					//拼串，这两种方式都可以
            					content+=' </td>';												
            					content+=' <tr>';
            					   
            					 
            				});
            				$("tbody").html(content);
            				// 创建分页
            				$("#Pagination").pagination(page.totalno,{
            					num_edge_entries: 1, //边缘页数
            					num_display_entries: 4, //主体页数
            					callback: queryProcess,
            					items_per_page:1 ,//每页显示1项
            					current_page:(page.pageno-1),
            					prev_text:"上一页",
            					next_text:"下一页"
            				});
            				
            			}else{
            				layer.msg(result.message, {time:1000, icon:5, shift:6});
            			}
            		},
            		error : function(){
            			layer.msg("加载数据失败!", {time:1000, icon:5, shift:6});
            		} 
            		
            		});
            }
            
            
            
            //单击了按钮之后，选中了文件之后，就让文件上传，（怎样上传呢，就是当反应到那个文件表单发生变化的时候就上传）
            $("#uploadDefBtn").click(function(){//click()函数有参数，则是绑定
            	$("#processDefFile").click();//click()函数没有参数,表示触发单击事件.
            	});
            
            $("#processDefFile").change(function(){
        		/*用了jQuery的form提交  */
        		var options = {
        			url:"${APP_PATH}/process/doAdd.do",
       				beforeSubmit : function(){
       					loadingIndex = layer.msg('流程定义正在上传中', {icon: 6});
               			return true ; //必须返回true,否则,请求终止.
       				},
       				success : function(result){
            			layer.close(loadingIndex);
            			if(result.success){
            				layer.msg("流程定义上传成功", {time:1000, icon:6});
            				 queryProcess(0); //上传成功之后就刷新局部的数据
            			}else{
            				layer.msg("流程定义上传失败", {time:1000, icon:5, shift:6});
            			}	
            		}	
        		};
        		
        		$("#uploadForm").ajaxSubmit(options); //异步提交
        		return ; 
        		
        		
        		/* $("#advertForm").attr("action","${APP_PATH}/advert/doAdd.do");
        		$("#advertForm").submit(); //这个是同步*/
        		
            	
            });	
            		
            
			function deleteProDef(id,username){
            	
            	
            	layer.confirm("是否确定删除流程定义{"+username+"}",  {icon: 3, title:'提示'}, function(cindex){

               
                	$.ajax({
                		type : "POST",
                		data : {
                			"id":id
                		},
                		url : "${APP_PATH}/process/doDelete.do",
                		beforeSend : function(){
                			loadingIndex = layer.load(2, {time: 10*1000});
                			return true ;
                		},
                		success : function(result){
                			 if(result.success){
                				 
 //我这里用了局部刷新，直接调用quaryProcess会出现删除之u后还会再弹出请求删除
                				 window.location.href="${APP_PATH}/process/toIndex.htm";
                    			 }else{
                					
                				layer.msg(result.message, {time:1000, icon:5, shift:6});
                			}
                		},
                		error : function(){
                			layer.msg("删除流程定义失败!", {time:1000, icon:5, shift:6});
                		}
            	
                	});
              
            		 	}, function(cindex){
    			    layer.close(cindex);
    			});
            	
            }
            		
        </script>
  </body>
</html>
