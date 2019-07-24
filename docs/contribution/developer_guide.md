# Developer and contribution guide

## Configure Checkstyle

The Checkstyle configuration file is parameterized with the `checkstyle.suppressions.file` property via the checkstyle-maven-plugin.

In order to make it work from the Checkstyle-IDEA IntelliJ plugin and have that property resolved, you need to set the value of the
property in the Checkstyle-IDEA plugin configuration: **Settings > Other Settings > Checkstyle**

Add the **/dev-config/checkstyle/fluentlenium_checks.xml** file to the list of Configuration Files, then edit the FluentLenium config file
and set the value of the `checkstyle.suppression.file` property to `/dev-config/checkstyle/fluentlenium_checks_suppressions.xml`.

This ensures that checkstyle execution works both when executed for a specific module and from the IDEA plugin as well.
