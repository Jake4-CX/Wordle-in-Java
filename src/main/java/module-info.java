module lat.jack.wordle.wordle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens lat.jack.wordle.wordle to javafx.fxml, javafx.base;
    opens lat.jack.wordle.wordle.Objects to javafx.base;
    exports lat.jack.wordle.wordle;
    exports lat.jack.wordle.wordle.Controllers;
    opens lat.jack.wordle.wordle.Controllers to javafx.fxml;
}