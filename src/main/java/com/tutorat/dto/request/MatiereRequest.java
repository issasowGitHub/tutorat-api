package com.tutorat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MatiereRequest {
    private String libelle;
    private String sigle;
    private String niveau;
    private String serie;
    //protected String utiCree;

    public MatiereRequest(String libelle, String sigle, String niveau){
        super();
        this.libelle = libelle;
        this.sigle = sigle;
        this.niveau = niveau;
    }
}
