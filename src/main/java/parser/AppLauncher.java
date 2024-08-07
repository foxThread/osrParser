package parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import parser.engine.PagesLinksGetter;

@SpringBootApplication
public class AppLauncher implements CommandLineRunner {

    @Autowired
    private PagesLinksGetter linksGetter;

    public static void main(String args[]) {
        SpringApplication.run(AppLauncher.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        
        linksGetter
                .setBaseUrl("https://www.farpost.ru/khabarovsk/realty/sell_houses")
                .setCssExpression("a[data-role*=bulletin-link]")
                .setPagePrefix("?page=")
                .setFirstPage(1)
                .setLastPage(1);

    }

}
