<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Edit item">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/item/${item.id}/update"
               modelAttribute="item" cssClass="form-horizontal">
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>

            <form:label path="type" cssClass="col-sm-2 control-label">Type</form:label>
            <div class="col-sm-10">
                <form:input path="type" cssClass="form-control"/>
                <form:errors path="type" cssClass="help-block"/>
            </div>
            <form:label path="characteristics" cssClass="col-sm-2 control-label">Characteristics</form:label>
            <div class="col-sm-10">
                <form:input path="characteristics" cssClass="form-control"/>
                <form:errors path="characteristics" cssClass="help-block"/>
            </div>

            <form:label path="status" cssClass="col-sm-2 control-label">Status</form:label>
            <div class="col-sm-10">
            <form:select name="status" path="status" cssClass="form-control">
                <c:forEach items="${statuses}" var="stat">
                    <form:option value="${stat}">
                            <c:out value="${stat}"/>
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
        <a href="${pageContext.request.contextPath}/item/${item.id}/edit/${archived ? 'archive-text' : 'archive' }"
           class="btn btn-primary">
            <i class="fas fa-trash-alt">"${archived ? 'Get archived data' : 'Archive Item' }"</i>
        </a>
    </c:when>
    </c:choose>

    <a href="${pageContext.request.contextPath}/item/${item.id}/edit/category"
       class="btn btn-primary">
        <i class="fas fa-trash-alt">"Set category"</i>
    </a>
</jsp:attribute>
</my:pagetemplate>