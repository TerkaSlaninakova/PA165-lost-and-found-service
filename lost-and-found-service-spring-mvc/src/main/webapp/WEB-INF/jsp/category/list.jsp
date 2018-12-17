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
        <c:otherwise>
            <h2>Only admin can create and edit categories</h2>
        </c:otherwise>
    </c:choose>


    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>attribute</th>
            <th>edit</th>
            <th>delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${categories}" var="category">
            <tr>
                <td>${category.id}</td>
                <td><c:out value="${category.name}"/></td>
                <td><c:out value="${category.attribute}"/></td>
                <td>
                     <c:choose>
                         <c:when test="${admin}">

                    <a href="${pageContext.request.contextPath}/category/edit/${category.id}/" class="btn btn-primary">
                        <i class="fas fa-edit"></i>
                    </a>
                         </c:when>
                         <c:otherwise>
                             (can't acess)
                         </c:otherwise>
                     </c:choose>
                </td>
                <td>
                     <c:choose>
                         <c:when test="${admin}">

                    <a href="${pageContext.request.contextPath}/category/delete/${category.id}/" class="btn btn-danger">
                        <i class="fas fa-trash-alt"></i>
                    </a>
                         </c:when>
                         <c:otherwise>
                             (can't acess)
                         </c:otherwise>
                     </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pagetemplate>