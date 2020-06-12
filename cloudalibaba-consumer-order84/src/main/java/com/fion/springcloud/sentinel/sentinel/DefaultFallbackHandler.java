package com.fion.springcloud.sentinel.sentinel;

import com.fion.springcloud.common.Result;

public class DefaultFallbackHandler {

    public static Result defaultFallback(Throwable t) {
        return Result.wrapError(444, "Sentinel Fallback: " + t.getMessage());
    }
}
