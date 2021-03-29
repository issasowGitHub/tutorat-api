package com.tutorat.model;

import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "enseignant_crenau")
@Table(name = "enseignant_crenaux",
        uniqueConstraints = @UniqueConstraint(columnNames={"formule_id", "jour_id", "enseignant_id", "heureDebut", "heureFin"})
)
public class EnseignantCreno implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Formule formule;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Jour jour;

    @OneToOne(fetch = FetchType.LAZY)
    private Enseignant enseignant;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date heureDebut;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date heureFin;

    private Boolean active; // par défaut oui
    /*@NotNull
    @Min(8)
    @Max(23)
    private int heureDebut;

    @NotNull
    @Min(8)
    @Max(23)
    private int minuteDebut;

    @NotNull
    @Min(8)
    @Max(23)
    private int heureFin;

    @NotNull
    @Min(0)
    @Max(59)
    private int minuteFin;*/

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
