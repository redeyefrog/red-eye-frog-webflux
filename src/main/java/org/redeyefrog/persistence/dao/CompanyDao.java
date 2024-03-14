package org.redeyefrog.persistence.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.redeyefrog.persistence.entity.CompanyEntity;

import java.util.List;

@Mapper
public interface CompanyDao {

    List<CompanyEntity> findByCondition(@Param("uid") String uid, @Param("name") String name);

    boolean isExist(String uid);

    int insert(CompanyEntity entity);

    int update(CompanyEntity entity);

    int deleteByUid(String uid);

}
