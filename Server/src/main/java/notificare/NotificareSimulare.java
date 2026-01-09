package notificare;
import model.Programare;
import model.Utilizator;
public class NotificareSimulare {
    public static void simulateNotification(Utilizator pacient, Programare programare, String status) {
        System.out.println("\n==============================================");
        System.out.println("   [SIMULARE NOTIFICARE CATRE PACIENT]   ");
        System.out.println("==============================================");
        System.out.println("Destinatar: " + pacient.getNume() + " " + pacient.getPrenume());
        System.out.println("Email: " + pacient.getEmail());
        System.out.println("Telefon: " + pacient.getTelefon());
        System.out.println("----------------------------------------------");
        System.out.println("SUBIECT: Actualizare Programare Medicala");
        System.out.println("MESAJ: Buna ziua, " + pacient.getPrenume() + "!");
        System.out.println("Va informam că programarea dumneavoastra din data");
        System.out.println("de " + programare.getData_programarii() + " este: " + status.toUpperCase() + ".");
        System.out.println("----------------------------------------------");
        System.out.println("Data: " + java.time.LocalDateTime.now());
        System.out.println("==============================================\n");
    }
}
