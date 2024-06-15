package com.ETechAdvisor.ETechAdvisor.Smartphone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SmartphoneRepository extends JpaRepository<Smartphone, Integer>, JpaSpecificationExecutor<Smartphone> {

}
