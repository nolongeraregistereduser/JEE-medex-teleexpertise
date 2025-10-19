package com.example.jeemedexteleexpertise.filter;

import com.example.jeemedexteleexpertise.model.Role;
import com.example.jeemedexteleexpertise.model.Utilisateur;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {
    "/infirmier/*",
    "/generaliste/*",
    "/specialiste/*"
})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (!isLoggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        Utilisateur user = (Utilisateur) session.getAttribute("user");
        String requestURI = httpRequest.getRequestURI();

        if (requestURI.startsWith(httpRequest.getContextPath() + "/infirmier/") &&
            user.getRole() != Role.INFIRMIER) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        if (requestURI.startsWith(httpRequest.getContextPath() + "/generaliste/") &&
            user.getRole() != Role.GENERALISTE) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        if (requestURI.startsWith(httpRequest.getContextPath() + "/specialiste/") &&
            user.getRole() != Role.SPECIALISTE) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

