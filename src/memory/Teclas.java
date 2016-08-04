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

import javax.tv.xlet.XletStateChangeException;

import org.dvb.event.EventManager;
import org.dvb.event.UserEvent;
import org.dvb.event.UserEventListener;
import org.dvb.event.UserEventRepository;
import org.havi.ui.event.HKeyEvent;

public class Teclas implements UserEventListener {
	
	EventManager eventManager;
	UserEventRepository repositoryExclusivo;
	Game g;

	public Teclas(Game juego) {
		eventManager=EventManager.getInstance();
		repositoryExclusivo = new UserEventRepository("dvbExc");
		repositoryExclusivo.addAllColourKeys();
		eventManager.addUserEventListener(this,repositoryExclusivo);
		g = juego;
	}

	public void userEventReceived(UserEvent e) {
		
		if (e.getCode() == HKeyEvent.VK_COLORED_KEY_2) {
			try {
				g.destroyXlet(true);
			} catch (XletStateChangeException e1) {
				e1.printStackTrace();
			}
			
		} else if (e.getCode() == HKeyEvent.VK_COLORED_KEY_1) {
			g.cont.Stop();
			g.configuracion();
		} else if (e.getCode() == HKeyEvent.VK_COLORED_KEY_0) {
			g.cont.Stop();
			g.configuracion();
			g.jugar(Game.tiempo, Game.nivel);
		}
		
	}
	
}
