
package acme.entities.configuration;

import java.util.Date;

import javax.persistence.Entity;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CurrencyRatio extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	protected Double			ratio;

	protected Date				date;

	protected String			fromCurrency;

	protected String			toCurrency;

}
