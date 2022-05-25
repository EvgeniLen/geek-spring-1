package ru.geekbrains;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(urlPatterns = "/first_servlet")
public class FirstServlet implements Servlet {
    private ServletConfig conf;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.conf = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return conf;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        servletResponse.getWriter().println("Hello World");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
