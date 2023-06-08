<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<header class="d-flex justify-content-between align-items-center">
			<h1>¡Bienvenid@ ${userSession.name}! </h1>
			<a href="/tasks/high">Priority high-low</a>
			<a href="/tasks/low">Priority low-high</a>
			<a href="/logout" class="btn btn-danger">Cerrar Sesión</a>
		</header>
		<div class="row">
			<h2>Todas las Tareas</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Tarea</th>
						<th>Líder de Tarea</th>
						<th>Asignada a:</th>
						<th>Prioridad</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listTasks}" var="t" >
						<tr>
							<td>
								<a href="/tasks/detail/${t.id}" >${t.name}</a>
							</td>
							<td>${t.lead.name}</td>
							<td>${t.assignee.name}</td>
							<td>${t.priority}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<hr>
		<a href="/tasks/new" class="btn btn-success">Nueva Tarea</a>
	</div>
</body>
</html>