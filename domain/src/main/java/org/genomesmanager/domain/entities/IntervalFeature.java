package org.genomesmanager.domain.entities;

public abstract class IntervalFeature {

	public abstract String getStrandness();
	public abstract void setStrandness(String strandness) throws IntervalFeatureException;
	public abstract int getX();
	public abstract void setX(int x) throws IntervalFeatureException;
	public abstract int getY();
	public abstract void setY(int y) throws IntervalFeatureException;

	public void validateStrandness(String strandness) throws IntervalFeatureException {
		if ( strandness.equals("+") || strandness.equals("-") ) {
			return;
		}
		else {
			throw new IntervalFeatureException("Strandness must be '+' or '-' not '" + 
					strandness + "'");
		}
	}
	
	public Boolean isInternal(int position) throws IntervalFeatureException {
		if ( getX() == 0 && getY() == 0) {
			throw new IntervalFeatureException("Start and end are both 0");
		}
		if ( position < this.getX() || position > this.getY() ) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public int length() {
		return this.getY() - this.getX() + 1;
	}

}
