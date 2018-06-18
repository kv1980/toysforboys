package be.vdab.toysforboys.web;

import java.util.Set;

public interface Error {
	public String getMessage();
	public Set<Long> getIds();
	public void add(long id);
}
