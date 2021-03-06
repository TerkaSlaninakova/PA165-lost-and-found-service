<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Items">
<jsp:attribute name="body">

    <my:a href="/item/create-found" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        Add found item
    </my:a>

    <my:a href="/item/create-lost" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        Add lost item
    </my:a>

    <h3> Filter Items </h3>
    <form:form method="post" action="${pageContext.request.contextPath}/item/all"
                   modelAttribute="search" id="searchItems" cssClass="form">
        <div class="form-group row">
            <form:label path="status" cssClass="col-sm-1 control-label">Status</form:label>
            <div class="col-md-3 col-sm-3">
            <form:select name="status" path="status" cssClass="form-control">
                <form:option value="${null}">
                    <c:out value="ALL"/>
                </form:option>
                <c:forEach items="${statuses}" var="stat">
                    <form:option value="${stat}">
                        <c:if test="${stat== 'RESOLVED'}">
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
        <div class="form-group row">
            <form:label path="categoryName" cssClass="col-sm-1 control-label">Category</form:label>
            <div class="col-md-3 col-sm-3">
            <form:select name="categoryName" path="categoryName" cssClass="form-control">
                <form:option value="">
                    <c:out value="ALL"/>
                </form:option>
                <c:forEach items="${categories}" var="cat">
                    <form:option value="${cat.name}">
                            <c:out value="${cat.name}"/>
                    </form:option>
                </c:forEach>
            </form:select>
                <p class="help-block"><form:errors path="categoryName" cssClass="error"/></p>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-md-3 col-sm-3">
        <button class="btn btn-primary" type="submit">Search</button>
            </div>
        </div>
    </form:form>

    <table class="table table-bordered">
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
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${items}" var="item">
            <c:set var="idAsString">${item.id}</c:set>
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/item/detail/${item.id}/">
                        <c:out value="${item.name}"/>
                    </a>
                </td>
                <td><c:out value="${item.characteristics}"/></td>
                <td>
                    <c:if test="${item.foundLocation != null}">
                    <a href="${pageContext.request.contextPath}/location/edit/${item.foundLocation.id}/">
                        <c:out value="${item.foundLocation.description}"/>
                    </a>
                    </c:if>
                </td>
                <td>${item.foundDate.toString()}</td>
                <td><c:out value="${item.lostLocation.description}"/></td>
                <td>${item.lostDate.toString()}</td>
                <td>${item.owner.name}</td>
                <td>
                    <c:if test="${item.status.toString() == 'RESOLVED'}">
                        <fmt:message key="item.status.resolved"/>
                    </c:if>
                    <c:if test="${item.status.toString() == 'CLAIM_RECEIVED_FOUND'}">
                        <fmt:message key="item.status.found"/>
                    </c:if>
                    <c:if test="${item.status.toString() == 'CLAIM_RECEIVED_LOST'}">
                        <fmt:message key="item.status.lost"/>
                    </c:if>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${item.status != 'RESOLVED'}">
                            <a href="${pageContext.request.contextPath}/item/resolve/${item.id}/" class="btn btn-primary">
                                Resolve
                            </a>
                        </c:when>
                    </c:choose>
                    <a href="${pageContext.request.contextPath}/item/detail/${item.id}/" class="btn btn-primary">
                        Detail
                    </a>
                    <c:choose>
                    <c:when test="${requestScope[idAsString]}">
                    <a href="${pageContext.request.contextPath}/item/edit/${item.id}/" class="btn btn-primary">
                        Edit
                    </a>
                    </c:when>
                    </c:choose>
                    <c:choose>
                        <c:when test="${requestScope[idAsString]}">
                            <a href="${pageContext.request.contextPath}/item/delete/${item.id}/" class="btn btn-danger">
                                Remove
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