package sistema_hotel_t4;

import java.util.ArrayList;
import java.util.List;

class QuartoThread extends Thread {
    private int numero;
    private boolean disponivel;
    private boolean sujo;
    private boolean chaveNaRecepcao;
    private List<HospedeThread> hospedes;
    private List<CamareiraThread> camareiras;

    public QuartoThread(int numero, List<CamareiraThread> camareiras) {
        this.numero = numero;
        this.disponivel = true;
        this.sujo = false;
        this.chaveNaRecepcao = false;
        this.hospedes = new ArrayList<>();
        this.camareiras = camareiras;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public boolean isSujo() {
        return sujo;
    }

    public boolean isChaveNaRecepcao() {
        return chaveNaRecepcao;
    }

    public synchronized void reservar(HospedeThread hospede) {
        if (disponivel) {
            System.out.println("Reservando Quarto " + numero + " para Hospede " + hospede.getId() + "...");
            disponivel = false;
            hospedes.add(hospede);
        } else {
            System.out.println("Quarto " + numero + " já está reservado.");
        }
    }

    public synchronized void liberar() {
        if (!disponivel) {
            System.out.println("Liberando Quarto " + numero + "...");
            disponivel = true;
            sujo = true; // Após ser liberado, o quarto fica sujo novamente
            chaveNaRecepcao = true; // Quando o quarto é liberado, a chave é deixada na recepção
            hospedes.clear(); // Remove todos os hóspedes do quarto
            for (CamareiraThread camareira : camareiras) {
                camareira.notificarLimpeza(this);
            }
        } else {
            System.out.println("Quarto " + numero + " já está livre.");
        }
    }

    public synchronized void limpar() {
        if (sujo) {
            System.out.println("Camareira está limpando o Quarto " + numero + "...");
            sujo = false;
        } else {
            System.out.println("Quarto " + numero + " já está limpo.");
        }
    }    

    public synchronized void devolverChave() {
        if (chaveNaRecepcao) {
            System.out.println("Recepcionista devolveu a chave do Quarto " + numero + "...");
            chaveNaRecepcao = false;
        } else {
            System.out.println("Chave do Quarto " + numero + " já está na recepção.");
        }
    }

    public List<HospedeThread> getHospedes() {
        return hospedes;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000); // Simula o tempo de permanência de um hóspede no quarto
                liberar(); // Libera o quarto após o tempo de permanência
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}