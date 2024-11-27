package com.example;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class PrimaryController implements Initializable {
    @FXML
    private WebView webView;
    private WebEngine engine;

    ChangeListener<Number> stageSizeListener;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        stageSizeListener = (observable, oldValue, newValue) -> {
            webView.setPrefSize(App.stage.getWidth(), App.stage.getHeight());
        };
        App.stage.widthProperty().addListener(stageSizeListener);
        App.stage.heightProperty().addListener(stageSizeListener);
        webView.setContextMenuEnabled(false);
        engine = webView.getEngine();
        loadPage();
    }

    public class Bridge {

        public void exit() {
            Platform.exit();
        }

        public void log(String text) {
            System.out.println(text);
        }

    }

    private void loadPage() {
        Bridge bridge = new Bridge();
        engine.getLoadWorker().stateProperty().addListener((observableValue, state, t1) -> {
            JSObject window = (JSObject) engine.executeScript("window");
            window.setMember("java", bridge);
            engine.executeScript("console.log = function() { java.log(Array.prototype.join.call(arguments, ' ')); }");
        });
        engine.load(getClass().getResource("/com/example/dist/index.html").toString());
    }
}
