package com.farias.webflux.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString @EqualsAndHashCode
public class Usuario {
    
    @Id
    private String id;

    @NotBlank @Size(max = 255)
    private String name;

    @NotBlank @Size(max = 255)
    private String stack;

}
