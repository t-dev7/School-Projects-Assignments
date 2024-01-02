package com.mycompany.a3;

public interface ICollider {
	public boolean collidesWIth(GameObject otherObject);
	public void handleCollision(GameObject otherObject);

}
