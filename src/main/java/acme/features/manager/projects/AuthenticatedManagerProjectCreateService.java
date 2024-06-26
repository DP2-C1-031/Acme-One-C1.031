
package acme.features.manager.projects;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.configuration.Configuration;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Service
public class AuthenticatedManagerProjectCreateService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Manager manager;
		Project object;

		manager = this.repository.findManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Project();
		object.setDraftMode(true);
		object.setManager(manager);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "abstrat", "indicator", "cost", "link");

	}

	@Override
	public void validate(final Project object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project existing;

			existing = this.repository.findOneProjectByCode(object.getCode());

			super.state(existing == null, "code", "manager.project.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("cost")) {
			Configuration config;
			config = this.repository.findConfiguration();

			super.state(Arrays.asList(config.getAcceptedCurrency().trim().split(",")).contains(object.getCost().getCurrency()), "cost", "manager.project.error.cost.currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("cost"))
			super.state(object.getCost().getAmount() >= 0., "cost", "manager.project.error.cost.negative-price");

	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstrat", "indicator", "cost", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
