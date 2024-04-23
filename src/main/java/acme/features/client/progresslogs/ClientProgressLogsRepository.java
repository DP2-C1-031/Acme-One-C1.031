
package acme.features.client.progresslogs;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.progressLogs.ProgressLogs;

@Repository
public interface ClientProgressLogsRepository extends AbstractRepository {

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);

	@Query("select pl.contract from ProgressLogs pl where pl.id = :id")
	Contract findOneContractByProgressLogsId(int id);

	@Query("select pl from ProgressLogs pl where pl.id = :id")
	ProgressLogs findOneProgressLogsById(int id);

	@Query("select pl from ProgressLogs pl where pl.contract.id = :masterId")
	Collection<ProgressLogs> findManyProgressLogsByMasterId(int masterId);

	@Query("select pl from ProgressLogs pl where pl.recordId = :recordId")
	ProgressLogs findOneProgressLogsByRecordId(String recordId);

}
