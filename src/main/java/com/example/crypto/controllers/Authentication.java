package com.example.crypto.controllers;

import com.example.crypto.WindowPage;
import com.example.crypto.methods.Account;
import com.example.crypto.methods.Request;
import com.example.crypto.methods.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Authentication {

    @FXML
    private Button btnAuth;

    @FXML
    private TextField field2FA;

    @FXML
    private Label fieldCreateAccount;

    @FXML
    private ImageView fieldGitHub;

    @FXML
    private ImageView fieldImage;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private Label fieldRecovery;

    @FXML
    private CheckBox fieldSave;

    @FXML
    private TextField fieldLogin;

    @FXML
    private Label fieldSendCode;

    @FXML
    private Label textSendCode;

    @FXML
    void initialize() {

        if(Settings.getSettingRemember()) eventChangeText();

        eventMouseOnEntered();
        eventMouseOnExited();
        eventMouseOnClicked();
        eventKeyOnField();
    }

    @FXML
    public void eventMouseOnEntered(){
        btnAuth.setOnMouseEntered(event ->
                btnAuth.setStyle("-fx-border-color: black; -fx-background-color: #c6ccd2"));
        fieldRecovery.setOnMouseEntered(event ->
                fieldRecovery.setStyle("-fx-text-fill:black"));
        fieldCreateAccount.setOnMouseEntered(mouseEvent ->
                fieldCreateAccount.setStyle("-fx-text-fill:black"));
        fieldSendCode.setOnMouseEntered(mouseEvent ->
                fieldSendCode.setStyle("-fx-text-fill:black"));
    }

    @FXML
    public void eventMouseOnExited(){
        fieldSendCode.setOnMouseExited(event ->
                fieldSendCode.setStyle("-fx-text-fill:grey"));
        fieldRecovery.setOnMouseExited(event ->
                fieldRecovery.setStyle("-fx-text-fill:grey"));
        fieldCreateAccount.setOnMouseExited(mouseEvent ->
                fieldCreateAccount.setStyle("-fx-text-fill:grey"));
        btnAuth.setOnMouseExited(event ->
                btnAuth.setStyle("-fx-border-color: black; -fx-background-color: LavenderBlush"));
    }

    @FXML
    public void eventMouseOnClicked(){
        fieldImage.setOnMouseClicked(mouseEvent ->
                WindowPage.openWebpage("https://hiveon.com/"));
        fieldRecovery.setOnMouseClicked(mouseEvent ->
                WindowPage.openWebpage("https://the.hiveos.farm/restore-pass/"));
        fieldGitHub.setOnMouseClicked(mouseEvent ->
                WindowPage.openWebpage("https://github.com/KirillNemtyrev/crypto"));
        fieldCreateAccount.setOnMouseClicked(mouseEvent ->
                WindowPage.openWebpage("https://the.hiveos.farm/register/"));
        fieldSendCode.setOnMouseClicked(event -> {

            String login = fieldLogin.getText().trim();
            if(login.isEmpty() || Request.sendCode(login) != Request.CODE_AUTHENTICATION_TOKEN){
                fieldLogin.setStyle("-fx-background-color: #c6ccd2; -fx-border-color: red");
                return;
            }
            textSendCode.setVisible(true);
            fieldSendCode.setStyle("-fx-text-fill:green");
        });
        btnAuth.setOnAction(event -> {
            String login = fieldLogin.getText().trim();
            String password = fieldPassword.getText().trim();
            String twofa_code = field2FA.getText().trim();

            if(login.isEmpty() || password.isEmpty() ||
                    Request.sendAuthentication(login, password, twofa_code) != Request.CODE_AUTHENTICATION_TOKEN) {
                fieldLogin.setStyle("-fx-background-color: #c6ccd2; -fx-border-color: red");
                fieldPassword.setStyle("-fx-background-color: #c6ccd2; -fx-border-color: red");
                field2FA.setStyle("-fx-background-color: #c6ccd2; -fx-border-color: red");
                return;
            }

            Settings.setSettingLogin(login);
            Settings.setSettingPassword(password);
            Settings.setSettingRemember(fieldSave.isSelected());
            Settings.setSettingToken(Account.getAccessToken());
            Settings.saveParams();

            Request.getAccount();
            Request.getFarms();

            Stage stage = (Stage) btnAuth.getScene().getWindow();
            WindowPage.updateWindow(stage, "Фермы", "farms.fxml", 950, 665);
        });
    }

    @FXML
    public void eventKeyOnField(){
        fieldLogin.textProperty().addListener((observableValue, oldValue, newValue) ->
                fieldLogin.setStyle("-fx-background-color: #c6ccd2; -fx-border-color: black"));
        fieldPassword.textProperty().addListener((observableValue, oldValue, newValue) ->
                fieldPassword.setStyle("-fx-background-color: #c6ccd2; -fx-border-color: black"));
        field2FA.textProperty().addListener((observableValue, oldValue, newValue) ->
                field2FA.setStyle("-fx-background-color: #c6ccd2; -fx-border-color: black"));
    }

    @FXML
    public void eventChangeText(){
        fieldLogin.setText(Settings.getSettingLogin());
        fieldPassword.setText(Settings.getSettingPassword());
    }
}
