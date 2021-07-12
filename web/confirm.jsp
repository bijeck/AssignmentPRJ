<%-- 
    Document   : confirm
    Created on : Jul 11, 2021, 7:08:29 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Page</title>
    </head>
    <body>
        <font color="red">
        Welcome, ${sessionScope.LASTNAME}
        </font>
        <h1>Are you sure to continue?</h1>
        <form action="edit" method="POST" style="float:left;">
            <input type="hidden" name="txtUsername" value="${param.txtUsername}" />
            <input type="hidden" name="txtPassword" value="${param.txtPassword}" />
            <input type="hidden" name="txtLastname" value="${param.txtLastname}" />
            <input type="hidden" name="chkAdmin" value="${param.chkAdmin}" />
            <input type="hidden" name="lastSearchValue" value="${param.lastSearchValue}" />
            <input type="submit" value="Yes" name="btAction"/>
        </form>
        <form action="editPage" method="POST" >
            <input type="hidden" name="txtUsername" value="${param.txtUsername}" />
            <input type="hidden" name="txtPassword" value="${param.txtPassword}" />
            <input type="hidden" name="txtLastname" value="${param.txtLastname}" />
            <c:set var="chkAdmin" value="${param.chkAdmin}"/>
            <c:if test="${empty chkAdmin}">
                <c:set var="role" value="${false}"/>
            </c:if>
            <c:if test="${not empty chkAdmin}">
                <c:set var="role" value="${param.role}"/>
            </c:if>
            <input type="hidden" name="role" value="${role}" />
            <input type="hidden" name="lastSearchValue" value="${param.lastSearchValue}" />
            <input type="submit" value="Cancel" name="btAction" style="margin-left:2%;"/>
        </form>
    </body>
</html>
