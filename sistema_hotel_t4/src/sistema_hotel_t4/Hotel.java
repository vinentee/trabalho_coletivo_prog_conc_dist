package sistema_hotel_t4;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    List<QuartoThread> quartos;
    private List<HospedeThread> filaEspera;
    private List<HospedeThread> reclamacoes;
    List<CamareiraThread> camareiras;

    public Hotel(int numeroQuartos) {
        quartos = new ArrayList<>();
        filaEspera = new ArrayList<>();
        reclamacoes = new ArrayList<>();
        camareiras = new ArrayList<>();
        for (int i = 1; i <= numeroQuartos; i++) {
            quartos.add(new QuartoThread(i, camareiras));
        }
    }

    public synchronized QuartoThread reservarQuarto(HospedeThread hospede) {
        for (QuartoThread quarto : quartos) {
            if (quarto.isDisponivel()) {
                if (quarto.getHospedes().size() < 4) {
                    quarto.reservar(hospede);
                    return quarto;
                } else {
                    // Se o quarto está cheio, tenta alocar em outro quarto vago
                    continue;
                }
            }
        }
        // Se nenhum quarto vago foi encontrado, adiciona o hóspede à fila de espera
        hospede.incrementarTentativas();
        if (hospede.getTentativas() >= 2) {
            System.out.println("Hospede " + hospede.getId() + " deixou uma reclamação e foi embora.");
            reclamacoes.add(hospede);
            return null;
        }
        filaEspera.add(hospede);
        System.out.println("Hospede " + hospede.getId() + " entrou na fila de espera.");
        return null;
    }

    public synchronized void liberarQuarto(QuartoThread quarto) {
        quarto.liberar();
        // Após liberar o quarto, verifica se há hóspedes na fila de espera
        if (!filaEspera.isEmpty()) {
            HospedeThread proximoHospede = filaEspera.remove(0);
            System.out.println("Hospede " + proximoHospede.getId() + " saiu da fila de espera e realizou check-in.");
            reservarQuarto(proximoHospede);
        }
        // Solicita a limpeza do quarto após liberá-lo
        for (CamareiraThread camareira : camareiras) {
            camareira.notificarLimpeza(quarto);
        }
    }    
    

    public synchronized void limparQuartos() {
        for (QuartoThread quarto : quartos) {
            if (quarto.isSujo() && quarto.isChaveNaRecepcao()) {
                quarto.limpar();
                quarto.devolverChave();
            }
        }
    }

    @SuppressWarnings("deprecation")
    public synchronized void atenderCliente(RecepcionistaThread recepcionista) {
        if (!filaEspera.isEmpty()) {
            HospedeThread proximoHospede = filaEspera.remove(0);
            System.out.println("Recepcionista " + recepcionista.getId() + " atendeu Hospede " + proximoHospede.getId() + ".");
            reservarQuarto(proximoHospede);
        } else {
            System.out.println("Recepcionista " + recepcionista.getId() + " está aguardando clientes.");
        }
    }
}
