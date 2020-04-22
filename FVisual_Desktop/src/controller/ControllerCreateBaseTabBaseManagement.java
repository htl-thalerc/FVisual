package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import bll.Base;
import handler.BaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import manager.GeoLocationsManager;

public class ControllerCreateBaseTabBaseManagement implements Initializable, MapComponentInitializedListener {
	@FXML
	private GoogleMapView mapView;
	@FXML
	private TextField tfBaseName, tfPlace, tfPostCode, tfStreet, tfHouseNr;
	@FXML
	private Label lbStatusbarPlace, lbStatusbarPostCode, lbStatusbarStreet, lbStatusbarHouseNr, lbStatusbarBaseName;

	private ControllerCreateBaseManagement controllerCreateBaseManagement;
	private GeoLocationsManager geoCodingService;
	private GoogleMap map;
	private Marker currentMarker;

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
				this.tfBaseName.setStyle("-fx-text-box-border: green;");
				this.tfBaseName.setStyle("-fx-focus-color: green;");
				this.tfBaseName.setStyle("-fx-border-color: green;");
			} else {
				isValidBasename.set(false);				
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
				this.tfHouseNr.setStyle("-fx-text-box-border: green;");
				this.tfHouseNr.setStyle("-fx-focus-color: green;");
				this.tfHouseNr.setStyle("-fx-border-color: green;");
			} else {
				isValidHouseNr.set(false);				
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
				this.tfPlace.setStyle("-fx-text-box-border: green;");
				this.tfPlace.setStyle("-fx-focus-color: green;");
				this.tfPlace.setStyle("-fx-border-color: green;");
			} else {
				isValidPlace.set(false);
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
				this.tfPostCode.setStyle("-fx-text-box-border: green;");
				this.tfPostCode.setStyle("-fx-focus-color: green;");
				this.tfPostCode.setStyle("-fx-border-color: green;");
			} else {
				isValidPostCode.set(false);
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
				this.tfStreet.setStyle("-fx-text-box-border: green;");
				this.tfStreet.setStyle("-fx-focus-color: green;");
				this.tfStreet.setStyle("-fx-border-color: green;");
			} else {
				isValidStreet.set(false);
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
		
		this.tfBaseName.setStyle(null);
		this.tfHouseNr.setStyle(null);
		this.tfPlace.setStyle(null);
		this.tfStreet.setStyle(null);
		this.tfPostCode.setStyle(null);
		
		//this.map.removeMarker(this.currentMarker);
	}

	@Override
	public void mapInitialized() {
		geoCodingService = GeoLocationsManager.newInstance();
		MapOptions mapOptions = new MapOptions();

		mapOptions.center(new LatLong(46.6103, 13.8558)).mapType(MapTypeIdEnum.ROADMAP).overviewMapControl(false)
				.panControl(false).rotateControl(false).scaleControl(false).streetViewControl(false).zoomControl(false)
				.zoom(12);

		map = mapView.createMap(mapOptions);

		map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
			try {
				LatLong latLong = event.getLatLong();

				// no Marker on MAP
				if (currentMarker == null) {
					LatLong currentPosition = new LatLong(latLong.getLatitude(), latLong.getLongitude());
					setMarkerOnMap(currentPosition);
					changeTextFields(geoCodingService.reverseGeoCoding(currentPosition));
				}
				// there is already a marker on the map placed
				else {
					map.removeMarker(this.currentMarker);
					LatLong currentPosition = new LatLong(latLong.getLatitude(), latLong.getLongitude());
					setMarkerOnMap(currentPosition);
					changeTextFields(geoCodingService.reverseGeoCoding(currentPosition));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		ArrayList<Base> list = BaseHandler.getInstance().getBaseList();

		list.forEach((b) -> {
			try {	
				String coordinates = geoCodingService.GeoCoding(b.getPlace() + " " + b.getPostCode() + " " + b.getStreet() + " " + b.getHouseNr());
				coordinates = coordinates.substring(coordinates.indexOf("\"coordinates\":")+15, coordinates.indexOf("\"coordinates\":")+34);
				
				String[] latLong = coordinates.split(",");
				double lat = Double.valueOf(latLong[1]);
				double lon = Double.valueOf(latLong[0]);
				
				LatLong latLonge = new LatLong(lat, lon);

				MarkerOptions markerOptionsCurrentMarker = new MarkerOptions();
				markerOptionsCurrentMarker.position(latLonge);
				Marker m = new Marker(markerOptionsCurrentMarker);
				map.addMarker(m);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		System.out.println("map initialized");
	}

	private void setMarkerOnMap(LatLong currentPosition) {
		MarkerOptions markerOptionsCurrentMarker = new MarkerOptions();
		markerOptionsCurrentMarker.position(currentPosition);
		this.currentMarker = new Marker(markerOptionsCurrentMarker);
		map.addMarker(this.currentMarker);
	}

	private void changeTextFields(String Adress) {
		try {
			String[] temp = Adress.split(",");
			tfPostCode.setText(temp[1].split(" ")[1]);
			tfPlace.setText(temp[1].split(" ")[2]);
			tfStreet.setText(temp[0].split(" ")[0]);
			tfHouseNr.setText(temp[0].split(" ")[1]);
		} catch(NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	public void btnChangeMarkerClicked(MouseEvent event) {
		try {
			geoCodingService = GeoLocationsManager.newInstance();
			String adress = tfStreet.getText() + "," + tfHouseNr.getText() + "," + tfPostCode.getText() + ","
					+ tfPlace.getText();
			System.out.println(geoCodingService.GeoCoding(adress));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}