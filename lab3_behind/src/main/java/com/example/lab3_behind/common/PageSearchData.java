package com.example.lab3_behind.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinition;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageSearchData {
    Integer pageNum;
    Integer pageSize;
    String search;
}
