package org.censorship.spring.client;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.security.core.context.SecurityContextHolder;

@UIScope
public class MainApplicationView extends AbstractAppRouterLayout {
    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu appLayoutMenu) {
        appLayout.setBranding(new Span("Censorship Detector").getElement());
        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("ISP", "isp"));
//        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Web Address", "web-address"));
//        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Censorship Status", "censorship-status"));
//        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Sniffers", "sniffer"));
        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Log out", e->logout()));
    }

    private void logout(){
        SecurityContextHolder.clearContext();
        UI.getCurrent().getSession().close();
        UI.getCurrent().navigate("login");
    }
}
