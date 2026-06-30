<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/produtos.css?v=1">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
    <title>Papelaria - Usuarios</title>
</head>
<body>
<div class="page-wrapper">
    <jsp:include page="/WEB-INF/pages/includes/header.jsp" />
    <c:choose>
        <c:when test="${param.msg == 'salvo'}">
            <div class="msg">Usuario cadastrado.</div>
        </c:when>
        <c:when test="${param.msg == 'excluido'}">
            <div class="msg">Usuario excluido.</div>
        </c:when>
        <c:when test="${param.msg == 'editado'}">
            <div class="msg">Usuario atualizado.</div>
        </c:when>
        <c:when test="${param.msg == 'proprio'}">
            <div class="msg">O administrador logado nao pode excluir a propria conta.</div>
        </c:when>
    </c:choose>

    <c:if test="${not empty erro}">
        <div class="msg">${erro}</div>
    </c:if>

    <div class="container">
        <div class="card">
            <h2>${usuarioEditado.id != null ? 'Editar usuario' : 'Cadastrar usuario'}</h2>
            <form action="usuario" method="post">
                <input type="hidden" name="origem" value="admin">
                <input type="hidden" name="id" value="${usuarioEditado.id}">

                <label>Nome</label>
                <input type="text" name="nome" value="${usuarioEditado.nome}" required>

                <label>E-mail</label>
                <input type="email" name="email" value="${usuarioEditado.email}" required>

                <label>Senha</label>
                <input type="password" name="senha" placeholder="${usuarioEditado.id != null ? 'Nova senha (deixe em branco para manter)' : ''}" ${usuarioEditado.id == null ? 'required' : ''}>

                <label>Status</label>
                <select name="ativo">
                    <c:choose>
                        <c:when test="${empty usuarioEditado || usuarioEditado.ativo}">
                            <option value="true" selected>Ativo</option>
                            <option value="false">Inativo</option>
                        </c:when>
                        <c:otherwise>
                            <option value="true">Ativo</option>
                            <option value="false" selected>Inativo</option>
                        </c:otherwise>
                    </c:choose>
                </select>

                <label>Perfil</label>
                <select name="admin">
                    <c:choose>
                        <c:when test="${not empty usuarioEditado && usuarioEditado.admin}">
                            <option value="false">Usuario</option>
                            <option value="true" selected>Administrador</option>
                        </c:when>
                        <c:otherwise>
                            <option value="false" selected>Usuario</option>
                            <option value="true">Administrador</option>
                        </c:otherwise>
                    </c:choose>
                </select>

                <button type="submit">${usuarioEditado.id != null ? 'Atualizar' : 'Cadastrar'}</button>
                <c:if test="${usuarioEditado.id != null}">
                    <a class="botao-secundario" href="usuario">Cancelar edicao</a>
                </c:if>
            </form>
        </div>

        <div class="card">
            <h2>Usuarios</h2>
            <table>
                <tr>
                    <th>#</th>
                    <th>Nome</th>
                    <th>E-mail</th>
                    <th>Status</th>
                    <th>Perfil</th>
                    <th>Acoes</th>
                </tr>
                <c:forEach var="u" items="${usuarios}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${u.nome}</td>
                        <td>${u.email}</td>
                        <td>${u.ativo ? 'Ativo' : 'Inativo'}</td>
                        <td>${u.admin ? 'Admin' : 'Usuario'}</td>
                        <td class="acoes">
                            <a href="usuario?acao=editar&id=${u.id}">Editar</a>
                            <c:if test="${u.id != sessionScope.usuario.id}">
                                <a href="usuario?acao=excluir&id=${u.id}" onclick="return confirm('Excluir este usuario?')">Excluir</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty usuarios}">
                    <tr>
                        <td colspan="6">Nenhum usuario cadastrado.</td>
                    </tr>
                </c:if>
            </table>
        </div>
    </div>
    <jsp:include page="/WEB-INF/pages/includes/footer.jsp" />
</div>
</body>
</html>
