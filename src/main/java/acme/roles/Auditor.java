
package acme.roles;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 77)
	private String				firm;

	@NotBlank
	@Length(max = 27)
	private String				professionalId;

	@NotBlank
	@Length(max = 102)
	private List<String>		certifications;

	@URL
	private String				url;
}
