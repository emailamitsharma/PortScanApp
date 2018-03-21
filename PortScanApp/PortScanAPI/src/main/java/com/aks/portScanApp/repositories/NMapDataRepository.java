package com.aks.portScanApp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aks.portScanApp.model.NMapData;
import com.aks.portScanApp.model.ScanDetails;

@Repository
public interface NMapDataRepository extends JpaRepository<NMapData, Long> {
	
	Optional<List<NMapData>> findByscanDetail(ScanDetails scanDetail);
	
	NMapData findById(Long id);
		
	@Query(nativeQuery=true,value="select nmapData from NMapData nmapData where nmapData.scan_id = (select max(scanId) from ScanDetails)")
	List<NMapData> findPreviousScanPorts();
	
	
	@Query(nativeQuery=true,value="select * from port_details nmapData inner join (select scan_id from scan_details scDet where scDet.identifier = :identifier and scDet.scan_id <= :lastScanId order by scDet.scan_id desc limit :maxHisItr) as scanData ON nmapData.scan_id = scanData.scan_id order by nmapData.scan_id desc")
	List<NMapData> findPreviousScansPortsUptoIteration(@Param("identifier") String identifier,@Param("lastScanId") long lastScanId,@Param("maxHisItr") long maxHisItr);
	
	@Query(nativeQuery=true,value="select * from port_details nmapData where nmapData.scan_id in (select scan_id from scan_details scDet where scDet.identifier = :identifier and scDet.scan_id <= :lastScanId order by scDet.scan_id desc) order by nmapData.scan_id desc")
	List<NMapData> findPreviousScansAllHistory(@Param("identifier") String identifier,@Param("lastScanId") long lastScanId);
	

}
