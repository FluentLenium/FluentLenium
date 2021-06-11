package org.fluentlenium.adapter.kotest.hooks

class ExampleHookOptions {

    var message = "ExampleHook"

    constructor() {
        // Default constructor
    }

    constructor(annotation: Example) {
        message = annotation.message
    }
}
