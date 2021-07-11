<%-- 
    Document   : createNewAccount
    Created on : Jun 28, 2021, 1:34:04 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Account</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="create" method="POST">
            <c:set var="errors" value="${requestScope.INSERT_ERRORS}"/>
            Username <input type="text" name="txtUsername" value="${param.txtUsername}" />(6 - 20 chars)</br>
            <c:if test="${not empty errors.usernameLengthErr}">
                <font color="red">
                ${errors.usernameLengthErr}<br>
                </font>
            </c:if>
            <c:if test="${not empty errors.usernameIsExisted}">
                <font color="red">
                ${errors.usernameIsExisted}<br>
                </font>
            </c:if>
            Password <input type="password" name="txtPassword" value="" />(6 - 30 chars)</br>
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                ${errors.passwordLengthErr}<br>
                </font>
            </c:if>
            Confirm <input type="password" name="txtConfirm" value="" /></br>
            <c:if test="${not empty errors.comfirmNotMatch}">
                <font color="red">
                ${errors.comfirmNotMatch}<br>
                </font>
            </c:if>
            Full name <input type="text" name="txtFullname" value="${param.txtFullname}" />(2 - 50 chars)</br>
            <c:if test="${not empty errors.fullnameLengthErr}">
                <font color="red">
                ${errors.fullnameLengthErr}</br>
                </font>
            </c:if>
            <input type="submit" value="Create New Account" name="btAction" />
            <input type="reset" value="Reset" />
        </form>
        <a href="loginPage">Click here to Login Page</a>
    </body>
</html>
