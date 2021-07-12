<%-- 
    Document   : search
    Created on : Jun 7, 2021, 12:46:17 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
        <c:set var="userLogin" value="${sessionScope.LOGIN_USERNAME}"/>
        <script>
            function checkDeleteUserLogin(username) {
                var userLogin = '${userLogin}';
                var message = 'You will delete a account. Are you sure to continue?';
                if (userLogin === username) {
                    message = 'You will delete your account. So you will logout. Are you sure to continue?';
                }
                return confirm(message);

            }
        </script>
    </head>
    <body>
        <font color="red">
        Welcome, ${sessionScope.LASTNAME}
        </font>
        <h1>Search Page</h1>
        <form action="searchLastName">
            Search Value <input type="text" name="txtSearchValue" 
                                value="${param.txtSearchValue}" /><br>
            <input type="submit" value="Search" name="btAction" />
            <a href="logout">Log Out!</a>
        </form><br/>
        
        <c:set var="searchValue" value="${param.txtSearchValue}"/>
        <c:set var="errors" value="${requestScope.UPDATE_ERRORS}"/>
        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>

            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                            <th>Edit</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                        <form action="update" method="POST" >
                            <tr>
                                <td>
                                    ${counter.count}.
                                </td>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" name="txtUsername" value="${dto.username}" />
                                </td>
                                <td>
                                    <input type="text" name="txtPassword" value="${dto.password}" /><br/>
                                    <c:if test="${not empty errors}">
                                        <c:if test="${errors.username eq dto.username}">
                                            <c:if test="${not empty errors.passwordLengthErr}">
                                                <font color="red">
                                                ${errors.passwordLengthErr}<br>
                                                </font>
                                            </c:if>
                                        </c:if>
                                    </c:if>
                                </td>
                                <td>
                                    ${dto.lastname}
                                </td>
                                <td>
                                    <input type="checkbox" name="chkAdmin" value="ON" 
                                           <c:if test="${dto.role}">
                                               checked="checked"
                                           </c:if>
                                           />
                                </td>
                                <td>
                                    <c:url var="deleteLink" 
                                           value="delete">
                                        <c:param name="btAction" value="Delete"/>
                                        <c:param name="username" value="${dto.username}"/>
                                        <c:param name="lastSearchValue" value="${searchValue}"/>
                                    </c:url>
                                    <a href="${deleteLink}" onclick="return checkDeleteUserLogin('${dto.username}');">Delete</a>
                                </td>
                                <td>
                                    <input type="submit" value="Update" name="btAction" />
                                </td>
                                <td>
                                    <c:url var="editLink" 
                                           value="edit">
                                        <c:param name="txtUsername" value="${dto.username}"/>
                                        <c:param name="lastSearchValue" value="${searchValue}"/>
                                    </c:url>
                                    <a href="${editLink}">Edit</a>
                                </td>
                            <input type="hidden" value="${searchValue}" name="lastSearchValue" />
                            </tr> 
                        </form>
                    </c:forEach>
                </tbody>
            </table>

        </c:if>
        <c:if test="${empty result}">
            <h2>
                No record matched!!!!
            </h2>
        </c:if>
    </c:if>
</body>
</html>
