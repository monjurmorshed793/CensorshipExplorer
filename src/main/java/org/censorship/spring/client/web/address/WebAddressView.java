package org.censorship.spring.client.web.address;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.censorship.spring.MainView;
import org.censorship.spring.domains.web.address.WebAddress;
import org.censorship.spring.domains.web.address.WebAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Route(value = "web-address", layout = MainView.class)
public class WebAddressView extends HorizontalLayout implements BeforeEnterObserver {

    private WebAddressRepository webAddressRepository;

    Dialog dialog;
    List<WebAddress> webAddressList;
    Grid<WebAddress> grid;

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            getUI().ifPresent(ui -> ui.navigate("login"));
        }
    }

    public WebAddressView(@Autowired WebAddressRepository webAddressRepository){
        setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setWidth("100%");

        HorizontalLayout buttonGroup = new HorizontalLayout();
        Button uploadButton = new Button("Upload");
        Button removeButton = new Button("Remove All");
        buttonGroup.add(uploadButton, removeButton);
        content.add(buttonGroup);
        content.setAlignSelf(Alignment.END, buttonGroup);

        webAddressList = webAddressRepository.findAll();
        grid = new Grid<>(WebAddress.class);
        grid.setItems(webAddressList);
        content.add(grid);
        content.setAlignSelf(Alignment.STRETCH, grid);

        add(content);
    }


}
