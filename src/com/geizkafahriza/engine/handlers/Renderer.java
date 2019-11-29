package com.geizkafahriza.engine.handlers;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.geizkafahriza.engine.interfaces.Manageable;
import com.geizkafahriza.engine.interfaces.Renderable;

public class Renderer extends Manager{
	
	private static Renderer instance;
	
	public static Renderer getInstance() {
		if(instance == null)instance = new Renderer();
		return instance;
	}

	private PriorityQueue<RendererEntry> renderables = new PriorityQueue<RendererEntry>();
	private LinkedBlockingQueue<RendererEntry> renderQue = new LinkedBlockingQueue<RendererEntry>();
	private LinkedBlockingQueue<RemoveEntry> removeQue = new LinkedBlockingQueue<Renderer.RemoveEntry>();
	
	@SuppressWarnings("unlikely-arg-type")
	public void render(Graphics2D g2d) {
		while(renderQue.size() > 0) {
			RendererEntry entry = renderQue.poll();
			renderables.add(entry);
			Manageable manageable = (Manageable)entry.getRenderable();
			manageable.managedBy(this);
		}
		while(removeQue.size() > 0) {
			RemoveEntry re = removeQue.poll();
			Iterator<RendererEntry> iter = renderables.iterator();
			while(iter.hasNext()) {
				RendererEntry ue = iter.next();
				if(ue.equals(re.getRenderable())) {
					iter.remove();
					Manageable manageable = (Manageable)ue.getRenderable();
					manageable.unmanagedBy(this);
				}
			}
		}
		for(RendererEntry rnd : renderables) {
			rnd.getRenderable().render(g2d);;
		}
	}
	
	private void addRenderable(Renderable rn, int priority) {
		renderQue.add(new RendererEntry(rn, priority));
	}
	
	private void removeRenderable(Renderable rn) {
		removeQue.add(new RemoveEntry(rn));
	}
	
	class RendererEntry implements Comparable<RendererEntry>{
		private Renderable rnd;
		private int priority = 0;
		
		public RendererEntry(Renderable rnd) {
			this.rnd = rnd;
		}
		
		public RendererEntry(Renderable rnd, int priority) {
			this.rnd = rnd;
			this.priority = priority;
		}
		
		@Override
		public int compareTo(RendererEntry o) {
			int diff = priority - o.priority;
			if(diff == 0)
				return 0;
			return diff/Math.abs(diff);
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof Renderable) {
				return rnd == o;
			}else if(o instanceof RendererEntry) {
				return this == o;
			}
			return false;
		}
		
		public Renderable getRenderable() {
			return rnd;
		}
	}
	
	class RemoveEntry{
		private Renderable rnd;
		
		public RemoveEntry(Renderable rnd) {
			this.rnd = rnd;
		}
		
		public Renderable getRenderable() {
			return rnd;
		}
	}

	@Override
	public void manage(Manageable object) {
		if(object instanceof Renderable) {
			Renderable renderable = (Renderable)object;
			addRenderable(renderable, renderable.getPriority());
			super.manage(object);
		}
	}

	@Override
	public void unmanage(Manageable object) {
		if(object instanceof Renderable) {
			removeRenderable((Renderable)object);
			super.unmanage(object);
		}
	}
	
}
