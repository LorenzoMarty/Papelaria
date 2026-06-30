<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/produtos.css?v=3">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
    <title>Papelaria - Itens</title>
</head>
<body>
<div class="page-wrapper">
    <jsp:include page="/WEB-INF/pages/includes/header.jsp" />

    <c:choose>
        <c:when test="${param.msg == 'salvo'}">
            <div class="msg">Item cadastrado.</div>
        </c:when>
        <c:when test="${param.msg == 'excluido'}">
            <div class="msg">Item excluido.</div>
        </c:when>
        <c:when test="${param.msg == 'editado'}">
            <div class="msg">Item atualizado.</div>
        </c:when>
        <c:when test="${param.msg == 'semPermissao'}">
            <div class="msg">Apenas administradores podem alterar os itens.</div>
        </c:when>
    </c:choose>

    <c:if test="${not empty erro}">
        <div class="msg">${erro}</div>
    </c:if>

    <c:choose>

        <%-- Vista admin: formulário + tabela --%>
        <c:when test="${sessionScope.usuario.admin}">
            <div class="container">
                <div class="card">
                    <h2>${produto.id != null ? 'Editar item' : 'Cadastrar item'}</h2>
                    <form action="produto" method="post">
                        <input type="hidden" name="id" value="${produto.id}">

                        <label>Nome</label>
                        <input type="text" name="nome" value="${produto.nome}" required>

                        <label>Descricao</label>
                        <input type="text" name="descricao" value="${produto.descricao}" required>

                        <label>Preco (R$)</label>
                        <input type="number" step="0.01" min="0.01" name="preco" value="${produto.preco}" required>

                        <label>Estoque</label>
                        <input type="number" min="0" name="quantidade" value="${produto.quantidade}" required>

                        <button type="submit">
                            ${produto.id != null ? 'Atualizar' : 'Cadastrar'}
                        </button>
                        <c:if test="${produto.id != null}">
                            <a class="botao-secundario" href="produto">Cancelar edicao</a>
                        </c:if>
                    </form>
                </div>

                <div class="card lista-itens">
                    <h2>Itens cadastrados</h2>
                    <c:choose>
                        <c:when test="${not empty produtos}">
                            <table>
                                <tr>
                                    <th>#</th>
                                    <th>Nome</th>
                                    <th>Descricao</th>
                                    <th>Preco</th>
                                    <th>Estoque</th>
                                    <th>Acoes</th>
                                </tr>
                                <c:forEach var="p" items="${produtos}" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1}</td>
                                        <td>${p.nome}</td>
                                        <td>${p.descricao}</td>
                                        <td>R$ <fmt:formatNumber value="${p.preco}" pattern="#,##0.00"/></td>
                                        <td>${p.quantidade}</td>
                                        <td class="acoes">
                                            <a href="produto?acao=editar&id=${p.id}">Editar</a>
                                            <a href="produto?acao=excluir&id=${p.id}"
                                               onclick="return confirm('Excluir este item?')">Excluir</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p class="vazio">Nenhum item cadastrado.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:when>

        <%-- Vista publica/usuario: grade com busca --%>
        <c:otherwise>
            <div class="vitrine-header">
                <div class="vitrine-titulo">
                    <h1>Nossos Produtos</h1>
                    <span class="contador-badge" id="contador">${produtos.size()} itens</span>
                </div>
                <input class="busca-input" id="busca" type="search"
                       placeholder="Buscar produto por nome ou descricao...">
            </div>

            <div class="vitrine-corpo">
                <c:choose>
                    <c:when test="${not empty produtos}">
                        <div class="grid-produtos" id="grade">
                            <c:forEach var="p" items="${produtos}">
                                <div class="produto-card"
                                     data-busca="${p.nome} ${p.descricao}">
                                    <div class="card-icone">&#128220;</div>
                                    <h3>${p.nome}</h3>
                                    <p class="descricao">${p.descricao}</p>
                                    <div class="produto-rodape">
                                        <div class="produto-info">
                                            <span class="preco">R$ <fmt:formatNumber value="${p.preco}" pattern="#,##0.00"/></span>
                                            <span class="estoque">Estoque: ${p.quantidade}</span>
                                        </div>
                                        <c:choose>
                                            <c:when test="${not empty sessionScope.usuario}">
                                                <a class="btn-comprar" href="${pageContext.request.contextPath}/venda">Comprar</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="btn-comprar" href="${pageContext.request.contextPath}/login?msg=loginParaComprar">Comprar</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <p class="vazio" id="sem-resultado" style="display:none">
                            Nenhum produto encontrado para esta busca.
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p class="vazio">Nenhum item disponivel no momento.</p>
                    </c:otherwise>
                </c:choose>

                <c:if test="${empty sessionScope.usuario}">
                    <div class="banner-login">
                        <span>Quer comprar? <a href="${pageContext.request.contextPath}/login">Faca login</a> ou <a href="${pageContext.request.contextPath}/cadastro">crie uma conta</a>.</span>
                    </div>
                </c:if>
            </div>
        </c:otherwise>
    </c:choose>

    <jsp:include page="/WEB-INF/pages/includes/footer.jsp" />
</div>

<script>
(function () {
    var input = document.getElementById('busca');
    if (!input) return;
    var contador = document.getElementById('contador');
    var semResultado = document.getElementById('sem-resultado');
    var cards = document.querySelectorAll('.produto-card');

    input.addEventListener('input', function () {
        var q = this.value.toLowerCase().trim();
        var visible = 0;
        cards.forEach(function (card) {
            var match = !q || card.dataset.busca.toLowerCase().includes(q);
            card.style.display = match ? '' : 'none';
            if (match) visible++;
        });
        contador.textContent = visible + ' ' + (visible === 1 ? 'item' : 'itens');
        semResultado.style.display = visible === 0 ? 'block' : 'none';
    });
})();
</script>
</body>
</html>
