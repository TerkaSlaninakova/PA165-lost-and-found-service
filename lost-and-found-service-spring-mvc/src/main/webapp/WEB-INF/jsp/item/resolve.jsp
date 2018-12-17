<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Resolve item">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/item/resolve/${item.id}"
               modelAttribute="item" cssClass="form-horizontal">
         <div class="form-group">
             <form:label path="" cssClass="col-sm-2 control-label">Location</form:label>
             <div class="col-sm-10">
            <form:select name="locationId" path="locationId" cssClass="form-control">
                    <c:forEach items="${locations}" var="location">
                        <form:option value="${location.id}">
                            <c:out value="${location.description}"/>
                        </form:option>
                    </c:forEach>
            </form:select>
                 <form:errors path="locationId" cssClass="help-block"/>
             </div>
         </div>
        <div class="form-group">
            <form:label path="date" cssClass="col-sm-2 control-label">Date</form:label>
            <div class="form-group col-md-3">
                <form:input  type="date" path="date" ></form:input>
                <form:errors path="date" cssClass="invalid-feedback"/>
            </div>
        </div>
        <c:choose>
            <c:when test="${item.status == 'CLAIM_RECEIVED_FOUND'}">
             <div class="form-group">
                 <form:label path="" cssClass="col-sm-2 control-label">Owner</form:label>
                 <div class="col-sm-10">
                <form:select name="ownerId" path="ownerId" cssClass="form-control">
                        <c:forEach items="${users}" var="user">
                            <form:option value="${user.id}">
                                <c:out value="${user.name}"/>
                            </form:option>
                        </c:forEach>
                </form:select>
                     <form:errors path="ownerId" cssClass="help-block"/>
                 </div>
             </div>
            </c:when>
        </c:choose>
        <button class="btn btn-primary" type="submit">Save changes</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>