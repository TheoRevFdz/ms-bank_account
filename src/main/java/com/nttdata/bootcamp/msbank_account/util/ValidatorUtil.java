package com.nttdata.bootcamp.msbank_account.util;

import java.util.List;

import com.nttdata.bootcamp.msbank_account.enums.TypesAccount;
import com.nttdata.bootcamp.msbank_account.model.BankAccount;

public class ValidatorUtil {
    public static boolean validatePersonalAccount(List<BankAccount> accounts) {
        int cont = 0;
        for (BankAccount ba : accounts) {
            if (ba.getTypeAccount().equals(TypesAccount.AHORRO.toString())
                    || ba.getTypeAccount().equals(TypesAccount.CORRIENTE.toString())
                    || ba.getTypeAccount().equals(TypesAccount.PLAZO_FIJO.toString())) {
                cont++;
            }
        }
        return cont < 3;
    }
}
