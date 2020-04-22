package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

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

import bll.Base;
import bll.Operation;
import bll.OperationCode;
import bll.OperationType;
import handler.BaseHandler;
import handler.OperationCodeHandler;
import handler.OperationTypeHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import manager.GeoLocationsManager;

public class ControllerCreateOperationTabOperationManagement implements Initializable, MapComponentInitializedListener {
	@FXML
	private GoogleMapView mapView;
	@FXML
	private ComboBox<OperationCode> cbOperationCode;
	@FXML
	private ComboBox<OperationType> cbOperationType;
	@FXML
	private ComboBox<Base> cbBase;
	@FXML
	private TextArea tfShortDescription;
	@FXML
	private TextField tfTitle, tfAddress;
	@FXML
	private DatePicker dpDate;
	@FXML
	private Label lbAddress, lbBasename;

	private Marker currentOperationMarker;
	private String address, postCode;

	private GeoLocationsManager geoCodingService;
	private GoogleMap map;
	private ArrayList<Base> bases = new ArrayList<Base>();
	private ControllerCreateOperationManagement controllerCreateOperationManagement;
	private int plzFromAddress = 0;
	private Base selectedBase;

	public ControllerCreateOperationTabOperationManagement(
			ControllerCreateOperationManagement controllerCreateOperationManagement) {
		this.controllerCreateOperationManagement = controllerCreateOperationManagement;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.fillComboboxes();
		this.initComponentsListeners();
		mapView.addMapInializedListener(this);
		this.controllerCreateOperationManagement.setAllOptionButtonsDisability(true);
	}

	@Override
	public void mapInitialized() {
		fillMapViewWithBases();
	}

	private void fillMapViewWithBases() {
		geoCodingService = GeoLocationsManager.newInstance();
		MapOptions mapOptions = new MapOptions();

		mapOptions.center(new LatLong(46.6103, 13.8558)).mapType(MapTypeIdEnum.ROADMAP).overviewMapControl(false)
				.panControl(false).rotateControl(false).scaleControl(false).streetViewControl(false).zoomControl(false)
				.zoom(12);

		map = mapView.createMap(mapOptions);

		ArrayList<Base> list = BaseHandler.getInstance().getBaseList();

		list.forEach((b) -> {
			try {
				String coordinates = geoCodingService
						.GeoCoding(b.getPlace() + " " + b.getPostCode() + " " + b.getStreet() + " " + b.getHouseNr());
				coordinates = coordinates.substring(coordinates.indexOf("\"coordinates\":") + 15,
						coordinates.indexOf("\"coordinates\":") + 34);

				String[] latLong = coordinates.split(",");
				double lat = Double.valueOf(latLong[1]);
				double lon = Double.valueOf(latLong[0]);

				LatLong latLonge = new LatLong(lat, lon);

				MarkerOptions markerOptionsCurrentMarker = new MarkerOptions();
				markerOptionsCurrentMarker.position(latLonge);
				Marker m = new Marker(markerOptionsCurrentMarker);
				map.addMarker(m);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		bases = list;

		map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
			try {
				LatLong latLong = event.getLatLong();

				// no Marker on MAP
				if (currentOperationMarker == null) {
					LatLong currentPosition = new LatLong(latLong.getLatitude(), latLong.getLongitude());
					setMarkerOnMap(currentPosition);
					changeTextFields(geoCodingService.reverseGeoCoding(currentPosition));
				}
				// there is already a marker on the map placed
				else {
					map.removeMarker(this.currentOperationMarker);
					LatLong currentPosition = new LatLong(latLong.getLatitude(), latLong.getLongitude());
					setMarkerOnMap(currentPosition);
					changeTextFields(geoCodingService.reverseGeoCoding(currentPosition));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void setMarkerOnMap(LatLong currentPosition) {
		MarkerOptions markerOptionsCurrentMarker = new MarkerOptions();
		markerOptionsCurrentMarker.position(currentPosition);
		this.currentOperationMarker = new Marker(markerOptionsCurrentMarker);
		map.addMarker(this.currentOperationMarker);
	}

	private void changeTextFields(String Adress) {
		try {
			String[] temp = Adress.split(",");
			this.postCode = temp[1].split(" ")[1];
			this.address = temp[0].split(" ")[0] + " " + temp[0].split(" ")[1];
			this.tfAddress.setText(this.postCode + " " + this.address);
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	private Base checkForBases(String Adress) {
		Base clickedBase = new Base();
		try {
			String[] temp = Adress.split(",");
			for (int i = 0; i < bases.size(); i++) {
				if (temp[1].contains(Integer.toString((bases.get(i).getPostCode())))) {
					clickedBase = bases.get(i);
				}
			}
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return clickedBase;
	}

	private void fillComboboxes() {
		if (this.cbOperationCode.getItems().size() == 0) {
			ObservableList<OperationCode> obsListOfOperationCodes = FXCollections.observableArrayList();
			ObservableList<OperationType> obsListOfOperationTypes = FXCollections.observableArrayList();
			ObservableList<Base> obsListOfBases = FXCollections.observableArrayList();

			obsListOfOperationCodes.addAll(OperationCodeHandler.getInstance().getOperationCodeList());
			obsListOfOperationTypes.addAll(OperationTypeHandler.getInstance().getOperationTypeList());
			obsListOfBases.addAll(BaseHandler.getInstance().getBaseList());

			this.cbOperationCode.setItems(obsListOfOperationCodes);
			this.cbOperationType.setItems(obsListOfOperationTypes);
			this.cbBase.setItems(obsListOfBases);

			this.cbOperationCode.getSelectionModel().select(0);
			this.cbOperationType.getSelectionModel().select(0);
			this.cbBase.setPromptText("Select Base");
		}
	}

	private void initComponentsListeners() {
		AtomicBoolean isValidTitle = new AtomicBoolean(false);
		AtomicBoolean isValidShortDesc = new AtomicBoolean(false);
		AtomicBoolean isValidAddress = new AtomicBoolean(false);
		AtomicBoolean isValidDate = new AtomicBoolean(false);

		this.cbBase.valueProperty().addListener(new ChangeListener<Base>() {
			@Override
			public void changed(ObservableValue<? extends Base> obs, Base oldValue, Base newValue) {
				selectedBase = newValue;
				lbBasename.setText(selectedBase.getName());
				lbAddress.setText(selectedBase.getStreet() + " " + selectedBase.getHouseNr() + ", " + selectedBase.getPostCode() 
						+ selectedBase.getPlace());
			}
		});

		this.tfTitle.textProperty().addListener((obj, oldValue, newValue) -> {
			if (this.tfTitle.getText().length() >= 10) {
				isValidTitle.set(true);
				this.tfTitle.setStyle("-fx-text-box-border: green;");
				this.tfTitle.setStyle("-fx-focus-color: green;");
				this.tfTitle.setStyle("-fx-border-color: green;");
			} else {
				isValidTitle.set(false);
				this.tfTitle.setStyle("-fx-text-box-border: red;");
				this.tfTitle.setStyle("-fx-focus-color: red;");
				this.tfTitle.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidTitle.get(), isValidShortDesc.get(), isValidAddress.get(), isValidDate.get());
		});

		this.tfShortDescription.textProperty().addListener((obj, oldValue, newValue) -> {
			if (this.tfShortDescription.getText().length() >= 30) {
				isValidShortDesc.set(true);
				this.tfShortDescription.setStyle("-fx-text-box-border: green;");
				this.tfShortDescription.setStyle("-fx-focus-color: green;");
				this.tfShortDescription.setStyle("-fx-border-color: green;");
			} else {
				isValidShortDesc.set(false);
				this.tfShortDescription.setStyle("-fx-text-box-border: red;");
				this.tfShortDescription.setStyle("-fx-focus-color: red;");
				this.tfShortDescription.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidTitle.get(), isValidShortDesc.get(), isValidAddress.get(), isValidDate.get());
		});

		this.tfAddress.textProperty().addListener((obj, oldValue, newValue) -> {
			if (this.tfAddress.getText().length() >= 1) {
				isValidAddress.set(true);
				this.tfAddress.setStyle("-fx-text-box-border: green;");
				this.tfAddress.setStyle("-fx-focus-color: green;");
				this.tfAddress.setStyle("-fx-border-color: green;");
			} else {
				isValidAddress.set(false);
				this.tfAddress.setStyle("-fx-text-box-border: red;");
				this.tfAddress.setStyle("-fx-focus-color: red;");
				this.tfAddress.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidTitle.get(), isValidShortDesc.get(), isValidAddress.get(), isValidDate.get());
		});

		this.dpDate.valueProperty().addListener((obj, oldValue, newValue) -> {
			if (this.dpDate.getValue() != null) {
				isValidDate.set(true);
				this.dpDate.setStyle("");
			} else {
				isValidDate.set(false);
				this.dpDate.setStyle("-fx-text-box-border: red;");
				this.dpDate.setStyle("-fx-focus-color: red;");
				this.dpDate.setStyle("-fx-border-color: red;");
			}
			setControlDisability(isValidTitle.get(), isValidShortDesc.get(), isValidAddress.get(), isValidDate.get());
		});
	}

	private void setControlDisability(boolean isValidTitle, boolean isValidShortDesc, boolean isValidAddress,
			boolean isValidDate) {
		if (isValidTitle && isValidShortDesc && isValidAddress && isValidDate && this.selectedBase != null) {
			this.controllerCreateOperationManagement.setButtonResetDisability(false);
			this.controllerCreateOperationManagement.setButtonNextDisability(false);
			this.controllerCreateOperationManagement.setButtonFinishDisability(false);
			//this.controllerCreateOperationManagement.setTabOperationVehicleManagementDisability(false);
			//this.controllerCreateOperationManagement.setTabMemberManagementDisability(false);
			//this.controllerCreateOperationManagement.setTabOtherOrganisationManagementDisability(false);
		} else {
			this.controllerCreateOperationManagement.setButtonResetDisability(true);
			this.controllerCreateOperationManagement.setButtonFinishDisability(true);
			this.controllerCreateOperationManagement.setButtonNextDisability(true);
//			this.controllerCreateOperationManagement.setTabOperationVehicleManagementDisability(true);
//			this.controllerCreateOperationManagement.setTabMemberManagementDisability(true);
//			this.controllerCreateOperationManagement.setTabOtherOrganisationManagementDisability(true);
		}
	}

	public Operation getCreatedOperationData() {
		return new Operation(0, this.cbOperationCode.getSelectionModel().getSelectedItem(),
				this.cbOperationType.getSelectionModel().getSelectedItem(), this.tfAddress.getText().trim(),
				Integer.parseInt(this.postCode), this.tfTitle.getText().trim(), this.tfShortDescription.getText().trim(),
				Date.from(Instant.from(this.dpDate.getValue().atStartOfDay(ZoneId.systemDefault()))),
				new Timestamp(System.currentTimeMillis()), this.selectedBase);
	}
	
	public Base getSelectedBase() {
		return this.selectedBase;
	}
}