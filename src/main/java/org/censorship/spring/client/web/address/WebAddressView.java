package org.censorship.spring.client.web.address;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.apache.commons.io.FileUtils;
import org.censorship.spring.MainView;
import org.censorship.spring.domains.web.address.WebAddress;
import org.censorship.spring.domains.web.address.WebAddressRepository;
import org.censorship.spring.service.ExcelWebAddressFileExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Route(value = "web-address", layout = MainView.class)
public class WebAddressView extends HorizontalLayout implements BeforeEnterObserver {

    private WebAddressRepository webAddressRepository;
    private ExcelWebAddressFileExtractionService excelWebAddressFileExtractionService;

    Dialog dialog;
    List<WebAddress> webAddressList;
    Grid<WebAddress> grid;

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            getUI().ifPresent(ui -> ui.navigate("login"));
        }
    }

    public WebAddressView(@Autowired WebAddressRepository webAddressRepository, @Autowired ExcelWebAddressFileExtractionService excelWebAddressFileExtractionService){
        this.webAddressRepository = webAddressRepository;
        this.excelWebAddressFileExtractionService = excelWebAddressFileExtractionService;
        setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setWidth("100%");

        HorizontalLayout buttonGroup = new HorizontalLayout();
        Button uploadButton = new Button("Upload");
        uploadButton.addClickListener(e->openFileUploadDialog());
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

    private void openFileUploadDialog(){
        dialog = new Dialog();
        dialog.open();
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setWidth("100%");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        content.add(upload);
        content.setAlignSelf(Alignment.STRETCH, upload);

        upload.addSucceededListener(e->{
            try{
                File excelFile = new File(e.getFileName());
                FileUtils.copyInputStreamToFile(buffer.getInputStream(), excelFile);
                excelWebAddressFileExtractionService.extractExcelData(excelFile);
                webAddressList = webAddressRepository.findAll();
                grid.setItems(webAddressList);
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });

        Button closeButton = new Button("Close");
        closeButton.addClickListener(e->dialog.close());
        content.add(closeButton);
        content.setAlignSelf(Alignment.END, closeButton);

        dialog.add(content);

    }


}
