package parser.impl.farpost;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import parser.engine.HtmlGetter;
import parser.engine.PagesLinksGetter;
import parser.engine.PauseMaker;

@Component
public class FarpostLinksGetter extends PagesLinksGetter {

    @PostConstruct
    public void init(){
        setBaseUrl("https://www.farpost.ru/khabarovsk/realty/sell_houses")
        .setCssExpression("a[data-role*=bulletin-link]")
        .setPagePrefix("?page=")
        .setFirstPage(1)
        .setLastPage(1);
        System.out.println("new");

    }
    
    
    public FarpostLinksGetter(PauseMaker pauseMaker, HtmlGetter htmlGetter) {
        super(pauseMaker, htmlGetter);

        
    }

}
