/*
    Copyright (c) 2013 Randy Gaul http://RandyGaul.net

    This software is provided 'as-is', without any express or implied
    warranty. In no event will the authors be held liable for any damages
    arising from the use of this software.

    Permission is granted to anyone to use this software for any purpose,
    including commercial applications, and to alter it and redistribute it
    freely, subject to the following restrictions:
      1. The origin of this software must not be misrepresented; you must not
         claim that you wrote the original software. If you use this software
         in a product, an acknowledgment in the product documentation would be
         appreciated but is not required.
      2. Altered source versions must be plainly marked as such, and must not be
         misrepresented as being the original software.
      3. This notice may not be removed or altered from any source distribution.
      
    Port to Java by Philip Diffenderfer http://magnos.org
*/

package PaooGame.ImpulseEngine;

public abstract class Shape
{
	public Shape(int x, int y) {
		body = new Body(this,x,y);
	}

	public enum Type
	{
		Circle, Poly,
	}

	public Body body;
	public float radius;
	public final Mat2 u = new Mat2();
	public Shape() {
		
	}
	public static Shape ShapeBuilder(Shape.Type type){
		if (type == Type.Circle)
			return new Circle();

		return new Polygon();
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public abstract Shape clone();

	public abstract void initialize();

	public abstract void computeMass( float density );

	public abstract void setOrient( float radians );

	public abstract Type getType();
}
