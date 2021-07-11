<%-- 
    Document   : edit
    Created on : Jul 6, 2021, 9:01:18 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Page</title>
    </head>
    <body>
        <font color="red">
        Welcome, ${sessionScope.LASTNAME}
        </font>
        <h1>Account information:</h1>
        <c:set var="lastSearchValue" value="${param.lastSearchValue}"/>
        <c:set var="errors" value="${requestScope.EDIT_ERRORS}"/>
        <form action="confirmPage" method="POST">
            Username: ${param.txtUsername}<br/>
            <input type="hidden" name="txtUsername" value="${param.txtUsername}" />
            Password:<input type="text" name="txtPassword" value="${param.txtPassword}" /><br/>
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                ${errors.passwordLengthErr}<br>
                </font>
            </c:if>
            Last name:<input type="text" name="txtLastname" value="${param.txtLastname}" /><br/>
            <c:if test="${not empty errors.fullnameLengthErr}">
                <font color="red">
                ${errors.fullnameLengthErr}<br/>
                </font>
            </c:if>
            Role: <input type="checkbox" name="chkAdmin" value="ON" 
                         <c:if test="${param.role}">
                             checked="checked"
                         </c:if>
                         />Admin<br/>
            <input type="submit" value="Edit" name="btAction"/>
            <input type="hidden" name="lastSearchValue" value="${lastSearchValue}" />
            <input type="hidden" name="role" value="${param.role}" />
        </form>
        <c:url var="searchLink" value="searchLastName">
            <c:param name="txtSearchValue" value="${lastSearchValue}"/>
        </c:url>
        <a href="${searchLink}">Back to Search</a>

    </body>
</html>
