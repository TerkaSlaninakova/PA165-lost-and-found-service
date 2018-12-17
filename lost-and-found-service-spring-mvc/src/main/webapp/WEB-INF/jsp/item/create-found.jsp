<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New item">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/item/create-found"
               modelAttribute="itemCreateFound" cssClass="form-horizontal">
        <div class="form-group">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="error"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="characteristics" cssClass="col-sm-2 control-label">Characteristics</form:label>
            <form:input path="characteristics" cssClass="form-control"/>
            <form:errors path="characteristics" cssClass="help-block"/>
        </div>
         <div class="form-group">
            <form:label path="type" cssClass="col-sm-2 control-label">Type</form:label>
            <div class="col-sm-10">
                <form:input path="type" cssClass="form-control"/>
                <form:errors path="type" cssClass="help-block"/>
            </div>
         </div>
         <div class="form-group">
            <form:label path="foundLocationId" cssClass="col-sm-2 control-label">Location</form:label>
            <div class="col-sm-10">
            <form:select name="foundLocationId" path="foundLocationId" cssClass="form-control">
                    <c:forEach items="${locations}" var="location">
                        <form:option value="${location.id}">
                            <c:out value="${location.description}"/>
                        </form:option>
                    </c:forEach>
            </form:select>
                <form:errors path="foundLocationId" cssClass="help-block"/>
            </div>
         </div>
        <div class="form-group">
            <form:label path="foundDate" cssClass="col-sm-2 control-label">Lost Date</form:label>
            <div class="form-group col-md-3">
                <form:input  type="date" path="foundDate" ></form:input>
                <form:errors path="foundDate" cssClass="invalid-feedback"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Create found item</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>