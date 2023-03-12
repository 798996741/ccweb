<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.fh.util.Jurisdiction"%>
<%
	Jurisdiction jurisdiction=new Jurisdiction();
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../../system/include/incJs_mx.jsp"%>
<style>
.pagination input {
	height: 33px;
}

.pagination select {
	height: 32px;
}
	.admin-button{
		display: flex;
		color: #fff !important;
		height: 24px;
		width: 70px;
		padding-left: 30px;
		border-radius: 20px;
		line-height: 24px;
		letter-spacing: 1px;
		background: url('../img/home/add_icon.png') #8dc73f no-repeat 1px center;
	}
	.width-110px{
		width:100px;
	}
	.border-style{
		margin:2px;
	}
</style>
</head>
<body class="no-skin">


	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper" style="width:100%;margin-left:0px;">
		<section class="container-fluid">
			        <div class="seat-middle-top" style="box-shadow: 4px 4px 6px #c7c7c7;">
				<div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt">角色</div>
					<div class="seat-button flex-row-center-center">
						<%if(jurisdiction.hasQx("990101")){ %>
						<a href="javascript:void (0)" style="float:left"
							onclick="addRole();">新增</a>
						<%} %>
					</div>
					<div style="margin-left: 5px">
					<c:choose>
						<c:when test="${pd.ROLE_ID == '99'}">
						</c:when>
						<c:otherwise>
								<span  onclick="editRights('${pd.ROLE_ID }');">
									<i class="icon-pencil"></i><c:if test="${pd.ROLE_ID == '1'}"><div class="seat-button flex-row-center-center"><a style="width: 120px">Admin权限</a></div></c:if>
									<c:if test="${pd.ROLE_ID != '1'}"><div class="seat-button flex-row-center-center"><a style="width: 130px">组菜单权限</a></div></c:if>
								</span>
						</c:otherwise>
					</c:choose>
					</div>
				</div>
				
                <div class="border-style">
                    <span class="border-size">类型</span>
                    <div class="border-line"></div>
                    <select class="seat-select width-110px" id="ROLE_TYPES" name="ROLE_TYPES">
                        <option value="">全部</option>
                        <c:forEach items="${roletypeList}" var="var" varStatus="vs">
                             <option value="${var.DICTIONARIES_ID }"
                                 <c:if test="${pd.ROLE_TYPE==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }
                             </option>
                         </c:forEach>
                       
                    </select>
                </div>
                <div class="border-style">
                    <span class="border-size">编码</span>
                    <div class="border-line"></div>
                    <input class="seat-input  width-110px" placeholder="角色编码" name="ROLE_CODES" id="ROLE_CODES"
                           value="${pd.ROLE_CODE }">
                </div>
                <div class="border-style">
                    <span class="border-size">角色</span>
                    <div class="border-line"></div>
                    <input class="seat-input  width-110px" placeholder="角色" name="ROLE_NAMES" id="ROLE_NAMES"
                           value="${pd.ROLE_NAME }">
                </div>
                <div class="border-style">
                    <span class="border-size">综合查询</span>
                    <div class="border-line"></div>
                    <input class="seat-input width-110px" placeholder="综合查询" name="keywords" id="keywords"
                           value="${pd.keywords }">
                </div>
                <button type="button" class="btn btn-default-sm button-blue width-45px"
                        style="margin: 5px 5px 5px 5px;" onclick="tosearch();">
                    <font>查&nbsp;询</font>
                </button>
                <button type="button" class="btn btn-default-sm button-blue width-45px"
                        style="margin: 5px 5px 5px 5px;" onclick="toclean();">
                    <font>重&nbsp;置</font>
                </button> 
				
			</div>
			<div class="seat-middle">
				<!--  <div class="seat-middle-nr"> -->

				<div class="xtyh-middle-r zxzgl-middle-r">
					<!-- <div class="box-body" > -->

					<table id="example2" class="table table-bordered table-hover">
						<thead>
							<tr>
								<!-- <th class="center" style="width:35px;">
                      <label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
                      </th> -->
								<th class="center" style="width: 30px;">序号</th>
								<th class='center' style="width: 150px;">角色编码</th>
								<th class='center' style="width: 150px;">角色类型</th>
								<th class='center' style="width: 150px;">角色</th>
								<th style="width:155px;" class="center">操作</th>
							</tr>
						</thead>
						<tbody>

							<c:choose>
								<c:when test="${not empty roleList_z}">
									<c:forEach items="${roleList_z}" var="var" varStatus="vs">

										<tr>
											<td class='center' style="width:30px;">${vs.index+1}</td>
											<td >${var.ROLE_CODE }</td>
											<td >${var.ROLE_TYPENAME }</td>
											<td id="ROLE_NAMETd${var.ROLE_ID }">${var.ROLE_NAME }</td>

											<td style="width:255px;" align="center">
												<div class="flex-position">
													<div class="flex-row-center-center">

														<%if(jurisdiction.hasQx("990204")){ %>
															<div class="button-edit" title="菜单权限"
																onclick="editRights('${var.ROLE_ID }');" title="菜单权限">
																<font class="button-content">权限</font>
															</div>
														<%}%>
														<%if (jurisdiction.hasQx("990202")) {%>
															<div class="button-edit" style="margin-left: 10px" title="编辑"
																onclick="editRole('${var.ROLE_ID }');" title="编辑">
																<font class="button-content">编辑</font>
															</div>
														<%}%>


														<c:choose>
															<c:when test="${var.ROLE_ID == '2' or var.ROLE_ID == '1'}"></c:when>
															<c:otherwise>
																<%if (jurisdiction.hasQx("990203")) {%>
																	<div class="button-delete" style="margin-left:10px;"
																		title="删除"
																		onclick="delRole('${var.ROLE_ID }','c','${var.ROLE_NAME }');">
																		<font class="button-content">删除</font>
																	</div>
																<%} %>
															</c:otherwise>
														</c:choose>
													</div>
												</div>
											</td>
										</tr>
									</c:forEach>

								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="100" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>

						</tbody>

					</table>


				</div>

			</div>
		</section>
		
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/include/incJs_foot.jsp" %>
	<!-- 日期框 -->
	    <!-- jquery.tips.js -->
	<script type="text/javascript">
		//$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			//top.jzts();
			var ROLE_TYPES=$("#ROLE_TYPES").val();
			var ROLE_CODES=$("#ROLE_CODES").val();
			var ROLE_NAMES=$("#ROLE_NAMES").val();
			var keywords=$("#keywords").val();
			location.href="<%=path%>/role/listRoles.do?keywords="+encodeURI(encodeURI(keywords))+"&ROLE_TYPE="+encodeURI(encodeURI(ROLE_TYPES))+"&ROLE_CODE="+encodeURI(encodeURI(ROLE_CODES))+"&ROLE_NAME="+encodeURI(encodeURI(ROLE_NAMES))+"";
		}
		
		function toclean() {
	        $("#keywords").val('');
	        $("#ROLE_TYPES").val('');
			$("#ROLE_CODES").val('');
			$("#ROLE_NAMES").val('');
			tosearch();
	    }
		function addRole(pid){
			var winId = "userWin";
			  modals.openWin({
		          winId: winId,
		          title: '新增',
		          width: '450px',
		          url: '<%=basePath%>role/toAdd.do?parent_id='+pid
		          /*, hideFunc:function(){
		              modals.info("hide me");
		          },
		          showFunc:function(){
		              modals.info("show me");
		          } */
		      });
		
		}
		
		//修改
		function editRole(ROLE_ID){
			var winId = "userWin";
			  modals.openWin({
		          winId: winId,
		          title: '编辑',
		          width: '450px',
		          url: '<%=basePath%>role/toEdit.do?ROLE_ID='+ROLE_ID
		          /*, hideFunc:function(){
		              modals.info("hide me");
		          },
		          showFunc:function(){
		              modals.info("show me");
		          } */
		      });
			
		}
		
		//删除
		function delRole(ROLE_ID,msg,ROLE_NAME){
			bootbox.confirm("确定要删除该角色吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>role/delete.do?ROLE_ID="+ROLE_ID+"&guid="+new Date().getTime();
					
					$.get(url,function(data){
						if("success" == data.result){
							if(msg == 'c'){
								//top.jzts();
								document.location.reload();
							}else{
								//top.jzts();
								window.location.href="role.do";
							}
							
						}else if("false" == data.result){
							modals.info("删除失败，请先删除下级角色!");
						}else if("false2" == data.result){
							modals.info("删除失败，此角色已被使用!");
							
						}else if("false3" == data.result){
							modals.info("删除失败，该角色不能删除!");
						}
					});
				}
			});
		}
		
		//菜单权限
		function editRights(ROLE_ID){
			var winId = "userWin";
			  modals.openWin({
		          winId: winId,
		          title: '权限',
		          width: '320px',
		          url: '<%=basePath%>role/menuqx.do?ROLE_ID='+ROLE_ID
		      });
		}
		
		//按钮权限(增删改查)
		function roleButton(ROLE_ID,msg){
			top.jzts();
			if(msg == 'add_qx'){
				var Title = "授权新增权限(此角色下打勾的菜单拥有新增权限)";
			}else if(msg == 'del_qx'){
				Title = "授权删除权限(此角色下打勾的菜单拥有删除权限)";
			}else if(msg == 'edit_qx'){
				Title = "授权修改权限(此角色下打勾的菜单拥有修改权限)";
			}else if(msg == 'cha_qx'){
				Title = "授权查看权限(此角色下打勾的菜单拥有查看权限)";
			}
			var winId = "userWin";
			  modals.openWin({
		          winId: winId,
		          title: Title,
		          width: '330px',
		          height: '450px',
		          url: '<%=basePath%>role/b4Button.do?ROLE_ID='+ROLE_ID+'&msg='+msg
		          /*, hideFunc:function(){
		              modals.info("hide me");
		          },
		          showFunc:function(){
		              modals.info("show me");
		          } */
		      });
			
		}
	</script>

	<script>
    $(function () {

        $('#example2').DataTable({
            'paging': true,
            'lengthChange': false,
            'searching': true,
            'ordering': true,
            'info': true,
            'autoWidth': true
        });
        $(".row thead").find("th:first").addClass("cy_th");
        $(".row tbody").find("tr").find("td:first").addClass("cy_td");
        $(".row thead").find("th:first").after("<th id='cy_thk'></th>");
        $(".row tbody").find("tr").find("td:first").after("<td id='cy_thk'></td>");
        $('.seat-middle').find(".row:first").hide();

        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
    });

</script>

</body>
</html>