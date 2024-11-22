import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ScannerTCPv2 {
    public static void main(String[] args) {
        // Vérification des arguments
        if (args.length != 3) {
            System.out.println("Usage: java ScannerTCPv2 <ip_address> <port_start> <port_end>");
            return;
        }

        try {
            // Lecture des arguments
            String ipAddress = args[0];
            int portStart = Integer.parseInt(args[1]);
            int portEnd = Integer.parseInt(args[2]);

            // Validation de la plage de ports
            if (portStart < 0 || portEnd > 65535 || portStart > portEnd) {
                System.out.println("Veuillez fournir une plage de ports valide (0-65535).");
                return;
            }

            System.out.println("Scanning " + ipAddress + " ports " + portStart + " to " + portEnd + "...");

            // Boucle sur chaque port de la plage
            for (int port = portStart; port <= portEnd; port++) {
                try (Socket socket = new Socket()) {
                    // Tenter d'établir une connexion vers le port distant avec un délai de 200ms
                    socket.connect(new InetSocketAddress(ipAddress, port), 200);
                    System.out.println("Port " + port + " is OPEN.");
                } catch (IOException e) {
                    // Si une exception est levée, le port est fermé ou inaccessible
                    // Pas besoin d'afficher ici pour les ports fermés (on montre seulement les ouverts)
                }
            }

            System.out.println("Scanning completed.");
        } catch (NumberFormatException e) {
            System.out.println("Les arguments doivent être des nombres entiers pour les ports.");
        } catch (Exception e) {
            System.out.println("Une erreur inattendue est survenue : " + e.getMessage());
        }
    }
}
