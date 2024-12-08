package autoservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "U01_USER")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long U01_ID;

    @Column(columnDefinition = "TEXT")
    private String U01_NAME;

    @Column(name = "U01_LOGIN", columnDefinition = "TEXT", unique = true)
    private String U01_LOGIN;

    @Column(columnDefinition = "TEXT")
    private String U01_PASS;

    @Column(columnDefinition = "TEXT", unique = true)
    private String U01_EMAIL;

    @Column(columnDefinition = "TEXT", unique = true)
    private String U01_PHONE;

    @ManyToOne
    @JoinColumn(name = "R01_ID", referencedColumnName = "R01_ID")
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Client client;

}

