package nextDevs.progettoSettimanale.service;

import nextDevs.progettoSettimanale.dto.DispositivoDto;
import nextDevs.progettoSettimanale.enums.StatoDispositivo;
import nextDevs.progettoSettimanale.exception.DipendenteNonTrovatoException;
import nextDevs.progettoSettimanale.exception.DispositivoNonTrovatoException;
import nextDevs.progettoSettimanale.model.*;
import nextDevs.progettoSettimanale.repository.DipendeteRepository;
import nextDevs.progettoSettimanale.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DispositivoService {

    @Autowired
    private DipendeteRepository dipendeteRepository;
    @Autowired
    private DispositivoRepository dispositivoRepository;

    public String saveDispositivo(DispositivoDto dispositivoDto) {
        Dispositivo dispositivo = switch (dispositivoDto.getTipoDispositivo()) {
            case "laptop" -> new Laptop();
            case "smartphone" -> new Smartphone();
            case "tablet" -> new Tablet();
            default -> throw new IllegalArgumentException("Tipo di dispositivo non supportato");
        };

        dispositivo.setTipoDispositivo(dispositivoDto.getTipoDispositivo());
        dispositivo.setNome(dispositivoDto.getNome());
        dispositivo.setMarca(dispositivoDto.getMarca());
        dispositivo.setStato(dispositivoDto.getStato());

        Optional<Dipendente> dipendenteOpt = dipendeteRepository.findById(dispositivoDto.getDipendenteId());
        if (dipendenteOpt.isPresent()) {
            Dipendente dipendente = dipendenteOpt.get();
            dispositivo.setDipendente(dipendente);
        } else {
            throw new DipendenteNonTrovatoException("Dipendente con id " + dispositivoDto.getDipendenteId() + " non trovato");
        }

        dispositivoRepository.save(dispositivo);
        return "Dispositivo " + dispositivo.getId() + " salvato con successo";

    }

    public Optional<Dispositivo> getDispositivoById(int id) {
        return dispositivoRepository.findById(id);

    }

    public Page<Dispositivo> getAllDispositivo(int page, int size , String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dispositivoRepository.findAll(pageable);
    }



    public Dispositivo updateDispositivo(int id, DispositivoDto dispositivoDto) throws DispositivoNonTrovatoException {
        Optional<Dispositivo> dispositivoOpt = getDispositivoById(id);

        if (dispositivoOpt.isPresent()) {
            Dispositivo dispositivo = dispositivoOpt.get();

            dispositivo.setNome(dispositivoDto.getNome());
            dispositivo.setMarca(dispositivoDto.getMarca());
            dispositivo.setStato(dispositivoDto.getStato());

            Optional<Dipendente> dipendenteOpt = dipendeteRepository.findById(dispositivoDto.getDipendenteId());
            if (dipendenteOpt.isPresent()) {
                Dipendente dipendente = dipendenteOpt.get();
                dispositivo.setDipendente(dipendente);
                dispositivoRepository.save(dispositivo);
                return dispositivo;
            } else {
                throw new DipendenteNonTrovatoException("Dipendente con id " + dispositivoDto.getDipendenteId() + " non trovato");
            }
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo con id " + id + " non trovato");
        }
    }

    public String deleteDispositivo(int id) throws DispositivoNonTrovatoException {
        Optional<Dispositivo> dispositivoOpt = getDispositivoById(id);
        if(dispositivoOpt.isPresent()){
            Dispositivo dispositivo = dispositivoOpt.get();
            dispositivoRepository.deleteById(dispositivo.getId());
            return "Dispositivo con id= " + dispositivo.getId()+" eliminato con successo";
        }else {
            throw new DispositivoNonTrovatoException("Dispositivo non trovato");
        }
    }

    public Dispositivo assegnaIlDispositivoAlDipendente(Integer id, Dipendente dipendente) throws DispositivoNonTrovatoException {
        Optional<Dispositivo> dispositivoOpt = getDispositivoById(id);

        if (dispositivoOpt.isPresent()) {
            Dispositivo dispositivo = dispositivoOpt.get();
            dispositivo.setDipendente(dipendente);
            dispositivo.setStato(StatoDispositivo.ASSEGNATO);
            return dispositivoRepository.save(dispositivo);
        }else {
         throw new DispositivoNonTrovatoException("Dispositivo non trovato");}
    }


}
