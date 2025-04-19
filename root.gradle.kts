plugins {
    id("dev.deftu.gradle.multiversion-root")
}

preprocess {
    strictExtraMappings.set(true)

    "1.12.2-forge"(11202, "srg") {
        "1.12.2-fabric"(11202, "yarn") {
            "1.11.2-fabric"(11102, "yarn") {
                "1.11.2-forge"(11102, "srg") {
                    "1.10.2-forge"(11002, "srg") {
                        "1.10.2-fabric"(11002, "yarn") {
                            "1.9.4-fabric"(10904, "yarn") {
                                "1.9.4-forge"(10904, "srg") {
                                    "1.8.9-forge"(10809, "srg") {
                                        "1.8.9-fabric"(10809, "yarn") {
                                        // "1.8.8-forge"(10808, "srg") {
                                        //     "1.8-forge"(10800, "srg")
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