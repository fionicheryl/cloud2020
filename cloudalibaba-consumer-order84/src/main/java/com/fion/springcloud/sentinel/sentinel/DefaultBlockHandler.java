package com.fion.springcloud.sentinel.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fion.springcloud.common.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultBlockHandler {

    public static Result defaultBlockHandle(BlockException exception) {
        log.info("Sentinel Block Handle......");
        return Result.wrapError(446, "Sentinel Block Handle: " + exception.getRuleLimitApp());
    }
}
