<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Locations">
<jsp:attribute name="body">

       <c:choose>
        <c:when test="${admin}">
               <my:a href="/location/new" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        New location
    </my:a>
        </c:when>
        <c:otherwise>
            <p>Only admin can create and edit locations</p>
        </c:otherwise>
    </c:choose>

    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${locations}" var="location">
            <tr>
                <td>${location.id}</td>
                <td><c:out value="${location.description}"/></td>
                <td>
                        <c:choose>
                         <c:when test="${admin}">
                             <a href="${pageContext.request.contextPath}/location/edit/${location.id}/" class="btn btn-primary">
                                 Edit
                             </a>
                         </c:when>
                     </c:choose>
                </a></td>
                <td>
                     <c:choose>
                         <c:when test="${admin}">
                    <a href="${pageContext.request.contextPath}/location/delete/${location.id}/" class="btn btn-danger">
                        Delete
                    </a>
                         </c:when>
                     </c:choose>
                </a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</jsp:attribute>
</my:pagetemplate>