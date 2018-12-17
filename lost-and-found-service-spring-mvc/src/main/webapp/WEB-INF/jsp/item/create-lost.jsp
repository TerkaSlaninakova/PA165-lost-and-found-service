<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Report lost item">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/item/create-lost"
               modelAttribute="itemCreateLost" cssClass="form-horizontal">
        <div class="form-group">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control" required="true"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="characteristics" cssClass="col-sm-2 control-label">Characteristics</form:label>
            <div class="col-sm-10">
                <form:input path="characteristics" cssClass="form-control" required="true"/>
                <form:errors path="characteristics" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="type" cssClass="col-sm-2 control-label">Type</form:label>
            <div class="col-sm-10">
                <form:input path="type" cssClass="form-control" required="true"/>
                <form:errors path="type" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="lostLocationId" cssClass="col-sm-2 control-label">Location</form:label>
            <div class="col-sm-10">
            <form:select name="lostLocationId" path="lostLocationId" cssClass="form-control">
                    <c:forEach items="${locations}" var="location">
                        <form:option value="${location.id}">
                            <c:out value="${location.description}"/>
                        </form:option>
                    </c:forEach>
            </form:select>
                <form:errors path="lostLocationId" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="ownerId" cssClass="col-sm-2 control-label">Owner</form:label>
            <div class="col-sm-10">
            <form:select name="ownerId" path="ownerId" cssClass="form-control">
                    <c:forEach items="${users}" var="user">
                        <form:option value="${user.id}">
                            <c:out value="${user.name}"/>
                        </form:option>
                    </c:forEach>
            </form:select>
                <form:errors path="lostLocationId" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="lostDate" cssClass="col-sm-2 control-label">Lost Date</form:label>
            <div class="form-group col-md-3">
                <form:input  type="date" path="lostDate" ></form:input>
                <form:errors path="lostDate" cssClass="invalid-feedback"/>
            </div>

        </div>
        <button class="btn btn-primary" type="submit">Create lost item</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>