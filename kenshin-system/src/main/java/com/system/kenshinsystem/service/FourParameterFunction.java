package com.system.kenshinsystem.service;

@FunctionalInterface
public interface FourParameterFunction< T , U, V, W, R > {

	public R apply(T t, U u, V v, W w);
}
