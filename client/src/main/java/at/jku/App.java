package at.jku;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        final HttpClient client = HttpClient.newBuilder().authenticator(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("productionManager","productionManager".toCharArray());
            }
        }).build();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/sortedOrders")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response);
        System.out.println(response.body());
        System.out.println();

        final JSONArray jsonArray = new JSONArray(response.body());
        for (int i = 0; i < jsonArray.length(); i++){
            System.out.println(
                    jsonArray.getJSONObject(i).get("id") + ": "+ jsonArray.getJSONObject(i).get("description")+ " " +
                            jsonArray.getJSONObject(i).get("priority") + " " + jsonArray.getJSONObject(i).get("machineDescription")
            );
        }

        System.out.println("Auswahl einer Production Order: ");


        final Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        final int id = Integer.parseInt(line);
        final JSONObject jsonObject = jsonArray.getJSONObject(id-1);
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/getOrderById?id="+id +
                "&description="+jsonObject.get("description")+
                "&priority="+jsonObject.get("priority")+
                "&machineDescription="+jsonObject.get("machineDescription"))).build();
        response = client.send(request,HttpResponse.BodyHandlers.ofString());

        System.out.println(response);
        System.out.println(response.body());
    }



}
