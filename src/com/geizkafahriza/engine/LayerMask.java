package com.geizkafahriza.engine;

import java.util.ArrayList;

public class LayerMask {

	private int layer;
	
	private static ArrayList<String> layerNames = new ArrayList<String>(31);
	
	public LayerMask(String name) {
		layer = NameToLayer(name);
	}
	
	public LayerMask(LayerMask layer) {
		this.layer = layer.layer;
	}
	
	public LayerMask() {
		layer = 0;
	}
	
	public int getLayerValue() {
		return layer;
	}
	
	public void addLayer(String name) {
		layer |= NameToLayer(name);
	}
	
	public void removeLayer(String name) {
		layer &= Integer.MAX_VALUE ^ NameToLayer(name);
	}
	
	public boolean contain(int layer) {
		return ((layer & this.layer) > 0);
	}
	
	//static
	
	public static int NameToLayer(String name) {
		int layer = 1;
		for(String curLayer : layerNames) {
			if(curLayer.contentEquals(name))
				break;
			layer = layer << 1;
		}
		return layer;
	}
	
	public static String LayerToName(int layer) {
		int count = 0;
		while(layer > 1) {
			layer = layer >> 1;
			count++;
		}
		return new String(layerNames.get(count));
	}
	
	public static void addLayerName(String name) {
		layerNames.add(new String(name));
	}
	
	public static String[] getLayerNames() {
		String[] names = new String[layerNames.size()];
		names = layerNames.toArray(names);
		return names;
	}
	
	public static int getLayerCount() {
		return layerNames.size();
	}
	
}
