package com.tutorat.model;


import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "annee")
@Table(name = "annees",
        uniqueConstraints = @UniqueConstraint(columnNames={"libelle", "sigle"})
)
public class Annee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2, max=60, message="Libelle doit etre comprise entre 2 et 60 cracteres")
    @Column(unique = true)
    private String libelle;

    @NotNull
    @Size(min=2, max=20, message="Sigle doit etre comprise entre 2 et 20 cracteres")
    @Column(unique = true)
    private String sigle;

    @NotNull
    private boolean actif;

    @NotNull
    private boolean closed;

    @NotNull
    private boolean current;

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
