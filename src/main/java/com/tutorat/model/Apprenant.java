package com.tutorat.model;

import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "apprenant")
@Table(name = "apprenants",
        uniqueConstraints = @UniqueConstraint(columnNames={"annee_id", "niveau_id"})
)
public class Apprenant implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Annee annee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Niveau niveau;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Serie serie;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Utilisateur tuteur;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Utilisateur utilisateur;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Utilisateur utiCree;

    @NotNull
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime dateCreation = LocalDateTime.now() ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    @Nullable
    private Utilisateur utiModifie;

    @Nullable
    @UpdateTimestamp
    private LocalDateTime dateModification;
}
