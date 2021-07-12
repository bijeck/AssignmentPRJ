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
        <c:set var="dto" value="${requestScope.DTO}"/>
        <c:set var="username" value="${dto.username}"/>
        <c:set var="password" value="${dto.password}"/>
        <c:set var="lastname" value="${dto.lastname}"/>
        <c:set var="role" value="${dto.role}"/>
        <c:if test="${empty dto}">
            <c:set var="username" value="${param.txtUsername}"/>
            <c:set var="password" value="${param.txtPassword}"/>
            <c:set var="lastname" value="${param.txtLastname}"/>
            <c:set var="role" value="${param.role}"/>
        </c:if>
        <c:set var="errors" value="${requestScope.EDIT_ERRORS}"/>
        <form action="confirmPage" method="POST">
            Username: ${username}<br/>
            <input type="hidden" name="txtUsername" value="${username}" />
            Password:<input type="text" name="txtPassword" value="${password}" /><br/>
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                ${errors.passwordLengthErr}<br>
                </font>
            </c:if>
            Last name:<input type="text" name="txtLastname" value="${lastname}" /><br/>
            <c:if test="${not empty errors.fullnameLengthErr}">
                <font color="red">
                ${errors.fullnameLengthErr}<br/>
                </font>
            </c:if>
            Role: <input type="checkbox" name="chkAdmin" value="ON" 
                         <c:if test="${role}">
                             checked="checked"
                         </c:if>
                         />Admin<br/>
            <input type="submit" value="Edit" name="btAction"/>
            <input type="hidden" name="lastSearchValue" value="${lastSearchValue}" />
            <input type="hidden" name="role" value="${role}" />
        </form>
        <c:url var="searchLink" value="searchLastName">
            <c:param name="txtSearchValue" value="${lastSearchValue}"/>
        </c:url>
        <a href="${searchLink}">Back to Search</a>

    </body>
</html>
