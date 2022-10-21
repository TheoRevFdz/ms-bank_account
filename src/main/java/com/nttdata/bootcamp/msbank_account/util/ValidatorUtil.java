package com.nttdata.bootcamp.msbank_account.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.nttdata.bootcamp.msbank_account.enums.TypesAccount;
import com.nttdata.bootcamp.msbank_account.interfaces.IBankAccountService;
import com.nttdata.bootcamp.msbank_account.model.BankAccount;

@Component
public class ValidatorUtil {
    @Autowired
    private IBankAccountService service;

    public ResponseEntity<?> validatePersonalAccount(BankAccount ba) {
        boolean isSingleType = ba.getTypeAccount().equals(TypesAccount.AHORRO.toString())
                || ba.getTypeAccount().equals(TypesAccount.CORRIENTE.toString());
        if (isSingleType
                || ba.getTypeAccount().equals(TypesAccount.PLAZO_FIJO.toString())) {
            List<BankAccount> accounts = service.findAccountByNroDocAndTypeAccount(ba.getNroDoc(),
                    ba.getTypeAccount());

            if (isSingleType && accounts.size() == 0) {
                return ResponseEntity.ok().body(true);
            } else if (!isSingleType && accounts.size() >= 0) {
                return ResponseEntity.ok().body(true);
            }
            return ResponseEntity.badRequest()
                    .body("El Cliente Personal solo puede tener un máximo de cuenta de "
                            + TypesAccount.AHORRO.toString() + ", una cuenta "
                            + TypesAccount.CORRIENTE.toString() + ", o cuentas a "
                            + TypesAccount.PLAZO_FIJO.toString());
        }
        return ResponseEntity.badRequest().body(String.format("Tipo de cuenta inválida (%s, %s, %s).",
                TypesAccount.AHORRO.toString(), TypesAccount.CORRIENTE.toString(), TypesAccount.PLAZO_FIJO.toString()));
    }

    public ResponseEntity<?> validateEmpresarialAccount(BankAccount ba) {
        if (ba.getTypeAccount().equals(TypesAccount.CORRIENTE.toString())) {
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.badRequest()
                .body(String.format("El Cliente Empresarial solo puede tener cuentas de tipo: %s",
                        TypesAccount.CORRIENTE.toString()));
    }
}
