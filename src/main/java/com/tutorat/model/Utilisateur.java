package com.tutorat.model;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "utilisateur")
@Table(name = "utilisateurs",
        uniqueConstraints = @UniqueConstraint(columnNames={"prenoms", "nom", "dateNaissance"})
)
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2,max=40, message="Prenom doit etre comprise entre 2 et 40 cracteres")
    private String prenoms;

    @NotNull
    @Size(min=2,max=40, message="Nom doit etre comprise entre 2 et 40 cracteres")
    private String nom;

    @NotNull
    private Date dateNaissance;

    @NotNull
    @Size(min=2,max=100, message="Lieu de naissance doit etre comprise entre 2 et 100 cracteres")
    private String lieuNaissance;

    @NotNull
    private String sexe;

    @NotNull
    private String situationFamiliale;

    private String photo;

    @Size(min=2,max=100, message="Adresse doit etre comprise entre 2 et 100 cracteres")
    private String adresse;

    @NotNull
    @Size(min=7, max=15, message="Telephone doit etre comprise entre 7 et 15 cracteres")
    private String telephone;

    @NotNull
    @Size(min=5, max=100, message="Email doit etre comprise entre 5 et 100 cracteres")
    private String email;

    @NotNull
    @Size(min=3, max=50, message="username doit etre comprise entre 3 et 50 cracteres")
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(min=6, max=255, message="mot de passe doit etre comprise entre 6 et 255 cracteres")
    @Column(unique = true)
    private String mdpasse;

    @Enumerated(EnumType.STRING)
    private AppProfil profil;

    private boolean active; // par défaut non

    private String identifiant;


    private Long utiCree;

    @NotNull
    @Column(updatable = false)
    @CreationTimestamp
    protected LocalDateTime dateCreation = LocalDateTime.now() ;


    protected Long utiModifie;

    @Nullable
    @UpdateTimestamp
    protected LocalDateTime dateModification;
}
