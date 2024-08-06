package parser.chromium;

import parser.engine.HtmlGetter;
import parser.exceptions.JCEFInitException;






public class ChromiumHtmlGetter extends HtmlGetter{

    private Browser browser;

    public ChromiumHtmlGetter() throws JCEFInitException{
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
