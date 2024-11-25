package com.exalt.bank_account.adapter.output;

/**
 * Generic interface to convert domain entities to infra entities, the same logic applies in reverse
 *
 * @param <I> Infra entity.
 * @param <D> Domain entity.
 */
public interface DomainMapper <D,I>{
    /**
     * Converts a domain entity to an infra entity.
     *
     * @param domainEntity The domain entity to convert.
     * @return The converted infra entity.
     */
    I domainToInfra(D domainEntity);
    /**
     * Converts an infra entity to a domain entity.
     *
     * @param infraEntity The infra entity to convert.
     * @return The converted domain entity.
     */
    D infraToDomain(I infraEntity);

}
