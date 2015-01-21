package net.younguard.bighorn.comm.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton Design Pattern used template.
 * 
 * Copyright 2014-2015 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 * @param <T>
 */
public abstract class GenericSingleton<T>
{
	private static Map<Class<?>, Object> instanceMap = new HashMap<Class<?>, Object>();

	public static synchronized <T> T getInstance(Class<T> clazz)
			throws IllegalArgumentException
	{
		T instance = clazz.cast(instanceMap.get(clazz));
		if (instance != null)
			return instance;

		try {
			instance = clazz.cast(clazz.newInstance());
		} catch (InstantiationException exc) {
			throw new IllegalArgumentException(exc);
		} catch (IllegalAccessException exc) {
			throw new IllegalArgumentException(exc);
		} catch (ClassCastException exc) {
			throw new IllegalArgumentException(exc);
		}

		instanceMap.put(clazz, instance);

		return instance;
	}

	/**
	 * Only for test.
	 * 
	 * @param argv
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static void main(String[] argv)
			throws IllegalArgumentException
	{
		MyClass my = GenericSingleton.getInstance(MyClass.class);
		System.out.println("My Name: " + my.getName());
		my.setName("Your");

		MyClass your = GenericSingleton.getInstance(MyClass.class);
		System.out.println("Your Name: " + your.getName());
	}

}

/**
 * Only for test.
 * 
 * @author thomas
 */
class MyClass
{
	private String name = "My";

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
