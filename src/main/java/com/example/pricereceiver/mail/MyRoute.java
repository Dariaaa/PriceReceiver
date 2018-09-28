package com.example.pricereceiver.mail;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {
    @Value("${camel.imap.url}")
    String url;
    @Value("${camel.imap.username}")
    String username;
    @Value("${camel.imap.password}")
    String pass;
    @Value("${camel.imap.params}")
    String params;
    private final EmailTrackerService emailTrackerService;

    public MyRoute(EmailTrackerService emailTrackerService){
        this.emailTrackerService = emailTrackerService;
    }
    @Override
    public void configure() {
        from(constructUrl())
                .process(emailTrackerService)
                .end();
    }


    private String constructUrl(){
        return String.format("%s?username=%s&password=%s&%s",url,username,pass,params);
    }
}
