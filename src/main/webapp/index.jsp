<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <title>Papelaria - Login</title>
</head>
<body>
<jsp:include page="/WEB-INF/pages/includes/header.jsp" />
<div class="container">
    <div class="box">
        <h2>Papelaria</h2>
        <p class="subtitulo">Acesse como administrador ou usuario.</p>
        <form action="login" method="post">
            <input type="email" name="email" placeholder="E-mail" required>
            <input type="password" name="senha" placeholder="Senha" required>
            <button type="submit">Entrar</button>
        </form>
        <a href="${pageContext.request.contextPath}/cadastro">Criar conta</a>
        <c:if test="${param.msg == 'cadastrado'}">
            <p class="sucesso">Conta criada. Entre com seu e-mail e senha.</p>
        </c:if>
        <c:if test="${param.msg == 'loginParaComprar'}">
            <p class="erro">Faca login para realizar uma compra.</p>
        </c:if>
        <c:if test="${not empty erro}">
            <p class="erro">${erro}</p>
        </c:if>
    </div>
</div>

<jsp:include page="/WEB-INF/pages/includes/footer.jsp" />

</body>
</html>
