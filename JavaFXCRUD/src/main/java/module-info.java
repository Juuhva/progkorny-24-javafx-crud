module crud.javafxcrud {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens crud.javafxcrud to javafx.fxml;
    exports crud.javafxcrud.Controllers;
    opens crud.javafxcrud.Controllers to javafx.fxml;
    opens crud.javafxcrud.User to javafx.base;
    exports Main;
    opens Main to javafx.fxml;
}