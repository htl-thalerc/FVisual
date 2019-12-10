package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import bll.Base;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class ControllerCreateBaseTabBaseManagement implements Initializable, MapComponentInitializedListener {
	@FXML
	private GoogleMapView mapView;
	@FXML
	private TextField tfBaseName;
	@FXML
	private TextField tfPlace;
	@FXML
	private TextField tfPostCode;
	@FXML
	private TextField tfStreet;
	@FXML
	private TextField tfHouseNr;
	@FXML
	private Label lbStatusbarPlace;
	@FXML
	private Label lbStatusbarPostCode;
	@FXML
	private Label lbStatusbarStreet;
	@FXML
	private Label lbStatusbarHouseNr;
	@FXML
	private Label lbStatusbarBaseName;

	private ControllerCreateBaseManagement controllerCreateBaseManagement;
    private GoogleMap map;

	public ControllerCreateBaseTabBaseManagement(ControllerCreateBaseManagement controllerCreateBaseManagement) {
		this.controllerCreateBaseManagement = controllerCreateBaseManagement;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTextFieldPatterns();
		this.initTextFieldListeners();
		this.initGoogleMaps();

	}
	
	private void initGoogleMaps() {
		mapView.addMapInializedListener(this);
		System.out.println("initializing google maps");
	}

	private void initTextFieldPatterns() {
		final Pattern pattern = Pattern.compile("\\d{0,4}");
		TextFormatter<?> formatter = new TextFormatter<>(change -> {
			if (pattern.matcher(change.getControlNewText()).matches()) {
				return change;
			} else {
				return null;
			}
		});
		this.tfPostCode.setTextFormatter(formatter);
	}

	private void initTextFieldListeners() {
		AtomicBoolean isValidBasename = new AtomicBoolean(false);
		AtomicBoolean isValidHouseNr = new AtomicBoolean(false);
		AtomicBoolean isValidPlace = new AtomicBoolean(false);
		AtomicBoolean isValidPostCode = new AtomicBoolean(false);
		AtomicBoolean isValidStreet = new AtomicBoolean(false);

		this.tfBaseName.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 12) {
				isValidBasename.set(true);
				this.lbStatusbarBaseName.setText("Valid Inputvalue for Basename");
				this.tfBaseName.setStyle("-fx-text-box-border: green;");
				this.tfBaseName.setStyle("-fx-focus-color: green;");
				this.tfBaseName.setStyle("-fx-border-color: green;");
			} else {
				isValidBasename.set(false);
				this.lbStatusbarBaseName.setText("Invalid Inputvalue for Basename - Inputvalue is too short (length >= 12)");
				this.tfBaseName.setStyle("-fx-text-box-border: red;");
				this.tfBaseName.setStyle("-fx-focus-color: red;");
				this.tfBaseName.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidBasename.get(), isValidHouseNr.get(), isValidPlace.get(), isValidPostCode.get(),
					isValidStreet.get());
		});

		this.tfHouseNr.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 1) {
				isValidHouseNr.set(true);
				this.lbStatusbarHouseNr.setText("Valid Inputvalue for HouseNr");
				this.tfHouseNr.setStyle("-fx-text-box-border: green;");
				this.tfHouseNr.setStyle("-fx-focus-color: green;");
				this.tfHouseNr.setStyle("-fx-border-color: green;");
			} else {
				isValidHouseNr.set(false);
				this.lbStatusbarHouseNr.setText("Invalid Inputvalue for HouseNr - Inputvalue is too short (length >= 1)");
				this.tfHouseNr.setStyle("-fx-text-box-border: red;");
				this.tfHouseNr.setStyle("-fx-focus-color: red;");
				this.tfHouseNr.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidBasename.get(), isValidHouseNr.get(), isValidPlace.get(), isValidPostCode.get(),
					isValidStreet.get());
		});

		this.tfPlace.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 3) {
				isValidPlace.set(true);
				this.lbStatusbarPlace.setText("Valid Inputvalue for Place");
				this.tfPlace.setStyle("-fx-text-box-border: green;");
				this.tfPlace.setStyle("-fx-focus-color: green;");
				this.tfPlace.setStyle("-fx-border-color: green;");
			} else {
				isValidPlace.set(false);
				this.lbStatusbarPlace.setText("Invalid Inputvalue for Place - Inputvalue is too short (length >= 3)");
				this.tfPlace.setStyle("-fx-text-box-border: red;");
				this.tfPlace.setStyle("-fx-focus-color: red;");
				this.tfPlace.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidBasename.get(), isValidHouseNr.get(), isValidPlace.get(), isValidPostCode.get(),
					isValidStreet.get());
		});

		this.tfPostCode.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() == 4) {
				isValidPostCode.set(true);
				this.lbStatusbarPostCode.setText("Valid Inputvalue for PostCode");
				this.tfPostCode.setStyle("-fx-text-box-border: green;");
				this.tfPostCode.setStyle("-fx-focus-color: green;");
				this.tfPostCode.setStyle("-fx-border-color: green;");
			} else {
				isValidPostCode.set(false);
				this.lbStatusbarPostCode.setText("Invalid Inputvalue for PostCode - Inputvalue is too short (length = 4)");
				this.tfPostCode.setStyle("-fx-text-box-border: red;");
				this.tfPostCode.setStyle("-fx-focus-color: red;");
				this.tfPostCode.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidBasename.get(), isValidHouseNr.get(), isValidPlace.get(), isValidPostCode.get(),
					isValidStreet.get());
		});

		this.tfStreet.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 3) {
				isValidStreet.set(true);
				this.lbStatusbarStreet.setText("Valid Inputvalue for Street");
				this.tfStreet.setStyle("-fx-text-box-border: green;");
				this.tfStreet.setStyle("-fx-focus-color: green;");
				this.tfStreet.setStyle("-fx-border-color: green;");
			} else {
				isValidStreet.set(false);
				this.lbStatusbarStreet.setText("Invalid Inputvalue for Street - Inputvalue is too short (length >= 3)");
				this.tfStreet.setStyle("-fx-text-box-border: red;");
				this.tfStreet.setStyle("-fx-focus-color: red;");
				this.tfStreet.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidBasename.get(), isValidHouseNr.get(), isValidPlace.get(), isValidPostCode.get(),
					isValidStreet.get());
		});
	}

	private void setControlDisability(boolean baseName, boolean houseNr, boolean place, boolean postCode,
			boolean street) {
		if (baseName && houseNr && place && postCode && street) {
			this.controllerCreateBaseManagement.setButtonResetDisability(false);
			this.controllerCreateBaseManagement.setButtonNextDisability(false);
			this.controllerCreateBaseManagement.setButtonFinishDisability(false);
			this.controllerCreateBaseManagement.setTabOperationVehicleManagementDisability(false);
			this.controllerCreateBaseManagement.setTabMemberManagementDisability(false);
		} else {
			this.controllerCreateBaseManagement.setButtonResetDisability(true);
			this.controllerCreateBaseManagement.setButtonFinishDisability(true);
			this.controllerCreateBaseManagement.setButtonNextDisability(true);
			this.controllerCreateBaseManagement.setTabOperationVehicleManagementDisability(true);
			this.controllerCreateBaseManagement.setTabMemberManagementDisability(true);
		}
	}

	public Base getCreatedBaseData() {
		return new Base(0, this.tfBaseName.getText(), this.tfPlace.getText(),
				Integer.parseInt(this.tfPostCode.getText()), this.tfStreet.getText(), this.tfHouseNr.getText());
	}

	public void reset() {
		this.tfBaseName.clear();
		this.tfHouseNr.clear();
		this.tfPlace.clear();
		this.tfPostCode.clear();
		this.tfStreet.clear();
	}

	@Override
	public void mapInitialized() {
		MapOptions mapOptions = new MapOptions();
        
        mapOptions.center(new LatLong(46.6103, 13.8558))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);
        System.out.println("map initialized");
	}
}