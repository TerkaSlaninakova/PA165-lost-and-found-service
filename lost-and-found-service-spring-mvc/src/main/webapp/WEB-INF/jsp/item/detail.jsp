<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Edit item">
<jsp:attribute name="body">

    <h1><c:out value="${item.name}"/></h1>
    <label>Type:</label>
    <c:out value="${item.type}"/>
    </br>
    <label>Characteristic:</label>
    <c:out value="${item.characteristics}"/>
    </br>
    <c:if test="${item.status == 'RESOLVED' || item.status == 'CLAIM_RECEIVED_FOUND'}">
    <label>Found date:</label>
    <c:out value="${item.foundDate}"/>
    </br>
    </c:if>
    <c:if test="${item.status == 'RESOLVED' || item.status == 'CLAIM_RECEIVED_LOST'}">
    <label>Lost date:</label>
    <c:out value="${item.lostDate}"/>
    </br>
    </c:if>
    <label>Status:</label>
    <c:if test="${item.status == 'RESOLVED'}">
        <fmt:message key="item.status.resolved"/>
    </c:if>
    <c:if test="${item.status == 'CLAIM_RECEIVED_FOUND'}">
        <fmt:message key="item.status.found"/>
    </c:if>
    <c:if test="${item.status == 'CLAIM_RECEIVED_LOST'}">
        <fmt:message key="item.status.lost"/>
    </c:if>
    </br>
    <label>Owner:</label>
    <c:out value="${item.owner.name}"/>
    </br>

    <c:if test="${not empty item.archive}">
        <a href="${pageContext.request.contextPath}/item/detail/${item.id}/archive-text"
           class="btn btn-success">
            "${'Get archived data'}"
        </a>
    </c:if>

    <h3>Categories</h3>
        <c:forEach items="${categories}" var="category">
                <c:choose>
                    <c:when test="${item.categories.contains(category)}">
                        <c:out value="${category.name}"/>
                    <br>
                    </c:when>
                </c:choose>
        </c:forEach>

</jsp:attribute>
</my:pagetemplate>