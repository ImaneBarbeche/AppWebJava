package com.example.usermanagement.controller;

import com.example.usermanagement.dao.UserDAO;
import com.example.usermanagement.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServletTest {
    UserServlet servlet = new UserServlet(); // la classe que l'on test afin d'appeler doGet() et doPost()
    UserDAO mockDao = mock(UserDAO.class); // pour éviter d'utiliser la vraie base de données on mock UserDAO

    HttpServletRequest request = mock(HttpServletRequest.class); // fournir des paramètres
    HttpServletResponse response = mock(HttpServletResponse.class); // vérifier les redirections
    private RequestDispatcher dispatcher; // transmettre le traitement à une autre ressource (JSP)


      @Before // Préparer un environnement complet pour tester UserServlet
    public void setUp() throws Exception {
        servlet = new UserServlet(); // créer la servlet à tester
        mockDao = mock(UserDAO.class); // simuler des dépendances 
        request = mock(HttpServletRequest.class); // Crée un faux objet HttpServletRequest
        response = mock(HttpServletResponse.class); // Crée un faux objet HttpServletResponse
        dispatcher = mock(RequestDispatcher.class); // Crée un faux "dispatcheur" vers une page JSP

        // Injection "manuelle" du DAO mocké
        servlet.init(); // lancer l'initialisation 
        java.lang.reflect.Field daoField = UserServlet.class.getDeclaredField("userDAO"); // Récupérer le champ privé userDAO à l’intérieur de la classe UserServlet
        daoField.setAccessible(true); // autorise l'accès même si c'est private
        daoField.set(servlet, mockDao); // injecte le mock dans le champ userDAO
    }

    @Test
    public void testDoPost_ajoutUtilisateur() throws Exception {
        // simuler les champs du formulaire 
        when(request.getParameter("name")).thenReturn("Test");
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("phone")).thenReturn("0123456789");
        when(request.getParameter("dateNaissance")).thenReturn("2000-01-01");

        servlet.doPost(request, response); // on simule l'appel à doPost()

        verify(mockDao).add(any(User.class)); // vérifie si add() est appelé
        verify(response).sendRedirect("users"); // vérifie la redirection sur la page users après ajout
    }

    @Test
    public void testDoGet_affichageListe() throws Exception {
        // Création de faux utilisateurs
        List<User> fakeUsers = new ArrayList<>();
        fakeUsers.add(new User(1, "Alice", "a@a.com", "0123", LocalDate.of(1990, 1, 1)));

        // Simulation du comportement du DAO et de la requête
        when(mockDao.listAll()).thenReturn(fakeUsers);
        when(request.getRequestDispatcher("listUsers.jsp")).thenReturn(dispatcher);

        // Exécution de la méthode testée
        servlet.doGet(request, response);

        // Vérifications Mockito
        verify(request).setAttribute(eq("users"), eq(fakeUsers));
        verify(dispatcher).forward(request, response);
    }


   // Nettoyer / réinitialiser 
@After
public void tearDown() {
    File f = new File("test-users.db");
    if (f.exists()) {
        f.delete();
    }
}


}
