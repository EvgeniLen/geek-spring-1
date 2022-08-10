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

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

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
                writer.println("<td>" + product.getId() + "</td>");
                writer.println("<td>" + product.getTitle() + "</td>");
                writer.println("<td>" + product.getCost() + "</td>");
                writer.println("</tr>");
            });
        } else {
            long l = Long.parseLong(req.getPathInfo().replace("/", ""));
            Product p = productList.getProduct(l);
            writer.println("<tr>");
            writer.println("<td>" + p.getId() + "</td>");
            writer.println("<td>" + p.getTitle() + "</td>");
            writer.println("<td>" + p.getCost() + "</td>");
            writer.println("</tr>");
        }
    }
}
