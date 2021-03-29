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
@Entity(name = "apprenant_abonnement")
@Table(name = "apprenant_abonnements",
        uniqueConstraints = @UniqueConstraint(columnNames={"apprenant_id", "enseignant_creno_id"})
)
public class ApprenantAbonnement implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Apprenant apprenant;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private EnseignantCreno enseignantCreno;

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
