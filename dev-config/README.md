Developer Guide
===============

Releases
---------

- Configure `settings.xml` from maven profile.

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

- Update references of FluentLenium version number to next release version number in docs and examples projects.

- Prepare the release.

```bash
$ mvn release:prepare -Dusername=GITHUB_USERNAME -Dpassword=GITHUB_PASSWORD
```

- Perform the release.

```bash
$ mvn release:perform -Dusername=GITHUB_USERNAME -Dpassword=GITHUB_PASSWORD
```

- Connect to [https://oss.sonatype.org](https://oss.sonatype.org)

- In Staging Repositories, select ```orgfluentlenium-XXXX``` repository.

- Click Close, then Refresh, then Release.

- Reset `master` branch to `develop`. `master` branch should always match a released version, so the website is 
updated with docs from the released version.

- Update references of FluentLenium version number to new snapshot version number in docs and examples projects.
