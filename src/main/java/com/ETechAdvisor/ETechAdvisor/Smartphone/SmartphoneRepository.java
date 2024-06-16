package com.ETechAdvisor.ETechAdvisor.Smartphone;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmartphoneRepository extends JpaRepository<Smartphone, Integer>, JpaSpecificationExecutor<Smartphone> {


}
