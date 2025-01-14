package controller;

import java.io.IOException;
import java.util.Optional;

import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.ApplicationOption;
import utils.IStylizable;

public class MenuController implements IStylizable {

	@FXML
	private Label _lPierre;
	@FXML
	private Label _lFeuille;
	@FXML
	private Label _lCiseaux;
	@FXML
	private Button _bNouvPartie;
	@FXML
	private Button _bOptions;
	@FXML
	private Button _bAide;
	@FXML
	private Button _bQuitter;

	public MenuController() {

	}

	@FXML
	public void newGame(ActionEvent e) {
		Scene scene = ((Node) (e.getSource())).getScene();

		try {
			Dialog<Boolean> dialog = new Dialog<>();
			dialog.setTitle("Nouvelle Partie");

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/vueNouvellePartie.fxml"));
			GridPane grille;
			NouvellePartieController nvp = new NouvellePartieController();
			loader.setController(nvp);
			grille = loader.load();
			nvp.updateStyle();
			dialog.getDialogPane().setContent(grille);
			ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
			ButtonType buttonTypeAnnuler = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeAnnuler);

			dialog.setResultConverter(new Callback<ButtonType, Boolean>() {

				@Override
				public Boolean call(ButtonType b) {
					if (b == buttonTypeOk) {
						return nvp.buttonOK();
					}
					return nvp.buttonCancel();
				}
			});

			Optional<Boolean> result = dialog.showAndWait();

			if (result.get() == true) {
				try {
					// JeuController Jeu=new JeuController();
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/vueJeu.fxml"));
					JeuController controll = new JeuController();
					fxmlLoader.setController(controll);
					GridPane root = fxmlLoader.load();
					scene.setRoot(root);
					controll.updateStyle();
					if (!Main.appOption.isFullscreen()) {
						((Stage) scene.getWindow()).setHeight(Main.appOption.getResolutionH());
						((Stage) scene.getWindow()).setWidth(Main.appOption.getResolutionW());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	private void optionsMenu(ActionEvent e) {
		optionsMenuWithUpdate(e, this);
	}

	public void optionsMenuWithUpdate(ActionEvent e, IStylizable style) {
		GridPane grille;
		try {
			Dialog<Boolean> dialog = new Dialog<>();
			dialog.setTitle("Options");

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vueOptions.fxml"));
			OptionsController ctrl = new OptionsController();
			loader.setController(ctrl);
			grille = loader.load();
			ctrl.updateStyle();
			dialog.getDialogPane().setContent(grille);
			ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
			ButtonType buttonTypeAnnuler = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeAnnuler);

			dialog.setResultConverter(new Callback<ButtonType, Boolean>() {

				@Override
				public Boolean call(ButtonType b) {
					if (b == buttonTypeOk) {
						return ctrl.buttonOk();
					}
					return ctrl.buttonCancel();
				}
			});
			Optional<Boolean> result = dialog.showAndWait();

			if (result.get() == true) {
				try {
					ctrl.actionbuttonOk();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else
				ctrl.actionbuttonCancel();

		} catch (IOException e1)

		{
			e1.printStackTrace();
		}
		style.updateStyle();
	}

	@FXML
	public void contactsMenu(ActionEvent e) {
		GridPane grille;
		try {
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setTitle("Contacts");

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vueContact.fxml"));
			ContactController ctrl = new ContactController();
			loader.setController(ctrl);
			grille = loader.load();
			ctrl.updateStyle();
			dialog.getDialogPane().setContent(grille);
			ButtonType buttonTypeOkAnnuler = new ButtonType("OK", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(buttonTypeOkAnnuler);

			dialog.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	public void quitter(ActionEvent e) {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setHeaderText("Quitter le jeu");
		dialog.setContentText("Voulez-vous vraiment quitter le jeu ?\nToute progression ne sera pas sauvegardée");
		ButtonType cancelButton = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
		dialog.showAndWait().filter(button -> button == ButtonType.OK).ifPresent(response -> Platform.exit());
	}

	@Override
	public void updateStyle() {
		ApplicationOption options = Main.appOption;
		Stage stage = ((Stage) _lPierre.getScene().getWindow());
		boolean isFull = options.isFullscreen();
		stage.setFullScreen(isFull);
		if (!isFull) {
			stage.setHeight(options.getResolutionH());
			stage.setWidth(options.getResolutionW());
		}

		switch (options.getFontSize()) {
		case Petit:
			_lCiseaux.setStyle("-fx-font-size: 80.0px");
			_lFeuille.setStyle("-fx-font-size: 80.0px");
			_lPierre.setStyle("-fx-font-size: 80.0px");
			_bAide.setStyle("-fx-font-size: 20.0px");
			_bNouvPartie.setStyle("-fx-font-size: 20.0px");
			_bOptions.setStyle("-fx-font-size: 20.0px");
			_bQuitter.setStyle("-fx-font-size: 20.0px");
			break;
		case Moyen:
			_lCiseaux.setStyle("-fx-font-size: 90.0px");
			_lFeuille.setStyle("-fx-font-size: 90.0px");
			_lPierre.setStyle("-fx-font-size: 90.0px");
			_bAide.setStyle("-fx-font-size: 25.0px");
			_bNouvPartie.setStyle("-fx-font-size: 25.0px");
			_bOptions.setStyle("-fx-font-size: 25.0px");
			_bQuitter.setStyle("-fx-font-size: 25.0px");
			break;
		default:
			_lCiseaux.setStyle("-fx-font-size: 100.0px");
			_lFeuille.setStyle("-fx-font-size: 100.0px");
			_lPierre.setStyle("-fx-font-size: 100.0px");
			_bAide.setStyle("-fx-font-size: 30.0px");
			_bNouvPartie.setStyle("-fx-font-size: 30.0px");
			_bOptions.setStyle("-fx-font-size: 30.0px");
			_bQuitter.setStyle("-fx-font-size: 30.0px");
			break;
		}
	}
}
