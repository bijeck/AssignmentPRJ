<%-- 
    Document   : onlineShopping
    Created on : Jun 20, 2021, 8:56:41 PM
    Author     : Administrator
--%>
<%@page import="sump.item.OutOfStockItem"%>
<%@page import="sump.item.ItemDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Online Shopping</title>
    </head>
    <body>
        <h1>Shopping View</h1>
        <c:set var="outOfStockItems" value="${sessionScope.OUT_OF_STOCK_ITEMS}"/>
        <c:if test="${not empty outOfStockItems}">
            <c:set var="outOfStockList" value="${outOfStockItems.items}"/>
        </c:if>
        <c:set var="items" value="${sessionScope.ITEMS}"/>
        <c:if test="${empty items}">
            <h2>No product is available for Shopping!!!</h2>
            <h2>We are so sorry about this!!!</h2>
            <a href="loginPage">Click here to Login Page</a>
        </c:if>
        <c:if test="${not empty items}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>sku</th>
                        <th>name</th>
                        <th>price</th>
                        <th>Add To Cart</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${items}" varStatus="counter">
                    <form action="cart">
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                ${dto.sku}
                                <input type="hidden" name="txtSku" 
                                       value="${dto.sku}" />
                            </td>
                            <td>${dto.name}</td>
                            <td>${dto.price}</td>
                            <td>
                                <c:set var="check" value="${false}"/>
                                <c:if test="${not empty outOfStockList}">
                                    <c:forEach var="item" items="${outOfStockList}" varStatus="counter">
                                        <c:if test="${dto.sku eq item}">
                                            <c:set var="check" value="${true}"/>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${check}">
                                    <font color="red">Sold Out</font>
                                </c:if>
                                <c:if test="${!check}">
                                    <input type="submit" value="Add To Cart" name="btAction" />
                                </c:if>
                            </td>
                        </tr>
                    </form> 
                </c:forEach>
            </tbody>
        </table>
        <form action="viewCart">
            <input type="submit" value="View Cart"/>
        </form>
        <a href="loginPage">Click here to Login Page</a>
    </c:if>
</body>
</html>
