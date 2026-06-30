<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">

<div class="header">
  <div class="marca">
    <a href="${pageContext.request.contextPath}/">Papelaria</a>
  </div>

  <div class="menu">
    <c:if test="${not empty sessionScope.usuario}">
      <a href="${pageContext.request.contextPath}/produto">Itens</a>
      <a href="${pageContext.request.contextPath}/venda">Comprar</a>
      <c:if test="${sessionScope.usuario.admin}">
        <a href="${pageContext.request.contextPath}/usuario">Usuarios</a>
        <a href="${pageContext.request.contextPath}/relatorio">Relatorio</a>
      </c:if>
      <span>Ola, ${sessionScope.usuario.nome}</span>
      <a href="${pageContext.request.contextPath}/logout">Sair</a>
    </c:if>

    <c:if test="${empty sessionScope.usuario}">
      <a href="${pageContext.request.contextPath}/login">Login</a>
    </c:if>
  </div>
</div>
