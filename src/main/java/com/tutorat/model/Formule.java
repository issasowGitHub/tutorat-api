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
@Entity(name = "formule")
@Table(name = "formules"//,
        //uniqueConstraints = @UniqueConstraint(columnNames={"matiere_id", "mode_encadrement_id", "type_encadrement_id", "type_fromule_id"})
)
public class Formule implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Matiere matiere;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ModeEncadrement modeEncadrement;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private TypeEncadrement typeEncadrement;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private TypeFormule typeFormule;

    @NotNull
    private Long cout;

    private Boolean active; //  par defaut oui

    private Boolean valide; // par défaut non

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
