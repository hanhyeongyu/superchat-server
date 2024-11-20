package com.example.template

abstract class Specification<T> {
    abstract fun isSatisfiedBy(candidate: T): Boolean

    fun and(other: Specification<T>): Specification<T> {
        return AndSpecification(this, other)
    }

    fun or(other: Specification<T>): Specification<T> {
        return OrSpecification(this, other)
    }

    fun not(): Specification<T> {
        return NotSpecification(this)
    }

    //abstract fun subsumes(other: Specification<T>): Boolean
}

class CompositeSpecification<T>(
    private val specifications: List<Specification<T>>
) : Specification<T>() {
    override fun isSatisfiedBy(candidate: T): Boolean {
        return specifications.all { it.isSatisfiedBy(candidate) }
    }

    fun leafSpecifications(): List<Specification<T>> {
        return specifications
    }
}

class AndSpecification<T>(
    private val one: Specification<T>,
    private val other: Specification<T>
) : Specification<T>() {
    override fun isSatisfiedBy(candidate: T): Boolean {
        return one.isSatisfiedBy(candidate) && other.isSatisfiedBy(candidate)
    }
}

class OrSpecification<T>(
    private val one: Specification<T>,
    private val other: Specification<T>
) : Specification<T>() {
    override fun isSatisfiedBy(candidate: T): Boolean {
        return one.isSatisfiedBy(candidate) || other.isSatisfiedBy(candidate)
    }
}

class NotSpecification<T>(
    private val wrapped: Specification<T>
) : Specification<T>() {
    override fun isSatisfiedBy(candidate: T): Boolean {
        return !wrapped.isSatisfiedBy(candidate)
    }
}
