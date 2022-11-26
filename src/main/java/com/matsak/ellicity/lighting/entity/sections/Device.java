package com.matsak.ellicity.lighting.entity.sections;

import com.matsak.ellicity.lighting.Activable;
import com.matsak.ellicity.lighting.Electric;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Device{

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="circuit_id", nullable=false)
    private Circuit circuit;

}
