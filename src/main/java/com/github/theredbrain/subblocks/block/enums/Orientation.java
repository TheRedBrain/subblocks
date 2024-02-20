package com.github.theredbrain.subblocks.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum Orientation implements StringIdentifiable {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical");

    private final String name;

    private Orientation(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
