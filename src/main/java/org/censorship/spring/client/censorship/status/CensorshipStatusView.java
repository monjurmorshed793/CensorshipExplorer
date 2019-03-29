package org.censorship.spring.client.censorship.status;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.censorship.spring.MainView;
import org.censorship.spring.domains.isp.IspProvider;
import org.censorship.spring.domains.isp.IspProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "censorship-status", layout= MainView.class)
public class CensorshipStatusView extends VerticalLayout  {

    private IspProviderRepository ispProviderRepository;

    public CensorshipStatusView(@Autowired IspProviderRepository ispProviderRepository) {

        this.ispProviderRepository = ispProviderRepository;

        setSizeFull();

        HorizontalLayout selectionLayout = new HorizontalLayout();

        List<IspProvider> ispProviderList = ispProviderRepository.findAll();
        Select<IspProvider> ispProviderSelect = new Select<>();
        ispProviderSelect.setPlaceholder("ISP Provider");
        ispProviderSelect.setTextRenderer(IspProvider::getName);
        ispProviderSelect.setItems(ispProviderList);
        selectionLayout.add(ispProviderSelect);

        Button censorshipCheckButton = new Button("Check Censorship Status");
        selectionLayout.add(censorshipCheckButton);

        add(selectionLayout);
        setAlignSelf(Alignment.CENTER, selectionLayout);
    }
}
