package com.sjkz1.lets_camping.block;

import net.minecraft.util.StringRepresentable;

public enum MatType implements StringRepresentable {
    FULL("full"),
    SLAB("slab"),
    STAIR("stair");

    private final String name;

    private MatType(final String string2) {
        this.name = string2;
    }

    public String toString() {
        return this.getSerializedName();
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

}
