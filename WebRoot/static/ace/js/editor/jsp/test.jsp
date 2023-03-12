<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
    <title>DATABASE</title>
    
    <meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
<link rel="stylesheet" type="text/css" href="styles.css">
-->

</head>

<body>
  <form name="uploadform" method="post" action="<%=path%>/editor/jsp/upload_json.jsp"  ENCTYPE="multipart/form-data"  > 
<center> 
<table border="1" width="65%"> 
<tr> 
<td colspan=2 nowrap align=center>广告图片上载</td> 

</tr> 
<tr> 
<td width="15%" nowrap >广告图片:</td> 
<td width="50%" nowrap><input type="file" id="image" name="image" size="20"></td> 
</tr> 
<tr> 
<td align="center" colspan=2 > 
<input type="submit" value="upload" > 
    
</td> 

</tr> 
</table>
</form> 

</body>
</html>


