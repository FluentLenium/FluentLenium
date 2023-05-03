## IntelliJ inspections in general

Inspections (Structural Search and Replace Templates) in IntelliJ are a nice feature to signal code violations and in
certain cases also provide quick fixes
to replace code snippets to a more optimal/better version.

You can find the official documentation at
the [Structural Search And Replace](https://www.jetbrains.com/help/idea/structural-search-and-replace.html) article.

You can either add the XML configs to your global IntelliJ configuration or to the project specific config
at *<project folder>/.idea/inspectionProfiles/Project_Default.xml*.

## FluentLenium specific inspections

This sections contains a list of templates that you can use in your test automation project where FluentLenium is used.

#### FluentConfiguration has remoteUrl but webDriver is not defined as "remote"

```xml
<searchConfiguration name="FluentConfiguration has remoteUrl but webDriver is not defined as &quot;remote&quot;" text="@io.fluentlenium.configuration.FluentConfiguration(webDriver = &quot;$NOT_REMOTE$&quot;, remoteUrl = $REMOTE_URL$)" recursive="true" caseInsensitive="true" type="JAVA">
    <constraint name="__context__" within="" contains="" />
    <constraint name="NOT_REMOTE" regexp="remote" target="true" negateName="true" within="" contains="" />
    <constraint name="REMOTE_URL" within="" contains="" />
</searchConfiguration>
```

#### FluentConfiguration has webDriver defined as "remote" but there is no remoteUrl

```xml
<searchConfiguration name="FluentConfiguration has webDriver defined as &quot;remote&quot; but there is no remoteUrl." text="@io.fluentlenium.configuration.FluentConfiguration(webDriver = &quot;$REMOTE$&quot;, $REMOTE_URL_ATTR$ = $REMOTE_URL$)" recursive="true" caseInsensitive="true" type="JAVA">
    <constraint name="__context__" within="" contains="" />
    <constraint name="REMOTE_URL" minCount="0" maxCount="0" within="" contains="" />
    <constraint name="REMOTE" regexp="remote" target="true" within="" contains="" />
    <constraint name="REMOTE_URL_ATTR" regexp="remoteUrl" minCount="0" maxCount="0" within="" contains="" />
</searchConfiguration>
```

#### CapabilitiesFactory and WebDriverFactory implementations must be annotated as @FactoryName to provide a reference to them

```xml
<searchConfiguration name="CapabilitiesFactory and WebDriverFactory implementations must be annotated as @FactoryName to provide a reference to them." text="@$FACTORY_NAME$&#10;public class $FACTORY_IMPLEMENTATION$ implements $FACTORY_INTERFACE$ {}" recursive="true" caseInsensitive="true" type="JAVA">
    <constraint name="__context__" within="" contains="" />
    <constraint name="FACTORY_INTERFACE" regexp="org\.fluentlenium\.configuration\.CapabilitiesFactory|org\.fluentlenium\.configuration\.WebDriverFactory" within="" contains="" />
    <constraint name="FACTORY_NAME" regexp="org\.fluentlenium\.configuration\.FactoryName" minCount="0" maxCount="0" within="" contains="" />
    <constraint name="FACTORY_IMPLEMENTATION" target="true" within="" contains="" />
</searchConfiguration>
```

#### PageUrl annotated class should extend FluentPage

```xml
<searchConfiguration name="PageUrl annotated class should extend FluentPage." text="@$PAGE_URL_ANNOTATION$( )&#10;public class $PAGE_OBJECT$ extends $FLUENT_PAGE$ { }" recursive="true" caseInsensitive="true" type="JAVA">
    <constraint name="__context__" within="" contains="" />
    <constraint name="PAGE_OBJECT" target="true" within="" contains="" />
    <constraint name="PAGE_URL_ANNOTATION" regexp="org\.fluentlenium\.core\.annotation\.PageUrl" within="" contains="" />
    <constraint name="FLUENT_PAGE" regexp="org\.fluentlenium\.core\.FluentPage" minCount="0" maxCount="0" within="" contains="" />
</searchConfiguration>
```

#### You can call id() on a FluentWebElement instead of attribute("id")

```xml
<replaceConfiguration name="You can call id() on a FluentWebElement instead of attribute(&quot;id&quot;)." text="$FluentWebElement$.attribute(&quot;id&quot;)" recursive="false" caseInsensitive="true" type="JAVA" reformatAccordingToStyle="false" shortenFQN="false" replacement="$FluentWebElement$.id()">
    <constraint name="__context__" within="" contains="" />
    <constraint name="FluentWebElement" nameOfExprType="org\.fluentlenium\.core\.domain\.FluentWebElement" expressionTypes="io.fluentlenium.core.domain.FluentWebElement" within="" contains="" />
</replaceConfiguration>
```

#### You can call textContent() on a FluentWebElement instead of attribute("textContent")

```xml
<replaceConfiguration name="You can call textContent() on a FluentWebElement instead of attribute(&quot;textContent&quot;)." text="$FluentWebElement$.attribute(&quot;textContent&quot;)" recursive="false" caseInsensitive="true" type="JAVA" reformatAccordingToStyle="false" shortenFQN="false" replacement="$FluentWebElement$.textContent()">
    <constraint name="__context__" within="" contains="" />
    <constraint name="FluentWebElement" nameOfExprType="org\.fluentlenium\.core\.domain\.FluentWebElement" expressionTypes="io.fluentlenium.core.domain.FluentWebElement" within="" contains="" />
</replaceConfiguration>
```

#### You can call value() on a FluentWebElement instead of attribute("value")

```xml
<replaceConfiguration name="You can call value() on a FluentWebElement instead of attribute(&quot;value&quot;)." text="$FluentWebElement$.attribute(&quot;value&quot;)" recursive="false" caseInsensitive="true" type="JAVA" reformatAccordingToStyle="false" shortenFQN="false" replacement="$FluentWebElement$.value()">
    <constraint name="__context__" within="" contains="" />
    <constraint name="FluentWebElement" nameOfExprType="org\.fluentlenium\.core\.domain\.FluentWebElement" expressionTypes="io.fluentlenium.core.domain.FluentWebElement" within="" contains="" />
</replaceConfiguration>
```

#### You can call name() on a FluentWebElement instead of attribute("name")

```xml
<replaceConfiguration name="You can call name() on a FluentWebElement instead of attribute(&quot;name&quot;)." text="$FluentWebElement$.attribute(&quot;name&quot;)" recursive="false" caseInsensitive="true" type="JAVA" reformatAccordingToStyle="false" shortenFQN="false" replacement="$FluentWebElement$.name()">
    <constraint name="__context__" within="" contains="" />
    <constraint name="FluentWebElement" nameOfExprType="org\.fluentlenium\.core\.domain\.FluentWebElement" expressionTypes="io.fluentlenium.core.domain.FluentWebElement" within="" contains="" />
</replaceConfiguration>
```

#### You can call first() on a FluentList instead of get(0)

```xml
<replaceConfiguration name="You can call first() on a FluentList instead of get(0)." text="$FluentList$.get(0)" recursive="false" caseInsensitive="true" type="JAVA" reformatAccordingToStyle="false" shortenFQN="false" replacement="$FluentList$.first()">
    <constraint name="__context__" within="" contains="" />
    <constraint name="FluentList" nameOfExprType="org\.fluentlenium\.core\.domain\.FluentList" expressionTypes="io.fluentlenium.core.domain.FluentList" within="" contains="" />
</replaceConfiguration>
```

#### The value of the @PageUrl annotation must start with /, http://, https://

```xml
<searchConfiguration name="The value of the @PageUrl annotation must start with /, http://, https://." text="@$PageUrl$ ($Value$)&#10;class $Class$ { }" recursive="true" caseInsensitive="true" type="JAVA">
    <constraint name="__context__" within="" contains="" />
    <constraint name="PageUrl" regexp="org\.fluentlenium\.core\.annotation\.PageUrl" target="true" within="" contains="" />
    <constraint name="Value" regexp="(^(&quot;\/|&quot;https?:\/\/).*&quot;$)|(value = (&quot;\/|&quot;https?:\/\/).*&quot;)" negateName="true" within="" contains="" />
    <constraint name="Class" within="" contains="" />
</searchConfiguration>
```

#### There is no actual assertion called from FluentLeniumAssertions

```xml
<searchConfiguration name="There is no actual assertion called from FluentLeniumAssertions." text="io.fluentlenium.assertj.FluentLeniumAssertions.$ASSERTION_METHOD$($PARAMETER$);" recursive="true" caseInsensitive="true" type="JAVA">
    <constraint name="ASSERTION_METHOD" regexp="assertThat" within="" contains="" />
    <constraint name="PARAMETER" within="" contains="" />
    <constraint name="__context__" within="" contains="" />
</searchConfiguration>
```

#### @Page annotated field is instantiated explicitly. Instantiation can be removed as the field is injected by FluentLenium

```xml
<searchConfiguration name="@Page annotated field is instantiated explicitly. Instantiaton can be removed as the field is injected by FluentLenium." text="class $Class$ { &#10;    @$PageAnnotation$ ( ) &#10;    @Modifier(&quot;Instance&quot;) $FieldType$ $Field$ = $Init$;&#10;}" recursive="true" caseInsensitive="true" type="JAVA">
    <constraint name="__context__" within="" contains="" />
    <constraint name="Class" within="" contains="" />
    <constraint name="FieldType" within="" contains="" />
    <constraint name="Field" maxCount="2147483647" target="true" within="" contains="" />
    <constraint name="Init" within="" contains="" />
    <constraint name="PageAnnotation" regexp="org\.fluentlenium\.core\.annotation\.Page" within="" contains="" />
</searchConfiguration>
```

#### @Page annotated field is static. It should be used as an instance field

```xml
<searchConfiguration name="@Page annotated field is static. It should be used as an instance field." text="class $Class$ {&#10;    @$PageAnnotation$ ( )&#10;    static $FieldType$ $Field$ = $Init$;&#10;}" recursive="true" caseInsensitive="true" type="JAVA">
    <constraint name="__context__" within="" contains="" />
    <constraint name="Class" within="" contains="" />
    <constraint name="PageAnnotation" regexp="org\.fluentlenium\.core\.annotation\.Page" within="" contains="" />
    <constraint name="FieldType" within="" contains="" />
    <constraint name="Field" target="true" within="" contains="" />
    <constraint name="Init" minCount="0" within="" contains="" />
</searchConfiguration>
```

#### The class is annotated as @FactoryName but doesn't implement WebDriverFactory or CapabilitiesFactory

```xml
<searchConfiguration name="The class is annotated as @FactoryName but doesn't implement WebDriverFactory or CapabilitiesFactory." text="@$FACTORY_NAME$&#10;public class $FACTORY_IMPLEMENTATION$ implements $FACTORY_INTERFACE$ {}" recursive="true" caseInsensitive="true" type="JAVA">
    <constraint name="__context__" within="" contains="" />
    <constraint name="FACTORY_NAME" regexp="org\.fluentlenium\.configuration\.FactoryName" within="" contains="" />
    <constraint name="FACTORY_IMPLEMENTATION" target="true" within="" contains="" />
    <constraint name="FACTORY_INTERFACE" regexp="org\.fluentlenium\.configuration\.CapabilitiesFactory|org\.fluentlenium\.configuration\.WebDriverFactory" minCount="0" maxCount="0" within="" contains="" />
</searchConfiguration>
```

#### The value of the @FactoryName annotation is either empty or consists only of whitespaces

```xml
<searchConfiguration name="The value of the @FactoryName annotation is either empty or consists only of whitespaces." text="@$FACTORY_NAME$ ($VALUE$)&#10;public class $FACTORY_IMPLEMENTATION$ implements $FACTORY_INTERFACE$ {}" recursive="true" caseInsensitive="true" type="JAVA">
    <constraint name="__context__" within="" contains="" />
    <constraint name="FACTORY_NAME" regexp="org\.fluentlenium\.configuration\.FactoryName" within="" contains="" />
    <constraint name="FACTORY_IMPLEMENTATION" within="" contains="" />
    <constraint name="FACTORY_INTERFACE" regexp="org\.fluentlenium\.configuration\.CapabilitiesFactory|org\.fluentlenium\.configuration\.WebDriverFactory" within="" contains="" />
    <constraint name="VALUE" regexp="^&quot;(( +)|)&quot;$" target="true" within="" contains="" />
</searchConfiguration>
```

#### Selenium Find... annotated class should extend FluentPage

Since: 4.2.2

```xml
<searchConfiguration name="Selenium Find... annotated class should extend FluentPage." text="@$FIND_ANNOTATION$( )&#10;public class $PAGE_OBJECT$ extends $FLUENT_PAGE$ { }" recursive="true" caseInsensitive="true" type="JAVA">
    <constraint name="__context__" within="" contains="" />
    <constraint name="PAGE_OBJECT" target="true" within="" contains="" />
    <constraint name="FLUENT_PAGE" regexp="org\.fluentlenium\.core\.FluentPage" minCount="0" maxCount="0" within="" contains="" />
    <constraint name="FIND_ANNOTATION" regexp="org\.openqa\.selenium\.support\.(FindBy|FindBys|FindAll)" within="" contains="" />
</searchConfiguration>
```
