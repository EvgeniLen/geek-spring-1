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
public class ProductServlet extends HttpServlet {

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
        if(req.getPathInfo() == null || req.getPathInfo().equals("/")){
            req.setAttribute("products", productList.findAll());
            getServletContext().getRequestDispatcher("/product.jsp").forward(req, resp);
        } else {
            Matcher matcher = PARAM_PATTERN.matcher(req.getPathInfo());
            if (matcher.matches()){
                long id = Long.parseLong(matcher.group(1));
                Product p = productList.getProduct(id);
                if (p == null){
                    resp.getWriter().println("User not found");
                    resp.setStatus(404);
                    return;
                }
                req.setAttribute("product", p.getTitle());
                getServletContext().getRequestDispatcher("/product_form.jsp").forward(req, resp);
            } else {
                resp.getWriter().println("Bad parameter value");
                resp.setStatus(400);
            }
        }

//        PrintWriter writer = resp.getWriter();
//        writer.println("<table>");
//        writer.println("<tr>");
//        writer.println("<th>Id</th>");
//        writer.println("<th>Title</th>");
//        writer.println("<th>Cost</th>");
//        writer.println("</tr>");
//
//        if(req.getPathInfo() == null || req.getPathInfo().equals("/")){
//            productList.findAll().forEach(product -> {
//                writer.println("<tr>");
//                writer.println("<th><a href='" + req.getContextPath() + "/product/" + product.getId() + "'>" + product.getId() + "</th>");
//                writer.println("<th>" + product.getTitle() + "</th>");
//                writer.println("<th>" + product.getCost() + "</th>");
//                writer.println("</tr>");
//            });
//        } else {
//            Matcher matcher = PARAM_PATTERN.matcher(req.getPathInfo());
//            if (matcher.matches()){
//                long id = Long.parseLong(matcher.group(1));
//                Product p = productList.getProduct(id);
//                if (p == null){
//                    resp.getWriter().println("User not found");
//                    resp.setStatus(404);
//                    return;
//                }
//                writer.println("<tr>");
//                writer.println("<th>" + p.getId() + "</th>");
//                writer.println("<th>" + p.getTitle() + "</th>");
//                writer.println("<th>" + p.getCost() + "</th>");
//                writer.println("</tr>");
//            } else {
//                resp.getWriter().println("Bad parameter value");
//                resp.setStatus(400);
//            }
//        }
    }
}
