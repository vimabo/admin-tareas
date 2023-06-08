<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${nameTask}</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<h1>${nameTask}</h1>
		<form:form action="/tasks/update" method="post"
			modelAttribute="taskEdit">

			<div class="form-group">
				<form:label path="name">Nombre:</form:label>
				<form:input path="name" class="form-control" />
				<form:errors path="name" class="text-danger" />
			</div>

			<div class="form-group">
				<form:label path="assignee">Assignee:</form:label>
				<form:select required="true" class="form-select" id="userId"
					path="assignee.id">
					<option value="">--Seleccionar usuario--</option>
					<c:forEach var="u" items="${listUsers}">
		  <option value="<c:out value="${u.id}"/>" 
		  <c:if test="${u.id eq taskEdit.assignee.id}">selected="selected"</c:if>><c:out value="${u.name}" /></option>					
					</c:forEach>
				</form:select>
				<form:errors path="assignee" class="text-danger" />
			</div>

			<div class="form-group">
				<form:label path="priority">Priority:</form:label>
				<form:select class="form-control" id="prioritysId" path="priority">
					<option value="">--Seleccionar prioridad--</option>
					<option value="high" <c:if test="${taskEdit.priority eq 'high'}">selected="selected"</c:if>>High</option>
				<option value="medium" <c:if test="${taskEdit.priority eq 'medium'}">selected="selected"</c:if>>Medium</option>
				<option value="low" <c:if test="${taskEdit.priority eq 'low'}">selected="selected"</c:if>>Low</option>
				</form:select>
				<form:errors path="priority" class="text-danger" />
			</div>

			<form:hidden path="lead" value="${userSession.id}" />

			<form:hidden path="id" value="${taskEdit.id}" />

			<input type="hidden" name="_method" value="put" />

			<input type="submit" value="Actualizar" class="btn btn-success" />

		</form:form>
	</div>
</body>
</html>