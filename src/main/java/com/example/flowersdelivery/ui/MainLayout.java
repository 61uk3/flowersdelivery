package com.example.flowersdelivery.ui;

import com.example.flowersdelivery.ui.view.FlowerView;
import com.example.flowersdelivery.ui.view.SaleView;
import com.example.flowersdelivery.ui.view.SupplieView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

@CssImport("./themes/my-theme/styles.css")
public class MainLayout extends AppLayout {
    public MainLayout() {
        header();
        drawer();
    }

    private void header() {
        H1 logo = new H1("Доставка цветов");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM
        );

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM
        );
        header.setWidthFull();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void drawer() {
        addToDrawer(new VerticalLayout(
                new RouterLink("Наличие", FlowerView.class),
                new RouterLink("Поставки", SupplieView.class),
                new RouterLink("Продажи", SaleView.class)
        ));
    }

}
