package com.openclassrooms.safetynet.repository;

import lombok.Data;

import java.util.List;

@Data
public abstract class UrlRepository {

    private List<com.openclassrooms.safetynet.model.Data> childAlertList;
}
