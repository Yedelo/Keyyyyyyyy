plugins {
    id("dev.deftu.gradle.multiversion-root")
}

preprocess {
    strictExtraMappings.set(true)
    "1.12.2-forge"(11202, "srg") {
        "1.12.1-forge"(11201, "srg") {
            "1.12-forge"(11200, "srg") {
                "1.11.2-forge"(11102, "srg") {
                    "1.11-forge"(11100, "srg") {
                        "1.10.2-forge"(11002, "srg") {
                            "1.10-forge"(11000, "srg") {
                                "1.9.4-forge"(10904, "srg") {
                                    "1.9-forge"(10900, "srg") {
                                        "1.8.9-forge"(10809, "srg") {
                                            // "1.8.8-forge"(10808, "srg") {
                                                // "1.8-forge"(10800, "srg")
                                            // }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}