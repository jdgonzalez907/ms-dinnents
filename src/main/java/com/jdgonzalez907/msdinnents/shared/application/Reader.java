package com.jdgonzalez907.msdinnents.shared.application;

public interface Reader<I, O> {
    O transform(I input);
}
