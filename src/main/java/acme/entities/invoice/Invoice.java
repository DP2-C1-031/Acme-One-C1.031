
package acme.entities.invoice;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.sponsorship.Sponsorship;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	// Serialisation identifier -------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Propieties ---------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	private String				code;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationTime;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date				dueDate;

	@NotNull
	@Valid
	private Money				quantity;

	@NotNull
	@PositiveOrZero
	@Max(1)
	private Double				tax;

	@URL
	private String				link;

	// Derived attributes -------------------------------------------------------


	@Transient
	public Money getTotalAmount() {
		Money res = new Money();
		res.setAmount(this.quantity.getAmount() / this.tax);
		res.setCurrency(this.quantity.getCurrency());
		return res;
	}

	// Relationships


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	private Sponsorship sponsorship;

}
