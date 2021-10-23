package com.jdgonzalez907.msdinnents.shared;

public interface Mapper <E, D> {
    E toEntity(D domain);
    D toDomain(E entity);
}
