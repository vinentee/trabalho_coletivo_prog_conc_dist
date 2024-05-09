package sistema_hotel_t4;

import java.util.Random;

class HospedeThread extends Thread {
    private int id;
    private Hotel hotel;
    private int tentativas;

    public HospedeThread(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.tentativas = 0;
    }

    public long getId() {
        return id;
    }

    public void incrementarTentativas() {
        tentativas++;
    }

    public int getTentativas() {
        return tentativas;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(random.nextInt(5000)); // Tempo aleatório para o próximo check-in
                QuartoThread quarto = hotel.reservarQuarto(this);
                if (quarto != null) {
                    Thread.sleep(random.nextInt(10000)); // Tempo de permanência no quarto
                    hotel.liberarQuarto(quarto);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}