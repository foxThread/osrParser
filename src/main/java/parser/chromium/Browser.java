package parser.chromium;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;

import org.cef.callback.CefStringVisitor;

import org.cef.handler.CefLoadHandlerAdapter;

import lombok.Getter;
import lombok.Setter;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;

import me.friwi.jcefmaven.UnsupportedPlatformException;

import parser.exceptions.JCEFInitException;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

class ContentHandler implements CefStringVisitor {

    private Browser browser;

    public ContentHandler(Browser browser) {
        this.browser = browser;
    }

    @Override
    public void visit(String content) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!! visit!!!!!");

        browser.setContent(content);

        browser.setContentSaved(true);

    }

}

public class Browser {

    @Getter
    @Setter
    private CefApp cefApp;
    private CefClient client;
    private volatile CefBrowser browser;

    private JFrame browserFrame;

    private volatile boolean loaded = false;
    @Getter
    @Setter
    private volatile boolean contentSaved = false;

    private String content;
    private ContentHandler contentHandler;

    private final boolean OFFSCREEN = false;
    private final boolean TRANSPARENT = false;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public boolean wasLoaded() {
        return loaded;

    }

    public void setLoadingStatus(boolean status) {
        this.loaded = status;
    }

    volatile int completedCount = 0;

   

    public Browser() throws JCEFInitException {


        CefAppBuilder builder = new CefAppBuilder();

        builder.getCefSettings().windowless_rendering_enabled = false; //Default - select OSR mode
        builder.setInstallDir(new File("/chromium"));
      
        builder.setSkipInstallation(true);
        builder.getCefSettings().cache_path="/chromium/rootcache/cache";
        builder.getCefSettings().locale="ru";
        builder.getCefSettings().persist_session_cookies=false;
    
    
        builder.getCefSettings().root_cache_path="/chromium/rootcache/";
    
        
    
    
    
    
    //Build a CefApp instance using the configuration above
    try {
        
        CefApp.startup(null);
        CefApp app = builder.build();
        client=app.createClient();
        client.addMessageRouter(CefMessageRouter.create());
    } catch (IOException e) {
        
        e.printStackTrace();
    } catch (UnsupportedPlatformException e) {
       
        e.printStackTrace();
    } catch (InterruptedException e) {
       
        e.printStackTrace();
    } catch (CefInitializationException e) {
     
        e.printStackTrace();
    }
            
    
/* 
        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = OFFSCREEN;
        CefApp.startup(null);
        cefApp = CefApp.getInstance(settings);
        client = cefApp.createClient();
        client.addMessageRouter(CefMessageRouter.create());
        browserFrame = new JFrame();
      
   */    
      


        client.addLoadHandler(
                new CefLoadHandlerAdapter() {

                    @Override
                    public void onLoadEnd(CefBrowser browser, CefFrame frame, int httpStatusCode) {
                        System.out.println("OnLOadEnd");

                    }

                    public void onLoadingStateChange(CefBrowser browser, boolean isLoading, boolean canGoBack,
                            boolean canGoForward) {

                        if (!isLoading) {

                            browser.getSource(contentHandler);

                            System.out.println("CEF.OnLoadingStateChange : Loading fully completed!!!!");

                        }

                    }

                    public void onLoadError(org.cef.browser.CefBrowser arg0, org.cef.browser.CefFrame arg1,
                            org.cef.handler.CefLoadHandler.ErrorCode arg2, java.lang.String arg3,
                            java.lang.String arg4) {
                        System.out.println("CEF.OnLoadError : Fired");
                        System.out.println("Event: " + arg2.name());

                    }

                });
       
             browser = client.createBrowser("", OFFSCREEN, TRANSPARENT);
                    

       
         
       
         browserFrame.add(browser.getUIComponent(), BorderLayout.CENTER);
         browserFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
         browserFrame.setSize(1024, 768);
        



         browserFrame.setVisible(true);

        contentHandler = new ContentHandler(this);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    

    public String getHtml(String url)  {

        int watchDogPeriod=10;
        int timeToWork=watchDogPeriod*1000;
        loaded = false;
        contentSaved = false;
       

        browser.loadURL(url);
      
      

       
       

        long start =System.currentTimeMillis();
        while (!contentSaved) {
            if ( (System.currentTimeMillis()-start)>timeToWork){
                
                System.out.println("Error: breaks getting content by wathdog timer");
                return null;
                               
            }

        }

        return content;

    }

}
