package com.example.lab3_behind.common;


import com.example.lab3_behind.domain.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserData {
    String number;
    String role;

    public static JwtUserData accountToJwt(UserAccount userAccount){
        return new JwtUserData(userAccount.getAccount(),userAccount.getRole());
    }
}
