import java.io.IOException;
import java.net.ServerSocket;

public class ScannerTCPv1 {
    public static void main(String[] args) {
        // Vérification des arguments
        if (args.length != 2) {
            System.out.println("Usage: java ScannerTCPv1 <port_start> <port_end>");
            return;
        }

        try {
            // Lecture des arguments
            int portStart = Integer.parseInt(args[0]);
            int portEnd = Integer.parseInt(args[1]);

            // Validation de la plage
            if (portStart < 0 || portEnd > 65535 || portStart > portEnd) {
                System.out.println("Veuillez fournir une plage de ports valide (0-65535).");
                return;
            }

            System.out.println("Scanning ports " + portStart + " to " + portEnd + "...");

            // Boucle sur chaque port de la plage
            for (int port = portStart; port <= portEnd; port++) {
                try {
                    // Essayer de créer un serveur sur ce port
                    ServerSocket serverSocket = new ServerSocket(port);
                    serverSocket.close(); // Libérer immédiatement le port
                } catch (IOException e) {
                    // Si une erreur survient, le port est occupé
                    System.out.println("Port " + port + " is OPEN (already in use).");
                }
            }

            System.out.println("Scanning completed.");
        } catch (NumberFormatException e) {
            System.out.println("Les arguments doivent être des nombres entiers.");
        } catch (Exception e) {
            System.out.println("Une erreur inattendue est survenue : " + e.getMessage());
        }
    }
}

