package parser.chromium;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import parser.engine.HtmlGetter;
import parser.exceptions.JCEFInitException;





@Service
public class ChromiumHtmlGetter extends HtmlGetter{

    private Browser browser;

    @PostConstruct
    public void init() throws JCEFInitException{
       browser=new Browser();

    }

    public String getHtmlString(String urlLink){
		        
        return browser.getHtml(urlLink);
      
    
    }

    @Override
    public  void dispose() {
        browser.getCefApp().dispose();
        
    }

   
           


}
