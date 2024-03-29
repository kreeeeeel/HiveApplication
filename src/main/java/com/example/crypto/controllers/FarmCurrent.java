package com.example.crypto.controllers;

import java.time.LocalTime;
import java.time.ZoneId;
import com.example.crypto.WindowPage;
import com.example.crypto.methods.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FarmCurrent {

    @FXML
    private Pagination PageCount;

    @FXML
    private Label btnAccess;

    @FXML
    private Label btnAccount;

    @FXML
    private Label btnChangeUser;

    @FXML
    private Label btnEnergy;

    @FXML
    private Label btnExitForFarm;

    @FXML
    private Label btnMain;

    @FXML
    private Label btnPayment;

    @FXML
    private Label btnSignOut;

    @FXML
    private Label btnTransferFarm;

    @FXML
    private Label btnTransferMoney;

    @FXML
    private Label btnWorkers;

    @FXML
    private Label fieldBalance;

    @FXML
    private Label fieldGPU;

    @FXML
    private ImageView fieldGitHub;

    @FXML
    private ImageView fieldImage;

    @FXML
    private Label fieldName;

    @FXML
    private Label fieldNameFarm;

    @FXML
    private Label fieldPower;

    @FXML
    private Label fieldRIGS;

    @FXML
    private Label fieldRole;

    @FXML
    private Label fieldWorkers;

    @FXML
    void initialize() {
        eventChangeText();

        eventMouseOnEntered();
        eventMouseOnExited();
        eventMouseOnClicked();

        initWorkers();
    }

    @FXML
    public void eventMouseOnEntered() {
        btnMain.setOnMouseEntered(mouseEvent ->
                btnMain.setTextFill(Paint.valueOf("#656060")));
        btnAccount.setOnMouseEntered(mouseEvent ->
                btnAccount.setTextFill(Paint.valueOf("#656060")));
        btnPayment.setOnMouseEntered(mouseEvent ->
                btnPayment.setTextFill(Paint.valueOf("#656060")));
        btnChangeUser.setOnMouseEntered(mouseEvent ->
                btnChangeUser.setTextFill(Paint.valueOf("#656060")));
        btnSignOut.setOnMouseEntered(mouseEvent ->
                btnSignOut.setTextFill(Paint.valueOf("#542323")));

        btnWorkers.setOnMouseEntered(mouseEvent ->
                btnWorkers.setTextFill(Paint.valueOf("#656060")));
        btnEnergy.setOnMouseEntered(mouseEvent ->
                btnEnergy.setTextFill(Paint.valueOf("#656060")));
        btnAccess.setOnMouseEntered(mouseEvent ->
                btnAccess.setTextFill(Paint.valueOf("#656060")));
        btnTransferFarm.setOnMouseEntered(mouseEvent ->
                btnTransferFarm.setTextFill(Paint.valueOf("#656060")));
        btnTransferMoney.setOnMouseEntered(mouseEvent ->
                btnTransferMoney.setTextFill(Paint.valueOf("#656060")));
        btnExitForFarm.setOnMouseEntered(mouseEvent ->
                btnExitForFarm.setTextFill(Paint.valueOf("#542323")));
    }

    @FXML
    public void eventMouseOnExited() {
        btnMain.setOnMouseExited(mouseEvent ->
                btnMain.setTextFill(Paint.valueOf("#9e9e9e")));
        btnAccount.setOnMouseExited(mouseEvent ->
                btnAccount.setTextFill(Paint.valueOf("#9e9e9e")));
        btnPayment.setOnMouseExited(mouseEvent ->
                btnPayment.setTextFill(Paint.valueOf("#9e9e9e")));
        btnChangeUser.setOnMouseExited(mouseEvent ->
                btnChangeUser.setTextFill(Paint.valueOf("#9e9e9e")));
        btnChangeUser.setOnMouseExited(mouseEvent ->
                btnChangeUser.setTextFill(Paint.valueOf("#9e9e9e")));
        btnSignOut.setOnMouseExited(mouseEvent ->
                btnSignOut.setTextFill(Paint.valueOf("#943e3e")));

        btnWorkers.setOnMouseExited(mouseEvent ->
                btnWorkers.setTextFill(Paint.valueOf("#9e9e9e")));
        btnEnergy.setOnMouseExited(mouseEvent ->
                btnEnergy.setTextFill(Paint.valueOf("#9e9e9e")));
        btnAccess.setOnMouseExited(mouseEvent ->
                btnAccess.setTextFill(Paint.valueOf("#9e9e9e")));
        btnTransferFarm.setOnMouseExited(mouseEvent ->
                btnTransferFarm.setTextFill(Paint.valueOf("#9e9e9e")));
        btnTransferMoney.setOnMouseExited(mouseEvent ->
                btnTransferMoney.setTextFill(Paint.valueOf("#9e9e9e")));
        btnExitForFarm.setOnMouseExited(mouseEvent ->
                btnExitForFarm.setTextFill(Paint.valueOf("#943e3e")));
    }

    @FXML
    public void eventMouseOnClicked() {
        fieldImage.setOnMouseClicked(mouseEvent ->
                WindowPage.openWebpage("https://hiveon.com/"));
        fieldGitHub.setOnMouseClicked(mouseEvent ->
                WindowPage.openWebpage("https://github.com/KirillNemtyrev/crypto"));
        btnMain.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) btnChangeUser.getScene().getWindow();
            WindowPage.updateWindow(stage, "Фермы", "farms.fxml", 950, 665, false);
        });

        btnAccount.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) btnAccount.getScene().getWindow();
            WindowPage.updateWindow(stage, "Аккаунт", "profile.fxml", 950, 665, false);
        });
        btnChangeUser.setOnMouseClicked(mouseEvent -> {
            Request.Logout();
            Settings.setSettingToken(null);
            Settings.saveParams();

            Stage stage = (Stage) btnChangeUser.getScene().getWindow();
            WindowPage.updateWindow(stage, "Авторизация", "auth.fxml", 678, 505);
        });
        btnSignOut.setOnMouseClicked(mouseEvent -> {
            Request.Logout();
            Settings.setSettingToken(null);
            Settings.saveParams();

            System.exit(0);
        });
    }

    @FXML
    public void eventChangeText() {
        fieldBalance.setText(Farm.getBalanceFarm() + " $");
        fieldWorkers.setText(String.valueOf(Workers.getCountWorkers()));
        fieldGPU.setText(Farm.getCountGPU());
        fieldRIGS.setText(Farm.getCountRIGS());
        fieldPower.setText(Farm.getPowerFarm());

        fieldNameFarm.setText(Farm.getCurrentFarmName());
        fieldRole.setText(Farm.getCurrentFarmRole().toUpperCase());

        // Get time
        ZoneId zoneId = ZoneId.systemDefault();
        LocalTime time = LocalTime.now(zoneId);
        int hour = time.getHour();
        String welcome = (hour < 6 ? "Доброй ночи" : hour < 12 ? "Доброго утра" :
                hour < 18 ? "Доброго дня" : "Доброго вечера") + " , " + Account.getLogin();

        fieldName.setText(welcome);
    }

    @FXML
    public void initWorkers() {
        PageCount.setVisible(true);
        PageCount.setPageCount((int) Math.ceil((double) Workers.getCountWorkers() / 13.0));

        setupPagination();
    }

    @FXML
    public void setupPagination() {
        PageCount.setPageFactory((pageIndex) -> {
            int count = 1;
            AnchorPane PagePane = createActivePane();
            while (13 * pageIndex + count <= Workers.getCountWorkers() && count <= 13) {

                final double posX = 23; // default
                double posY = 70 + 30.0 * (count - 1);
                int index = 13 * pageIndex + (count - 1);

                PagePane.getChildren().add(insertWorker(posX, posY, index));
                count++;
            }
            return PagePane;
        });
    }

    @FXML
    public AnchorPane createActivePane() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefWidth(950.0);
        pane.setPrefHeight(485);
        pane.setLayoutX(0);
        pane.setLayoutY(138);
        return pane;
    }

    @FXML
    public AnchorPane createPane(double posX, double posY, int index) {

        AnchorPane ListPane = new AnchorPane();
        ListPane.setLayoutX(posX);
        ListPane.setLayoutY(posY);
        ListPane.setPrefWidth(903);
        ListPane.setPrefHeight(23);
        ListPane.setCursor(Cursor.cursor("HAND"));
        ListPane.setStyle("-fx-background-color: #2f353c");

        ListPane.setOnMouseEntered(event -> ListPane.setStyle("-fx-background-color: #192128"));
        ListPane.setOnMouseExited(event -> ListPane.setStyle("-fx-background-color: #2f353c"));
        ListPane.setOnMouseClicked(event -> {
            JSONObject worker = Workers.getCurrentWorker(index);
            Request.getWorkerID(Farm.getCurrentFarmID(), (Long) worker.get("id"));
            WindowPage.updateWindow(WindowPage.getPrimaryStage(), "Воркеры", "worker.fxml", 950, 665, false);
        });
        return ListPane;
    }

    @FXML
    public void addLabel(AnchorPane ListPane, String Aligmnment, String name, String color, double posX, double posY,
                         double prefWidth, double prefHeight, double size, String font) {
        Label Name = new Label(name);
        Name.setFont(Font.font(font, size));
        Name.setTextFill(Paint.valueOf(color));
        Name.setLayoutX(posX);
        Name.setLayoutY(posY);
        Name.setPrefWidth(prefWidth);
        Name.setPrefHeight(prefHeight);
        if (!Aligmnment.isEmpty()) Name.setAlignment(Pos.valueOf(Aligmnment));
        ListPane.getChildren().add(Name);
    }

    @FXML
    public AnchorPane insertWorker(double posX, double posY, int index) {

        JSONObject worker = Workers.getCurrentWorker(index);
        JSONObject stats = (JSONObject) worker.get("stats");
        JSONArray gpu_stats = (JSONArray) worker.get("gpu_stats");

        AnchorPane workerPane = createPane(posX, posY, index);

        String NAME = String.valueOf(worker.get("name"));
        String COLOR_NAME = String.valueOf(stats.get("online")).equals("true") ? "#1cc62d" : "#943e3e";
        addLabel(workerPane, "", NAME, COLOR_NAME, 5.0, 3.0, 178, 18, 16, "Consolas Bold");

        String POWER = "⚡ " + stats.get("power_draw") + " w";
        addLabel(workerPane, "CENTER_RIGHT", POWER, "#686565", 834, 3.0, 62, 17, 14, "Consolas");

        String FAN_AMOUNT = getFan(gpu_stats) + "%";
        addLabel(workerPane, "CENTER_RIGHT", FAN_AMOUNT, "#c3c3c3", 786, 3.0, 36, 18, 16, "Consolas Bold");

        JSONObject autofan = (JSONObject) worker.get("autofan");
        String FAN_COLOR = (Boolean) autofan.get("enabled") ? "#a0ff00" : "#ffbd00";
        addLabel(workerPane, "CENTER_RIGHT", "FAN", FAN_COLOR, 751, 3.0, 27, 18, 16, "Consolas Bold");

        String AUTOREBOOT_COLOR = (Boolean) autofan.get("reboot_on_errors") ? "#a0ff00" : "#ffbd00";
        addLabel(workerPane, "CENTER_RIGHT", "AUTOREBOOT", AUTOREBOOT_COLOR, 654, 3.0, 88, 18, 16, "Consolas Bold");

        JSONObject miners_summary = (JSONObject) worker.get("miners_summary");
        JSONArray hashrates = (JSONArray) miners_summary.get("hashrates");
        JSONObject parse = (JSONObject) hashrates.get(0);

        String COIN = parse.get("coin") + ((parse.get("dcoin") != null) ? " + " + parse.get("dcoin") : "");
        addLabel(workerPane, "CENTER", COIN, "#c3c3c3", 188, 3.0, 185, 18, 16, "Consolas Bold");

        JSONObject shares = (JSONObject) parse.get("shares");
        String MINER = parse.get("miner") + " " + shares.get("ratio") + "%";
        addLabel(workerPane, "CENTER_LEFT", MINER, "#686565", 373, 3.0, 185, 17, 14, "Consolas");


        return workerPane;
    }

    public long getFan(JSONArray object) {
        long max = 0;
        for (Object o : object) {
            JSONObject data = (JSONObject) o;
            long current = (Long) data.get("fan");
            if (current > max) max = current;
        }
        return max;
    }
}
