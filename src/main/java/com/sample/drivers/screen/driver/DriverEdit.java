package com.sample.drivers.screen.driver;

import com.sample.drivers.entity.Document;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.*;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import com.sample.drivers.entity.Driver;
import liquibase.pro.packaged.S;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Driver.edit")
@UiDescriptor("driver-edit.xml")
@EditedEntityContainer("driverDc")
public class DriverEdit extends StandardEditor<Driver> {

    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Downloader downloader;

    @Autowired
    private InstanceContainer<Driver> driverDc;
    @Autowired
    private Image<byte[]> photo;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if(driverDc.getItem().getPhoto() == null){
            photo.setVisible(false);
        }
    }

    @Install(to = "documentsTable.docRef", subject = "columnGenerator")
    private Component documentsTableDocRefColumnGenerator(Document document) {
        if (document.getDocRef() != null) {
            LinkButton linkButton = uiComponents.create(LinkButton.class);

            linkButton.setCaption(document.getDocRef().getFileName());
            linkButton.setAction(new BaseAction("download").withHandler(actionPerformedEvent ->
                    downloader.download(document.getDocRef())));

            return linkButton;
        }

        return null;
    }

}