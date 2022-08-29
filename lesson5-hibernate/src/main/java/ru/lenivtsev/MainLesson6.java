package ru.lenivtsev;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.lenivtsev.config.AppConfiguration;
import ru.lenivtsev.service.GlobalService;

public class MainLesson6 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        GlobalService globalService = context.getBean("globalService", GlobalService.class);


        globalService.findProductByUserId(2).forEach(System.out::println);
        globalService.findUserByProductId(3).forEach(System.out::println);

        System.out.println("Общая стоимость товара у user1= " + globalService.getPriceProducts(1));
        System.out.println("Общая стоимость товара у user2 = " + globalService.getPriceProducts(2));
        CreationFactory.getFactory().close();
    }
}
