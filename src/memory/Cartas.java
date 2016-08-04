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

import java.awt.Image;
import java.io.File;

public class Cartas {

	static File[] imagenes;

	static Image reverso, reverso_foc;

	static String[] cosas;

	public Cartas() {
		File f = new File("Images/cartas");
		cosas = f.list();
		
		imagenes = new File[f.list().length];
		// imagenes = f.listFiles();

		// Obtener el reverso
		reverso = java.awt.Toolkit.getDefaultToolkit().getImage("Images/reverso.jpg");
		reverso_foc = java.awt.Toolkit.getDefaultToolkit().getImage("Images/reverso_selected.jpg");
	}

	static public Image[] getCartas(int numero) {
		Image[] resultado = new Image[numero];
		for (int i = 0; i < imagenes.length; i++) {
			
				resultado[i] = java.awt.Toolkit.getDefaultToolkit().getImage(
						"Images/cartas/" + cosas[i]);
				while (resultado[i].getHeight(null) < 10);
				if (i == numero-1) break;
			
		}
		return resultado;
	}

}
