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

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {

    @jakarta.ejb.EJB
    private UserStoreBean userStore;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String number = req.getParameter("number");

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String numberPattern = "^0\\d{9}$";

        if (fullName == null || email == null || password == null || number == null ||
                fullName.isEmpty() || email.isEmpty() || password.isEmpty() || number.isEmpty()) {

            resp.sendRedirect("user/UserRegister.jsp?error=emptyFields");
            return;

        } else if (fullName.length() > 100) {
            resp.sendRedirect("user/UserRegister.jsp?error=nameTooLong");
            return;

        } else if (email.length() > 100 || !email.matches(emailPattern)) {
            resp.sendRedirect("user/UserRegister.jsp?error=invalidEmail");
            return;

        } else if (password.length() < 6) {
            resp.sendRedirect("user/UserRegister.jsp?error=shortPassword");
            return;

        } else if (!number.matches(numberPattern)) {
            resp.sendRedirect("user/UserRegister.jsp?error=invalidNumber");
            return;
        }

        if (userStore.userExists(email)) {
            resp.sendRedirect("user/UserRegister.jsp?error=userExists");
            return;
        }

        User newUser = new User(fullName, email, password, number);
        userStore.addUser(newUser);

        HttpSession session = req.getSession();
        session.setAttribute("userSession", newUser);
        resp.sendRedirect("user/UserAuctions.jsp");
    }
}
