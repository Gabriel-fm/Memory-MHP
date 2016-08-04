/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  This file is part of MEMORY-MHP											 * 
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

import java.text.NumberFormat;

import org.havi.ui.HText;

public class Contador extends Thread {

	public int minutos, segundos;
	private NumberFormat nf = NumberFormat.getInstance();
	HText ht = null;
	private Game g;
	boolean ejecutado;

	public Contador(Game gam) {
		minutos = 0;
		segundos = 0;
		nf.setMinimumIntegerDigits(2);
		ht = new HText("00:00");
		g = gam;
		
		nf.format(minutos);
		nf.format(segundos);
		dibujaTiempo();
	}

	public void setTiempo(int min) {
		minutos = min;
		segundos = 0;
		dibujaTiempo();
	}

	public void dibujaTiempo() {
		ht.setTextContent(minutos + ":" + segundos,
				HText.ALL_STATES);
	}

	public void run() {
		ejecutado = true;
		while (ejecutado == true) {

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("Error en el contador");
				e.printStackTrace();
			}

			segundos--;
			if (segundos == -1) {
				segundos = 59;
				minutos--;
			}

			if (minutos == -1) {
				segundos = 0;
				minutos = 0;
				g.levels.perdido();
				break;
			}

			ht.setTextContent(minutos + ":" + segundos, HText.ALL_STATES);
		}

	}
	
	public void Stop() {
		ejecutado = false;
	}

}
