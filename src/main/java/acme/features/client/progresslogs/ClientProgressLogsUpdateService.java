
package acme.features.client.progresslogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.progressLogs.ProgressLogs;
import acme.roles.client.Client;

@Service
public class ClientProgressLogsUpdateService extends AbstractService<Client, ProgressLogs> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogsRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int progressLogId;
		Contract contract;

		progressLogId = super.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractByProgressLogsId(progressLogId);
		status = contract != null && !contract.isPublished() && super.getRequest().getPrincipal().hasRole(contract.getClient());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLogs object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProgressLogsById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProgressLogs object) {
		assert object != null;

		super.bind(object, "recordId", "completeness", "comment", "registrationMoment", "responsable");
	}

	@Override
	public void validate(final ProgressLogs object) {
		assert object != null;
	}

	@Override
	public void perform(final ProgressLogs object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final ProgressLogs object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness", "comment", "registrationMoment", "responsiblePerson");
		dataset.put("masterId", object.getContract().getId());
		dataset.put("published", object.getContract().isPublished());
	}

}
