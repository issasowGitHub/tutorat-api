package com.tutorat.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "enseignant_matiere")
@Table(name = "enseignant_matiere",
        uniqueConstraints = @UniqueConstraint(columnNames={"matiere_id", "enseignant_id"})
)
public class EnseignantMatiere {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Matiere matiere;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Utilisateur enseignant;

    @NotNull
    private Boolean active;

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
