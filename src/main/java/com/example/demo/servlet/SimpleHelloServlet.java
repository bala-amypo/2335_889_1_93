package com.example.demo.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SimpleHelloServlet", urlPatterns = "/hello")
public class SimpleHelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Set response type
        resp.setContentType("text/plain");

        // Get writer to send response
        PrintWriter writer = resp.getWriter();

        // Send response
        writer.println("Hello from SimpleHelloServlet!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Optional: Handle POST requests
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.println("POST not supported in SimpleHelloServlet");
    }
}
