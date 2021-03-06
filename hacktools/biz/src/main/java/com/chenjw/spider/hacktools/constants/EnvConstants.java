package com.chenjw.spider.hacktools.constants;

import com.chenjw.spider.hacktools.env.CloudfoundryProvider;
import com.chenjw.spider.hacktools.env.EnvProvider;
import com.chenjw.spider.hacktools.env.LocalProvider;
import com.chenjw.spider.hacktools.env.SaeProvider;

public class EnvConstants {

	private static EnvProvider envProvider;

	static {
		EnvProvider[] envs = new EnvProvider[] { new CloudfoundryProvider(),
				new LocalProvider(), new SaeProvider(), };
		for (EnvProvider env : envs) {
			if (env.isEnable()) {
				envProvider = env;
				break;
			}
		}
	}

	public static EnvProvider getEnvProvider() {
		return envProvider;
	}

	public static boolean isProductMode() {
		// return true;
		return !"local".equals(envProvider.getName());
	}

}
