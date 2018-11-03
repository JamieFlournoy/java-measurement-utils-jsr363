package com.pervasivecode.utils.measure.api;

import java.util.Locale;
import javax.measure.Quantity;
import javax.measure.quantity.Length;

/**
 * Instances can format a Quantity of a given type in a representation that makes sense for a given
 * locale.
 * <p>
 * Example: A value of {@link Quantity}&lt;{@link Length}&gt; representing 33.5 centimeters could be
 * formatted for the {@link Locale#US US} locale as "33.5 cm". It could also be formatted as "0.335
 * meters". One reasonable formatted representation for the same value in the {@link Locale#FRANCE
 * FRANCE} locale would be "33,5 centimètres".
 * 
 * @param <Q> The kind of Quantity that this object can format. Example: {@link Length}
 */
public interface QuantityFormatter<Q extends Quantity<Q>> {

  /**
   * Format the specified quantity, according to the rules and locale inherent to this instance of
   * QuantityFormatter.
   * 
   * @param quantity The quantity that should be formatted.
   * 
   * @return The formatted representation of the quantity.
   */
  public String format(Quantity<Q> quantity);
}
