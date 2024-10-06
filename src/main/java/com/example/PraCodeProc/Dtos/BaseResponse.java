package com.example.PraCodeProc.Dtos;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BaseResponse <T> {
    String msg = "";
    Boolean status = false;
    T Data = null;
}
