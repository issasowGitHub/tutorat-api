package com.tutorat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApprenantRequest {
    private String annee;
    private String niveau;
    private String serie;
    private String tuteur;
    private String utilisateur;
    //protected String utiCree;

    public ApprenantRequest(String annee, String niveau, String utilisateur){
        super();
        this.annee = annee;
        this.niveau = niveau;
        this.utilisateur = utilisateur;
    }
}
