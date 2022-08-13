package ru.geekbrains;

import ru.geekbrains.products.Product;
import ru.geekbrains.products.ProductList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet{

    private static final Pattern PARAM_PATTERN = Pattern.compile("\\/(\\d+)");
    private ProductList productList;

    @Override
    public void init() throws ServletException {

        this.productList = new ProductList();
        productList.insert(new Product("Potato", 5));
        productList.insert(new Product("Tomato", 23));
        productList.insert(new Product("Apple", 20));
        productList.insert(new Product("Bananas", 10));
        productList.insert(new Product("Carrot", 8));
        productList.insert(new Product("Broccoli", 30));
        productList.insert(new Product("Corn", 15));
        productList.insert(new Product("Onion", 3));
        productList.insert(new Product("Kiwi", 23));
        productList.insert(new Product("Mango", 40));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<table>");
        writer.println("<tr>");
        writer.println("<td>Id</td>");
        writer.println("<td>Title</td>");
        writer.println("<td>Cost</td>");
        writer.println("</tr>");

        if(req.getPathInfo() == null || req.getPathInfo().equals("/")){
            productList.findAll().forEach(product -> {
                writer.println("<tr>");
                writer.println("<td><a href='" + getServletContext().getContextPath() + "/product/" + product.getId() + "'>" + product.getId() + "</td>");
                writer.println("<td>" + product.getTitle() + "</td>");
                writer.println("<td>" + product.getCost() + "</td>");
                writer.println("</tr>");
            });
        } else {
            Matcher matcher = PARAM_PATTERN.matcher(req.getPathInfo());
            if (matcher.matches()){
                long id = Long.parseLong(matcher.group(1));
                Product p = productList.getProduct(id);
                if (p == null){
                    resp.getWriter().println("Product not found");
                    resp.setStatus(404);
                    return;
                }
                writer.println("<tr>");
                writer.println("<td>" + p.getId() + "</td>");
                writer.println("<td>" + p.getTitle() + "</td>");
                writer.println("<td>" + p.getCost() + "</td>");
                writer.println("</tr>");
            } else {
                resp.getWriter().println("Bad parameter value");
                resp.setStatus(400);
            }
        }

    }
}