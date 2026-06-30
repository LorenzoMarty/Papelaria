<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/produtos.css?v=2">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
    <title>Papelaria - Relatorio</title>
</head>
<body>
<div class="page-wrapper">
    <jsp:include page="/WEB-INF/pages/includes/header.jsp" />

    <h2 class="page-title">Relatorio Gerencial</h2>

    <div class="cards-resumo">
        <div class="card-stat">
            <div class="numero">${totalVendas}</div>
            <div class="label">Total de Vendas</div>
        </div>
        <div class="card-stat">
            <div class="numero">R$ <fmt:formatNumber value="${totalFaturado}" pattern="#,##0.00"/></div>
            <div class="label">Total Faturado</div>
        </div>
        <div class="card-stat">
            <div class="numero ${empty produtosEstoqueBaixo ? '' : 'alerta'}">${fn:length(produtosEstoqueBaixo)}</div>
            <div class="label">Produtos com Estoque Baixo (ate 5)</div>
        </div>
    </div>

    <div class="container somente-lista">

        <c:if test="${not empty produtosEstoqueBaixo}">
            <div class="card">
                <h2>Atencao: Estoque Baixo</h2>
                <table>
                    <tr>
                        <th>Produto</th>
                        <th>Descricao</th>
                        <th>Estoque</th>
                        <th>Preco</th>
                    </tr>
                    <c:forEach var="p" items="${produtosEstoqueBaixo}">
                        <tr>
                            <td><strong>${p.nome}</strong></td>
                            <td>${p.descricao}</td>
                            <td class="alerta">${p.quantidade}</td>
                            <td>R$ <fmt:formatNumber value="${p.preco}" pattern="#,##0.00"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>

        <div class="card">
            <h2>Historico de Vendas</h2>
            <c:choose>
                <c:when test="${not empty vendas}">
                    <table>
                        <tr>
                            <th>#</th>
                            <th>Data</th>
                            <th>Produto</th>
                            <th>Qtd</th>
                            <th>Preco Unit.</th>
                            <th>Total</th>
                            <th>Operador</th>
                        </tr>
                        <c:forEach var="v" items="${vendas}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${v.dataVenda}</td>
                                <td>${v.nomeProduto}</td>
                                <td>${v.quantidade}</td>
                                <td>R$ <fmt:formatNumber value="${v.precoUnitario}" pattern="#,##0.00"/></td>
                                <td><strong>R$ <fmt:formatNumber value="${v.total}" pattern="#,##0.00"/></strong></td>
                                <td>${v.nomeUsuario}</td>
                            </tr>
                        </c:forEach>
                        <tr class="tr-total">
                            <td colspan="5" style="text-align:right;">TOTAL FATURADO:</td>
                            <td>R$ <fmt:formatNumber value="${totalFaturado}" pattern="#,##0.00"/></td>
                            <td></td>
                        </tr>
                    </table>
                </c:when>
                <c:otherwise>
                    <p class="vazio">Nenhuma venda registrada ainda.</p>
                </c:otherwise>
            </c:choose>
        </div>

    </div>

    <jsp:include page="/WEB-INF/pages/includes/footer.jsp" />
</div>
</body>
</html>
