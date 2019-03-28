package org.censorship.spring;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.censorship.spring.client.login.registration.LoginView;

import java.util.Objects;

@UIScope
public class LoginAndRegistrationView extends AbstractAppRouterLayout {
    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu appLayoutMenu) {
        appLayout.setBranding(new Span("Censorship Detector").getElement());
        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Login", "login"));
        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Registration", "register"));
    }
}
