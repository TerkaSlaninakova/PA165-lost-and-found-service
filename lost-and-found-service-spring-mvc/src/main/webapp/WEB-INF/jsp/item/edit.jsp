<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Edit item">
<jsp:attribute name="body">
    <form:form method="post" action="${pageContext.request.contextPath}/item/edit/${item.id}"
               modelAttribute="item" cssClass="form-horizontal">
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control" required="true"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>

            <form:label path="type" cssClass="col-sm-2 control-label">Type</form:label>
            <div class="col-sm-10">
                <form:input path="type" cssClass="form-control" required="true"/>
                <form:errors path="type" cssClass="help-block"/>
            </div>
            <form:label path="characteristics" cssClass="col-sm-2 control-label">Characteristics</form:label>
            <div class="col-sm-10">
                <form:input path="characteristics" cssClass="form-control" required="true"/>
                <form:errors path="characteristics" cssClass="help-block"/>
            </div>

            <form:label path="status" cssClass="col-sm-2 control-label">Status</form:label>
            <div class="col-sm-10">
                <form:select name="status" path="status" cssClass="form-control">
                    <c:forEach items="${statuses}" var="stat">
                        <form:option value="${stat}">
                            <c:if test="${stat == 'RESOLVED'}">
                                <fmt:message key="item.status.resolved"/>
                            </c:if>
                            <c:if test="${stat == 'CLAIM_RECEIVED_FOUND'}">
                                <fmt:message key="item.status.found"/>
                            </c:if>
                            <c:if test="${stat == 'CLAIM_RECEIVED_LOST'}">
                                <fmt:message key="item.status.lost"/>
                            </c:if>
                        </form:option>
                    </c:forEach>
                </form:select>
                <p class="help-block"><form:errors path="status" cssClass="error"/></p>
            </div>

        </div>
        <button class="btn btn-primary" type="submit">Save changes</button>
    </form:form>
    <c:choose>
        <c:when test="${item.status == 'RESOLVED'}">
            <a href="${pageContext.request.contextPath}/item/edit/${item.id}/${archived ? 'archive-text' : 'archive' }"
               class="btn btn-success">
                "${archived ? 'Get archived data' : 'Archive Item' }"
            </a>
        </c:when>
    </c:choose>
    <h2>Manage categories with item</h2>
    <table class="table">
        <thead>
        <tr>
            <th>name</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${categories}" var="category">

            <tr>
                <c:choose>
                    <c:when test="${!item.categories.contains(category)}">
                        <td><c:out value="${category.name}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/item/edit/${item.id}/category/set/${category.id}"
                               class="btn btn-success">
                                Set to item
                            </a>
                        </td>
                    </c:when>
                    <c:when test="${item.categories.contains(category)}">
                        <td><c:out value="${category.name}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/item/edit/${item.id}/category/remove/${category.id}"
                               class="btn btn-warning">
                                Remove from item
                            </a>
                        </td>
                    </c:when>
                </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>