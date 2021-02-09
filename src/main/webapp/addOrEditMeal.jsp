<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <title>Edit meal</title>
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

        label {
            margin-right: 100px;
        }

    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<c:set var="meal" value="${requestScope.oldMeal}"/>
<h2><c:out value="${meal.mealId > 0 ? 'Edit meal' : 'Create meal'}"/></h2>
<form action="meals" method="post">
    <label for="datetime">DateTime: </label>
    <input id="datetime" type="datetime-local" name="data" value="${meal.dateTime}">
    <br>
    <br>
    <label for="description">Description: </label>
    <input id="description" type="text" name="description" value="${meal.description}" style="width: 200px">
    <br>
    <br>
    <label for="calories">Calories: </label>
    <input id="calories" type="number" name="calories" value="${meal.calories}">
    <input type="hidden" name="id" value="${meal.mealId}">
    <br>
    <br>
    <button type="submit" value="Save">Save</button>
    <input type="button" onclick="window.location.href = 'meals' " value="Cancel"/>
</form>
</body>
</html>

