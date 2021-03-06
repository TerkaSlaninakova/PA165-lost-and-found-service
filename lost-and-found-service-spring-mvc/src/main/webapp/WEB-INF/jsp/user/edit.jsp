<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Edit User">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/user/edit/${user.id}/"
               modelAttribute="user" cssClass="form-horizontal">
                        <div class="form-group ${name_error?'has-error':''}">
                            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
                            <div class="col-sm-10">
                                <form:input path="name" cssClass="form-control" required="true"/>
                                <form:errors path="name" cssClass="help-block"/>
                            </div>

                            <form:label path="email" cssClass="col-sm-2 control-label">Email</form:label>
                            <div class="col-sm-10">
                                <form:input path="email" cssClass="form-control" required="true"/>
                                <form:errors path="email" cssClass="help-block"/>
                            </div>

                            <form:label path="password" cssClass="col-sm-2 control-label">Password</form:label>
                            <div class="col-sm-10">
                                <form:input type="password" path="password" cssClass="form-control" required="true"/>
                                <form:errors path="password" cssClass="help-block"/>
                            </div>

                            <form:label path="isAdmin" cssClass="col-sm-2 control-label">Is admin</form:label>
                            <div class="col-sm-10">
                                <form:checkbox path="isAdmin" cssClass="check-menu-item"/>
                                <form:errors path="isAdmin" cssClass="help-block"/>
                            </div>
                        </div>
        <button class="btn btn-primary" type="submit">Save changes</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>