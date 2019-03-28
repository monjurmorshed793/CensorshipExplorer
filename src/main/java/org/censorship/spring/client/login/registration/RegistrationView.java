package org.censorship.spring.client.login.registration;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.censorship.spring.LoginAndRegistrationView;
import org.censorship.spring.domains.role.UserRole;
import org.censorship.spring.domains.role.UserRoleRepository;
import org.censorship.spring.domains.user.User;
import org.censorship.spring.domains.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route(value="register", layout = LoginAndRegistrationView.class)
@VaadinSessionScope
public class RegistrationView extends Div {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;


    public RegistrationView(@Autowired UserRepository userRepository, @Autowired UserRoleRepository userRoleRepository, @Autowired PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        FlexLayout flexLayout = new FlexLayout();
        flexLayout.setSizeFull();
        flexLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);


        FormLayout formLayout = new FormLayout();
        Binder<User> binder = new Binder<>();
        User user = new User();


        TextField firstName = new TextField();
        firstName.setSizeFull();
        formLayout.addFormItem(firstName, "First Name").getElement().setAttribute("colspan","2").setAttribute("align","right");

        TextField lastName = new TextField();
        lastName.setSizeFull();
        formLayout.addFormItem(lastName, "Last Name").getElement().setAttribute("colspan","2").setAttribute("align","right");

        EmailField email = new EmailField();
        email.setSizeFull();
        formLayout.addFormItem(email, "Email").getElement().setAttribute("colspan","2").setAttribute("align","right");
//        formLayout.getElement().appendChild(ElementFactory.createBr());


        PasswordField passwordField = new PasswordField();
        passwordField.setSizeFull();
        formLayout.addFormItem(passwordField, "Password").getElement().setAttribute("align","right");

        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setSizeFull();
        formLayout.addFormItem(confirmPassword, "Confirm Password").getElement().setAttribute("align","right");

        Button register = new Button("Register");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button login = new Button("Login");
        login.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        login.addClickListener(l-> getUI().ifPresent(ui->ui.navigate("login")));

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSizeFull();
        buttonLayout.add(register, login);
        formLayout.addFormItem(buttonLayout,"");

        SerializablePredicate<String> emptyValuePredicate = value->!firstName.getValue().trim().isEmpty()
                || lastName.getValue().trim().isEmpty() || email.getValue().trim().isEmpty()
                || passwordField.getValue().trim().isEmpty();

        Binder.Binding<User, String> firstNameBinding = binder.forField(firstName)
                .withValidator(emptyValuePredicate, "First name must not be empty")
                .bind(User::getFirstName, User::setFirstName);

        Binder.Binding<User, String> lastNameBinding = binder.forField(lastName)
                .withValidator(emptyValuePredicate, "Last name must not be empty")
                .bind(User::getLastName, User::setLastName);

        Binder.Binding<User,String> emailBinding = binder.forField(email)
                .withValidator(emptyValuePredicate, "Email must not be empty")
                .withValidator(new EmailValidator("Incorrect email address"))
                .bind(User::getEmailAddress, User::setEmailAddress);

        Binder.Binding<User, String> passwordBinding = binder.forField(passwordField)
                .withValidator(emptyValuePredicate,"Password must not be empty")
                .bind(User::getPassword, User::setPassword);

        SerializablePredicate<String> passwordMismtch = value->passwordField.getValue().equals(value);
        Binder.Binding<User, String> confirmPasswordBinding = binder.forField(confirmPassword)
                .withValidator(passwordMismtch,"Password is not matching")
                .bind(User::getConfirmPassword, User::setConfirmPassword);

        register.addClickListener(l->{
           if(binder.writeBeanIfValid(user)){
               registerUser(user);
           }
        });

        flexLayout.add(formLayout);
        verticalLayout.add(flexLayout);
        add(verticalLayout);

    }


    private void registerUser(User user){
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        userRoleRepository.save(new UserRole(1L, user.getId()));
        getUI().ifPresent(ui -> ui.navigate("login"));
    }
}
