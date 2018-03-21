package com.aks.portScanApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aks.portScanApp.model.ScanDetails;

@Repository
public interface ScanDetailsRepository extends JpaRepository<ScanDetails, Long> {
	
	@Query("select scanDetails from ScanDetails scanDetails")
	ScanDetails findAllScanDetails();
	
	ScanDetails findFirstByIdentifierOrderByScanIdDesc(String id);
}
