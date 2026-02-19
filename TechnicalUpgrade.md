# Java 17 Upgrade

- Waltz has been upgraded to run on Java 17, moving from Java 8.
- Developer Actions
  - Please ensure your local development environment is configured to use JDK 17.
  - Update your IDE settings (e.g., IntelliJ, Eclipse) to point to the JDK 17 installation.

## Framework Upgrade
- The web framework, Spark Java, has been upgraded from version 2.7.1 to 2.9.4.
- Developer Impact
  - There are no immediate code changes required for developers. The upgrade is backward compatible with our existing usage.
  - This change helps keep Waltz current and secure by using a more recent version of its core web framework.

## Key Changes 
- See [pom.xml](https://github.com/finos/waltz/pull/7400/changes#diff-9c5fb3d1b7e3b0f54bc5c4182965c4fe1f9023d449017cece3005d3f90e8e4d8) for a full list of Java dependencies against Java & Sparx upgrade.


