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
@Entity(name = "matiere")
@Table(name = "matieres",
        uniqueConstraints = @UniqueConstraint(columnNames={"libelle", "sigle"})
)
public class Matiere implements Serializable {
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    protected Niveau niveau;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    protected Serie serie;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    protected Utilisateur utiCree;

    @NotNull
    @Column(updatable = false)
    @CreationTimestamp
    protected LocalDateTime dateCreation = LocalDateTime.now() ;

    @Nullable
    @OneToOne
    @JoinColumn(nullable = true)
    protected Utilisateur utiModifie;

    @Nullable
    @UpdateTimestamp
    protected LocalDateTime dateModification;
}
