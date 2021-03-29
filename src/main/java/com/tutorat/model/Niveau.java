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
@Entity(name = "niveau")
@Table(name = "niveaux",
        uniqueConstraints = @UniqueConstraint(columnNames={"libelle", "sigle"})
)
public class Niveau implements Serializable {
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

    @OneToOne
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
