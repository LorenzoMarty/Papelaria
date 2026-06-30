<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/produtos.css?v=1">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
    <title>Papelaria - Vendas</title>
</head>
<body>
<div class="page-wrapper">
    <jsp:include page="/WEB-INF/pages/includes/header.jsp" />

    <c:choose>
        <c:when test="${param.msg == 'registrado'}">
            <div class="msg">Venda registrada com sucesso.</div>
        </c:when>
    </c:choose>

    <c:if test="${not empty erro}">
        <div class="msg">${erro}</div>
    </c:if>

    <div class="container">
        <div class="card">
            <h2>Registrar Venda</h2>
            <form action="${pageContext.request.contextPath}/venda" method="post">

                <label>Produto</label>
                <select name="produtoId" required>
                    <option value="">Selecione um produto...</option>
                    <c:forEach var="p" items="${produtos}">
                        <option value="${p.id}">${p.nome} — R$ ${p.preco} (estoque: ${p.quantidade})</option>
                    </c:forEach>
                </select>

                <label>Quantidade</label>
                <input type="number" name="quantidade" min="1" required>

                <button type="submit">Registrar</button>
            </form>
        </div>

        <div class="card">
            <h2>Historico de Vendas</h2>
            <c:choose>
                <c:when test="${not empty vendas}">
                    <table>
                        <tr>
                            <th>#</th>
                            <th>Produto</th>
                            <th>Qtd</th>
                            <th>Preco Unit.</th>
                            <th>Total</th>
                            <th>Vendido por</th>
                            <th>Data</th>
                        </tr>
                        <c:forEach var="v" items="${vendas}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${v.nomeProduto}</td>
                                <td>${v.quantidade}</td>
                                <td>R$ <fmt:formatNumber value="${v.precoUnitario}" pattern="#,##0.00"/></td>
                                <td><strong>R$ <fmt:formatNumber value="${v.total}" pattern="#,##0.00"/></strong></td>
                                <td>${v.nomeUsuario}</td>
                                <td>${v.dataVenda}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <p class="vazio">Nenhuma venda registrada.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <jsp:include page="/WEB-INF/pages/includes/footer.jsp" />
</div>
</body>
</html>
