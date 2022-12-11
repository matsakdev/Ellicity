package com.matsak.ellicity.lighting.entity.sections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matsak.ellicity.lighting.Electric;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "[System]")
public class System implements Electric{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @NotNull
    @Column(name="name")
    private String name;
    @NotNull
    @Column(name="passKey")
    private String passKey;



//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }

//    @NotNull
//    @ManyToMany
//    @JoinTable(name="Users_Systems",
//            joinColumns = @JoinColumn(name="system_id"),
//            inverseJoinColumns = @JoinColumn(name="user_id"))
//    private List<User> users;
    @OneToMany(mappedBy = "system", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Circuit> electricalCircuits;


    public System(){}

    public System(String name, String passKey) {
        this.name = name;
        this.passKey = passKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String password) {
        this.passKey = password;
    }

    public List<Circuit> getElectricalCircuits() {
        return electricalCircuits;
    }

    public void setElectricalCircuits(List<Circuit> electricalCircuits) {
        this.electricalCircuits = electricalCircuits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof System)) return false;
        System system = (System) o;
        return Objects.equals(id, system.id) && Objects.equals(name, system.name) && Objects.equals(passKey, system.passKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, passKey);
    }

    @Override
    public String toString() {
        return "System{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passKey='" + passKey + '\'' +
                '}';
    }
}
