package com.example.userservicefinal.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetUserRolesRequestDTO {
    private List<Long> roleIds;
}
