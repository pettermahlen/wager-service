package com.williamsinteractive.casino.wager.acceptance;

import com.google.common.base.Function;
import org.voltdb.VoltTable;

import javax.annotation.Nullable;

/**
 * TODO: this is duplicated between the repo and the service - it should go into a library!
 *
 * @author Petter Måhlén
 */
public class FirstLong implements Function<VoltTable, Object[]> {
    @Nullable
    public Object[] apply(VoltTable input) {
        return new Object[]{input.getLong(0)};
    }
}
