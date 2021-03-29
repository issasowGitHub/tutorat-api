package com.tutorat.dto.request;

import com.tutorat.model.Utilisateur;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UtilisateurRequest {
    private String prenoms;
    private String nom;
    private String dateNaissance;
    private String lieuNaissance;
    private String sexe;
    private String situationFamiliale;
    private String adresse;
    private String telephone;
    private String email;
    private String username;
    private String mdpasse;
    //private String profil;  //  AppProfil
    //private String identifiant;
    //protected String utiCree;
}
