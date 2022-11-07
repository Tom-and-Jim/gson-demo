load("@rules_java//java:defs.bzl", "java_binary", "java_library", "java_test")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "gson-lib",
    srcs = glob([
        "src/main/java/com/example/*.java",
    ]),
    deps = [
        "@maven//:com_google_code_gson_gson",
        "@maven//:org_apache_commons_commons_lang3",
        "@maven//:junit_junit",
    ]
)

java_binary(
    name = "gson-demo",
    main_class = "com.example.Application",
    runtime_deps = [":gson-lib"],
)

java_test(
    name = "tests",
    srcs = glob([
        "src/test/java/com/example/TestApplication.java",
        "src/main/java/com/example/User.java"
    ]),
    test_class = "com.example.TestApplication",
    deps = [
        "@maven//:junit_junit",
        "@maven//:com_google_code_gson_gson"
    ],
)