package com.nttdata.bootcamp.msbank_account.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CustomerDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String typeDoc;
    private String nroDoc;
    private String phone;
    private String email;
    private String typePerson;
    private String typeProduct;
    private Date regDate;
}
