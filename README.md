# Pervasive Code's Java Measurement Utilities for JSR 363

This library includes classes for using the [JSR 363 Units of Measurement API](https://jcp.org/en/jsr/detail?id=363) with measurements of amount of data and rate of data.

The JSR 363 API specification provides for a type-safe Java representation of quantities with units.

This library depends on the [Units of Measurement](https://github.com/unitsofmeasurement) GitHub project and its [Units of Measurement Systems](https://github.com/unitsofmeasurement/uom-systems) and [JSR 363 Implementation for Java SE 8](https://github.com/unitsofmeasurement/uom-se).

This library provides bytes-per-second and bits-per-second unit types, and code to format quantities using these types in familiar formats, such as "300 GB" (SI prefixes) or "1.4 KiB" (IEC binary prefixes).


## Overview of included classes

Javadocs are available on `javadoc.io`:

[![Javadocs](https://www.javadoc.io/badge/com.pervasivecode/measurement-utils-jsr363.svg)](https://www.javadoc.io/doc/com.pervasivecode/measurement-utils-jsr363)


See the separate [OVERVIEW.md](OVERVIEW.md) file for a description of what interfaces and classes are included. (Overview content is taken from class Javadoc comments, so there's no need to read both.)

## How to use it in your code

See the [Example Code](OVERVIEW.md#example-code) section in [OVERVIEW.md](OVERVIEW.md) for details.


## Including it in your project

Use groupId `com.pervasivecode`, name `measurement-utils-jsr363`, version `1.0` in your build tool of choice.

### Gradle Example

If you are using Gradle 4.x, put this in your build.properties file:

```
// in your build.gradle's repositories {} block:
    mavenCentral();

// in your build.gradle's dependencies {} block:
    implementation 'com.pervasivecode:measurement-utils-jsr363:1.0'

    // or, if you prefer the separated group/name/version syntax:
    implementation group: 'com.pervasivecode', name: 'measurement-utils-jsr363', version: '1.0'
```


## Contributing

See [DEVELOPERS.md](DEVELOPERS.md) and [GRADLE_INTRO.md](GRADLE_INTRO.md) if you want to build and hack on the code yourself.


## Copyright and License

Copyright © 2018 Jamie Flournoy.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

