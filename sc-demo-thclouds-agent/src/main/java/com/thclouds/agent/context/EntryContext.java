package com.thclouds.agent.context;

import com.alibaba.ttl.TransmittableThreadLocal;

public final class EntryContext {

    private static final TransmittableThreadLocal<EntryHolder> holderThreadLocal = new TransmittableThreadLocal<EntryHolder>();

    public static EntryHolder getEntryHolder() {
        EntryHolder entryHolder = holderThreadLocal.get();
        holderThreadLocal.remove();
        return entryHolder;
    }

    public static void putEntryHolder(EntryHolder entryHolder) {
        holderThreadLocal.set(entryHolder);
    }

}
