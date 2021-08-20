package org.huan.myk8s.dto.mapper;

public interface BookMapper<D,E> {
	public E toEntity(D dto);
	public D toDto(E entity);
	
}
