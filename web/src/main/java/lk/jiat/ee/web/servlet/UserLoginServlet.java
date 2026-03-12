package lk.jiat.ee.web.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.jiat.ee.core.model.User;
import lk.jiat.ee.ejb.beans.UserStoreBean;

import java.io.IOException;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {

    @jakarta.ejb.EJB
    private UserStoreBean userStore;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            resp.sendRedirect("user/UserLogin.jsp?error=emptyFields");
            return;

        } else if (email.length() > 100 || !email.matches(emailPattern)) {
            resp.sendRedirect("user/UserLogin.jsp?error=invalidEmail");
            return;

        } else if (password.length() < 6) {
            resp.sendRedirect("user/UserLogin.jsp?error=shortPassword");
            return;
        }

        if (!userStore.userExists(email)) {
            resp.sendRedirect("user/UserLogin.jsp?error=userNotFound");
            return;
        }

        User user = userStore.getUser(email);

        if (!user.getPassword().equals(password)) {
            resp.sendRedirect("user/UserLogin.jsp?error=wrongPassword");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("userSession", user);
        resp.sendRedirect("user/UserAuctions.jsp");
    }
}
