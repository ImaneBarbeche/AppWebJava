<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un utilisateur</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>

    <h1> 😺 Ajouter un nouvel utilisateur </h1>

    <form action="users" method="post">
        <label for="name">Nom :</label>
        <input type="text" id="name" name="name" required>

        <label for="email">Email :</label>
        <input type="email" id="email" name="email" required>

        <label for="phone">Téléphone :</label>
        <input type="text" id="phone" name="phone">

        <label for="dateNaissance">Date de naissance :</label>
        <input type="date" id="dateNaissance" name="dateNaissance" required>

        <button type="submit">Ajouter</button>
    </form>

    <p style="text-align:center; margin-top: 2rem;">
        <a href="users">Voir la liste des utilisateurs</a>
    </p>

</body>
</html>
