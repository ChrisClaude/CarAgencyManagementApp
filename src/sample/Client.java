package sample;

import project_classes.Customer;
import project_classes.Rental;
import project_classes.Vehicle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Client {

    private String serverName;
    private int port;
    private Object[] data;

    Client(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;

        try (Socket client = new Socket(serverName, port)) {
            System.out.printf("Connecting to %s on port %d%n", serverName, port);
            System.out.printf("Connected to %s%n", client.getRemoteSocketAddress().toString());

            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject("This is from " + client.getLocalSocketAddress());

            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            data = (Object[]) in.readObject();
        } catch (IOException e) {
            System.out.printf("An error occurred: %s%n", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    List<Customer> getCustomers() {
        return (List<Customer>) data[0];
    }

//    void setCustomers() {}

    List<Vehicle> getVehicles() {
        return (List<Vehicle>) data[1];
    }

    List<Rental> getRentals() {
        return (List<Rental>) data[2];
    }

    public String writeCustomerToServer(Customer customer) {
        String response = "response";
        try (Socket client = new Socket(serverName, port)) {
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(customer);

            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            do {
                if (in.readObject() instanceof String)
                    response =  (String) in.readObject();

            } while (!(response.equals("Customer added") || response.equals("An error occurred while adding customer")));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return String.format("Client = {server name: %s, port: %d}",
                serverName, port);
    }
}
