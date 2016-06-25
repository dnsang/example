/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sls.slsjtemplate.core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author sangdn User a thread local to keep sessionId per Thread. The
 * principle is put sessionId when start process request, and retrieve it
 * whenever need.
 */
public class SessionHolder {

    /*basically _counter = max handle thread */
    private static final AtomicInteger _counter = new AtomicInteger(0);
    private static final ThreadLocal<String> _context = new ThreadLocal<>();

    public static void put(String sessionId) {
        _context.set(sessionId);
        _counter.incrementAndGet();
    }

    public static String get() {
        return _context.get();
    }

    public static void end() {
        _context.remove();
        _counter.decrementAndGet();
    }

    public static int size() {
        return _counter.get();
    }
}
