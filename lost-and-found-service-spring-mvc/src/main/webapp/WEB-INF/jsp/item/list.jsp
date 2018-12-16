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
        New item found
    </my:a>

    <my:a href="/item/new-lost" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        New item lost
    </my:a>

    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>characteristics</th>
            <th>found location</th>
            <th>found date</th>
            <th>lost location</th>
            <th>lost date</th>
            <th>owner</th>
            <th>status</th>
            <th>edit</th>
            <th>delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${items}" var="item">
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.characteristics}</td>
                <td>${item.foundLocation.description}</td>
                <td>${item.foundDate.toString()}</td>
                <td>${item.lostLocation.description}</td>
                <td>${item.lostDate.toString()}</td>
                <td>${item.owner.name}</td>
                <td>${item.status.toString()}</td>
                <td><a href="${pageContext.request.contextPath}/item/${item.id}/update" class="btn btn-primary">
                    <i class="fas fa-edit"></i>
                </a></td>
                <td><a href="${pageContext.request.contextPath}/item/${item.id}/delete" class="btn btn-danger">
                    <i class="fas fa-trash-alt"></i>
                </a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pagetemplate>