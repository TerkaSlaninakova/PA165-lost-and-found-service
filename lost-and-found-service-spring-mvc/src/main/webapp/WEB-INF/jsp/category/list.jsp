<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Categories">
<jsp:attribute name="body">

    <c:choose>
        <c:when test="${admin}">
            <my:a href="/category/new" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                New category
            </my:a>
        </c:when>
    </c:choose>


    <table class="table">
        <thead>
        <tr>
            <th>name</th>
            <th>attribute</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${categories}" var="category">
            <tr>
                <td><c:out value="${category.name}"/></td>
                <td><c:out value="${category.attribute}"/></td>
                <td>
                     <c:choose>
                         <c:when test="${admin}">

                    <a href="${pageContext.request.contextPath}/category/edit/${category.id}/" class="btn btn-primary">
                        Edit
                    </a>
                         </c:when>
                     </c:choose>
                </td>
                <td>
                     <c:choose>
                         <c:when test="${admin}">

                    <a href="${pageContext.request.contextPath}/category/delete/${category.id}/" class="btn btn-danger">
                        Delete
                    </a>
                         </c:when>
                     </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pagetemplate>