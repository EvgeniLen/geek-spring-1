package ru.lenivtsev;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.lenivtsev.config.AppConfiguration;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ProductService productService = context.getBean("productService", ProductService.class);
        Cart cart = context.getBean("cart", Cart.class);

        Scanner scanner = new Scanner(System.in);
        boolean needDel = false;
        while (true) {
            if (needDel) {
                System.out.println("Введите id товара для удаления:");
            } else {
                System.out.println("Введите id товара:");
            }

            try {
                long id = Long.parseLong(scanner.nextLine());
                if (!needDel && productService.getProduct(id)!= null) {
                    cart.addToCart(id);
                    System.out.println("Продукт " + productService.getProduct(id).getTitle() + " добавлен.");
                } else if (needDel && cart.getListProduct().contains(id)) {
                    cart.removeFromCart(id);
                    System.out.println("Продукт " + productService.getProduct(id).getTitle() + " удален.");
                    needDel = false;
                } else {
                    throw new Exception();
                }
                System.out.println("Для продолжения нажмите: Enter " +
                        "\nДля удаления из корзины введите: \"del\" " +
                        "\nДля создания новой корзины введите: \"new\" " +
                        "\nДля выхода введите: \"end\"" );
                String answer = scanner.nextLine();

                if (answer.equals("new")) {
                    cart = context.getBean("cart", Cart.class);
                }  else if (answer.equals("del")) {
                    needDel = true;
                } else if (answer.equals("end")) {
                    break;
                }
            } catch (Exception e) {

                if (needDel) {
                    System.out.println("Вы ввели неверный id для удаления из корзины!:");
                } else {
                    System.out.println("Вы ввели неверный id!");
                }
            }
        }

        System.out.println("Список покупок:");
        for (Long aLong : cart.getListProduct()) {
            System.out.print(productService.getProduct(aLong).getCost() + " ");
            System.out.println(productService.getProduct(aLong).getTitle());
            return;
        }
        System.out.println("Корзина пуста!");
    }
}
