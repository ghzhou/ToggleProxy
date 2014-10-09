package com.zhoujie.toggleproxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MyProxySelector extends ProxySelector {
	
	private boolean proxyIsReachable = true;

	@Override
	public void connectFailed(URI arg0, SocketAddress arg1, IOException arg2) {
		proxyIsReachable = false;
	}

	@Override
	public List<Proxy> select(URI uri) {
		if (!proxyIsReachable || !blcokedByGreatWall(uri)){
			return null;
		}
		Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("16.153.99.7",8080));
		List<Proxy> l = new ArrayList<Proxy>(1);
		l.add(p);
		return l;
	}

	private boolean blcokedByGreatWall(URI uri) {
		return Pattern.matches("google.com$|facebook.com$", uri.getHost());
	}

}
