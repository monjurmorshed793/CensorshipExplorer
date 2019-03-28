package org.censorship.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;

@Route("")
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends Div implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(SecurityContextHolder.getContext().getAuthentication()==null)
            beforeEnterEvent.forwardTo("login");
    }

    public MainView(@Autowired MessageBean bean) {

        Button button = new Button("Click me",
                e -> Notification.show(bean.getMessage()));
        add(button);
    }

}
