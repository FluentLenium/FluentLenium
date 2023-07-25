buildScan.buildScanPublished {

    def scan = it.getBuildScanUri().toString()
    def out = new File(".gradle_build_scan")
    out.write("${scan}\n")

    println "wrote buildscan URL $scan to $out"
}