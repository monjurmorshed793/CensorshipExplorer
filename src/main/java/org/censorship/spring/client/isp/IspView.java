package org.censorship.spring.client.isp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.censorship.spring.MainView;
import org.censorship.spring.client.MainApplicationView;
import org.censorship.spring.domains.isp.IspProvider;
import org.censorship.spring.domains.isp.IspProviderRepository;
import org.censorship.spring.enums.ProviderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.awt.*;
import java.util.List;

@Route(value = "isp", layout = MainView.class)
@RouteAlias(value="", layout = MainView.class)
public class IspView extends HorizontalLayout implements BeforeEnterObserver {

    @Autowired
    private IspProviderRepository ispProviderRepository;


    List<IspProvider> ispProviderList;
    Grid<IspProvider> grid;
    Dialog dialog;
    IspProvider editableIspProvider;


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            getUI().ifPresent(ui -> ui.navigate("login"));
        }
    }


    public IspView(@Autowired IspProviderRepository ispProviderRepository){
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();
        VerticalLayout contentLayout = new VerticalLayout();
//        contentLayout.setAlignItems(Alignment.CENTER);
        contentLayout.setWidth("100%");

        HorizontalLayout buttonGroup = new HorizontalLayout();
        buttonGroup.setAlignItems(Alignment.END);
        Button addIspBtn = new Button("Add ISP");
        addIspBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addIspBtn.addClickListener(e->{
            editableIspProvider = new IspProvider();
            openIspAddDialog();
        });
        buttonGroup.add(addIspBtn);
        contentLayout.add(buttonGroup);
        contentLayout.setAlignSelf(Alignment.END, buttonGroup);

        ispProviderList = ispProviderRepository.findAll();
        grid = new Grid<>();
        grid.setItems(ispProviderList);
        grid.addColumn(IspProvider::getName).setHeader("Provider Name");
        grid.addColumn(IspProvider::getDescription).setHeader("Description");
        grid.addColumn(IspProvider::getProviderType).setHeader("Provider Type");
        grid.addItemClickListener(e->{
            editableIspProvider = e.getItem();
            openIspAddDialog();
        });
        contentLayout.add(grid);
        contentLayout.setAlignSelf(Alignment.STRETCH, grid);


        add(contentLayout);
    }



    private void openIspAddDialog(){
        dialog = new Dialog();

        dialog.open();

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setWidth("100%");

        FormLayout formLayout = new FormLayout();
        TextField ispName = new TextField();
        ispName.setSizeFull();
        formLayout.addFormItem(ispName, "ISP Name").getElement().setAttribute("colspan","2");

        TextField description = new TextField();
        description.setSizeFull();
        formLayout.addFormItem(description, "Description").getElement().setAttribute("colspan", "2");

        Select<String> providerTypeSelect = new Select<>("Broadband", "Mobile network");
        providerTypeSelect.setPlaceholder("Select provider type");
        formLayout.addFormItem(providerTypeSelect, "Provider Type").getElement().setAttribute("colspan", "2");

        content.add(formLayout);
        content.setAlignSelf(Alignment.STRETCH, formLayout);

        HorizontalLayout buttonGroup = new HorizontalLayout();
        if(editableIspProvider.getId()==null){
            Button addButton = new Button("Add");
            addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            addButton.addClickListener(e->add(ispName.getValue(), description.getValue(), providerTypeSelect.getValue()));
            buttonGroup.add(addButton);
        }else{
            Button updateButton = new Button("Update");
            updateButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            updateButton.addClickListener(e->update(ispName.getValue(), description.getValue(), providerTypeSelect.getValue()));
            buttonGroup.add(updateButton);
        }


        Button closeButton = new Button("Close");
        closeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        closeButton.addClickListener(e-> dialog.close());
        buttonGroup.add(closeButton);
        content.add(buttonGroup);
        content.setAlignSelf(Alignment.END, buttonGroup);


        if(editableIspProvider.getId()!=null){
            ispName.setValue(editableIspProvider.getName());
            description.setValue(editableIspProvider.getDescription());
            providerTypeSelect.setPlaceholder(editableIspProvider.getProviderType().equals(ProviderType.BROAD_BAND)?"Broadband":"Mobile Network");
        }
        dialog.add(content);
        dialog.setSizeFull();

    }

    private void add(String ispName, String description, String providerType){
        IspProvider ispProvider = new IspProvider();
        ispProvider.setName(ispName);
        ispProvider.setDescription(description);
        ispProvider.setProviderType(providerType.equals("Broadband")?ProviderType.BROAD_BAND: ProviderType.MOBILE_NETWORK);
        ispProviderRepository.save(ispProvider);
        dialog.close();
        ispProviderList = ispProviderRepository.findAll();
        grid.setItems(ispProviderList);
    }

    private void update(String ispName, String description, String providerType){
        editableIspProvider.setName(ispName);
        editableIspProvider.setDescription(description);
        if(providerType!=null)
            editableIspProvider.setProviderType(providerType.equals("Broadband")?ProviderType.BROAD_BAND: ProviderType.MOBILE_NETWORK);
        ispProviderRepository.save(editableIspProvider);
        dialog.close();
        ispProviderList = ispProviderRepository.findAll();
        grid.setItems(ispProviderList);

    }
}
