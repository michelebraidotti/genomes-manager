package org.genomesmanager.domain.entities;

public abstract class PointFeature {
	public abstract int getPos();
	public abstract void setPos(int pos) throws PointFeatureException;
}
