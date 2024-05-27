package nextDevs.progettoSettimanale.controller;


import nextDevs.progettoSettimanale.dto.DispositivoDto;
import nextDevs.progettoSettimanale.exception.BadRequestException;
import nextDevs.progettoSettimanale.exception.DispositivoNonTrovatoException;
import nextDevs.progettoSettimanale.model.Dipendente;
import nextDevs.progettoSettimanale.model.Dispositivo;
import nextDevs.progettoSettimanale.service.DipendenteService;
import nextDevs.progettoSettimanale.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DispositivoController {

    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private DispositivoService dispositivoService;

    @PostMapping("/dispositivi")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveDispositivo(@RequestBody @Validated DispositivoDto dispositivoDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage()).reduce("",((s1,s2) -> s1+s2)));
        }
        return dispositivoService.saveDispositivo(dispositivoDto);
    }

    @GetMapping("/dispositivi")
    public Page<Dispositivo> getAllDispositivo(@RequestParam (defaultValue = "0") int page,
                                               @RequestParam (defaultValue = "15") int size,
                                               @RequestParam (defaultValue = "id") String sortBy) {
        return dispositivoService.getAllDispositivo( page, size, sortBy);
    }


    @GetMapping("/dispositivi/{id}")
    public Dispositivo getDispositivoById(@PathVariable int id) throws DispositivoNonTrovatoException {
        Optional<Dispositivo> blogPostOpt = dispositivoService.getDispositivoById(id);
        if (blogPostOpt.isPresent()) {
            return blogPostOpt.get();
        } else {
            throw new DispositivoNonTrovatoException( "Dispositivo con id: " + id+" non trovato" );
        }
    }


    @PutMapping("/dispositivi/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Dispositivo updateDispositivo(@PathVariable int id, @RequestBody @Validated DispositivoDto blogPostDto, BindingResult bindingResult) throws DispositivoNonTrovatoException {
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage()).reduce("",((s1,s2) -> s1+s2)));
        }
        return dispositivoService.updateDispositivo(id, blogPostDto);
    }

    @DeleteMapping("/dispositivi/{id}")
    public String deleteDispositivo(@PathVariable int id) throws DispositivoNonTrovatoException {
        return dispositivoService.deleteDispositivo(id);
    }

    @PostMapping("/dispositivi/{dispositivoId}/assegna/{dipendenteId}")
    public ResponseEntity<Dispositivo> assegnaIlDispositivoAlDipendente(@PathVariable int dispositivoId, @PathVariable int dipendenteId) {
        Optional<Dipendente> dipendenteOpt = dipendenteService.getDipendenteById(dipendenteId);
        if (dipendenteOpt.isPresent()) {
            Dipendente dipendente = dipendenteOpt.get();
            try {
                Dispositivo dispositivo = dispositivoService.assegnaIlDispositivoAlDipendente(dispositivoId, dipendente);
                return new ResponseEntity<>(dispositivo, HttpStatus.OK);
            } catch (DispositivoNonTrovatoException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
