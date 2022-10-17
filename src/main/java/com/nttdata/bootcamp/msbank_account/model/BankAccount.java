package com.nttdata.bootcamp.msbank_account.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class BankAccount {
    @Id
    private String id;
    private String nroAccount;
    private String typeAccount;
    private Double contableBalance;
    private String typeDoc;
    private String nroDoc;
    private Date regDate;
}
