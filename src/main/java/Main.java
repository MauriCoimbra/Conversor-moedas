import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = "a4bbbd7df451e259ecb35118";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();

        String opcao = "";
        String baseCode;
        String targetCode;

        while (true) {
            System.out.println("******************************************");
            System.out.println("Seja bem-vindo/a ao Conversor de Moeda :)");
            System.out.println(" ");
            System.out.println("1) Dólar >> Peso argentino");
            System.out.println("2) Peso argentino >> Dólar");
            System.out.println("3) Dólar >> Real brasileiro");
            System.out.println("4) Real brasileiro >> Dólar");
            System.out.println("5) Dólar >> Peso colombiano");
            System.out.println("6) Peso colombiano >> Dólar");
            System.out.println("7) Sair");
            System.out.println(" ");
            System.out.println("Escolha uma opção válida:");
            System.out.println("******************************************");
            System.out.println(" ");

            opcao = scanner.nextLine();

            if (opcao.equals("7")) {
                System.out.println("Obrigado por usar o conversor!");
                break;
            }

            switch (opcao) {
                case "1":
                    baseCode = "USD";
                    targetCode = "ARS";
                    break;
                case "2":
                    baseCode = "ARS";
                    targetCode = "USD";
                    break;
                case "3":
                    baseCode = "USD";
                    targetCode = "BRL";
                    break;
                case "4":
                    baseCode = "BRL";
                    targetCode = "USD";
                    break;
                case "5":
                    baseCode = "USD";
                    targetCode = "COP";
                    break;
                case "6":
                    baseCode = "COP";
                    targetCode = "USD";
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
            }

            try {
                String endereco = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/" + baseCode + "/" + targetCode;

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                Conversao conversao = gson.fromJson(response.body(), Conversao.class);

                System.out.println("Digite o valor em " + baseCode + " para converter:");
                double valor = scanner.nextDouble();
                scanner.nextLine();

                double valorConvertido = valor * conversao.getConversionRate();
                System.out.println(String.format("O valor de %.2f %s corresponde a %.2f %s", valor, baseCode, valorConvertido, targetCode));

            } catch (IOException | InterruptedException e) {
                System.out.println("Erro ao obter a taxa de conversão. Verifique sua chave de API ou conexão.");
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
