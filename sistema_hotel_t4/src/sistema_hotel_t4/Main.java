package sistema_hotel_t4;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel(10); // Criando um hotel com 10 quartos

        // Iniciando as threads representando os quartos
        for (QuartoThread quarto : hotel.quartos) {
            quarto.start();
        }

        // Iniciando as threads representando as hóspedes
        for (int i = 1; i <= 50; i++) {
            new HospedeThread(i, hotel).start();
        }

        // Iniciando as threads representando as camareiras
        for (int i = 1; i <= 10; i++) {
            CamareiraThread camareira = new CamareiraThread(i, hotel);
            hotel.camareiras.add(camareira);
            camareira.start();
        }

        // Iniciando as threads representando os recepcionistas
        for (int i = 1; i <= 5; i++) {
            new RecepcionistaThread(i, hotel).start();
        }
    }
}
