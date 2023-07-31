def isCI = System.getenv("CI") as Boolean

def GITHUB_STEP_SUMMARY = System.getenv("GITHUB_STEP_SUMMARY")

println "gradle custom user data: $isCI"
println GITHUB_STEP_SUMMARY

if (isCI) {
    buildScan.buildScanPublished {

        def buildScan = it.getBuildScanUri().toString()

        def badgeUrl="https://img.shields.io/badge/Build%20Scan%E2%84%A2-PUBLISHED-06A0CE?logo=Gradle"
        def badgeHtml = """<img src="${badgeUrl}" alt="Build Scan Published" />"""
        def url = """<a href="${buildScan}" rel="nofollow">${badgeHtml}</a>"""

        def file = new File(GITHUB_STEP_SUMMARY)

        println file

        file.append(url)
        println "appended $url to $file"
    }
}