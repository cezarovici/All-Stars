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

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Circle extends Shape
{
	public Circle(){
		super();

	}
	public Circle( int x ,int y,float r )
	{
		super(x,y);
		radius = r;
	}

	public void Draw(Graphics2D g2d) {
		Body b = this.body;
		Circle c = this;

		float rx = (float) StrictMath.cos(body.getOrient()) * radius;
		float ry = (float) StrictMath.sin(body.getOrient()) * radius;

		g2d.setColor(Color.red);
		g2d.draw(new Ellipse2D.Float(b.position.x - c.radius, b.position.y - c.radius, c.radius * 2, c.radius * 2));
		g2d.draw(new Line2D.Float(b.position.x, b.position.y, b.position.x + rx, b.position.y + ry));
	}

	@Override
	public Shape clone()
	{
		return new Circle((int) body.position.x, (int) body.position.y,radius);
	}

	@Override
	public void initialize()
	{
		computeMass( 1.0f );
	}

	@Override
	public void computeMass( float density )
	{
		body.mass = ImpulseMath.PI * radius * radius * density;
		body.invMass = (body.mass != 0.0f) ? 1.0f / body.mass : 0.0f;
		body.inertia = body.mass * radius * radius;
		body.invInertia = (body.inertia != 0.0f) ? 1.0f / body.inertia : 0.0f;
	}

	@Override
	public void setOrient( float radians )
	{
	}

	@Override
	public Type getType()
	{
		return Type.Circle;
	}

}
