package com.example.PraCodeProc.Entites;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "TblError", schema = "dbo")
public class Errors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ErrCode")
    Integer errCode;

    @Column(name = "ErrMsg")
    String errMsg;
}
