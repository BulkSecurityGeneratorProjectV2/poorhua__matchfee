<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>征收管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {			
			
		});
		
	</script>
</head>
<body>
	<legend>征收列表</legend>	
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>代码</th>
				<th>项目编号</th>
				<th>项目名称</th>
				<th>申报人</th>
				<th>申报单位</th>
				<th>申报时间</th>
				<th>测算金额</th>
				<th>付款金额</th>
				<th>状态</th>
				<shiro:hasPermission name="charge:charge:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="charge">
			<tr>
				<td>
					${charge.id}
				</td>
				<td>
					${charge.project.prjNum}
				</td>
				<td>
					${charge.project.prjName}
				</td>
				<td>
					${charge.reportStaff.name}
				</td>
				<td>
					${charge.reportEntity}
				</td>
				<td>
					<fmt:formatDate value="${charge.reportDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${charge.calMoney}
				</td>
				<td>
					${charge.payMoney}
				</td>
				<td>
					${charge.statusLabel}
				</td>
				<shiro:hasPermission name="charge:charge:edit"><td>
				   <c:choose>
				      <c:when test="${charge.status eq '10' || charge.status eq '20'}">
				        <a href="${ctx}/charge/charge/opinionBookTab?id=${charge.id}">进入</a>
		   			  </c:when>
				      <c:when test="${charge.status eq '30'}">
				        <a href="${ctx}/charge/charge/payTicketTab?id=${charge.id}">进入</a>
		   			  </c:when>
				      <c:when test="${charge.status eq '40'}">
				        <a href="${ctx}/charge/charge/showSettlementList?id=${charge.id}">进入</a>
		   			  </c:when>			   			  		   			  
		   			  <c:otherwise></c:otherwise>		   			  
				   </c:choose>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>