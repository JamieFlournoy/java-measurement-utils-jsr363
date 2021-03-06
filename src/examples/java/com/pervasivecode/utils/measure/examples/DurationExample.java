package com.pervasivecode.utils.measure.examples;

import static java.nio.charset.StandardCharsets.UTF_8;
import static tec.uom.se.unit.Units.SECOND;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Time;
import com.google.common.collect.ImmutableMap;
import com.pervasivecode.utils.measure.QuantityFormatter;
import com.pervasivecode.utils.measure.ScalingDurationFormatter;
import com.pervasivecode.utils.measure.ScalingFormatter;
import com.pervasivecode.utils.measure.SiPrefixSelector;
import com.pervasivecode.utils.measure.SimpleUnitLabelProvider;
import com.pervasivecode.utils.time.DurationFormat;
import com.pervasivecode.utils.time.DurationFormats;
import com.pervasivecode.utils.time.DurationFormatter;
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
    new DurationExample()
        .runExample(new PrintWriter(new OutputStreamWriter(System.out, UTF_8), true));
  }

  public static ScalingFormatter<Time> timeInSiSeconds() {
    ImmutableMap.Builder<Unit<Time>, String> labels = ImmutableMap.builder();
    return new ScalingFormatter<Time>(SECOND, new SiPrefixSelector(),
        NumberFormat.getInstance(Locale.US), new SimpleUnitLabelProvider<Time>(labels.build()));
  }

  @Override
  public void runExample(PrintWriter output) {
    FormatterPicker picker = new FormatterPicker();
    QuantityFormatter<Time> siFormatter = timeInSiSeconds();

    for (int i = 0; i < 16; i++) {
      int exponent = i * 2;
      long value = 1L << exponent;
      Quantity<Time> millis = Quantities
          .getQuantity(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000)), SI.SECOND);

      QuantityFormatter<Time> quantityFormatter = picker.getFormatFor(value);
      String outputLine =
          String.format("%s (%s)", quantityFormatter.format(millis), siFormatter.format(millis));
      output.println(outputLine);
    }
  }

  private static class FormatterPicker {
    private final QuantityFormatter<Time> subsecondFormatter;
    private final QuantityFormatter<Time> subminuteFormatter;
    private final QuantityFormatter<Time> minuteOrMoreFormatter;

    public FormatterPicker() {
      final DurationFormat usDefault = DurationFormats.getUsDefaultInstance();
      subsecondFormatter = makeQuantityFormatter(usDefault);

      subminuteFormatter = makeQuantityFormatter(DurationFormat.builder(usDefault) //
          .setSmallestUnit(ChronoUnit.SECONDS) //
          .setNumFractionalDigits(3) //
          .build());

      minuteOrMoreFormatter = makeQuantityFormatter(DurationFormat.builder(usDefault) //
          .setSmallestUnit(ChronoUnit.SECONDS) //
          .build());
    }

    private static QuantityFormatter<Time> makeQuantityFormatter(DurationFormat durationFormat) {
      return new ScalingDurationFormatter(new DurationFormatter(durationFormat));
    }

    QuantityFormatter<Time> getFormatFor(long millis) {
      if (millis < 1000) {
        return subsecondFormatter;
      }
      if (millis >= 60000) {
        return minuteOrMoreFormatter;
      }
      return subminuteFormatter;
    }
  }
}
