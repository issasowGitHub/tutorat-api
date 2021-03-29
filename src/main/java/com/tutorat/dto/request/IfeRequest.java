package com.tutorat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IfeRequest {
    private String libelle;
    private String sigle;
    //protected String utiCree;
}
