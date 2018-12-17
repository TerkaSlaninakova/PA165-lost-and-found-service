<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Items">
<jsp:attribute name="body">

    <my:a href="/item/new-found" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        Add found item
    </my:a>

    <my:a href="/item/new-lost" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        Add lost item
    </my:a>

    <table class="table">
        <thead>
        <tr>
            <th>name</th>
            <th>characteristics</th>
            <th>found location</th>
            <th>found date</th>
            <th>lost location</th>
            <th>lost date</th>
            <th>owner</th>
            <th>status</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${items}" var="item">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/item/${item.id}">
                        <c:out value="${item.name}"/>
                    </a>
                </td>
                <td><c:out value="${item.characteristics}"/></td>
                <td>
                    <c:if test="${item.foundLocation != null}">
                    <a href="${pageContext.request.contextPath}/location/${item.foundLocation.id}">
                        <c:out value="${item.foundLocation.description}"/>
                    </a>
                    </c:if>
                </td>
                <td>${item.foundDate.toString()}</td>
                <td>
                    <c:out value="${item.lostLocation.description}"/>
                </td>
                <td>${item.lostDate.toString()}</td>
                <td>${item.owner.name}</td>
                <td>${item.status.toString()}</td>
                <td>
                    <c:choose>
                    <c:when test="${item.status != 'RESOLVED'}">
                    <a href="${pageContext.request.contextPath}/item/resolve/${item.id}/" class="btn btn-primary">
                        Resolve
                    </a>
                    </c:when>
                    </c:choose>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/item/edit/${item.id}/" class="btn btn-primary">
                        Edit
                    </a>
                </td>
                <td><a href="${pageContext.request.contextPath}/item/delete/${item.id}/" class="btn btn-danger">
                    Remove
                </a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>