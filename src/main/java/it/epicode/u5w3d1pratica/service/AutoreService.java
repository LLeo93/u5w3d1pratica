package it.epicode.u5w3d1pratica.service;


import com.cloudinary.Cloudinary;
import it.epicode.u5w3d1pratica.dto.AutoreDto;
import it.epicode.u5w3d1pratica.exception.NonTrovatoException;
import it.epicode.u5w3d1pratica.model.Autore;
import it.epicode.u5w3d1pratica.repository.AutoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Service
public class AutoreService {

    @Autowired
    private AutoreRepository autoreRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public Autore saveAutore(AutoreDto autoreDto){

        Autore autore = new Autore();
        autore.setNome(autoreDto.getNome());
        autore.setCognome(autoreDto.getCognome());
        autore.setEmail(autoreDto.getEmail());
        autore.setDataDiNascita(autoreDto.getDataDiNascita());
        //autore.setAvatar("https://ui-avatars.com/api/?name="+autore.getNome()+"+"+autore.getCognome());

        //sendMail(autoreDto.getEmail());
        return autoreRepository.save(autore);

    }

    public Page<Autore> getAutori(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return autoreRepository.findAll(pageable);
    }

    public Autore getAutore(int id) throws NonTrovatoException {
        return autoreRepository.findById(id).
                orElseThrow(() -> new NonTrovatoException("Autore con id:" + id + " non trovato"));
    }

    public Autore updateAutore(int id, AutoreDto autoreDto) throws NonTrovatoException {
        Autore autoreDaAggiornare = getAutore(id);

        autoreDaAggiornare.setNome(autoreDto.getNome());
        autoreDaAggiornare.setCognome(autoreDto.getCognome());
        autoreDaAggiornare.setEmail(autoreDto.getEmail());
        autoreDaAggiornare.setDataDiNascita(autoreDto.getDataDiNascita());
        //autoreDaAggiornare.setAvatar("https://ui-avatars.com/api/?name="+autoreDto.getNome()+"+"+autoreDto.getCognome());

        return autoreRepository.save(autoreDaAggiornare);
    }


    public void deleteAutore(int id) throws NonTrovatoException {
        Autore autoreDaRimuovere = getAutore(id);

        autoreRepository.delete(autoreDaRimuovere);
    }

    public String patchAutore(int id, MultipartFile file) throws NonTrovatoException, IOException {
        Autore autore = getAutore(id);

        String url =(String)cloudinary.uploader().upload(file.getBytes(), Collections.emptyMap()).get("url");

        autore.setAvatar(url);

        autoreRepository.save(autore);

        return url;
    }

    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Servizio rest");
        message.setText("Registrazione al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }
}
