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
@Entity(name = "bon_activite")
@Table(name = "bon_activites")
public class BonActivite implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private EnseignantActivite enseignantActivite;

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
