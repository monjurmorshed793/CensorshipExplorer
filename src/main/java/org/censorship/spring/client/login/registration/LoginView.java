package org.censorship.spring.client.login.registration;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.censorship.spring.LoginAndRegistrationView;
import org.censorship.spring.MessageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value="login", layout = LoginAndRegistrationView.class)
public class LoginView extends Div {
    private LoginOverlay loginOverlay = new LoginOverlay();


    public LoginView() {
        SecurityContextHolder.clearContext();
        loginOverlay.setTitle("Censorship Explorer");
        loginOverlay.setDescription("Login to access your information");
        final UI currentUi = UI.getCurrent();
        loginOverlay.setAction("login");
        loginOverlay.addForgotPasswordListener(l->{
               currentUi.navigate("register");
               loginOverlay.close();
        });
        loginOverlay.setOpened(true);
        add(loginOverlay);
    }
}
