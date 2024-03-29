package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import project_classes.Customer;
import project_classes.Rental;
import project_classes.Vehicle;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private Button btnNewCustomer;

    @FXML
    private Button btnNewVehicle;

    @FXML
    private Button btnRentVehicle;

    @FXML
    private Button btnReturnVehicle;

    @FXML
    private Button btnListAllRentals;

    @FXML
    private Pane status;

    @FXML
    private Label labelStatus;

    @FXML
    private GridPane pnAddCustomers;

    @FXML
    private GridPane pnAllRentals;

    @FXML
    private GridPane pnAddVehicle;

    @FXML
    private GridPane pnRentVehicle;

    @FXML
    private GridPane pnReturnVehicle;

    @FXML
    private GridPane pnAddCustomerFields;

    @FXML
    private GridPane pnAddVehicleFields;

    @FXML
    private Button addCustomerBtn;

    // Add Customer Stage variable
    @FXML
    private Button saveNewCustomer;

    @FXML
    private Button cancelAddCustomerStage;

    @FXML
    private TextField customerIDNewCustomer;

    @FXML
    private TextField phoneNumberNewCustomer;

    @FXML
    private TextField firsNameNewCustomer;

    @FXML
    private TextField surnameNewCustomer;

    @FXML
    private CheckBox canRentNewCustomer;

    // Add Vehicle Stage variable
    @FXML
    private Button addVehicleBtn;

    @FXML
    private Button saveNewVehicle;

    @FXML
    private Button cancelAddVehicle;

    @FXML
    private TextField vehicleNumNewVehicle;

    @FXML
    private TextField makeNewVehicle;

    @FXML
    private TextField dailyPriceNewVehicle;

    @FXML
    private TextField categoryNewVehicle;

    @FXML
    private CheckBox isAvalaibleNewVehicle;



    /*
     *************************
     * CUSTOMER TABLE SET UP *
     * ***********************
     * */
    @FXML
    private TableView<Customer> customerTableView;

    private ObservableList<Customer> customerCollection;

    @FXML
    private TableColumn<Customer, String> customerNumber;

    @FXML
    private TableColumn<Customer, String> customerFirstname;

    @FXML
    private TableColumn<Customer, String> customerSurname;

    @FXML
    private TableColumn<Customer, String> customerPhone;

    @FXML
    private TableColumn<Customer, String> customerCanRent;

    /*
     *************************
     * VEHICLE TABLE SET UP *
     * ***********************
     * */
    @FXML
    private TableView<Vehicle> vehicleTableView;

    @FXML
    private TableColumn<Vehicle, String> vehNumberCol;

    @FXML
    private TableColumn<Vehicle, String> vehMakeCol;

    @FXML
    private TableColumn<Vehicle, String> vehCatCol;

    @FXML
    private TableColumn<Vehicle, Double> vehRentPriceCol;

    @FXML
    private TableColumn<Vehicle, String> vehAvailCol;

    private ObservableList<Vehicle> vehicleCollection;


    /*
     *************************
     * RENTAL TABLE SET UP *
     * ***********************
     * */
    @FXML
    private TableView<Rental> rentalsTable;

    @FXML
    private TableColumn<Vehicle, Integer> rentalNumCol;

    @FXML
    private TableColumn<Vehicle, String> rentalDateCol;

    @FXML
    private TableColumn<Vehicle, String> rentalReturnDateCol;

    @FXML
    private TableColumn<Vehicle, Double> rentalDailyPriceCol;

    @FXML
    private TableColumn<Vehicle, String> rentalCustCol;

    @FXML
    private TableColumn<Vehicle, String> rentalVehCol;

    @FXML
    private TableColumn<Rental, Double> rentalTotalCol;

    private ObservableList<Rental> rentalCollection;


    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initializing the client side
        client = new Client("localhost", 8085);
        client.runClient();

        // customers tableview
        customerNumber.setCellValueFactory(new PropertyValueFactory<>("IdNum"));
        customerFirstname.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("PhoneNum"));
        customerCanRent.setCellValueFactory(new PropertyValueFactory<>("canRent"));
        customerCollection = FXCollections.observableArrayList(client.getCustomers());
        customerTableView.setItems(customerCollection);

        // vehicles tableview
        vehNumberCol.setCellValueFactory(new PropertyValueFactory<>("VehNumber"));
        vehMakeCol.setCellValueFactory(new PropertyValueFactory<>("Make"));
        vehCatCol.setCellValueFactory(new PropertyValueFactory<>("Category"));
        vehRentPriceCol.setCellValueFactory(new PropertyValueFactory<>("RentalPrice"));
        vehAvailCol.setCellValueFactory(new PropertyValueFactory<>("AvailableForRent"));
        vehicleCollection = FXCollections.observableArrayList(client.getVehicles());
        vehicleTableView.setItems(vehicleCollection);

        // rentals tableview
        rentalNumCol.setCellValueFactory(new PropertyValueFactory<>("RentalNumber"));
        rentalDateCol.setCellValueFactory(new PropertyValueFactory<>("DateRental"));
        rentalReturnDateCol.setCellValueFactory(new PropertyValueFactory<>("DateReturned"));
        rentalDailyPriceCol.setCellValueFactory(new PropertyValueFactory<>("PricePerDay"));
        rentalCustCol.setCellValueFactory(new PropertyValueFactory<>("CustNumber"));
        rentalVehCol.setCellValueFactory(new PropertyValueFactory<>("VehNumber"));
        rentalTotalCol.setCellValueFactory(new PropertyValueFactory<>("TotalRental"));
        rentalCollection = FXCollections.observableArrayList(client.getRentals());
        rentalsTable.setItems(rentalCollection);
    }

    @FXML
    private void handleClicks(ActionEvent event) {

        if (event.getSource() == btnNewCustomer) {
            // New Customer
            labelStatus.setText("Customers");
            status.setBackground(new Background(new BackgroundFill(Color.rgb(113, 86, 221),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            managePaneVisibility(pnAddCustomers);
        } else if (event.getSource() == btnNewVehicle) {
            // New Vehicle
            labelStatus.setText("Vehicles");
            status.setBackground(new Background(new BackgroundFill(Color.rgb(43, 63, 99),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            managePaneVisibility(pnAddVehicle);
        } else if (event.getSource() == btnRentVehicle) {
            /*
             * ****************
             *   RENT VEHICLE *
             * ****************
             * */
            labelStatus.setText("Rentals");
            status.setBackground(new Background(new BackgroundFill(Color.rgb(44, 99, 63),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            managePaneVisibility(pnRentVehicle);
        } else if (event.getSource() == btnReturnVehicle) {
            // Return Vehicle
            labelStatus.setText("Returns");
            status.setBackground(new Background(new BackgroundFill(Color.rgb(99, 43, 63),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            managePaneVisibility(pnReturnVehicle);
        } else if (event.getSource() == btnListAllRentals) {
            // List rentals
            labelStatus.setText("All Rentals");
            status.setBackground(new Background(new BackgroundFill(Color.rgb(42, 28, 66),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            managePaneVisibility(pnAllRentals);
        }

    }

    @FXML
    protected void handleAddCustomer(ActionEvent event) {

        if (event.getSource() == addCustomerBtn) {
            managePaneVisibility(pnAddCustomerFields);
        } else if (event.getSource() == saveNewCustomer) {
            /*
             * *****************
             *   SAVE CUSTOMER *
             * *****************
             * */

            String custID = customerIDNewCustomer.getText();
            String phoneNumber = phoneNumberNewCustomer.getText();
            String firstName = firsNameNewCustomer.getText();
            String surname = surnameNewCustomer.getText();
            boolean canRent = canRentNewCustomer.isSelected();

            customerIDNewCustomer.setText("");
            phoneNumberNewCustomer.setText("");
            firsNameNewCustomer.setText("");
            surnameNewCustomer.setText("");
            canRentNewCustomer.setText("");

            Customer customer = new Customer(firstName, surname, custID, phoneNumber, canRent);
            String response = client.writeCustomerToServer(customer);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText(response);

            alert.showAndWait();
        } else if (event.getSource() == cancelAddCustomerStage) {
            managePaneVisibility(pnAddCustomers);
        }
    }

    @FXML
    void handleAddVehicle(ActionEvent event) {
        if (event.getSource() == addVehicleBtn) {
            managePaneVisibility(pnAddVehicleFields);
        } else if (event.getSource() == saveNewVehicle) {
            /*
             * *****************
             *   SAVE VEHICLE *
             * *****************
             * */

            int vn = Integer.parseInt(vehicleNumNewVehicle.getText());
            String make = makeNewVehicle.getText();
            int category = categoryNewVehicle.getText().equals("Sedan")? 1 : 2;
            double dailyPrice = Double.parseDouble(dailyPriceNewVehicle.getText());
            boolean isAvailable = isAvalaibleNewVehicle.isSelected();

            vehicleNumNewVehicle.setText("");
            makeNewVehicle.setText("");
            categoryNewVehicle.setText("");
            dailyPriceNewVehicle.setText("");
            isAvalaibleNewVehicle.setText("");

            Vehicle vehicle = new Vehicle(vn, make, category, dailyPrice, isAvailable);
//            String response = client.writeCustomerToServer(customer);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("response");

            alert.showAndWait();
        } else if (event.getSource() == cancelAddVehicle) {
            managePaneVisibility(pnAddVehicle);
        }
    }

    private void managePaneVisibility(Pane pane) {
        Pane[] panes = {pnAddCustomers, pnAllRentals, pnAddVehicle, pnRentVehicle, pnReturnVehicle, pnAddCustomerFields,
        pnAddVehicleFields};
        for (Pane cpane : panes) {
            if (pane.equals(cpane)) {
                cpane.setVisible(true);
            } else {
                cpane.setVisible(false);
            }
        }
    }

}
