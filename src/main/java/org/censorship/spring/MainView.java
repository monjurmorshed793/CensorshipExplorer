package org.censorship.spring;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
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

//@Route("")
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends AbstractAppRouterLayout {
    public MainView() {
    }

    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu appLayoutMenu) {
        appLayout.setBranding(new Span("Censorship Detector").getElement());
        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("ISP", "isp"));
        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Web Address", "web-address"));
        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Censorship Status", "censorship-status"));
        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Sniffers", "sniffer"));
        appLayoutMenu.addMenuItem(new AppLayoutMenuItem("Log out", e->logout()));
    }

    private void logout(){
        SecurityContextHolder.clearContext();
        UI.getCurrent().getSession().close();
        UI.getCurrent().navigate("login");
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        super.showRouterLayoutContent(content);
    }
}
