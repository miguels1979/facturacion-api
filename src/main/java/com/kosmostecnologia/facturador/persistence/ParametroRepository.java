package com.kosmostecnologia.facturador.persistence;

import bo.gob.impuestos.siat.ParametricasDto;
import com.kosmostecnologia.facturador.commons.ParametricaEnum;
import com.kosmostecnologia.facturador.domain.repository.IParametroRepository;
import com.kosmostecnologia.facturador.persistence.crud.ParametroCrudRepository;
import com.kosmostecnologia.facturador.persistence.entity.ParametroEntity;
import com.kosmostecnologia.facturador.persistence.mapper.ParametroMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ParametroRepository implements IParametroRepository {

    private final ParametroMapper parametroMapper;
    private final ParametroCrudRepository parametroCrudRepository;

    public ParametroRepository(ParametroMapper parametroMapper, ParametroCrudRepository parametroCrudRepository) {
        this.parametroMapper = parametroMapper;
        this.parametroCrudRepository = parametroCrudRepository;
    }

    @Override
    public void save(ParametricasDto parametricasDto, ParametricaEnum parametricaEnum) {
        ParametroEntity parametroEntity = this.parametroMapper.toParametroEntity(parametricasDto);
        parametroEntity.setCodigoTipoParametro(parametricaEnum.name());
        this.parametroCrudRepository.save(parametroEntity);
    }

    @Override
    public void deleteAll() {
        this.parametroCrudRepository.deleteAll();
    }
}
