package com.example.cookie.listner;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import lombok.extern.log4j.Log4j2;

@WebListener
@Log4j2
public class LoginListner implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String name = event.getName();
        Object obj= event.getValue();

        if(name.equals("loginInfo"))
        {
            log.info("A user logged in ......... ");
            log.info(obj);
        }
    }
}
