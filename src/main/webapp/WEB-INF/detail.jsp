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
		<br> <br>
		<div class="form-group">
			<h2>Creator:</h2>
			<h3 style="color: blue;">
				<c:out value="${taskDetail.lead.name}" />
			</h3>
		</div>
		<br>
		<div class="form-group">
			<h2>Assignee:</h2>
			<h3 style="color: blue;">
				<c:out value="${taskDetail.assignee.name}" />
			</h3>
		</div>
		<br>
		<div class="form-group">
			<h2>Priority:</h2>
			<h3 style="color: blue;">
				<c:out value="${taskDetail.priority}" />
			</h3>
		</div>
		<br>
		<div class="d-grid gap-2 d-md-flex justify-content-md-end">
			<a class="${editDelete}" href="/tasks/edit/${taskDetail.id}">Edit</a>
			<form action="/tasks/${taskDetail.id}"
				onsubmit="return confirm('Desea eliminar el registro?');"
				method="post">
				<input type="hidden" name="_method" value="delete">
				<button class="${editDelete}" type="submit">Delete</button>
			</form>
			<form action="/tasks/${taskDetail.id}" method="post">
				<input type="hidden" name="_method" value="delete">
				<button class="${editComplete}" type="submit">Completado</button>
			</form>
		</div>
	</div>
</body>
</html>