package com.example.usermanagement.dao;

import com.example.usermanagement.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOTest {

    private UserDAO dao;

    @Before
    public void setUp() {
        dao = new UserDAO();
    }

    @Test
    public void testAddAndListAll() {
        User u = new User(1, "Minou", "minou@catmail.com", "0011223344", LocalDate.of(2000, 1, 1));
        dao.add(u);
        List<User> users = dao.listAll();
        boolean found = users.stream().anyMatch(user -> user.getEmail().equals("minou@catmail.com"));

        assertTrue("L'utilisateur devrait exister après ajout", found);
    }

    @Test
    public void testDelete() {
        User u = new User(0, "ToDelete", "delete@example.com", "000", LocalDate.of(1999, 12, 31));
        dao.add(u);
        List<User> before = dao.listAll();

        // On récupère l'utilisateur ajouté
        User toDelete = before.stream()
                .filter(user -> user.getEmail().equals("delete@example.com"))
                .findFirst()
                .orElse(null);

        assertNotNull("Utilisateur à supprimer doit exister", toDelete);

        dao.delete(toDelete.getId());

        List<User> after = dao.listAll();
        boolean stillExists = after.stream().anyMatch(user -> user.getId() == toDelete.getId());

        assertFalse("L'utilisateur doit être supprimé", stillExists);
    }

    @After
    public void tearDown() {
        File f = new File("test-users.db");
        if (f.exists())
            f.delete();
    }

}