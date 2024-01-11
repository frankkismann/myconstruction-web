/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.myconstruction.myconstruction.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtener los datos del formulario
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Flag para comprobar si las credenciales son válidas
        boolean isValidUser = false;

        // Intentar conectar a la base de datos y verificar el usuario
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                // Si el usuario existe en la base de datos, isValidUser será true
                isValidUser = rs.next();
            }
        } catch (SQLException e) {
            // Manejar excepciones de SQL aquí
            // errorBd = e.toString();
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("<style>");
            out.println("body { font-family: 'Times New Roman', Times, serif; background-color: #2c3e50; color: #ecf0f1; display: flex; justify-content: center; align-items: center; height: 100vh; }");
            out.println(".message-container { text-align: center; background-color: #34495e; padding: 40px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.5); }");
            out.println("h2 { margin-bottom: 20px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            if (isValidUser) {
                out.println("<div class='message-container'>");
                out.println("<h2>Hola " + username + "!</h2>");
                // Puedes redirigir al usuario a otra página o servlet que maneje la sesión del usuario
                out.println("</div>");
            } else {
                out.println("<div class='message-container'>");
                out.println("<h2>Ha cometido un error en los datos.</h2>");
                // Puedes permitir al usuario intentarlo de nuevo o mostrar un mensaje de error
                out.println("</div>");
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
