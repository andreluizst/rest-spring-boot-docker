package br.com.restsb.adapter;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerAdapter {

	public static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	
	public static <O, D> D ParseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> ParseListObjects(List<O> origin, Class<D> destination){
		List<D> destinationObjects = new ArrayList<>();
		origin.forEach(o -> destinationObjects.add(mapper.map(o, destination)));
		return destinationObjects;
	}
	
	public static <O, D> Iterable<D> ParseIterableObjects(Iterable<O> origin, Class<D> destination){
		List<D> destinationObjects = new ArrayList<>();
		origin.forEach(o -> destinationObjects.add(mapper.map(o, destination)));
		return destinationObjects;
	}
}
