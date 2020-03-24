package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import manager.GeoLocationsManager;

public class ControllerCreateOperationTabOperationManagement implements Initializable, MapComponentInitializedListener {
	@FXML
	private GoogleMapView mapView;
	@FXML
	private TextField tfBaseName, tfPlace, tfPostCode, tfStreet, tfHouseNr;
	@FXML
	private Label lbStatusbarPlace, lbStatusbarPostCode, lbStatusbarStreet, lbStatusbarHouseNr, lbStatusbarBaseName;
	
	private ControllerCreateOperationManagement controllerCreateOperationManagement;
	private GeoLocationsManager geoCodingService;
	private GoogleMap map;
	private Marker currentMarker;
	
	public ControllerCreateOperationTabOperationManagement(ControllerCreateOperationManagement controllerCreateOperationManagement) {
		this.controllerCreateOperationManagement = controllerCreateOperationManagement;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.controllerCreateOperationManagement.setAllOptionButtonsDisability(false);
		this.initTextFieldListeners();
	}
	
	private void initTextFieldListeners() {
		this.tfBaseName.textProperty().addListener((obj, oldVal, newVal) -> {
			this.controllerCreateOperationManagement.setAllOptionButtonsDisability(false);
			this.controllerCreateOperationManagement.setTabOperationVehicleManagementDisability(false);
		});
	}

	@Override
	public void mapInitialized() {
		MapOptions mapOptions = new MapOptions();

		mapOptions.center(new LatLong(46.6103, 13.8558)).mapType(MapTypeIdEnum.ROADMAP).overviewMapControl(false)
				.panControl(false).rotateControl(false).scaleControl(false).streetViewControl(false).zoomControl(false)
				.zoom(12);

		map = mapView.createMap(mapOptions);

		map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
			try {
				LatLong latLong = event.getLatLong();
				geoCodingService = GeoLocationsManager.newInstance();

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
		System.out.println("map initialized");
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

	private void setMarkerOnMap(LatLong currentPosition) {
		MarkerOptions markerOptionsCurrentMarker = new MarkerOptions();
		markerOptionsCurrentMarker.position(currentPosition);
		this.currentMarker = new Marker(markerOptionsCurrentMarker);
		map.addMarker(this.currentMarker);
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