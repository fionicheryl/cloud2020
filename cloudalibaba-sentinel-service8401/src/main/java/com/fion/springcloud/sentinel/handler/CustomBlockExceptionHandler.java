package com.fion.springcloud.sentinel.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fion.springcloud.common.Result;

public class CustomBlockExceptionHandler {

    public static Result handlerException_1(BlockException exception) {
        return Result.wrapError(444, "Global Handle By Way 1! " + exception.getClass().getCanonicalName() + " 服务不可用");
    }

    public static Result handlerException_2(BlockException exception) {
        return Result.wrapError(444, "Global Handle By Way 2! " + exception.getClass().getCanonicalName() + " 服务不可用");
    }
}
