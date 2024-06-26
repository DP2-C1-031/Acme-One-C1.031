
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoice.Invoice;
import acme.entities.projects.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface AuthenticatedSponsorSponsorshipRepository extends AbstractRepository {

	@Query("SELECT s FROM Sponsorship s WHERE s.sponsor.id = :id")
	Collection<Sponsorship> findAllSponsorshipsOfSponsor(int id);

	@Query("SELECT s FROM Sponsorship s")
	Collection<Sponsorship> findAllSponsorships();

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("SELECT s FROM Sponsorship s WHERE s.code = :code")
	Sponsorship findSponsorshipByCode(String code);

	@Query("SELECT s FROM Sponsor s WHERE s.id = :id")
	Sponsor findSponsorById(int id);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id = :id")
	Collection<Invoice> findInvoiceBySponsorshipId(int id);

	@Query("SELECT p FROM Project p")
	Collection<Project> findAllProjects();

	@Query("SELECT p FROM Project p WHERE p.draftMode = false")
	Collection<Project> findAllProjectsPublished();

	@Query("SELECT COUNT(i) FROM Invoice i WHERE i.sponsorship.id = :id AND i.draftMode = false")
	long countNonDraftInvoicesBySponsorshipId(int id);

	@Query("SELECT COUNT(i) FROM Invoice i WHERE i.sponsorship.id = :id AND i.draftMode = true")
	long countDraftInvoicesBySponsorshipId(int id);
}
