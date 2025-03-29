package com.kosmostecnologia.facturador.domain.repository;

import bo.gob.impuestos.siat.ParametricasDto;
import com.kosmostecnologia.facturador.commons.ParametricaEnum;

public interface IParametroRepository {

    void save (ParametricasDto parametricasDto , ParametricaEnum parametricaEnum);
    void deleteAll();
}
