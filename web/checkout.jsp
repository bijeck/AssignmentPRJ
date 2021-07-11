<%-- 
    Document   : checkout
    Created on : Jul 5, 2021, 8:27:56 PM
    Author     : Administrator
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Your Information: </h1>

        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:if test="${not empty cart}">
            <c:set var="items" value="${cart.items}"/>
            <c:if test="${not empty items}">
                <form action="checkout">
                    Name:<input type="text" name="txtCustomerName" value="" /><br/>
                    Address:<input type="text" name="txtCustomerAddress" value="" />
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Sku</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="totalPrice" value="${0.0}"/>
                            <c:forEach var="entry" items="${items}" varStatus="counter">
                                <c:set var="item" value="${entry.value}"/>
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>${item.sku}</td>
                                    <td>${item.name}</td>
                                    <td>${item.price}</td>
                                    <td>${item.quantity}</td>
                                    <td>
                                        <c:set var="total" value="${item.quantity*item.price}"/>
                                        ${total}
                                        <c:set var="priceSum" value="${totalPrice + total}"/>
                                        <c:set var="totalPrice" value="${priceSum}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="5">
                                    Total Money:
                                </td>
                                <td >
                                    ${totalPrice}
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <input type="submit" value="Check out" name="btAction"/>
                </form>
                <a href="shoppingPage">Add More</a>
            </c:if>
        </c:if>

    </body>
</html>
