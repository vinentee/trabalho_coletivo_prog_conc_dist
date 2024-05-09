package sistema_hotel_t4;

class CamareiraThread extends Thread {
    @SuppressWarnings("unused")
    private int id;
    private Hotel hotel;

    public CamareiraThread(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
    }

    public void notificarLimpeza(QuartoThread quarto) {
        synchronized (this) {
            notify();
        }
        // Camareira inicia a limpeza do quarto
        quarto.limpar();
    } 

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    wait(); // Aguarda notificação de limpeza
                }
                Thread.sleep(10000); // Tempo entre limpezas dos quartos
                hotel.limparQuartos();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}