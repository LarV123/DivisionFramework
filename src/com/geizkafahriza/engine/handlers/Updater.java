package com.geizkafahriza.engine.handlers;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.geizkafahriza.engine.interfaces.Manageable;
import com.geizkafahriza.engine.interfaces.Updateable;

public class Updater extends Manager{
	
	private static Updater instance;
	
	public static Updater getInstance() {
		if(instance == null)instance = new Updater();
		return instance;
	}

	private PriorityQueue<UpdaterEntry> updateables = new PriorityQueue<UpdaterEntry>();
	private LinkedBlockingQueue<UpdaterEntry> updateQue = new LinkedBlockingQueue<UpdaterEntry>();
	private LinkedBlockingQueue<RemoveEntry> removeQue = new LinkedBlockingQueue<RemoveEntry>();
	
	@SuppressWarnings("unlikely-arg-type")
	public void update() {
		while(updateQue.size() > 0) {
			UpdaterEntry entry = updateQue.poll();
			updateables.add(entry);
			Manageable manageable = (Manageable)entry.getUpdateable();
			manageable.managedBy(this);
		}
		while(removeQue.size() > 0) {
			RemoveEntry re = removeQue.poll();
			Iterator<UpdaterEntry> iter = updateables.iterator();
			while(iter.hasNext()) {
				UpdaterEntry ue = iter.next();
				if(ue.equals(re.getUpdateable())) {
					iter.remove();
					Manageable manageable = (Manageable)ue.getUpdateable();
					manageable.unmanagedBy(this);
				}
			}
		}
		for(UpdaterEntry upd : updateables) {
			upd.getUpdateable().update();
		}
	}
	
	private void addUpdateable(Updateable upd, int priority) {
		updateQue.add(new UpdaterEntry(upd, priority));
	}
	
	private void removeUpdateable(Updateable upd) {
		removeQue.add(new RemoveEntry(upd));
	}
	
	class UpdaterEntry implements Comparable<UpdaterEntry>{
		private Updateable upd;
		private int priority = 0;
		
		public UpdaterEntry(Updateable upd) {
			this.upd = upd;
		}
		
		public UpdaterEntry(Updateable upd, int priority) {
			this.upd = upd;
			this.priority = priority;
		}
		
		@Override
		public int compareTo(UpdaterEntry o) {
			int diff = priority - o.priority;
			if(diff == 0)return diff;
			return diff/Math.abs(diff);
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof Updateable) {
				return upd == o;
			}else if(o instanceof UpdaterEntry) {
				return this == o;
			}
			return false;
		}
		
		public Updateable getUpdateable() {
			return upd;
		}
	}
	
	class RemoveEntry{
		private Updateable upd;
		
		public RemoveEntry(Updateable upd) {
			this.upd = upd;
		}
		
		public Updateable getUpdateable() {
			return upd;
		}
	}

	@Override
	public void manage(Manageable object) {
		if(object instanceof Updateable) {
			Updateable updateable = (Updateable)object;
			addUpdateable(updateable, updateable.getPriority());
			super.manage(object);
		}
	}

	@Override
	public void unmanage(Manageable object) {
		if(object instanceof Updateable) {
			removeUpdateable((Updateable)object);
			super.unmanage(object);
		}
	}
}
