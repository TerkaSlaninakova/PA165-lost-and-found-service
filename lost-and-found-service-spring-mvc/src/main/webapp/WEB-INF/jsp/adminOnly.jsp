<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title = "Forbidden access">
<jsp:attribute name="body">

    <h1>401 Unauthorized</h1>
    <img src="${pageContext.request.contextPath}/droids.jpg">
    <h2>You are not authorized to access this page or operation</h2>
    Only admin (or item owner in case of items) can access this operation.


</jsp:attribute>
</my:pagetemplate>