
package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class AuthenticatedManagerUserStoryListService extends AbstractService<Manager, UserStory> {

	private static final String						PROJECTID	= "projectId";

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedManagerUserStoryRepository	repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		final int id;
		Project project;
		id = super.getRequest().getPrincipal().getAccountId();
		projectId = super.getRequest().getData(AuthenticatedManagerUserStoryListService.PROJECTID, int.class);
		project = this.repository.findProjectById(projectId);
		status = project != null && project.getManager().getUserAccount().getId() == id;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<UserStory> objects;
		final int projectId = super.getRequest().getData(AuthenticatedManagerUserStoryListService.PROJECTID, int.class);
		objects = this.repository.findAllUserStoryOfProject(projectId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Project project;

		final int projectId = super.getRequest().getData(AuthenticatedManagerUserStoryListService.PROJECTID, int.class);
		Dataset dataset;

		project = this.repository.findProjectById(projectId);

		dataset = super.unbind(object, "title", "description", "priorityType", "estimatedCost");

		super.getResponse().addGlobal(AuthenticatedManagerUserStoryListService.PROJECTID, projectId);
		super.getResponse().addGlobal("draftMode1", project.isDraftMode());
		super.getResponse().addData(dataset);

	}

}
