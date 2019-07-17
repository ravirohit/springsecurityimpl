package com.learn.test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class App {

	public static void main(String[] args) {
		String str = "uname=admin&pwd=password";
		String pwd = str.substring(str.lastIndexOf("="));
		String uname = str.substring(str.indexOf("="), str.indexOf("&"));
		System.out.println("uname:"+uname+"  pwd:"+pwd);

	}

}
