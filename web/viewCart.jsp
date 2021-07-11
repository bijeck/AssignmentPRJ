<%-- 
    Document   : viewCart
    Created on : Jun 18, 2021, 1:02:53 PM
    Author     : Administrator
--%>

<%@page import="sump.item.ItemDTO"%>
<%@page import="java.util.Map"%>
<%@page import="sump.cart.CartObject"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
    </head>
    <body>
        <h1>Your Cart include:</h1>
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:if test="${empty cart}">
            <h2>
                No cart is existed!!!
                <a href="shoppingPage">Go to Shopping</a>
            </h2>
        </c:if>
        <c:if test="${not empty cart}">
            <c:set var="items" value="${cart.items}"/>
            <c:if test="${empty items}">
            <h2>
                No items is existed!!!
                <a href="shoppingPage">Go to Shopping</a>
            </h2>
        </c:if>
            <c:if test="${not empty items}">
                <form action="cart">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Sku</th>
                                <th>Name</th>
                                <th>Quantity</th>
                                <th>Remove</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="entry" items="${items}" varStatus="counter">
                                <c:set var="item" value="${entry.value}"/>
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>${item.sku}</td>
                                    <td>${item.name}</td>
                                    <td>${item.quantity}</td>
                                    <td>
                                        <input type="checkbox" name="chkItem" 
                                               value="${item.sku}" />
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="4">

                                </td>
                                <td>
                                    <input type="submit" value="Remove Selected" name="btAction" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <input type="submit" value="Check Out" name="btAction" />
                </form>
                <a href="shoppingPage">Add More</a>
            </c:if>
        </c:if>

    </body>
</html>
