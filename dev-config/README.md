Releasing Guide
===============

Before release
---------

**1. Request Sonatype access**

* Create account on [Sonatype](https://oss.sonatype.org/)

* Request access to org.fluentlenium

  [Example request - OSSRH-18426](https://issues.sonatype.org/browse/OSSRH-18426)
  
  [Example request - OSSRH-37752](https://issues.sonatype.org/browse/OSSRH-37752)
  
**2. Generate gpg key and upload to public gpg servers**

- Generate new gpg key (RSA 2048)

  [GitHub full guide](https://help.github.com/en/articles/generating-a-new-gpg-key)

```
gpg --full-generate-key
```
  
- Verify keys

```
gpg --list-secret-keys
```
  
- Upload your key to public keyserver (ubuntu one works)

```
gpg --keyserver keyserver.ubuntu.com --send-keys ${LONG_KEY_NUMBER_FROM_STEP_ABOVE}
```

**3. Make sure your gpg password is correct because when not it won't be detected until `release:perform`**

  [Solution from StackOverflow](https://stackoverflow.com/questions/11381123/how-to-use-gpg-command-line-to-check-passphrase-is-correct)

```
gpg --list-secret-keys echo “dummy_text” | gpg -o /dev/null --local-user ${LONG_KEY_NUMBER_FROM_STEP_ABOVE} -as - && echo "The correct passphrase was entered for this key"
```

**4. Preventing _Inappropriate ioctl for device_ issue**

  [Issue details 1](https://github.com/keybase/keybase-issues/issues/1712#issuecomment-372158682)
  
  [Issue details 2](https://github.com/keybase/keybase-issues/issues/2798)
  
```
GPG_TTY=$(tty)
export GPG_TTY
```

Release
---------

**1. Configure `settings.xml` from maven profile.**

```xml
<settings>
    <servers>
        <server>
            <id>sonatype-nexus-staging</id>
            <!-- Authentication information from https://oss.sonatype.org -->
            <username>OSS_SONATYPE_USERNAME</username>
            <password>OSS_SONATYPE_PASSWORD</password>
        </server>

        <server>
            <id>gpg.passphrase</id>
            <passphrase>OSS_SONATYPE_GPG_PASSPHRASE</passphrase>
        </server>
    </servers>
</settings>
```

**2. Java 11 release**

```
git checkout develop
export JAVA_HOME=/usr/libexec/java_home -v 11
mvn -Pjava11 release:prepare -Dresume=false
mvn -Pjava11 release:perform -s settings.xml -Darguments="-DskipTests=true"
```

Tests are ignored in second step because we don't want them to run twice

**3. Java 8 release**

```
git checkout -v java8/v3.8.1`
export JAVA_HOME=/usr/libexec/java_home -v 1.8
mvn -Pjava8 release:prepare -Dresume=false
mvn -Pjava8 release:perform -s settings.xml -Darguments="-DskipTests=true"
```

Tests are ignored in second step because we don't want them to run twice

**4. Staging**

- Connect to [https://oss.sonatype.org](https://oss.sonatype.org)

- In Staging Repositories, select ```orgfluentlenium-XXXX``` repository.

- Click Close, then Refresh, then Release.

After release
---------

**1. Update FluentLenium version number in docs and examples projects**

[Example commit](https://github.com/FluentLenium/FluentLenium/commit/69175ef94990dc47527f694ea3b37102d447fbab)

**2. Update CHANGELOG.md**

[Example commit](https://github.com/FluentLenium/FluentLenium/commit/69175ef94990dc47527f694ea3b37102d447fbab)

**3. Reset `master` branch to `develop`**
 
`master` branch should always match a released version, so the website is 
updated with docs from the released version.