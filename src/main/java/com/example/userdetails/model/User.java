package com.example.userdetails.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity

@Table(name="users")

public class User {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Setter
    @NotBlank(message="Enter valid name")
    @Size(max=20)
    private String name;


    @Setter
    @NotBlank(message="Enter valid name")
    @Size(max=30)
    private String city;

    @Setter
    @NotNull(message = "Status is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean status;

    @Setter
    @Email(message = "Email must be valid")
    private String email;

    public User() {}

    // Response field

    @JsonProperty("status")

    public String getStatusText() {

        return Boolean.TRUE.equals(status) ? "Active" : "Inactive";

    }



}