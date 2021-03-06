package com.pervasivecode.utils.measure.examples;

import static com.pervasivecode.utils.measure.InformationRateUnits.BITS_PER_SECOND;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;
import javax.measure.Quantity;
import com.pervasivecode.utils.measure.QuantityFormatter;
import com.pervasivecode.utils.measure.ScalingFormatters;
import systems.uom.quantity.InformationRate;
import tec.uom.se.quantity.Quantities;

/**
 * Print a series of quantities of data rate in terms of bits per second, scaled with SI prefixes
 * and formatted for the {@link Locale#US US} locale.
 *
 * @see {@code examples.feature} for expected output of this program.
 */
public class BitsPerSecondExample implements ExampleApplication {
  public static void main(String[] args) {
    new BitsPerSecondExample()
        .runExample(new PrintWriter(new OutputStreamWriter(System.out, UTF_8), true));
  }

  @Override
  public void runExample(PrintWriter output) {
    QuantityFormatter<InformationRate> formatter = ScalingFormatters.dataRateInSiBitsPerSecondUs();

    for (int i = 0; i < 18; i++) {
      long value = 3L * (long) Math.pow(10, i);
      Quantity<InformationRate> bps = Quantities.getQuantity(value, BITS_PER_SECOND);
      output.println(formatter.format(bps));
    }
  }
}
