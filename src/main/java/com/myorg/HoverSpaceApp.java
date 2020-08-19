package com.myorg;

import software.amazon.awscdk.core.App;

public final class HoverSpaceApp {
    public static void main(final String[] args) {
        App app = new App();

        new HoverSpaceStack(app, "HoverSpaceStack");

        app.synth();
    }
}
