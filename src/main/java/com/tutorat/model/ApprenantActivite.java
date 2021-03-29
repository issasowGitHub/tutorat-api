package com.tutorat.model;

import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "apprenant_activite")
@Table(name = "apprenant_activites",
        uniqueConstraints = @UniqueConstraint(columnNames={"apprenant_abonnement_id", "enseignant_activite_id"})
)
public class ApprenantActivite implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private ApprenantAbonnement apprenantAbonnement; // amene apprenant, son abonnement sur l'activite

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private EnseignantActivite enseignantActivite;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePrevue;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEffective;

    @NotNull
    private Boolean annule; // par défaut non

    @NotNull
    private Boolean effectuee;  // par défaut non

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    protected Utilisateur utiCree;

    @NotNull
    @Column(updatable = false)
    @CreationTimestamp
    protected LocalDateTime dateCreation = LocalDateTime.now() ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    @Nullable
    protected Utilisateur utiModifie;

    @Nullable
    @UpdateTimestamp
    protected LocalDateTime dateModification;
}
