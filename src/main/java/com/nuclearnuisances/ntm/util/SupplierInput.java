package com.nuclearnuisances.ntm.util;

@FunctionalInterface
public interface SupplierInput<I, O> {
    O get(I input);
}
