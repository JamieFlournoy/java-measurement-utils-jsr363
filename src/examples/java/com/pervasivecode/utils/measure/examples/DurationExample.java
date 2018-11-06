package com.pervasivecode.utils.measure.examples;

import static java.nio.charset.StandardCharsets.UTF_8;
import static tec.uom.se.unit.Units.SECOND;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Time;
import com.google.common.collect.ImmutableMap;
import com.pervasivecode.utils.measure.api.QuantityFormatter;
import com.pervasivecode.utils.measure.impl.ScalingDurationFormatter;
import com.pervasivecode.utils.measure.impl.ScalingFormatter;
import com.pervasivecode.utils.measure.impl.SiPrefixSelector;
import com.pervasivecode.utils.measure.impl.SimpleUnitLabelProvider;
import si.uom.SI;
import tec.uom.se.quantity.Quantities;

/**
 * Print a series of quantities of time, formatted in two ways:
 * <ul>
 * <li>First, with a {@link ScalingDurationFormatter} (hours, minutes, seconds) instance,</li>
 * <li>Second, with a {@link ScalingFormatter}{@code <}{@link Time}{@code >} instance that shows
 * {@link SI#SECOND}{@code S} scaled with SI prefixes.</li>
 * </ul>
 * 
 * @see {@code examples.feature} for expected output of this program.
 */
public class DurationExample implements ExampleApplication {
  public static void main(String[] args) {
    new DurationExample().runExample(new PrintWriter(System.out, true, UTF_8));
  }

  public static ScalingFormatter<Time> timeInSiSeconds() {
    ImmutableMap.Builder<Unit<Time>, String> labels = ImmutableMap.builder();
    return new ScalingFormatter<Time>(SECOND, new SiPrefixSelector(),
        NumberFormat.getInstance(Locale.US), new SimpleUnitLabelProvider<Time>(labels.build()));
  }

  @Override
  public void runExample(PrintWriter output) {
    QuantityFormatter<Time> formatter = ScalingDurationFormatter.US();
    QuantityFormatter<Time> siFormatter = timeInSiSeconds();

    for (int i = 0; i < 16; i++) {
      int exponent = i * 2;
      long value = 1L << exponent;
      Quantity<Time> millis = Quantities
          .getQuantity(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000)), SI.SECOND);
      String outputLine =
          String.format("%s (%s)", formatter.format(millis), siFormatter.format(millis));
      output.println(outputLine);
    }
  }
}
