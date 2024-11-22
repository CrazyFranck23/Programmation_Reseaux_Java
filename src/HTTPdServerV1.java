import java.io.*;
import java.net.*;

public class HTTPdServerV1 {
    public static void main(String[] args) {
        // Vérification des arguments
        if (args.length != 1) {
            System.out.println("Usage: java HTTPdServerV1 <port>");
            return;
        }

        try {
            // Lecture du port
            int port = Integer.parseInt(args[0]);

            // Création du serveur
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("HTTP server is running on port " + port);

            while (true) {
                // Acceptation des connexions des clients
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Traitement de la requête du client
                handleClient(clientSocket);
            }
        } catch (NumberFormatException e) {
            System.out.println("Le port doit être un nombre entier.");
        } catch (IOException e) {
            System.out.println("Erreur lors du démarrage du serveur : " + e.getMessage());
        }
    }

    // Fonction pour gérer la connexion avec un client
    private static void handleClient(Socket clientSocket) {
        try (
                // Flux d'entrée et de sortie
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Lire la requête HTTP du client (première ligne uniquement ici)
            String requestLine = in.readLine();
            System.out.println("Request: " + requestLine);

            // Ignorer les autres lignes de l'entête HTTP (jusqu'à une ligne vide)
            String line;
            while (!(line = in.readLine()).isEmpty()) {
                System.out.println("Header: " + line);
            }

            // Construire la réponse HTTP
            String httpResponse =
                    "HTTP/1.1 200 OK\r\n" + // Ligne de statut
                            "Content-Type: text/html; charset=UTF-8\r\n" + // Type de contenu
                            "Content-Length: 208\r\n" + // Longueur du contenu HTML
                            "\r\n" + // Séparation de l'entête et du corps
                            "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head>\n" +
                            "    <title>Mon premier serveur HTTP</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <h1>Bienvenue sur mon serveur HTTP !</h1>\n" +
                            "    <p>Ceci est une page HTML statique générée par Java. </p>\n" +
                            "</body>\n" +
                            "</html>";

            // Envoyer la réponse au client
            out.write(httpResponse);
            out.flush();

            // Fermer la connexion avec le client
            clientSocket.close();
            System.out.println("Client disconnected.");
        } catch (IOException e) {
            System.out.println("Erreur lors du traitement du client : " + e.getMessage());
        }
    }
}
