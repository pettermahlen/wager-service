package com.williamsinteractive.casino.wager.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * TODO: document!
 *
 * @author Petter Måhlén
 */
public class Id<T> {

    private static final Id NONE = new Id(-1, "this is just used to get around the non-negative argument check");

    private final long id;

    public Id(long id) {
        Preconditions.checkArgument(id >= 0, "id must be non-negative, got %d", id);
        this.id = id;
    }

    private Id(long id, String onlyUsedToInstantiate_NONE_withANegativeId) {
        this.id = id;
    }

    public long id() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Id)) {
            return false;
        }

        // hmmm... this could lead to confusion where Ids of two different types are equal - that is solved by having different ID spaces. Probably not a big deal anyway.
        return id == ((Id) o).id();
    }

    @Override
    public String toString() {
        return "Id{" +
               "id=" + id +
               '}';
    }

    public static <U> Id<U> none() {
        // This cast is here to avoid having to re-create
        //noinspection unchecked
        return (Id<U>) NONE;
    }

    public static <U> Id<U> of(long id) {
        return new Id<U>(id);
    }
}
