import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Principal {
    // Clave de API para acceder al servicio de conversión de moneda.
    private static final String API_KEY = "3682c5a0e818d8249c2e3319";
    // URL base para el servicio de conversión de moneda.
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Instancia de HttpClient para realizar solicitudes HTTP.
        HttpClient client = HttpClient.newHttpClient();
        // Construye la solicitud HTTP para obtener las tasas de cambio más recientes.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + API_KEY + "/latest/USD"))
                .build();

        // Envía la solicitud HTTP y obtiene la respuesta.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Instancia de Gson para analizar el JSON de la respuesta.
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        // Instancia de Scanner para leer la entrada del usuario.
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Menú principal del programa.
        while (running) {
            System.out.println("Bienvenido al Conversor de Monedas");
            System.out.println("1. Convertir monedas");
            System.out.println("2. Salir");

            // Lee la opción seleccionada por el usuario.
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Ejecuta la función para convertir moneda.
                    convertCurrency(jsonObject);
                    break;
                case 2:
                    // Termina el programa.
                    System.out.println("Saliendo del programa");
                    running = false;
                    break;
                default:
                    // Opción no válida.
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    // Función para convertir la moneda.
    public static void convertCurrency(JsonObject jsonObject) {
        Scanner scanner = new Scanner(System.in);

        // Lee la cantidad a convertir.
        System.out.println("Ingrese la cantidad a convertir:");
        double amount = scanner.nextDouble();

        // Lee la moneda de origen.
        System.out.println("Ingrese la moneda de origen:");
        String sourceCurrency = getCurrencyCode(scanner);

        // Lee la moneda de destino.
        System.out.println("Ingrese la moneda de destino:");
        String targetCurrency = getCurrencyCode(scanner);

        // Obtiene las tasas de cambio para las monedas de origen y destino.
        double sourceRate = jsonObject.getAsJsonObject("conversion_rates").get(sourceCurrency).getAsDouble();
        double targetRate = jsonObject.getAsJsonObject("conversion_rates").get(targetCurrency).getAsDouble();

        // Realiza la conversión de moneda.
        double result = amount * (targetRate / sourceRate);

        // Imprime el resultado de la conversión.
        System.out.println("El resultado de la conversión es: " + result + " " + targetCurrency);
    }

    // Función para obtener el código de moneda.
    public static String getCurrencyCode(Scanner scanner) {
        System.out.println("Opciones de moneda:");
        System.out.println("1. ARS - Peso argentino");
        System.out.println("2. BOB - Boliviano boliviano");
        System.out.println("3. BRL - Real brasileño");
        System.out.println("4. CLP - Peso chileno");
        System.out.println("5. COP - Peso colombiano");
        System.out.println("6. USD - Dólar estadounidense");

        System.out.println("Seleccione el código de la moneda:");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return "ARS";
            case 2:
                return "BOB";
            case 3:
                return "BRL";
            case 4:
                return "CLP";
            case 5:
                return "COP";
            case 6:
                return "USD";
            default:
                System.out.println("Opción no válida. Seleccionando USD por defecto.");
                return "USD";
        }
    }

}
