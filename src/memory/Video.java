/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  MEMORY-MHP - The game of Memory for MHP platforms						 *
 *  Copyright (C) 2008  Gabriel Franco (find.the.error@gmail.com)				 *
 *																		 	 *
 *  This program is free software; you can redistribute it and/or modify	 *
 *  it under the terms of the GNU General Public License as published by	 *
 *  the Free Software Foundation; either version 2 of the License, or		 *
 *  (at your option) any later version. See LICENSE document for details. 	 *
 *																	     	 *
 *  This program is distributed in the hope that it will be useful, 		 *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of 		     *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the   		 *
 *  GNU General Public License for more details.							 *
 *																		     *
 *  You should have received a copy of the GNU General Public License along  *
 *  with this program; if not, write to the Free Software Foundation, Inc.,  *
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.			     *
 *             															     *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package memory;

import javax.media.Player;
import javax.tv.media.AWTVideoSize;
import javax.tv.media.AWTVideoSizeControl;
import javax.tv.service.selection.ServiceContextException;
import javax.tv.service.selection.ServiceContextFactory;
import javax.tv.xlet.XletContext;

public class Video {
	
	private static Player p = null;
	protected static XletContext ctx = null;
	
	public static void reducir(int x, int y, int ancho, int alto, XletContext contextoXlet) {
		ctx = contextoXlet;
		ServiceContextFactory scf = ServiceContextFactory.getInstance();
		javax.tv.service.selection.ServiceContext sc = null;
		try {
			sc = scf.getServiceContext(contextoXlet);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ServiceContextException e) {
			e.printStackTrace();
		}

		if (sc != null) {
			javax.tv.service.selection.ServiceContentHandler[] sch = sc
					.getServiceContentHandlers();
			for (int i = 0; i < sch.length; i++) {
				if (sch[i] instanceof Player) {
					p = (Player) sch[i];
					break;
				}
			}
		}
		if (p != null) {
			AWTVideoSizeControl vsc = (AWTVideoSizeControl) p
					.getControl("javax.tv.media.AWTVideoSizeControl");
			if (vsc != null) {
				AWTVideoSize tamano = new AWTVideoSize(new java.awt.Rectangle(
						0, 0, 720, 576), new java.awt.Rectangle(/*365, 20, 330,
						260*/x,y,ancho,alto));
				vsc.setSize(tamano);
			}
		}
	}
	
	public static void cerrar() {
		reducir(0,0,720,576,ctx);
		ctx = null;
		p.close();
	}
}
