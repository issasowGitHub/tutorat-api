package com.tutorat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FormuleRequest {
    private String matiere;
    private String modeEncadrement;
    private String typeEncadrement;
    private String typeFormule;
    private String cout;
}
