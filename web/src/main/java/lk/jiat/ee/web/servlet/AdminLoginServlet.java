package lk.jiat.ee.web.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.jiat.ee.core.model.Admin;
import lk.jiat.ee.ejb.beans.AdminStoreBean;

import java.io.IOException;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    @jakarta.ejb.EJB
    private AdminStoreBean adminStore;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String admin_username = req.getParameter("admin_username");
        String admin_password = req.getParameter("admin_password");


        if (admin_username == null || admin_password == null ||
                admin_username.isEmpty() || admin_password.isEmpty()) {
            resp.sendRedirect("admin/AdminLogin.jsp?error=emptyFields");
            return;

        } else if (admin_username.length() > 100) {
            resp.sendRedirect("admin/AdminLogin.jsp?error=invalidUsername");
            return;

        } else if (admin_password.length() < 6) {
            resp.sendRedirect("admin/AdminLogin.jsp?error=shortPassword");
            return;
        }

        if (!adminStore.adminExists(admin_username)) {
            resp.sendRedirect("admin/AdminLogin.jsp?error=userNotFound");
            return;
        }

        Admin admin = adminStore.getAdmin(admin_username);

        if (!admin.getAdminPassword().equals(admin_password)) {
            resp.sendRedirect("admin/AdminLogin.jsp?error=wrongPassword");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("adminSession", admin);
        resp.sendRedirect("admin/AdminDashboard.jsp");
    }
}
