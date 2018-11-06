# Code Overview

## Interfaces

### [QuantityFormatter](src/main/java/com/pervasivecode/utils/measure/api/QuantityFormatter.java)

Instances can format a Quantity of a given type in a representation that makes sense for a given locale.

Example: A value of Quantity&lt;Length&gt; representing 33.5 centimeters could be formatted for the US locale as "33.5 cm". It could also be formatted as "0.335 meters". One reasonable formatted representation for the same value in the [FRANCE](https://docs.oracle.com/javase/10/docs/api/java/util/Locale.html?is-external=true#FRANCE) locale would be "33,5 centimètres".

### [QuantityPrefixSelector](src/main/java/com/pervasivecode/utils/measure/api/QuantityPrefixSelector.java)

Instances can automatically determine the appropriate scale prefix (e.g. kilo or mega) that should be used to present a value of any size in a human-friendly form.

Prefixes are represented by a Unit instance that that contains the base unit and the prefix, so the output of a QuantityPrefixSelector is just the input Quantity transformed to an appropriately-scaled Unit.

### [UnitLabelProvider](src/main/java/com/pervasivecode/utils/measure/api/UnitLabelProvider.java)

Instances can provide the appropriate String label for a specified unit in a given locale. (A given instance only knows how to provide labels for a single Locale.)

Example: for the unit KIBI * InformationRate, a US-locale instance would return "KiBps" or "KiB/s".

## Classes

### [IecBinaryPrefixSelector](src/main/java/com/pervasivecode/utils/measure/impl/IecBinaryPrefixSelector.java)

Instances select an appropriate IEC binary prefix for a given Quantity and return the same Quantity transformed to use that prefix.

Examples: Given 4,096 bytes per second, return 4 kibibits per second. Given 1,048,576 mebibytes, return 1 tebibyte.

### [InformationRateUnits](src/main/java/com/pervasivecode/utils/measure/impl/InformationRateUnits.java)

Units to help with type-safe formatting of information rates.

### [ScalingDurationFormatter](src/main/java/com/pervasivecode/utils/measure/impl/ScalingDurationFormatter.java)

Instances format a Quantity&lt;Time&gt; value with a scale expressed in standard units of time (seconds, minutes, hours, etc.). The maximum unit of time that is supported is the week, since the size of larger units of time is not fixed.

### [ScalingFormatter](src/main/java/com/pervasivecode/utils/measure/impl/ScalingFormatter.java)

Formatter for Quantity objects that applies scale prefixes to make the formatted value easier to read.

Example: formatting a value of 12,000 meters for the US locale and the SI prefix system would result in a format of "12 km" since the value would be scaled to kilometers.

Note: The system of scale prefixes, the number-value formatter, and the unit-label provider are all parameterized, to enable formatting for various locales and prefix systems. This means that callers will need to provide an appropriately localized NumberFormat and UnitLabelProvider, as well as a QuantityPrefixSelector capable of applying the caller's desired system of prefixes as appropriate.

### [ScalingFormatters](src/main/java/com/pervasivecode/utils/measure/impl/ScalingFormatters.java)

This class contains factory methods for formatters using commonly-used data size and data rate formats, suitable for use in the US locale.

### [SiPrefixSelector](src/main/java/com/pervasivecode/utils/measure/impl/SiPrefixSelector.java)

Instances select an appropriate SI prefix for a given Quantity and return the same Quantity transformed to use that prefix.

Examples: given 10,000 meters, return 10 kilometers. Given 1,000,000 joules, return 1 megajoule.

### [SimpleUnitLabelProvider](src/main/java/com/pervasivecode/utils/measure/impl/SimpleUnitLabelProvider.java)

This class provides unit labels for a single locale by looking them up in a static mapping of units to labels.

### [SimpleUnitLabelProviders](src/main/java/com/pervasivecode/utils/measure/impl/SimpleUnitLabelProviders.java)

Factory methods for instances of SimpleUnitLabelProvider, providing prefixed unit labels for information units BIT, BYTE, BYTES\_PER\_SECOND and BITS\_PER\_SECOND.
