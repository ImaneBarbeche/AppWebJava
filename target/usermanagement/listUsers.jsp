<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="java.util.List" %>
        <%@ page import="com.example.usermanagement.model.User" %>
            <!DOCTYPE html>

            <html>

            <head>
                <title>Liste des utilisateurs enregistrés</title>
                <link rel="stylesheet" type="text/css" href="css/styles.css">
            </head>

            <body>
                <p><a href="index.jsp">Ajouter un nouvel utilisateur</a></p>

                <h1>Liste des utilisateurs enregistrés</h1>

                <% List<User> users = (List<User>) request.getAttribute("users");
                        %>
                        <table border="1">
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Email</th>
                                <th>Téléphone</th>
                                <th>Date de naissance</th>
                                <th>Actions</th>

                            </tr>
                            <% for (User u : users) { %>
                                <tr>
                                    <td>
                                        <%= u.getId() %>
                                    </td>
                                    <td>
                                        <%= u.getName() %>
                                    </td>
                                    <td>
                                        <%= u.getEmail() %>
                                    </td>
                                    <td>
                                        <%= u.getPhone() %>
                                    </td>
                                    <td>
                                        <%= u.getDateNaissance() %>
                                    </td>
                                    <td>
                                        <a href="users?action=delete&id=<%= u.getId() %>" class="delete-btn">🗑
                                            Supprimer</a>
                                    </td>
                                </tr>
                                <% } %>

                        </table>

            </body>

            </html>