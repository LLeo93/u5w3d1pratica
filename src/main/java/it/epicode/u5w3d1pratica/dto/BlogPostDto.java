package it.epicode.u5w3d1pratica.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BlogPostDto {
    @NotEmpty(message = "la categoria non può essere nulla o vuota")
    private String categoria;
    @NotEmpty(message = "il titolo non può essere nullo o vuoto")
    private String titolo;
    @NotEmpty(message = "il contenuto non può essere nullo o vuoto")
    private String contenuto;
    private int tempoDiLettura;
    private int autoreId;
}
