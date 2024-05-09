package sistema_hotel_t4;

import java.util.Random;

class RecepcionistaThread extends Thread {
    @SuppressWarnings("unused")
    private int id;
    private Hotel hotel;

    public RecepcionistaThread(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
    }

    

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(random.nextInt(10000)); // Tempo aleatório para o próximo atendimento
                hotel.atenderCliente(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
