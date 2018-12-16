<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Users">
<jsp:attribute name="body">

    <my:a href="/user/new" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        New User
    </my:a>

    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>email</th>
            <%--<th>edit</th>--%>
            <%--<th>delete</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <%--<td><a href="${pageContext.request.contextPath}/user/${user.id}/update" class="btn btn-primary">--%>
                    <%--<i class="fas fa-edit"></i>--%>
                <%--</a></td>--%>
                <%--<td><a href="${pageContext.request.contextPath}/user/${user.id}/delete" class="btn btn-danger">--%>
                    <%--<i class="fas fa-trash-alt"></i>--%>
                <%--</a></td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pagetemplate>