<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <title>Meals</title>
    <style type="text/css">
        table {
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
            table-layout: fixed; /* Фиксированная ширина ячеек */
            width: 100%; /* Ширина таблицы */
        }
        td, th {
            padding: 3px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
        }

    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tbody>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:set var="meals" value="${requestScope.mealsToList}" />
    <c:forEach items="${meals}" var="meal">
        <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="dateForFormat"/>
        <fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${dateForFormat}" var="date" />
        <c:choose>
            <c:when test="${meal.excess == 'true'}">
                <tr style="color: red">
                    <td>${date}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="link">Update</a></td>
                    <td><a href="link">Delete</a></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr style="color: green">
                    <td>${date}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="link">Update</a></td>
                    <td><a href="link">Delete</a></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
